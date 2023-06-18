package com.psm.petcare.dao;

import com.psm.petcare.entity.Comment;
import com.psm.petcare.entity.CommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDAO {

    // make comment -- user
    public int insertComment(Comment comment);

    // delete comment -- user
    public int deleteCommentById(int commentId);

    //query all comment by product id --  user view
    public List<CommentVO> queryCommentByProductId(int productId);

    //query all comments by user id -- user view
    public List<CommentVO> queryCommentByUserId(int userId);

    //query all comment by store id -- owner view
    public List<CommentVO> queryCommentByStoreId(int storeId);

    // edit the comment content
    public int updateComment(int commentId, String content);

}
