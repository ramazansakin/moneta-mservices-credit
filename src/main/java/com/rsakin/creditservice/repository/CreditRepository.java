package com.rsakin.creditservice.repository;

import com.rsakin.creditservice.document.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {

    Mono<Credit> getByCustomerId(Long id);

}
