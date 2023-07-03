package com.shopwell.api.service.implementations;

import com.shopwell.api.service.BirthdayEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BirthdayEmailServiceImpl implements BirthdayEmailService {
    private final Scheduler scheduler;

    private final JobDetail jobDetail;

    private final Trigger trigger;

    @Override
    public void scheduleBirthdayEmailJob() {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.info("Failed to schedule birthday email job");
        }
    }
}
