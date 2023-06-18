package com.psm.petcare.service.impl;

import com.psm.petcare.dao.PetServiceDAO;
import com.psm.petcare.dao.ReservationDAO;
import com.psm.petcare.entity.PetService;
import com.psm.petcare.entity.PetServiceVO;
import com.psm.petcare.service.PetServiceService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class PetServiceServiceImpl implements PetServiceService {

    @Autowired
    private PetServiceDAO petServiceDAO;
    @Autowired
    private ReservationDAO reservationDAO;
    @Override
    public ResultVO getListPetService(String storeId) {

        int sid = Integer.parseInt(storeId);
        List<PetService> petServices = petServiceDAO.listPetService(sid);

        return new ResultVO(RespondStatus.OK, "List of pet service", petServices);

    }

    @Override
    public ResultVO getPetService(String serviceId) {
        int sid = Integer.parseInt(serviceId);
        PetService petService = petServiceDAO.getPetService(sid);
        if(petService == null){
            return new ResultVO(RespondStatus.NO, "Not found", null);
        }else{
            return new ResultVO(RespondStatus.OK, "Found", petService);
        }
    }

    @Override
    public ResultVO editPetService(PetService petService) {
        petService.setUpdateTime(new Date());


        int i = petServiceDAO.updateService(petService);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Service edited", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to edit", null);
        }
    }

    @Override
    public ResultVO addPetService(PetService petService) {

        petService.setCreateTime(new Date());
        petService.setUpdateTime(new Date());
        int i = petServiceDAO.insertPetService(petService);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "New service added", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to add ", null);
        }

    }

    @Override
    public ResultVO deleteService(String serviceId) {
        int sid = Integer.parseInt(serviceId);
        int i = petServiceDAO.deleteService(sid);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Service deleted", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to delete", null);
        }
    }

    @Override
    public ResultVO getPetServiceAndStore() {
        List<PetServiceVO> petServiceVOS = petServiceDAO.queryPetService();
        return new ResultVO(RespondStatus.OK, "List of PetServiceVO", petServiceVOS);
    }

    @Override
    public ResultVO getOnePetServiceAndStore(String pid) {
        int psid = Integer.parseInt(pid);
        PetServiceVO petServiceVO = petServiceDAO.queryAPetServiceByPid(psid);
        return new ResultVO(RespondStatus.OK, "List of PetServiceVO", petServiceVO);
    }

    @Override
    public ResultVO getReservedPetService() {
        List<PetServiceVO> petServiceVOS = reservationDAO.selectReservedService();
        return new ResultVO(RespondStatus.OK, "List of PetServiceVO", petServiceVOS);
    }


}
