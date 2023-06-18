package com.psm.petcare.service.impl;

import com.psm.petcare.dao.PetDAO;
import com.psm.petcare.dao.ReservationDAO;
import com.psm.petcare.entity.Pet;
import com.psm.petcare.service.PetService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PetServiceImp implements PetService {

    @Autowired
    private PetDAO petDAO;

    @Autowired
    private ReservationDAO reservationDAO;

    @Override
    public ResultVO getListPet(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<Pet> pets = petDAO.listPets(sid);

        return new ResultVO(RespondStatus.OK, "Get list of pets", pets);
    }

    @Override
    public ResultVO getPet(String petId) {
        int pid = Integer.parseInt(petId);
        Pet pet = petDAO.queryPet(pid);
        if(pet == null){
            return new ResultVO(RespondStatus.NO, "Pet not found", null);
        }else{
            return new ResultVO(RespondStatus.OK, "Pet found", pet);
        }
    }

    @Override
    public ResultVO editPet(Pet pet) {
        int i = petDAO.updatePet(pet);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Service edited", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to edit", null);
        }
    }


    @Override
    public ResultVO deletePet(String petId) {
        int pid = Integer.parseInt(petId);
        int i = petDAO.deletePet(pid);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Pet deleted", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to delete", null);
        }
    }

    @Override
    public ResultVO documentPet(String reserveId,Pet pet) {

        pet.setCreateTime(new Date());
        pet.setUpdateTime(new Date());
        int i = petDAO.addPet(pet);
        if(i > 0){

            // also need to update the reservation with documented value
            int rid = Integer.parseInt(reserveId);
            int i1 = reservationDAO.changeIsDocument(rid);
            if(i1 > 0 ) {
                return new ResultVO(RespondStatus.OK, "Service documented", null);
            }else{
                return new ResultVO(RespondStatus.NO, "Failed to document", null);
            }
        }else{
            return new ResultVO(RespondStatus.NO, "Failed to document", null);
        }
    }

    @Override
    public List<Pet> getPetType(String storeId) {

        int sid = Integer.parseInt(storeId);
        List<Pet> pets = petDAO.petType(sid);

        return pets;
    }
}
