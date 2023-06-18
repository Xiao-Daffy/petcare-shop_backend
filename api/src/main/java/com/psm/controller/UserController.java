package com.psm.controller;

import com.psm.blob.AzureStorageConfigure;
import com.psm.petcare.entity.User;
import com.psm.petcare.entity.UserAddress;
import com.psm.petcare.service.UserAddressService;
import com.psm.petcare.service.UserService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@Controller
//@ResponseBody // asynchronous request, it will return JSON format
@RestController // @Controller + @ResponseBody
@RequestMapping("/user") // http://localhost/user/
@CrossOrigin // allow cross origin(允许前后端跨域访问)
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private AzureStorageConfigure azureStorageConfigure;
    @Resource
    private UserAddressService userAddressService;
    /*
        post: add
        get: query
        put: update
        delete: delete
    */


    @PostMapping("/register")
    public ResultVO register(@RequestBody User user){

        ResultVO resultVO = userService.userRegister(user.getEmail(), user.getPassword());// return ResultVO to front end
        return resultVO;
    }

    @GetMapping("/login") // http://localhost/user/login
    //Postman test: http://localhost:8080/user/login?email=daffy@gmail.com&password=d1234
    public ResultVO login(@RequestParam(value = "email") String email,
                          @RequestParam(value = "password") String psw){

        ResultVO resultVO = userService.checkLogin(email, psw);
        return resultVO; // return ResultVO object to front end

    }

    // user reset password by email
    @PutMapping("/reset")
    public ResultVO reset(@RequestBody User user){

        return userService.restPassword(user.getEmail(),user.getPassword());
    }


    // users view their profile
    @GetMapping("/profile/{uid}") //eg. http:localhost:8080/user/profile/1
    public ResultVO viewProfile(@PathVariable("uid") String uid){
        return userService.getProfile(uid);
    }

    // users update their profile
    @PutMapping("/edit/{uid}") //eg. http:localhost:8080/user/edit/1
    public ResultVO editProfile(@RequestBody User user){
        return userService.updateProfile(user.getUserId()+"", user);
    }


    /*
        Testing
    */

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/imageupload")
    public ResultVO testing(@RequestBody MultipartFile file) throws IOException {
        if(file ==null){
            return new ResultVO(RespondStatus.NO, "Image", null);
        }else {
            System.out.println(MultipartFile.class);
            String originalFilename = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = uuid + originalFilename;
            System.out.println(fileName);
            // upload the img to local
            ApplicationHome applicationHome = new ApplicationHome(this.getClass());
            String pre = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static";
            String path = pre + fileName;
            file.transferTo(new File(path));
            System.out.println(path);
            // System.out.println(user);
            return new ResultVO(RespondStatus.NO, "Image", path);
        }
    }
        @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/azureupload")
    public ResultVO photoUpload(@RequestBody MultipartFile[] file) throws IOException {
        if(file ==null){
            return new ResultVO(RespondStatus.NO, "Image", null);
        }else {
            List<String> strings = azureStorageConfigure.uploadFiles(file);


            return new ResultVO(RespondStatus.NO, "Image", strings.get(0));
        }
    }

    @PostMapping("/testing1")
    public ResultVO testingUser(@RequestBody User user)  {

        return new ResultVO(RespondStatus.NO, "Image", user);
    }

    /*
        Testing
    */



    /*
        Here is to fetch user's address
    */


    //get list of address under the particular user
    @GetMapping("/address/{uid}")
    public ResultVO viewAddres(@PathVariable("uid") String uid){
        return userAddressService.getListAddress(uid);
    }

    @DeleteMapping("/address/delete/{aid}")
    public ResultVO deleteAddress(@PathVariable("aid") String aid){
        return userAddressService.deleteAddress(aid);
    }


    @PostMapping("/address/add")
    public ResultVO addAddress(@RequestBody UserAddress userAddress){

        return userAddressService.addAddress(userAddress);
    }

    // get particular address
    @GetMapping("/address/get/{aid}")
    public ResultVO getAddress(@PathVariable("aid") String aid){
        return userAddressService.getAddress(aid);
    }

    // users update their profile
    @PutMapping("/address/edit/{aid}") //eg. http:localhost:8080/user/edit/1
    public ResultVO editAddress(@RequestBody UserAddress userAddress){
        return userAddressService.updateAddressInfo(userAddress);
    }


}
