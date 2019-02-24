package com.barnwaldo.timescalekafkaconnect.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class DataRecord {

	// time of data set
	@Id
	@Column(name="time", columnDefinition="TIMESTAMP WITH TIME ZONE NOT NULL")
	private LocalDateTime time;
	// class result
	@Column
	private int result;
	// simulated sensors
	@Column
	private double sensor1;
	@Column
	private double sensor2;
	@Column
	private double sensor3;
	@Column
	private double sensor4;
	@Column
	private double sensor5;

	// simulated states/categories
	@Column
	private int cat1;
	@Column
	private int cat2;
	@Column
	private int cat3;
	@Column
	private int cat4;
	
	public DataRecord() {
	}

	@JsonSetter("class")
	public void setResult(int result) {
		this.result = result;
	}
	
	@JsonGetter("result")
	public int setResult() {
		return this.result;
	}
}
