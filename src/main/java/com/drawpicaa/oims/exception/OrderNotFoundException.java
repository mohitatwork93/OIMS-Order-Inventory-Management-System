package com.drawpicaa.oims.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String orderId){
        super("No Order found with id: "+ orderId);
    }
}
