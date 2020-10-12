package com.rsakin.creditservice.service;

import com.rsakin.creditservice.document.Credit;
import com.rsakin.creditservice.model.CreditStatusRequest;
import com.rsakin.creditservice.model.CreditStatusResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

    Flux<Credit> getAllCustomerCredits();

    Mono<CreditStatusResponse> getCustomerCreditAvailability(Long id, CreditStatusRequest request);

}
