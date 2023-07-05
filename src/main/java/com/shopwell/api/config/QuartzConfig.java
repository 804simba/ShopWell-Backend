package com.shopwell.api.config;

import com.shopwell.api.services.jobs.BirthdayEmailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    @Bean
    public JobDetail birthdayEmailJobDetail() {
        return JobBuilder.newJob(BirthdayEmailJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger birthdayEmailJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(birthdayEmailJobDetail())
                .withIdentity("birthdayEmailJobTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();
    }
}
