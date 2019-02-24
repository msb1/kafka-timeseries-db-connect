package com.barnwaldo.influxkafkaconnect.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barnwaldo.influxkafkaconnect.model.DataRecord;


/**
 * @author barnwaldo
 * @since Feb 22, 2019
 * 
 * DataSet is class to JSON deserialize messages with Kafka using Jackson mapper
 * Deserialized messages are placed in blocking queue to be consumed by this thread
 * The data is manipulated into the InfluxDB writer and sent to the DB
 */
public class JMessageConsumer implements Runnable {

	private final BlockingQueue<DataRecord> queue;
	private final InfluxDB influxDB;
	private static final Logger logger = LoggerFactory.getLogger(JMessageConsumer.class);

	public JMessageConsumer(InfluxDB influxDB, BlockingQueue<DataRecord> queue) {
		this.queue = queue;
		this.influxDB = influxDB;
	}

	@Override
	public void run() {
		while (true) {
			// remove record from FIFO blocking queue
			DataRecord record;
			try {
				record = queue.take();
			} catch (InterruptedException e) {
				logger.error("Kafka message queue exception: " + e.getMessage());
				continue;
			}
			// Use InfluxDB writer to commit to database
			influxDB.write(Point.measurement("endpoint-1A")
				    .time(record.getCurrentTime(), TimeUnit.MILLISECONDS)
				    .addField("sensor1", record.getSensor1())
				    .addField("sensor2", record.getSensor2())
				    .addField("sensor3", record.getSensor3())
				    .addField("sensor4", record.getSensor4())
				    .addField("sensor5", record.getSensor5())
				    .addField("cat1", record.getCat1())
				    .addField("cat2", record.getCat2())
				    .addField("cat3", record.getCat3())
				    .addField("cat4", record.getCat4())
				    .addField("result", record.getResult())
				    .build());
		}
	}

}
