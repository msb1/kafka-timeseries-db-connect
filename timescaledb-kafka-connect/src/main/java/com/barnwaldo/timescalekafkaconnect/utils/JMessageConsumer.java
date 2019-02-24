package com.barnwaldo.timescalekafkaconnect.utils;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barnwaldo.timescalekafkaconnect.model.DataRecord;
import com.barnwaldo.timescalekafkaconnect.repository.TimeSeriesRepository;


/**
 * @author barnwaldo
 * @since Feb 19, 2019
 * 
 * DataSet is class to JSON deserialize messages with Kafka Consumer
 * LocalDateTime.now() is set when DataSet object is consumed
 * JPA repository is used to write data records to Timescale DB
 * 
 */
public class JMessageConsumer implements Runnable {

	private final BlockingQueue<DataRecord> queue;
	private final TimeSeriesRepository timeSeriesRepository;
	private static final Logger logger = LoggerFactory.getLogger(JMessageConsumer.class);

	public JMessageConsumer(TimeSeriesRepository timeSeriesRepository, BlockingQueue<DataRecord> queue) {
		this.queue = queue;
		this.timeSeriesRepository = timeSeriesRepository;
	}

	@Override
	public void run() {
		while (true) {
			// remove record from FIFO blocking queue
			DataRecord dataRecord;
			try {
				dataRecord = queue.take();
			} catch (InterruptedException e) {
				logger.error("Kafka message queue exception: " + e.getMessage());
				continue;
			}
			// write to database with time as primary ID
			timeSeriesRepository.save(dataRecord);
		}
	}

}
