package com.shopwell.api.event_driven;

import com.shopwell.api.model.entity.Customer;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegisterListener implements ApplicationListener<RegisterEvent> {
    private final JavaMailSender javaMailSender;
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        String otp = event.getOtp();
        Customer customer = event.getCustomer();
        String email = customer.getCustomerEmail();
        otp_generator(email,otp);
    }
    @SneakyThrows
    private void otp_generator(String customer,String otp){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        messageHelper.setFrom("temple.chiorlu@decagon.dev");
        messageHelper.setSubject("OTP Verification");
        messageHelper.setTo(customer);
        String mailContent = "<div style='width:100%; background:#f8f8f8;' >"
                + "<p style='font-size: 18px;'>Hello, " + customer + "</p>"
                + "<p style='font-size: 16px;'>"+ "Welcome to Shop Well </p>"
                + "<p style='font-size: 16px;'>Thank you for registering with us.</p>"
                + "<p style='font-size: 16px;'>Please enter your OTP below to complete your registration:</p>"
                + "<h1 style='font-size: 24px; margin: 20px 0;'>" + otp + "</h1>"
                + "<p style='font-size: 16px;'>Thank you,</p>"
                + "<p style='font-size: 16px;'> Shop Well </p>"
                + "</div>";
        messageHelper.setText(mailContent,true);
        javaMailSender.send(mimeMessage);
        log.info("OTP HAVE BEEN SENT TO {}" ,customer);

    }
}
