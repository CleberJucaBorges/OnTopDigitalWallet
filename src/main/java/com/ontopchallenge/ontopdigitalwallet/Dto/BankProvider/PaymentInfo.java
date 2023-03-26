package com.ontopchallenge.ontopdigitalwallet.Dto.BankProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInfo {
    private double amount;
    private UUID id;
    public UUID getId() {
        return id;
    }
}