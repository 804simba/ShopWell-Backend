package com.shopwell.api.services.jobs;

import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.services.CustomerService;
import com.shopwell.api.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class BirthdayEmailJob implements Job {
    private final EmailService emailService;

    private final CustomerService customerService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LocalDate currentDate = LocalDate.now();

        List<Customer> customers = customerService
                .findCustomersByBirthDate(currentDate.getMonthValue(), currentDate.getDayOfMonth());

        customers.forEach(customer -> {
            String customerEmail = customer.getEmail();
            String subject = "Happy birthday";
            String text = String.format("Dear %s , \n\nWishing you a very happy birthday from all of us at ShopWell", customer.getFirstName());

            emailService.sendEmail(customerEmail, subject, text);
        });
    }
}
