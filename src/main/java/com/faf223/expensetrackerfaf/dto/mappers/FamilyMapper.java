package com.faf223.expensetrackerfaf.dto.mappers;

import com.faf223.expensetrackerfaf.dto.FamilyCreationDTO;
import com.faf223.expensetrackerfaf.dto.FamilyDTO;
import com.faf223.expensetrackerfaf.model.Family;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FamilyMapper {

    private final UserMapper userMapper;

    public FamilyDTO toDto(Family family) {
        return new FamilyDTO(family.getId(), family.getName(), userMapper.toDto(family.getMembers()));
    }

    public Family toFamily(FamilyCreationDTO familyCreationDTO) {
        Family family = new Family();
        family.setName(familyCreationDTO.getName());
        return family;
    }

}
