package com.rsakin.creditservice.controller;

import com.rsakin.creditservice.document.Credit;
import com.rsakin.creditservice.model.CreditStatusRequest;
import com.rsakin.creditservice.model.CreditStatusResponse;
import com.rsakin.creditservice.service.CreditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/credits")
@RequiredArgsConstructor
@Api(value = "Credit Controller REST Endpoints")
@ApiResponses(
        value = {
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 200, message = "Successful"),
                @ApiResponse(code = 404, message = "Not Found")
        }
)
public class CreditController {

    private final CreditService creditService;

    @ApiOperation(value = "Returns all defined credits wrapped via Flux")
    @GetMapping("/all")
    public Flux<Credit> getAll() {
        return creditService.getAllCustomerCredits();
    }

    @ApiOperation(value = "Checks and define customer's credit status, if possible stores it")
    @PostMapping("/credit-status/{id}")
    public Mono<CreditStatusResponse> getCustomerCreditStatus(@PathVariable Long id, @RequestBody CreditStatusRequest request) {
        return creditService.getCustomerCreditAvailability(id, request);
    }

}
