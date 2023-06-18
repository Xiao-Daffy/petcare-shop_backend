package com.psm.controller;


import com.psm.petcare.entity.Reservation;
import com.psm.petcare.service.ReservationService;
import com.psm.petcare.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController // @Controller + @ResponseBody
@RequestMapping("/reservation") // http://localhost/user/
@CrossOrigin // allow cross origin()
public class ReservationController {

    @Resource
    private ReservationService reservationService;

    @PostMapping("/make")
    public ResultVO makeReservation(@RequestBody Reservation reservation){
        return reservationService.makingReservation(reservation);
    }
    @GetMapping("/list/user/{uid}")
    public ResultVO viewReservationFromUser(@PathVariable("uid") String uid){
        return reservationService.listReservationFromUserView(uid);
    }
    @GetMapping("/list/owner/{sid}")
    public ResultVO viewReservationFromOwner(@PathVariable("sid") String sid){
        return reservationService.listReservationFromOwnerView(sid);
    }

    @PutMapping("/cancel/{rid}")
    public ResultVO cancelReservation(@PathVariable("rid") String rid){

        return reservationService.cancelReservation(rid);
    }
    @PutMapping("/close/{rid}")
    public ResultVO closeReservation(@PathVariable("rid") String rid){

        return reservationService.closeReservation(rid);

    }

    @PutMapping("/approve/{rid}")
    public ResultVO approveReservation(@PathVariable("rid") String rid){

        return reservationService.approveReservation(rid);

    }

    @PutMapping("/reject/{rid}")
    public ResultVO rejectReservation(@PathVariable("rid") String rid){

        return reservationService.rejectReservation(rid);

    }

    @PutMapping("/edit/{rid}")
    public ResultVO editReservation(@RequestParam(value = "total") String total, @PathVariable("rid") String rid){
        double totalAmount = Double.parseDouble(total);
        return reservationService.editTotalAmountStatus(rid,totalAmount);

    }

}
