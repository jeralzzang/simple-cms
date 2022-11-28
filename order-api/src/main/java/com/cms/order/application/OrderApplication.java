package com.cms.order.application;

import com.cms.order.client.UserClient;
import com.cms.order.client.user.ChangeBalanceForm;
import com.cms.order.client.user.CustomerDto;
import com.cms.order.domain.model.ProductItem;
import com.cms.order.domain.redis.Cart;
import com.cms.order.exception.CustomException;
import com.cms.order.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static com.cms.order.exception.ErrorCode.ORDER_FAIL_BALANCE_LACK;
import static com.cms.order.exception.ErrorCode.ORDER_FAIL_CHECK_CART;

@Service
@RequiredArgsConstructor
public class OrderApplication {

    private final CartApplication cartApplication;
    private final UserClient userClient;
    private final ProductItemService productItemService;

    @Transactional
    public void order(String token, Cart cart) {
        Cart orderCart = cartApplication.refreshCart(cart);
        if (orderCart.getMessages().size() > 0) {
            throw new CustomException(ORDER_FAIL_CHECK_CART);
        }
        CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();

        int totalPrice = getTotalPrice(cart);
        if (customerDto.getBalance() < totalPrice) {
            throw new CustomException(ORDER_FAIL_BALANCE_LACK);
        }
        //todo
        //rollback 계획에 대한 생각
        userClient.changeBalance(token, ChangeBalanceForm.builder()
                .from("USER")
                .message("Order")
                .money(-totalPrice)
                .build());

        for (Cart.Product product : orderCart.getProducts()) {
            for (Cart.ProductItem cartItem : product.getItems()) {
                ProductItem productItem = productItemService.getProductItem(cartItem.getId());
                productItem.setCount(productItem.getCount() - cartItem.getCount());
            }
        }
    }

    public Integer getTotalPrice(Cart cart) {

        return cart.getProducts().stream().flatMapToInt(product ->
                product.getItems().stream()
                        .flatMapToInt(productItem ->
                                IntStream.of(productItem.getPrice() * productItem.getCount())))
                .sum();

    }
}
