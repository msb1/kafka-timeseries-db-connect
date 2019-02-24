package com.barnwaldo.kairoskafkaconnect.model;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SensorNode {
	private String name;
	private long timestamp;
	private String type;
	private double value;
	private Map<String,String> tags;
	
	public SensorNode() {
		type = "double";
		tags = new HashMap<>();
	}
	
}
