package com.cms.user.controller;

import com.cms.user.application.SignUpApplication;
import com.cms.user.domain.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signUp")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpApplication signUpApplication;

    @PostMapping("/customer")
    public ResponseEntity<?> customerSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.customerSignup(form));
    }

    @GetMapping("/customer/verify")
    public ResponseEntity<?> verifyCustomer(String email, String code) {
        signUpApplication.customerVerify(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }

    @PostMapping("/seller")
    public ResponseEntity<?> sellerSignup(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.sellerSignup(form));
    }

    @GetMapping("/seller/verify")
    public ResponseEntity<?> verifySeller(String email, String code) {
        signUpApplication.sellerVerify(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}