package com.psm.petcare.service;

import com.psm.petcare.entity.Pet;
import com.psm.petcare.vo.ResultVO;

import java.util.List;

// Pet
public interface PetService {

    // get list of pet
    public ResultVO getListPet(String storeId);

    // get a pet
    public ResultVO getPet(String petId);

    // update a pet
    public ResultVO editPet(Pet pet);

    // delete a pet
    public ResultVO deletePet(String petId);

    // add
    public ResultVO documentPet(String reserveId, Pet pet);

    /**
     * Data Analysis
     */
    public List<Pet> getPetType(String storeId);
}
