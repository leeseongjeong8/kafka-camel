package com.example.project.processor;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.support.DefaultMessageHistory;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.AgentConfig;

@Component
public class HistoryProcessor implements Processor {

	@Autowired
	private AgentConfig agentConfig;

	@Override
	public void process(Exchange exchange) throws Exception {

		Map<String, Object> historyMap = new ConcurrentHashMap<String, Object>();

		historyMap.put("exchangeId", exchange.getExchangeId());
		historyMap.put("agentId", agentConfig.getId());
		historyMap.put("routeId", exchange.getFromRouteId());
		
		//RecordMetaData 는 ArrayList 의 첫번째 인자임 
		//RecordMetadata내에 timestamp에 시간정보있다!
		
		ArrayList<RecordMetadata> list = (ArrayList)exchange.getIn().getHeaders().get("org.apache.kafka.clients.producer.RecordMetadata");
		Long timestamp = list.get(0).timestamp();
		String sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
		historyMap.put("sendTime", sendTime);

		if (exchange.getException() == null) {
			historyMap.put("success", "true");
		} else {
			historyMap.put("success", exchange.getException());
		}


		exchange.getIn().setBody(historyMap);

	}
}
