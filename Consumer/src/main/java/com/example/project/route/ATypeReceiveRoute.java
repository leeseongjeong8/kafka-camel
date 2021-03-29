package com.example.project.route;


import org.apache.camel.Exchange;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.ErrorHandler;
import org.apache.camel.processor.errorhandler.RedeliveryErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.ConsumerGroupConfig;
import com.example.project.config.TopicConfig;
import com.example.project.processor.KafkaProcessor;

@Component
public class ATypeReceiveRoute extends EndpointRouteBuilder {

	public static final String ROUTE_ID = ATypeReceiveRoute.class.getSimpleName();
	public static final String HISTORY_WRITE_ROUTE = HistoryWriteRoute.class.getSimpleName();
	//public static final String ERROR_SAVE_ROUTE = AErrorSaveRoute.class.getSimpleName();
	public static final String ERROR_SAVE_ROUTE = ErrorSaveRoute.class.getSimpleName();

	
	@Autowired
	private ConsumerGroupConfig consumerGroupConfig;
	
	@Autowired
	private TopicConfig topic;

	@Autowired
	private KafkaProcessor processor;


	@Override
	public void configure() throws Exception {

		errorHandler(deadLetterChannel("direct:"+ERROR_SAVE_ROUTE)
				.maximumRedeliveries(5)
				.maximumRedeliveryDelay(5000)
				.logExhaustedMessageBody(true));

		from(kafka(topic.getaRoute()).groupId(consumerGroupConfig.getaRouteGroupId())).log("consumerA 수신완료")
		.routeId(ROUTE_ID)
		.unmarshal().json(JsonLibrary.Jackson)
		.process(processor)
		.to(sql("INSERT INTO TEST(nickname,phonenum) VALUES(:#nickname, :#phoneNumddd)"))
		.process(e -> {
			e.getIn().setHeader("type", "receive");
		})
		.to(direct(HISTORY_WRITE_ROUTE)).log("A 타입은 history전송완료")
		.log("consumerGroup은" + consumerGroupConfig.getaRouteGroupId());


	}

}
