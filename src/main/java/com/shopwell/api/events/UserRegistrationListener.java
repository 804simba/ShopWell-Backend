package com.shopwell.api.events;

import com.shopwell.api.model.entity.BaseUser;
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
public class UserRegistrationListener implements ApplicationListener<UserRegistrationEvent> {
    private final JavaMailSender javaMailSender;
    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        String otp = event.getOtp();
        BaseUser user = event.getUser();
        String email = user.getEmail();
        otp_generator(email,otp);
    }
    @SneakyThrows
    private void otp_generator(String user,String otp){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        messageHelper.setFrom("temple.chiorlu@decagon.dev");
        messageHelper.setSubject("OTP Verification");
        messageHelper.setTo(user);
        String mailContent = "<div style='width:100%; background:#f8f8f8;' >"
                + "<p style='font-size: 18px;'>Hello, " + user + "</p>"
                + "<p style='font-size: 16px;'>"+ "Welcome to Shop Well </p>"
                + "<p style='font-size: 16px;'>Thank you for registering with us.</p>"
                + "<p style='font-size: 16px;'>Please enter your OTP below to complete your registration:</p>"
                + "<h1 style='font-size: 24px; margin: 20px 0;'>" + otp + "</h1>"
                + "<p style='font-size: 16px;'>Thank you,</p>"
                + "<p style='font-size: 16px;'> Shop Well </p>"
                + "</div>";
        messageHelper.setText(mailContent,true);
        javaMailSender.send(mimeMessage);
        log.info("OTP HAVE BEEN SENT TO {}", user);
    }
}
