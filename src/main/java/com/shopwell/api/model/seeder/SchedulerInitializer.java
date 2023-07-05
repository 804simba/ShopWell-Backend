package com.shopwell.api.model.seeder;

import com.shopwell.api.services.BirthdayEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerInitializer implements CommandLineRunner {
    private final BirthdayEmailService birthdayEmailService;

    @Override
    public void run(String... args) {
        birthdayEmailService.scheduleBirthdayEmailJob();
    }
}
