package com.dago.service.rent_application.enums;

public enum RentApplicationStatusEnum {
    PENDING("Pending"),
    APPROVED("Approved"),
    DECLINED("Declined");

    private String value;

    private RentApplicationStatusEnum(String value) {
        this.value = value;
    }

    public String returnValue() {
        return this.value;
    }
}
