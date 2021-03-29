package com.example.project.schedule;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SchedulerBean {

	@Autowired
	private Scheduler scheduler;

	Logger logger = LoggerFactory.getLogger(SchedulerBean.class);

	//JobDetail 생성메서드
	public JobDetail getJobDetail(Class jobClass, String jobName, String description) {

		JobDetail jobDetail = JobBuilder.newJob(jobClass)
							.withIdentity(jobName)
							.withDescription(description)
							.build();
		
		return jobDetail;
	}
	
	//Trigger 얻어오는 메서드
	public Trigger getTrigger(String triggerName, String cronSchedule) {
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName)
						.withSchedule(
						CronScheduleBuilder.cronSchedule(cronSchedule).withMisfireHandlingInstructionFireAndProceed())
						.build();

		return trigger;
	}
	
	//JobDetail과 Trigger를 넣어주면 key와 state 정보까지 들어있는 map생성해주는 메서드
	public Map<String, Object> getScheduleInform(JobDetail jobDetail, Trigger trigger) throws SchedulerException {


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobKey", jobDetail.getKey());
		map.put("triggerKey", trigger.getKey());
		map.put("triggerState", scheduler.getTriggerState(trigger.getKey()).toString());
		map.put("jobDetail", jobDetail);
		map.put("trigger", trigger);

		return map;

	}

	//job 정보와 trigger 정보를 넣어주면 jobDetail생성 및 trigger 생성 , 정보담긴 map까지 한번에 생성해주는 메서드
	public Map<String, Object> setSchedule(Class jobClass, String jobName, String description, String triggerName, String cronSchedule) 
			throws SchedulerException{
		
	
		return getScheduleInform(getJobDetail(jobClass,jobName, description),getTrigger(triggerName,cronSchedule));
	}
	
	
	// Shceduler 검사하는 메서드
	public void validateSchedule(Map<String, Object> map)
			throws SchedulerException {
		
		JobDetail jobDetail = (JobDetail)map.get("jobDetail");
		Trigger trigger = (Trigger)map.get("trigger");

		if (!scheduler.checkExists((JobKey) map.get("jobKey"))) {
			scheduler.scheduleJob(jobDetail, trigger);
		} else if (map.get("triggerState").equals("ERROR")) {

			Trigger newTrigger = trigger;
			scheduler.rescheduleJob(trigger.getKey(), newTrigger);

			logger.info("triggerB는 ERROR라 리스케줄");
			logger.info("new triggerB의 상태는" + scheduler.getTriggerState(newTrigger.getKey()).toString());
		} else {
			// scheduler.deleteJob(jobKeyB);
			// scheduler.scheduleJob(jobDetailB, triggerB);
			logger.info("triggerB의 현재상태는" + map.get("triggerState"));

		}

		// scheduler.start

	}

}
