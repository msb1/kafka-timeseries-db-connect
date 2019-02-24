package com.barnwaldo.influxkafkaconnect.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataRecord {

	// time of data set
	private long currentTime;
	// class result
	private int result;
	// simulated sensors
	private double sensor1;
	private double sensor2;
	private double sensor3;
	private double sensor4;
	private double sensor5;

	// simulated states/categories
	private int cat1;
	private int cat2;
	private int cat3;
	private int cat4;

	@JsonSetter("class")
	public void setResult(int result) {
		this.result = result;
	}
	
	@JsonGetter("result")
	public int setResult() {
		return this.result;
	}
}
