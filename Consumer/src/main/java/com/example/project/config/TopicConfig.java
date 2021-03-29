package com.example.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="eips.command.topic")
public class TopicConfig {

	private String aRoute;
	private String bRoute;
	private String historyRoute;
	private String historyReceiveRoute;
	private String historyWriteRoute;

	
	public String getaRoute() {
		return aRoute;
	}
	public void setaRoute(String aRoute) {
		this.aRoute = aRoute;
	}
	public String getbRoute() {
		return bRoute;
	}
	public void setbRoute(String bRoute) {
		this.bRoute = bRoute;
	}
	public String getHistoryRoute() {
		return historyRoute;
	}
	public void setHistoryRoute(String historyRoute) {
		this.historyRoute = historyRoute;
	}
	public String getHistoryReceiveRoute() {
		return historyReceiveRoute;
	}
	public void setHistoryReceiveRoute(String historyReceiveRoute) {
		this.historyReceiveRoute = historyReceiveRoute;
	}
	public String getHistoryWriteRoute() {
		return historyWriteRoute;
	}
	public void setHistoryWriteRoute(String historyWriteRoute) {
		this.historyWriteRoute = historyWriteRoute;
	}
	
	
	
	
	
	

	
	
}
