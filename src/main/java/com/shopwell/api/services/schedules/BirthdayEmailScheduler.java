package com.shopwell.api.services.schedules;

import com.shopwell.api.config.QuartzConfig;
import com.shopwell.api.services.BirthdayEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BirthdayEmailScheduler implements BirthdayEmailService {

    private final Scheduler scheduler;

    private final QuartzConfig quartzConfig;

    @Override
    public void scheduleBirthdayEmailJob() {
        try {
            JobDetail jobDetail = quartzConfig.birthdayEmailJobDetail();
            Trigger trigger = quartzConfig.birthdayEmailJobTrigger();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.info("Failed to schedule birthday email: " +e.getMessage());
        }
    }
}
