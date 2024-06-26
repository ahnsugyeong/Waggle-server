package com.example.waggle.domain.pet.presentation.converter;

import com.example.waggle.domain.pet.persistence.entity.Pet;
import com.example.waggle.global.util.MediaUtil;
import com.example.waggle.domain.pet.presentation.dto.PetResponse.PetDetailDto;
import com.example.waggle.domain.pet.presentation.dto.PetResponse.PetSummaryDto;

import java.util.List;
import java.util.stream.Collectors;

public class PetConverter {
    public static PetSummaryDto toPetSummaryDto(Pet pet) {
        return PetSummaryDto.builder()
                .petId(pet.getId())
                .profileImgUrl(MediaUtil.getProfileImg(pet))
                .name(pet.getName())
                .gender(pet.getGender())
                .build();
    }

    public static PetDetailDto toPetDetailDto(Pet pet) {
        return PetDetailDto.builder()
                .petId(pet.getId())
                .breed(pet.getBreed())
                .age(pet.getAge())
                .description(pet.getDescription())
                .gender(pet.getGender())
                .name(pet.getName())
                .profileImgUrl(MediaUtil.getProfileImg(pet))
                .build();
    }

    public static List<PetSummaryDto> toPetSummaryListDto(List<Pet> pets) {
        return pets.stream().map(PetConverter::toPetSummaryDto).collect(Collectors.toList());
    }

    public static List<PetDetailDto> toPetDetailListDto(List<Pet> pets) {
        return pets.stream()
                .map(PetConverter::toPetDetailDto)
                .collect(Collectors.toList());
    }
}