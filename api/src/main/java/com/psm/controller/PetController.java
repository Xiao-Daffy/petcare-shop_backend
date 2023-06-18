package com.psm.controller;

import com.psm.blob.AzureStorageConfigure;
import com.psm.petcare.entity.Pet;
import com.psm.petcare.service.PetService;
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
@RequestMapping("/pet") // http://localhost/user/
@CrossOrigin // allow cross origin(允许前后端跨域访问)
public class PetController {
    @Resource
    private AzureStorageConfigure azureStorageConfigure;
    @Resource
    private PetService petService;

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


    // get list of pets
    @GetMapping("/list/{sid}")
    public ResultVO getListPet(@PathVariable("sid") String storeId){
        return petService.getListPet(storeId);
    }

    @GetMapping("/get/{pid}")
    public ResultVO getPet(@PathVariable("pid") String petId){
        return petService.getPet(petId);
    }

    // edit
    @PutMapping("/edit/{pid}")
    public ResultVO editPet(@RequestBody Pet pet){

        return petService.editPet(pet);
    }


    // delete
    @DeleteMapping("delete/{pid}")
    public ResultVO deletePet(@PathVariable("pid") String pid){
        return petService.deletePet(pid);
    }


    // document
    @PostMapping("/document/{rid}")
    public ResultVO documentPet(@PathVariable("rid") String rid,  @RequestBody Pet pet){
        return petService.documentPet(rid, pet);
    }
}
