package com.faf223.expensetrackerfaf.util;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
import com.faf223.expensetrackerfaf.service.CredentialService;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionFilter {

    private final CredentialService credentialService;

    public List<? extends IMoneyTransaction> filterByEmail(List<? extends IMoneyTransaction> transactions, String email) {
        return transactions
                .stream()
                .filter(transaction -> {
                    Credential credential = credentialService.findByEmail(email);
                    if(credential == null)
                        throw new UserNotFoundException("The user has not been found");
                    return credential.getUser().equals(transaction.getUser());
                })
                .toList();
    }

}
