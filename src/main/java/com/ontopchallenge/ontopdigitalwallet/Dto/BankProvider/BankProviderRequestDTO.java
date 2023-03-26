package com.ontopchallenge.ontopdigitalwallet.Dto.BankProvider;
import com.ontopchallenge.ontopdigitalwallet.Enum.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankProviderRequestDTO {
    private Source source;
    private Destination destination;
    private Double amount;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Source {
        private AccountType type;
        private SourceInformation sourceInformation;
        private Account account;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Destination {
        private String name;
        private Account account;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SourceInformation {
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Account {
        private String accountNumber;
        private String currency;
        private String routingNumber;
    }
}
