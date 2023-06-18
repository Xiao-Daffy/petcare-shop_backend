package com.psm.petcare.dao;

import com.psm.petcare.entity.PetService;
import com.psm.petcare.entity.PetServiceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetServiceDAO {

    // get list of pet service
    public List<PetService> listPetService(int storeId);

    // get single pet service
    public PetService getPetService(int serviceId);

    // update a pet service information
    public int updateService(PetService petService);

    // insert a pet service
    public int insertPetService(PetService petService);

    // delete service
    public int deleteService(int serviceId);

    // query list of pet service & store --> front end
    public List<PetServiceVO> queryPetService();

    // query one pet service & store by pet service id
    public PetServiceVO queryAPetServiceByPid(int pid);


}
