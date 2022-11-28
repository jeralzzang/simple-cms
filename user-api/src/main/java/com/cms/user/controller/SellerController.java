package com.cms.user.controller;

import com.cms.user.domain.model.Seller;
import com.cms.user.domain.seller.SellerDto;
import com.cms.user.exception.CustomException;
import com.cms.user.exception.ErrorCode;
import com.cms.user.service.seller.SellerService;
import com.cms.domain.common.UserVo;
import com.cms.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final SellerService sellerService;

    @PostMapping("/getInfo")
    public ResponseEntity<?> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        Seller seller = sellerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(SellerDto.from(seller));
    }

}
