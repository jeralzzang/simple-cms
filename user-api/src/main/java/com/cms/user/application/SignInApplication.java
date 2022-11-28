package com.cms.user.application;

import com.cms.user.domain.SignInForm;
import com.cms.user.domain.model.Customer;
import com.cms.user.domain.model.Seller;
import com.cms.user.exception.CustomException;
import com.cms.user.service.customer.CustomerService;
import com.cms.user.service.seller.SellerService;
import com.cms.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.cms.user.exception.ErrorCode.LOGIN_CHECK_FAIL;
import static com.cms.domain.common.UserType.CUSTOMER;
import static com.cms.domain.common.UserType.SELLER;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Customer customer = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        // 2. 토큰 발행
        // 3. 토큰을 response함
        return jwtAuthenticationProvider.createToken(customer.getEmail(), customer.getId(), CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form) {
        Seller seller = sellerService.findValidSeller(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        return jwtAuthenticationProvider.createToken(seller.getEmail(), seller.getId(), SELLER);
    }
}
