package com.cms.user.service;

import com.cms.user.client.MailgunClient;
import com.cms.user.client.mailgun.SendMailForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailgunClient mailgunClient;

    @Test
    void sendEmail() {
        //given
        SendMailForm form = SendMailForm.builder()
                .from("zerobase-test@soheeEmail.com")
                .to("jeralzzang@naver.com")
                .subject("test email from zero base")
                .text("test text")
                .build();

        //when
        String response = mailgunClient.sendEmail(form).getBody();

        //then
    }
}