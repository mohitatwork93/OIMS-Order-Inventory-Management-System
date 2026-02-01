package com.drawpicaa.oims.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id){
        super("No Product found with id: "+id);
    }
}
