package com.psm.petcare.service;


import com.psm.petcare.entity.Notification;
import com.psm.petcare.vo.ResultVO;

import java.util.List;

public interface NotificationService {

    public ResultVO addNotification(Notification notification);
    // get list of notification
    public ResultVO listNotifications(String userId);

    public ResultVO storeGetNotification(String receiveStore);
    public ResultVO userGetNotification(String receiveUser);
}
