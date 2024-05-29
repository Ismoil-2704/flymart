package com.example.flymart.entity.enums;

public enum ServerCode {
    USER_EXITS("User already exists!"),
    USER_NOT_FOUND("User not Found!"),
    USER_CREATED("User created!"),
    USER_UPDATED("User updated!"),
    USER_DELETED("User deleted!"),
    USER_STATUS_UPDATED("User status updated!"),
    ROLE_NOT_FOUND("Role not found!"),

    ROLE_CREATED("Role created!"),
    ROLE_UPDATE("Role updated!"),
    ROLE_DELETE("Role deleted!"),
    ROLE_EXIST("Role already exists!"),

    PRODUCT_CREATE("Product created!"),
    PRODUCT_UPDATE("Product updated!"),
    PRODUCT_EXIST("Product exist"),
    PRODUCT_EXIST_WITH_CODE("Product exist with such like code!"),
    PRODUCT_NOT_FOUND("Product not found!"),
    PRODUCT_DELETE("Product Delete"),

    PLACE_CREATE("Place created!"),
    PLACE_UPDATE("Place updated!"),
    PLACE_DELETE("Place delete!"),
    PLACE_EXIST("Place exist!"),
    PLACE_NOT_FOUND("Place not found!"),

    REGION_CREATE("Region created!"),
    REGION_UPDATE("Region updated!"),
    REGION_DELETE("Region delete!"),
    REGION_EXIST("Region exist!"),
    REGION_NOT_FOUND("Region not found!"),

    OFFER_CREATE("Offer created!"),
    OFFER_NOT_FOUND("Offer not found!"),

    REQUEST_CREATE("Request created!"),
    REQUEST_UPDATE("Request updated!"),
    REQUEST_NOT_FOUND("Request not found"),
    REQUEST_EXIST_CODE("Request with such like code exist!"),

    INVALID_TOKEN("Invalidate Token"),

    TRANSACTION_EXIST("Transaction with such like offer or request exist!"),
    TRANSACTION_CREATE("Transaction created!"),
    TRANSACTION_NOT_FOUND("Transaction not found!");

    public final String message;

    ServerCode(String message) {
        this.message = message;
    }
}
