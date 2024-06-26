package com.example.waggle.domain.pet.application;

import com.example.waggle.domain.member.persistence.entity.Gender;
import com.example.waggle.domain.member.persistence.entity.Member;
import com.example.waggle.domain.member.persistence.entity.Role;
import com.example.waggle.domain.pet.persistence.dao.PetRepository;
import com.example.waggle.domain.pet.persistence.entity.Pet;
import com.example.waggle.domain.pet.presentation.dto.PetRequest;
import com.example.waggle.exception.object.handler.MemberHandler;
import com.example.waggle.exception.object.handler.PetHandler;
import com.example.waggle.exception.payload.code.ErrorStatus;
import com.example.waggle.global.service.aws.AwsS3Service;
import com.example.waggle.global.util.MediaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class PetCommandServiceImpl implements PetCommandService {

    private final PetRepository petRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public Long createPet(PetRequest createPetRequest, Member member) {
        Pet pet = buildPet(createPetRequest, member);
        petRepository.save(pet);
        return pet.getId();
    }

    @Override
    public Long updatePet(Long petId,
                          PetRequest updatePetRequest,
                          Member member) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetHandler(ErrorStatus.PET_NOT_FOUND));
        validateIsOwner(member, pet);
        if (MediaUtil.validateRemoveImgInS3(pet.getProfileImgUrl(), updatePetRequest.getPetProfileImg())) {
            awsS3Service.deleteFile(pet.getProfileImgUrl());
        }
        pet.update(updatePetRequest);
        return pet.getId();
    }

    @Override
    public void deletePet(Long petId, Member member) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetHandler(ErrorStatus.PET_NOT_FOUND));
        validateIsOwner(member, pet);
        awsS3Service.deleteFile(pet.getProfileImgUrl());
        petRepository.delete(pet);
    }

    @Override
    public void deletePetByAdmin(Long petId, Member member) {
        if (!member.getRole().equals(Role.ADMIN)) {
            throw new MemberHandler(ErrorStatus.MEMBER_ACCESS_DENIED_BY_AUTHORIZATION);
        }
        petRepository.deleteById(petId);
    }


    private void validateIsOwner(Member member, Pet pet) {
        if (!pet.getMember().equals(member)) {
            throw new PetHandler(ErrorStatus.PET_INFO_CANNOT_EDIT_OTHERS);
        }
    }

    private Pet buildPet(PetRequest createPetRequest, Member member) {
        return Pet.builder()
                .age(createPetRequest.getAge())
                .name(createPetRequest.getName())
                .description(createPetRequest.getDescription())
                .breed(createPetRequest.getBreed())
                .gender(Gender.valueOf(createPetRequest.getGender()))
                .profileImgUrl(createPetRequest.getPetProfileImg())
                .member(member)
                .build();
    }

}
