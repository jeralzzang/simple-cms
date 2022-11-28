package com.cms.user.service;

import com.cms.user.domain.SignUpForm;
import com.cms.user.service.customer.SignUpCustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;

@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService signUpCustomerService;

    @Test
    void signUp() {
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("sample@gmail.com")
                .password("1")
                .phone("01000000000")
                .build();
        Assert.notNull(signUpCustomerService.signUp(form).getId());
    }
}