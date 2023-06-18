package com.psm.controller;


import com.psm.petcare.dao.NotificationDAO;
import com.psm.petcare.entity.Notification;
import com.psm.petcare.service.NotificationService;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/add")
    public ResultVO pushNotification(@RequestBody Notification notification){
        return notificationService.addNotification(notification);
    }

    @GetMapping("/list/store/{sid}")
    public ResultVO storeGetNotification(@PathVariable("sid") String receiveStore){
       return notificationService.storeGetNotification(receiveStore);
    }
    @GetMapping("/list/user/{uid}")
    public ResultVO userGetNotification(@PathVariable("uid") String receiveUser){
        return notificationService.userGetNotification(receiveUser);
    }
}
