package com.psm.controller;

import com.psm.petcare.entity.Ship;
import com.psm.petcare.service.ShipService;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody
@RequestMapping("/ship") // http://localhost/ship/
@CrossOrigin // allow cross origin(允许前后端跨域访问)
public class ShipController {

    @Autowired
    private ShipService shipService;
    @PostMapping("/add")
    public ResultVO addShipNumber(@RequestBody Ship ship){
        return shipService.addShipNumber(ship);
    }
}
