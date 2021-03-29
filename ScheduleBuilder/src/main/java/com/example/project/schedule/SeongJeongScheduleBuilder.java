package com.example.project.schedule;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.job.JobA;
import com.example.project.job.JobB;

//쿼츠라이브러리를 사용해서 DB 에 스케줄 정보를 넣어준다 with Scheduler
@Component
public class SeongJeongScheduleBuilder{

	@Autowired
	private Scheduler scheduler;
	//기존의 스프링은 schedulerFactory를 통해 scheduler 를 생성해야하지만 
	//스프링부트는 알아서 생성해준다.
	
	@Autowired
	private SchedulerBean schedulerBean;

	
	Logger logger = LoggerFactory.getLogger(SeongJeongScheduleBuilder.class);
	
	@PostConstruct
	public void executeSchedule() throws SchedulerException{
		
		
		
		Map<String, Object> jobAMap = schedulerBean.setSchedule(JobA.class, "jobAToAsendroute","jobA는 30초 주기로 실행됩니다","TriggerA",  "0/30 * * * * ?");
		Map<String, Object> jobBMap =schedulerBean.setSchedule(JobB.class, "jobBToBSendRoute","jobB는 1분 주기로 실행됩니다","TriggerB",  "0 0/1 * * * ?");
		
		
		schedulerBean.validateSchedule(jobAMap);
		schedulerBean.validateSchedule(jobBMap);
		
			
	}//executeScheduler()
	
	
	
	


}
