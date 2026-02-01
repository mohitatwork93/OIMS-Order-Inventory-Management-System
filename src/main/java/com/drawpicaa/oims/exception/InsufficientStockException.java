package com.drawpicaa.oims.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String name, Integer quantity, Integer quantity1) {
        super("Product: "+name+" | Available Quantity: "+quantity+" | Required Quantity: "+quantity1);
    }
}
