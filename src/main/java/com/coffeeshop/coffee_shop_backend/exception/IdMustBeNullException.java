package com.coffeeshop.coffee_shop_backend.exception;

public class IdMustBeNullException extends BadRequestException {
    public IdMustBeNullException(String message) {
        super(message);
    }
}
