package com.psm.controller;

import com.psm.blob.AzureStorageConfigure;
import com.psm.petcare.entity.PetProduct;
import com.psm.petcare.entity.PetService;
import com.psm.petcare.service.PetProductService;
import com.psm.petcare.service.PetServiceService;
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
@RequestMapping("/product") // http://localhost/user/
@CrossOrigin // allow cross origin(允许前后端跨域访问)
public class PetProductController {


    @Resource
    private AzureStorageConfigure azureStorageConfigure;
    @Resource
    private PetProductService productService;



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


    // add
    @PostMapping("/add")
    public ResultVO newProduct(@RequestBody PetProduct product){

        return productService.addProduct(product);
    }

    // get list of pet service by store id
    @GetMapping("/list/{sid}")
    public ResultVO getPetProductList(@PathVariable("sid") String sid){

        return productService.listProduct(sid);
    }
    // get a product -- owner side
    @GetMapping("/get/{pid}")
    public ResultVO getAProduct(@PathVariable("pid") String pid){
        return productService.getProduct(pid);
    }

    // delete
    @DeleteMapping("/delete/{pid}")
    public ResultVO deleteProduct(@PathVariable("pid") String pid){
        return productService.deleteProduct(pid);
    }


    // edit
    @PutMapping("/edit/{pid}")
    public ResultVO editPetProduct(@RequestBody PetProduct product){
        System.out.println(product);
        return  productService.editProduct(product);
    }

    // user -- get all
    @GetMapping("/all")
    public ResultVO getAllProducts(){
        return productService.getAllProduct();
    }

    // user -- get a product
    @GetMapping("/detail/{pid}")
    public ResultVO getroductDetail(@PathVariable("pid") String pid){
        return productService.getProductByProductId(pid);
    }

    // get 3 newest product
    @GetMapping("/latest")
    public ResultVO getNewProduct(){
        return productService.getNewestProduct();
    }

    // get 3 best sale product
    @GetMapping("/best")
    public ResultVO getBestSaleProducts(){
        return productService.getBestSaleProduct();
    }
}
