package com.psm.petcare.service.impl;


import com.psm.petcare.dao.NotificationDAO;
import com.psm.petcare.entity.Notification;
import com.psm.petcare.service.NotificationService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {


    @Autowired
    private NotificationDAO notificationDAO;

    @Override
    public ResultVO addNotification(Notification notification) {
        notification.setNostatus("unread");
        notification.setSendTime(new Date());
        int i = notificationDAO.insertNotification(notification);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "added", null);

        }else{
            return new ResultVO(RespondStatus.NO, "failed", null);
        }
    }

    @Override
    public ResultVO listNotifications(String userId) {
        List<Notification> listNotification = notificationDAO.getListNotification(Integer.parseInt(userId));
        return new ResultVO(RespondStatus.OK, "list of responses", listNotification);
    }

    @Override
    public ResultVO storeGetNotification(String receiveStore) {
        List<Notification> notificationByStore = notificationDAO.getNotificationByStore(Integer.parseInt(receiveStore));
        return new ResultVO(RespondStatus.OK, "list of notification", notificationByStore);
    }

    @Override
    public ResultVO userGetNotification(String receiveUser) {
        List<Notification> notificationByUser = notificationDAO.getNotificationByUser(Integer.parseInt(receiveUser));
        return new ResultVO(RespondStatus.OK, "list of notification", notificationByUser);
    }


}
