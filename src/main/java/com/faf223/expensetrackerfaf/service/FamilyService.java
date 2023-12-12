package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;

}
