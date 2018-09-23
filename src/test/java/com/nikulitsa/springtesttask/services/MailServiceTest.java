package com.nikulitsa.springtesttask.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendTestMail() {
        mailService.send(mailService.buildMessage(
                mailService.from(),
                "Test message",
                "Test text",
                "sa.nikulitsa@gmail.com"
        ));
    }
}
