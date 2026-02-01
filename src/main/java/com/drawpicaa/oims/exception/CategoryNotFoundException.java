package com.drawpicaa.oims.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long categoryId) {
        super("No Category found with id: "+ categoryId);
    }
}
