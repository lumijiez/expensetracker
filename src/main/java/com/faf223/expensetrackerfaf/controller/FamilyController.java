package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.dto.FamilyCreationDTO;
import com.faf223.expensetrackerfaf.dto.FamilyDTO;
import com.faf223.expensetrackerfaf.dto.mappers.FamilyMapper;
import com.faf223.expensetrackerfaf.model.Family;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.FamilyService;
import com.faf223.expensetrackerfaf.service.UserService;
import com.faf223.expensetrackerfaf.util.errors.ErrorResponse;
import com.faf223.expensetrackerfaf.util.exceptions.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/families")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final FamilyMapper familyMapper;
    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FamilyDTO>> getAllFamilies() {
        List<FamilyDTO> families = familyService.getFamilies().stream().map(familyMapper::toDto).collect(Collectors.toList());
        if (!families.isEmpty()) return ResponseEntity.ok(families);
        else throw new FamiliesNotFoundException("Families not found");
    }

    @PostMapping()
    public ResponseEntity<Map<String, Long>> createNewFamily(@RequestBody @Valid FamilyCreationDTO familyCreationDTO,
                                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new FamilyNotCreatedException("Could not create new family");

        Family family = familyMapper.toFamily(familyCreationDTO);

        familyService.createOrUpdate(family);
        Map<String, Long> response = new HashMap<>();
        response.put("familyId", family.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateFamily(@PathVariable long id, @RequestBody @Valid FamilyCreationDTO familyDTO,
                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new FamilyNotUpdatedException(ErrorResponse.from(bindingResult).getMessage());

        Family family = familyService.getFamilyById(id);

        if(family == null)
            throw new FamiliesNotFoundException("The family has not been found");

        if(!familyService.containsMember(family))
            throw new NotAMemberOfTheFamily("You are not a member of this family");

        family.setName(familyDTO.getName());

        familyService.createOrUpdate(family);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFamily(@PathVariable long id) {
        familyService.deleteFamilyById(id);
    }

    @PatchMapping("/add-member/{id}")
    public ResponseEntity<Void> addFamilyMember(@PathVariable long id, @RequestParam Optional<String> email) {
        if(email.isEmpty())
            throw new UserNotFoundException("You have not specified the user email");

        Family family = familyService.getFamilyById(id);

        if(family == null)
            throw new FamiliesNotFoundException("The family has not been found");

        if(!familyService.containsMember(family))
            throw new NotAMemberOfTheFamily("You are not a member of this family");

        User user = userService.getUserByEmail(email.get());

        if(user == null)
            throw new UserNotFoundException("User with the specified email has not been found");

        family.getMembers().add(user);

        familyService.createOrUpdate(family);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/remove-member/{id}")
    public ResponseEntity<Void> removeFamilyMember(@PathVariable long id, @RequestParam Optional<String> email) {
        if(email.isEmpty())
            throw new UserNotFoundException("You have not specified the user email");

        Family family = familyService.getFamilyById(id);

        if(family == null)
            throw new FamiliesNotFoundException("The family has not been found");

        if(!familyService.containsMember(family))
            throw new NotAMemberOfTheFamily("You are not a member of this family");

        User user = userService.getUserByEmail(email.get());

        if(user == null)
            throw new UserNotFoundException("User with the specified email has not been found");

        family.getMembers().remove(user);

        familyService.createOrUpdate(family);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
