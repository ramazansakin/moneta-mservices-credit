package com.rsakin.creditservice.service.impl;

import com.rsakin.creditservice.document.Credit;
import com.rsakin.creditservice.model.CreditStatusRequest;
import com.rsakin.creditservice.model.CreditStatusResponse;
import com.rsakin.creditservice.repository.CreditRepository;
import com.rsakin.creditservice.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    private static final int CREDIT_LIMIT_MULTIPLIER = 4;

    @Override
    public Flux<Credit> getAllCustomerCredits() {
        return creditRepository.findAll();
    }

    @Override
    public Mono<CreditStatusResponse> getCustomerCreditAvailability(Long id, CreditStatusRequest request) {

        Mono<Credit> byCustomerId = creditRepository.getByCustomerId(id);

        return byCustomerId
                .flatMap(credit -> Mono.just(CreditStatusResponse.builder().status(true).creditLimit(credit.getCustomerCreditLimit()).build()))
                .defaultIfEmpty(myFallbackMethod(id, request));

    }

    private CreditStatusResponse myFallbackMethod(Long id, CreditStatusRequest request) {
        int customerCreditScore = getCustomerCreditScore(id);
        log.info("Customer " + request.getName() + " " + request.getLastname() + " - credit score : " + customerCreditScore);

        long creditLimit = defineCustomerCreditLimit(customerCreditScore, request);
        if (creditLimit == -1) {
            return CreditStatusResponse
                    .builder()
                    .status(false)
                    .creditLimit(0L)
                    .build();
        }
        // After returning successive creditLimit, we can persist it on mongo
        creditRepository.save(Credit.builder().customerId(id).customerCreditLimit(creditLimit).build());

        return CreditStatusResponse
                .builder()
                .status(true)
                .creditLimit(creditLimit)
                .build();
    }

    private long defineCustomerCreditLimit(int customerCreditScore, CreditStatusRequest customerInfo) {
        if (customerCreditScore >= 500 && customerCreditScore < 1000) {
            if (customerInfo.getSalary() < 5000) {
                return 10000;
            }
        } else if (customerCreditScore >= 1000) {
            return (long) (CREDIT_LIMIT_MULTIPLIER * customerInfo.getSalary());
        }
        return -1;
    }

    private int getCustomerCreditScore(Long id) {
        log.info("Customer with id : " + id + " test score is calculating...");
        Random random = new Random();
        List<Integer> sampleTestScores = Arrays.asList(250, 400, 500, 800, 1250, 1450);
        return sampleTestScores.get(random.nextInt(sampleTestScores.size()));
    }

}
