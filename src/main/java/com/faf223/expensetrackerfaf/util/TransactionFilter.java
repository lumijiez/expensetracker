package com.faf223.expensetrackerfaf.util;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
import com.faf223.expensetrackerfaf.service.CredentialService;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionFilter {

    private final CredentialService credentialService;

    public List<? extends IMoneyTransaction> filterByEmail(List<? extends IMoneyTransaction> transactions, String email) {
        Optional<Credential> credential = credentialService.findByEmail(email);
        if(credential.isEmpty())
            throw new UserNotFoundException("The user has not been found");

        return transactions
                .stream()
                .filter(transaction -> credential.get().getUser().equals(transaction.getUser()))
                .toList();
    }

}
