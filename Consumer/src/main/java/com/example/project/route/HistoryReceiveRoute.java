package com.example.project.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.ConsumerGroupConfig;
import com.example.project.config.TopicConfig;
import com.example.project.processor.FinalHistoryProcessor;

@Component
public class HistoryReceiveRoute extends EndpointRouteBuilder{
	
	public static final String HISTORY_WRITE_ROUTE = HistoryWriteRoute.class.getSimpleName();
	
	@Autowired
	private TopicConfig topic;
	
	@Autowired
	private ConsumerGroupConfig consumerGroupConfig;

	@Override
	public void configure() throws Exception {
		
		
		from(kafka(topic.getHistoryRoute()).groupId(consumerGroupConfig.getHistoryRouteGroupId()))
		.log("kafka에서 history-data를 받아왔다")
		.process(e->{
			e.getIn().setHeader("type","send");
			
		})
		.unmarshal().json(JsonLibrary.Jackson)
		.to(direct(HISTORY_WRITE_ROUTE))
		.log("historyWriteRoute로 전송완료");
		
		
	}
	
	
	
}
