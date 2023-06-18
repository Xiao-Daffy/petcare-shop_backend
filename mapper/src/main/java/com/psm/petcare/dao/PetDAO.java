package com.psm.petcare.dao;

import com.psm.petcare.entity.Pet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetDAO {

    public List<Pet> listPets(int storeId);

    // query one pet
    public Pet queryPet(int petId);

    // update a pet
    public int updatePet(Pet pet);
    // delete
    public int deletePet(int petId);

    // add
    public int addPet(Pet pet);


    /**
     * Data Analysis
     */
    public List<Pet> petType(int storeId);

}
