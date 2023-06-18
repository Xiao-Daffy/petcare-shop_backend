package com.psm.petcare.service;

import com.psm.petcare.entity.PetService;
import com.psm.petcare.vo.ResultVO;

import java.awt.image.RescaleOp;

// Pet Service
public interface PetServiceService {

    // get list of pet service
    public ResultVO getListPetService(String storeId);

    // get single pet service
    public ResultVO getPetService(String serviceId);

    // edit pet service
    public ResultVO editPetService(PetService petService);

    // add pet service
    public ResultVO addPetService(PetService petService);

    // delete a service
    public ResultVO deleteService(String serviceId);

    // query list pet service with store information
    public ResultVO getPetServiceAndStore();

    // query one pet service with store information
    public ResultVO getOnePetServiceAndStore(String pid);

    //
    public ResultVO getReservedPetService();

}
