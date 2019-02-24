package com.barnwaldo.timescalekafkaconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barnwaldo.timescalekafkaconnect.model.DataRecord;


/**
 * Simple Spring JpaRepository to access Timescale (Postgres) database 
 *
 * @author barnwaldo
 * @since Feb 19, 2019
 *
 */
public interface TimeSeriesRepository extends JpaRepository<DataRecord, Long> {
	
}
