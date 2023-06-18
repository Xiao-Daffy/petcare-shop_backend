package com.psm.controller;

import com.psm.blob.AzureStorageConfigure;
import com.psm.petcare.entity.PetService;
import com.psm.petcare.entity.Store;
import com.psm.petcare.service.PetServiceService;
import com.psm.petcare.service.StoreService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

//@Controller
//@ResponseBody // asynchronous request, it will return JSON format
@RestController // @Controller + @ResponseBody
@RequestMapping("/petservice") // http://localhost/user/
@CrossOrigin // allow cross origin(允许前后端跨域访问)
public class PetServiceController {


    @Resource
    private AzureStorageConfigure azureStorageConfigure;
    @Resource
    private PetServiceService petService;



//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/imageupload")
    public ResultVO fileUpload(@RequestBody MultipartFile[] file) throws IOException {
        if(file ==null){
            return new ResultVO(RespondStatus.NO, "Image", null);
        }else {
            List<String> strings = azureStorageConfigure.uploadFiles(file);


            return new ResultVO(RespondStatus.NO, "Image", strings.get(0));
        }
    }


    // get list of pet service by store id
    @GetMapping("/list/{sid}")
    public ResultVO getPetSerivceList(@PathVariable("sid") String sid){

        return petService.getListPetService(sid);
    }

    // get single pet service
    @GetMapping("/get/{srid}")
    public ResultVO getPetSerivce(@PathVariable("srid") String sid){

        return petService.getPetService(sid);
    }

    // edit
    @PutMapping("/edit/{srid}")
    public ResultVO editPetSerivce(@RequestBody PetService service){

        return petService.editPetService(service);
    }

    // add

    @PostMapping("/add")
    public ResultVO addPetService(@RequestBody PetService service){
        return petService.addPetService(service);
    }

    // delete
    @DeleteMapping("delete/{srid}")
    public ResultVO deleteService(@PathVariable("srid") String sid){
        return petService.deleteService(sid);
    }

    // pet service with store information
    @GetMapping("/all")
    public ResultVO getPetServiceStore(){
        return petService.getPetServiceAndStore();
    }
    @GetMapping("/detail/{pid}")
    public ResultVO getPetServiceStoreDetail(@PathVariable("pid") String pid){
        return petService.getOnePetServiceAndStore(pid);
    }

    @GetMapping("/reserved")
    public ResultVO getReservedPetService(){
        return petService.getReservedPetService();
    }
}
