package com.example.project.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.AgentConfig;

@Component
public class FinalHistoryProcessor implements Processor{
	
	
	@Autowired
	private AgentConfig agentConfig;

	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		
		//A or B TypeReceiveRoute에서 왔으면 tyep="recievve"로 넣고
		//HistoryReceiveRoute에서 왔으면 type="send"로 넣을 것!
		String type = (String)exchange.getIn().getHeader("type");
		
		if(type.equals("send")) {
			
			//얘는 send에 대한 history 정보로 이미 history정보가 json으로 들어있음 -> 이걸 map으로 바꿔서
				
					Map<String, Object> map = exchange.getIn().getBody(HashMap.class);
					if(map!=null) {
					map.put("type", type);
			
					}else {
						System.out.println("map이 null이다!!");
					
					}
					
			}else if(type.equals("receive")) {
				
				//얘는 아직 history 정보가 아님 a,b TypeRecieveRoute 에서 넘어온 데이터임
				//historyMap 만들어서 history 정보를 넣어주고 그 map을 body에 setting해서 보내준다.
				Map<String, Object> historyMap = new ConcurrentHashMap<String, Object>();
				
				historyMap.put("exchangeId", exchange.getExchangeId());
				historyMap.put("agentId", agentConfig.getId());
				historyMap.put("routeId", exchange.getFromRouteId());
				Calendar time = Calendar.getInstance();
				String sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getTime());
				historyMap.put("sendTime", sendTime);
				historyMap.put("type", type );
				
					if(exchange.getException()==null) {
						historyMap.put("success","true");
					}else {
						historyMap.put("success",exchange.getException());
					}
					
				
				exchange.getIn().setBody(historyMap);
		
				
			}//else if
			
		}//process
		

		
}//Processor 

