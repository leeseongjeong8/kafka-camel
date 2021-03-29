package com.example.project.route;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.spi.DataFormat;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.TopicConfig;
import com.example.project.processor.MapToBodyProcessor;


@Component
public class BTypeSendRoute extends EndpointRouteBuilder {

	public static final String ROUTE_ID = BTypeSendRoute.class.getSimpleName();
	public static final String HISTORY_ROUTE_ID = HistorySendRoute.class.getSimpleName();

	@Autowired
	private MapToBodyProcessor processor;
	
	@Autowired
	private TopicConfig topic;

	@Override
	public void configure() throws Exception {
	
		//from(timer("hihi").repeatCount(1))
		from(direct(ROUTE_ID)).routeId(ROUTE_ID).log("broute")
		.process(processor)
		.marshal().jacksonxml()
		.to(kafka(topic.getbRoute()))
		.log("라우트로 보냄?")
		.to(direct(HISTORY_ROUTE_ID))
		.log("라우트로 보냄");
	}
}
