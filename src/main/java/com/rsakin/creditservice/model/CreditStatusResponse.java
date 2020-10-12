package com.rsakin.creditservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditStatusResponse {
    private Boolean status;
    private Long creditLimit;
}
