package com.rsakin.creditservice.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreditStatusRequest {
    private String tckn;
    private String name;
    private String lastname;
    private Double salary;
    private String phone;
}
