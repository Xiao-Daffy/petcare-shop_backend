package com.psm.petcare.dao;

import com.psm.petcare.entity.Notification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDAO {

    // insert notification
    public int insertNotification(Notification notification);

    // pet owner view the list of notification -- pet owner is receiver
    // pet owner view the list of notification  -- pet shop owner is receiver
    public List<Notification> getListNotification(int userId);



    // update the status
    public int updateStatus(int userId);

    public List<Notification> getNotificationByStore(int receiveStore);
    public List<Notification> getNotificationByUser(int receiveUser);

}

