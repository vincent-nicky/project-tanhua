package com.wsj.server.controller;

import com.wsj.domain.UserInfo;
import com.wsj.server.service.MessagesService;
import com.wsj.vo.PageResult;
import com.wsj.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    /**
     * 根据环信ID查询用户详细信息
     */
    @GetMapping("/userinfo")
    public ResponseEntity userInfo(String huanxinId){
        UserInfoVo vo = messagesService.findUserInfoByHuanxin(huanxinId);
        return ResponseEntity.ok(vo);
    }

    /**
     * 添加好友
     */
    @PostMapping("/contacts")
    public ResponseEntity contacts(@RequestBody Map<String,Object> map){
        Long friendId = Long.valueOf(map.get("userId").toString());
        messagesService.contacts(friendId);
        return ResponseEntity.ok(null);
    }

    /**
     * 分页查询联系人列表
     */
    @GetMapping("/contacts")
    public ResponseEntity contacts(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pagesize,
                                   String keyword) {
        PageResult pr = messagesService.findFriends(page,pagesize,keyword);
        return ResponseEntity.ok(pr);
    }
}
