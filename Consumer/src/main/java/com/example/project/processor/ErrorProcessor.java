package com.example.project.processor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.stereotype.Component;


@Component
public class ErrorProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", exchange.getIn().getHeader(KafkaConstants.OFFSET));
		map.put("topic", exchange.getIn().getHeader(KafkaConstants.TOPIC));
		
		exchange.getIn().setBody(map);
			
		

	}
}
