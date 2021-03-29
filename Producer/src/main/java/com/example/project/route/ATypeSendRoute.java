package com.example.project.route;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.TopicConfig;
import com.example.project.processor.MapToBodyProcessor;


@Component
public class ATypeSendRoute extends EndpointRouteBuilder{
	
	public static final String ROUTE_ID = ATypeSendRoute.class.getSimpleName();
	public static final String HISTORY_ROUTE_ID = HistorySendRoute.class.getSimpleName();
	
	@Autowired
	private MapToBodyProcessor processor;

	
	@Autowired
	private TopicConfig topic;
	

	@Override
	public void configure() throws Exception {
		
		//from(timer("imaroute").repeatCount(1))
		from(direct(ROUTE_ID)).routeId(ROUTE_ID).log("routeA")
		.process(processor)
		.marshal().json(JsonLibrary.Jackson)
		.log("${body}")
		.to(kafka(topic.getaRoute()))
		.to(direct(HISTORY_ROUTE_ID));
	
	
		
		
	}

	
	
}
