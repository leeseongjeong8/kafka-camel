package com.example.project.job;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.route.BTypeSendRoute;

@Component
public class JobB implements Job {

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext camelContext;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Exchange exchange = new DefaultExchange(camelContext);

		exchange.setProperty("TriggerId", context.getTrigger().getKey().getName());
		exchange.setProperty("TriggerGroup", context.getTrigger().getKey().getGroup());
		exchange.setProperty("JobExecutionContext", context);
		exchange.setProperty("JobData", context.getMergedJobDataMap());
		exchange.setProperty("BaseDate", context.getMergedJobDataMap().get("BaseDate"));
		System.out.println("jobB가 실행되고 있습니다");
		exchange = template.send("direct:" + BTypeSendRoute.ROUTE_ID, exchange);
	
	}
	
	
}
