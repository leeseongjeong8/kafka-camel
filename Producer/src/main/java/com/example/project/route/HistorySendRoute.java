package com.example.project.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.TopicConfig;
import com.example.project.processor.HistoryProcessor;

@Component
public class HistorySendRoute extends EndpointRouteBuilder {
	
	public static final String HISTORY_ROUTE_ID = HistorySendRoute.class.getSimpleName();
	
	@Autowired
	private HistoryProcessor historyProcessor;
	
	@Autowired
	private TopicConfig topic;
	
	@Override
	public void configure() throws Exception {
	
		
		//from(timer("history").repeatCount(1))
		from(direct(HISTORY_ROUTE_ID)).routeId(HISTORY_ROUTE_ID)
		.process(historyProcessor)
		.marshal().json(JsonLibrary.Jackson)
		.to(kafka(topic.getHistoryRoute()));
		
		
	}
}
