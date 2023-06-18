package com.psm.petcare.service;


import com.psm.petcare.entity.Comment;
import com.psm.petcare.vo.ResultVO;

public interface CommentService {

    // make comment
    public ResultVO makeComment(Comment comment);

    // get list of comments by product id;
    public ResultVO getListCommentByProductId(String productId);



    // get list comment
    public ResultVO getListCommentByUserId(String userId);
    // get list comment
    public ResultVO getListCommentByStoreId(String storeId);

    // update the comment
    public ResultVO updateComment(String commentId, String content);
}
