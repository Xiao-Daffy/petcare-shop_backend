package com.psm.petcare.service.impl;


import com.psm.petcare.dao.*;
import com.psm.petcare.entity.*;
import com.psm.petcare.service.CommentService;
import com.psm.petcare.service.ReservationService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private NotificationDAO notificationDAO;
    @Resource
    private OrderItemDAO orderItemDAO;

    @Override
    public ResultVO makeComment(Comment comment) {

        // check it's already comment
        OrderItem orderItem = orderItemDAO.queryOrderItemByItemId(comment.getOrderItemId());
        int isComment = orderItem.getIsComment();
        if(isComment == 1){
            // commented
            return new ResultVO(RespondStatus.NO, "commented", isComment);
        }else{

            comment.setCreateTime(new Date());
            int i = commentDAO.insertComment(comment); // comment
            if(i > 0){
                // update the comment status in order_item
                orderItemDAO.updateOrderItemComment(comment.getOrderItemId(), 1);

                // the order is completed, change the order status to 'Completed' ,
                orderDAO.updateOrderStatusByOrderId2(orderItem.getOrderId(), "Completed");


                // push the notification into the database -- sender: user, receiver: store
                // get the user
                User user = userDAO.queryUserById(comment.getUserId());

                Notification notification = new Notification();
                notification.setTitle("New Comment on the product: "+orderItem.getProductName());
                notification.setContent(comment.getContent());
                notification.setNostatus("unread");
                notification.setSentFrom(comment.getUserId());
                notification.setSendTo(orderItem.getStoreId());
                notification.setSendTime(new Date());

                notificationDAO.insertNotification(notification);


                return new ResultVO(RespondStatus.OK, "commented", null);

            }else{
                return new ResultVO(RespondStatus.NO, "Failed", null);
            }
        }



    }

    @Override
    public ResultVO getListCommentByProductId(String productId) {
        int pid = Integer.parseInt(productId);
        List<CommentVO> comments = commentDAO.queryCommentByProductId(pid);
        return new ResultVO(RespondStatus.OK, "list of comment", comments);
    }

    @Override
    public ResultVO getListCommentByUserId(String userId) {
        List<CommentVO> commentVOS = commentDAO.queryCommentByUserId(Integer.parseInt(userId));
        return new ResultVO(RespondStatus.OK, "list of comment", commentVOS);
    }

    @Override
    public ResultVO getListCommentByStoreId(String storeId) {
        List<CommentVO> commentVOS = commentDAO.queryCommentByStoreId(Integer.parseInt(storeId));
        return new ResultVO(RespondStatus.OK, "list of comment", commentVOS);
    }

    @Override
    public ResultVO updateComment(String commentId, String content) {
        int i = commentDAO.updateComment(Integer.parseInt(commentId), content);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "edited", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed", null);
        }
    }
}
