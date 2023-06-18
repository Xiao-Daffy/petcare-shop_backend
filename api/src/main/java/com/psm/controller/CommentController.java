package com.psm.controller;


import com.psm.petcare.entity.Comment;
import com.psm.petcare.service.CommentService;
import com.psm.petcare.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/list/{pid}")
    public ResultVO getListCommentByProductId(@PathVariable("pid")String pid){
        return commentService.getListCommentByProductId(pid);
    }

    @PostMapping("/make")
    public ResultVO makeComment(@RequestBody Comment comment,@RequestHeader("token")String token){
        System.out.println(comment);
        return commentService.makeComment(comment);
    }

    @GetMapping("/get/{uid}")
    public ResultVO getListCommentByUserId(@PathVariable("uid")String uid, @RequestHeader("token")String token){
        return commentService.getListCommentByUserId(uid);
    }
    @GetMapping("/all/{sid}")
    public ResultVO getListCommentByStoreId(@PathVariable("sid")String sid, @RequestHeader("token")String token){
        return commentService.getListCommentByStoreId(sid);
    }

    @PutMapping("/edit/{comid}")
    public ResultVO getListCommentByUserId(@PathVariable("comid")String comid, @RequestParam(value = "content") String content, @RequestHeader("token")String token){
        return commentService.updateComment(comid, content);
    }

}
