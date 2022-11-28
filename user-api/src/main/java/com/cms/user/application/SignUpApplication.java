package com.cms.user.application;

import com.cms.user.client.MailgunClient;
import com.cms.user.client.mailgun.SendMailForm;
import com.cms.user.domain.SignUpForm;
import com.cms.user.domain.model.Customer;
import com.cms.user.domain.model.Seller;
import com.cms.user.exception.CustomException;
import com.cms.user.exception.ErrorCode;
import com.cms.user.service.customer.SignUpCustomerService;
import com.cms.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SellerService sellerService;

    public String customerSignup(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
        }

        Customer customer = signUpCustomerService.signUp(form);
        String code = getRandomCode();
        mailgunClient.sendEmail(SendMailForm.builder()
                .from("test@test.com")
                .to(form.getEmail())
                .subject("Verification Email!")
                .text(getVerificationEmailBody(customer.getEmail(), customer.getName(), "customer", code))
                .build());

        signUpCustomerService.changeCustomerValidateEmail(customer.getId(), code);
        return "회원 가입을 완료하였습니다.";
    }

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String sellerSignup(SignUpForm form) {
        if (sellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_USER);
        }

        Seller seller = sellerService.signUp(form);
        String code = getRandomCode();
        /*mailgunClient.sendEmail(SendMailForm.builder()
                .from("test@test.com")
                .to(form.getEmail())
                .subject("Verification Email!")
                .text(getVerificationEmailBody(seller.getEmail(), seller.getName(), "seller", code))
                .build());*/

        sellerService.changeSellerValidateEmail(seller.getId(), code);
        return "회원 가입을 완료하였습니다.";
    }

    public void sellerVerify(String email, String code) {
        sellerService.verifyEmail(email, code);
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello").append(name).append("! Please Click Link for verification. \n\n")
                .append("http://localhost:8080/signup/"+type+"/verify?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }

}
