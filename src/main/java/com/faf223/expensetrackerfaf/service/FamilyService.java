package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.Family;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.FamilyRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotAuthenticatedException;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;

    public List<Family> getFamilies() {
        return familyRepository.findAll();
    }

    public void createOrUpdate(Family family) {
        familyRepository.save(family);
    }

    public Family getFamilyById(long id) {
        return familyRepository.findById(id).orElse(null);
    }

    public boolean containsMember(Family family) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            if(authentication.getAuthorities().stream().noneMatch(authority -> authority.getAuthority().equals("ADMIN"))) {

                Optional<Credential> credential = credentialRepository.findByEmail(userDetails.getUsername());
                if(credential.isEmpty()) throw new UserNotFoundException("The user has not been found");
                Optional<User> user = userRepository.findById(credential.get().getUser().getUserUuid());
                if(user.isEmpty()) throw new UserNotFoundException("The user has not been found");

                return user.get().getFamily().equals(family);

            }

            return true;

        }

        throw new UserNotAuthenticatedException("You are not authenticated");
    }

    public void deleteFamilyById(long id) {
        familyRepository.deleteById(id);
    }

}
