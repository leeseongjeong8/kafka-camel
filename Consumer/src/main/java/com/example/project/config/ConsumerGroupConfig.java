package com.example.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("eips.consumer")
public class ConsumerGroupConfig {
	private String aRouteGroupId;
	private String bRouteGroupId;
	private String historyRouteGroupId;
	
	public String getaRouteGroupId() {
		return aRouteGroupId;
	}
	public void setaRouteGroupId(String aRouteGroupId) {
		this.aRouteGroupId = aRouteGroupId;
	}
	public String getbRouteGroupId() {
		return bRouteGroupId;
	}
	public void setbRouteGroupId(String bRouteGroupId) {
		this.bRouteGroupId = bRouteGroupId;
	}
	public String getHistoryRouteGroupId() {
		return historyRouteGroupId;
	}
	public void setHistoryRouteGroupId(String historyRouteGroupId) {
		this.historyRouteGroupId = historyRouteGroupId;
	}
	
	
	
	
}
