package com.rsakin.creditservice.document;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credit")
@Builder
@ToString
@Data
@EqualsAndHashCode
public class Credit {

    @Id
    private String id;
    private Long customerId;
    private Long customerCreditLimit;

}
