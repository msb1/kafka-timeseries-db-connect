package com.barnwaldo.kairoskafkaconnect;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

import com.barnwaldo.kairoskafkaconnect.model.DataRecord;
import com.barnwaldo.kairoskafkaconnect.utils.JMessageConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author barnwaldo
 * @since Feb 19, 2019
 * 
 * See JMessageConsumer for implementation details
 */
@SpringBootApplication
public class KairosKafkaConnectApplication {

	private BlockingQueue<DataRecord> queue;
	private ObjectMapper mapper;
	private final String kairosUrl = "http://192.168.5.4:8080/api/v1/datapoints";
	private final String topic = "sim-test";
    private static final Logger logger = LoggerFactory.getLogger(KairosKafkaConnectApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(KairosKafkaConnectApplication.class, args);
	}
	
    @KafkaListener(topics = topic, groupId = "barnwaldo")
    public void listen(ConsumerRecord<?, ?> consumerRecord) {
    	
    	// set current time when new QueueRecord is instantiated
    	DataRecord record;
		try {
			record = mapper.readValue((String) consumerRecord.value(), DataRecord.class);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return;
		}
    	record.setCurrentTime(System.currentTimeMillis());
    	String msg = "Received Message: " + record.toString();
        System.out.println(msg);
        // logger.info(msg);
        try {
            queue.put(record);
        } catch (InterruptedException e) {
            logger.error("Put message to queue failure - InterruptedException exerted...");
        }
    }

    @Bean
    ApplicationRunner init() {
        // return main class args & use Application Runner to start Consumer Thread
    	// Can add additional consumer threads with Thread pool or ExecutorService using same HttpClient
        return args -> {
            queue = new ArrayBlockingQueue<>(1024);
            mapper = new ObjectMapper();
            JMessageConsumer consumerThread = new JMessageConsumer(queue, kairosUrl);
            Thread consumer = new Thread(consumerThread);
            consumer.start();
        };
    }

}
