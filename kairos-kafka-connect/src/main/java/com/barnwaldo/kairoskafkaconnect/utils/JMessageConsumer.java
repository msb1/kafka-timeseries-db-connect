package com.barnwaldo.kairoskafkaconnect.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.barnwaldo.kairoskafkaconnect.model.CatNode;
import com.barnwaldo.kairoskafkaconnect.model.DataRecord;
import com.barnwaldo.kairoskafkaconnect.model.SensorNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author barnwaldo
 * @since Feb 19, 2019
 * 
 *        DataSet is class to JSON deserialize messages with Kafka using Jackson mapper
 *        Deserialized messages are placed in blocking queue to be consumed by this thread
 *        The data is manipulated back to JSON format required by KairosDB REST interface
 */
public class JMessageConsumer implements Runnable {

	private final BlockingQueue<DataRecord> queue;
	private final RestTemplate client;
	private final String kairosUrl;
	private Map<String, String> tags;
	private static final Logger logger = LoggerFactory.getLogger(JMessageConsumer.class);

	public JMessageConsumer(BlockingQueue<DataRecord> queue, String kairosUrl) {
		this.kairosUrl = kairosUrl;
		this.queue = queue;
		this.client = new RestTemplate();
		this.tags = new HashMap<>();
	}

	@Override
	public void run() {
		tags.put("endpoint", "1A");
		tags.put("alias", "rainbow");
		ObjectMapper mapper = new ObjectMapper();
		
		while (true) {
			// remove record from FIFO blocking queue
			DataRecord record;
			try {
				record = queue.take();
			} catch (InterruptedException e) {
				logger.error("Kafka message queue exception: " + e.getMessage());
				continue;
			}
			// Map data to Node classes
			SensorNode s1 = new SensorNode("sensor1", record.getCurrentTime(), "double", record.getSensor1(), tags);
			SensorNode s2 = new SensorNode("sensor2", record.getCurrentTime(), "double", record.getSensor2(), tags);
			SensorNode s3 = new SensorNode("sensor3", record.getCurrentTime(), "double", record.getSensor3(), tags);
			SensorNode s4 = new SensorNode("sensor4", record.getCurrentTime(), "double", record.getSensor4(), tags);
			SensorNode s5 = new SensorNode("sensor5", record.getCurrentTime(), "double", record.getSensor5(), tags);
			
			CatNode c1 = new CatNode("cat1", record.getCurrentTime(), "long", record.getCat1(), tags);
			CatNode c2 = new CatNode("cat2", record.getCurrentTime(), "long", record.getCat2(), tags);
			CatNode c3 = new CatNode("cat3", record.getCurrentTime(), "long", record.getCat3(), tags);
			CatNode c4 = new CatNode("cat4", record.getCurrentTime(), "long", record.getCat4(), tags);
			CatNode result = new CatNode("result", record.getCurrentTime(), "long", record.getResult(), tags);
			
			StringBuilder sb = new StringBuilder("[");
			try {
				sb.append(mapper.writeValueAsString(s1)).append(",");
				sb.append(mapper.writeValueAsString(s2)).append(",");
				sb.append(mapper.writeValueAsString(s3)).append(",");
				sb.append(mapper.writeValueAsString(s4)).append(",");
				sb.append(mapper.writeValueAsString(s5)).append(",");
				sb.append(mapper.writeValueAsString(c1)).append(",");
				sb.append(mapper.writeValueAsString(c2)).append(",");
				sb.append(mapper.writeValueAsString(c3)).append(",");
				sb.append(mapper.writeValueAsString(c4)).append(",");
				sb.append(mapper.writeValueAsString(result));
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
			}
			sb.append("]");
			
			// System.out.println(sb.toString());
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(sb.toString(), headers);
			ResponseEntity<String> response = client.postForEntity(kairosUrl, entity, String.class);
			System.out.println("Response result: " + response.getStatusCode());
		}
	}

}
