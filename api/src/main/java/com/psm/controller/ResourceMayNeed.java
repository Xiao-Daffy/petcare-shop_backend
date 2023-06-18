package com.psm.controller;

import com.psm.petcare.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/example")
public class ResourceMayNeed {

    // delete user by id



    @DeleteMapping("/{id}")  // http://localhost:8080/example/1  id =1
//    @RequestMapping("/delete/{uid}")  // http://localhost:8080/example/delete/2  id =2
    public ResultVO deleteUser(@PathVariable("uid") int id){
        return new ResultVO(1000, "Successfully Deleted", null);
    }

//    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @PutMapping("/{id}")
    public ResultVO updateUser(){return null;}

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultVO listUser(){return null;}

    // http://localhost:8080/example/2  id =2
    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
    public ResultVO getUser(int userId){
        return null;
    }

    @PostMapping("/add")
    public ResultVO addUser(){return  null;}

}
