package com.wsj.server.controller;

import com.wsj.mongo.Movement;
import com.wsj.server.service.CommentService;
import com.wsj.server.service.MovementService;
import com.wsj.vo.MovementsVo;
import com.wsj.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @Autowired
    private CommentService commentService;

    /**
     * 发布动态
     */
    @PostMapping
    public ResponseEntity movements(Movement movement,
                                    MultipartFile[] imageContent) throws IOException {
        movementService.publishMovement(movement, imageContent);
        return ResponseEntity.ok(null);
    }

    /**
     * 查询我的动态
     */
    @GetMapping("/all")
    public ResponseEntity findByUserId(Long userId,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pagesize) {
        PageResult pr = movementService.findByUserId(userId, page, pagesize);
        return ResponseEntity.ok(pr);
    }

    /**
     * 查询好友动态
     */
    @GetMapping
    public ResponseEntity movements(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pagesize) {
        PageResult pr = movementService.findFriendMovements(page, pagesize);
        return ResponseEntity.ok(pr);
    }

    /**
     * 查询推荐动态
     */
    @GetMapping("/recommend")
    public ResponseEntity recommend(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pagesize) {
        PageResult pr = movementService.findRecommendMovements(page,pagesize);
        return  ResponseEntity.ok(pr);
    }

    /**
     * 查询单条动态
     */
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") String movementId) {
        MovementsVo vo = movementService.findById(movementId);
        return ResponseEntity.ok(vo);
    }

    /**
     * 点赞
     */
    @GetMapping("/{id}/like")
    public ResponseEntity like(@PathVariable("id") String movementId){
        Integer likeCount = commentService.likeComment(movementId);
        return ResponseEntity.ok(likeCount);
    }

    /**
     * 取消点赞
     */
    @GetMapping("/{id}/dislike")
    public ResponseEntity dislike(@PathVariable("id") String movementId){
        Integer likeCount = commentService.disLikeComment(movementId);
        return ResponseEntity.ok(likeCount);
    }

    /**
     * 喜欢动态
     */
    @GetMapping("/{id}/love")
    public ResponseEntity love(@PathVariable("id") String movementId) {
        Integer likeCount = commentService.loveComment(movementId);
        return ResponseEntity.ok(likeCount);
    }

    /**
     * 取消动态喜欢
     */
    @GetMapping("/{id}/unlove")
    public ResponseEntity unlove(@PathVariable("id") String movementId) {
        Integer likeCount = commentService.disloveComment(movementId);
        return ResponseEntity.ok(likeCount);
    }



}
