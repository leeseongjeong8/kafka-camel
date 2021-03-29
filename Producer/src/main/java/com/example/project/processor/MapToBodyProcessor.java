package com.example.project.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MapToBodyProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {


		String phoneNum = "010";
		String nickname = "";
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			// 8자리 전화번호
			phoneNum += random.nextInt(9);
			if(i<6) {
				nickname += String.valueOf((char) ((random.nextInt(26)) + 97));
				
			}
		}



		Map<String, Object> properties = new HashMap();
		properties.put("nickname", nickname);
		properties.put("phoneNum", phoneNum);
		exchange.getIn().setBody(properties);

	

	}
}
