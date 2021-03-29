package com.example.project.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Map<String, Object> map = exchange.getIn().getBody(HashMap.class);
		
		String phoneNum = (String)map.get("phoneNum");
		String nickname = (String)map.get("name");

	
		exchange.getIn().setHeader("phoneNum",phoneNum);
		exchange.getIn().setHeader("nickname",nickname);
		

		
		
		
	
		

	}
}
