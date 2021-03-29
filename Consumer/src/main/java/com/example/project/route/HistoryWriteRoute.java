package com.example.project.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.processor.FinalHistoryProcessor;

@Component
public class HistoryWriteRoute extends EndpointRouteBuilder{

	public static final String HISTORY_WRITE_ROUTE = HistoryWriteRoute.class.getSimpleName();
	
	@Autowired
	private FinalHistoryProcessor historyProcessor;
	
	@Override
	public void configure() throws Exception {
		
		from(direct(HISTORY_WRITE_ROUTE)).routeId(HISTORY_WRITE_ROUTE)
		.process(historyProcessor)
		.log("완료!").to(sql("INSERT INTO HISTORY_TABLE(process_type, exchange_id, agent_id, route_id, send_time, success) "
				+ "VALUES(:#type,:#exchangeId,:#agentId,:#routeId,:#sendTime,:#success)")).log("db저장완료");
		

		
	}
	
}
