package com.wsj.mongo.utils;

import com.wsj.mongo.Friend;
import com.wsj.mongo.MovementTimeLine;
import org.bouncycastle.math.ec.rfc7748.X448;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeLineService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Async
    public void saveTimeLine(Long userId, ObjectId movementId){
        //2、查询当前用户的好友数据
        Criteria criteria = Criteria.where("userId").is(userId);
        Query query = Query.query(criteria);
        List<Friend> friends = mongoTemplate.find(query, Friend.class);
        //3、循环好友数据，构建时间线数据存入数据库
        for (Friend friend : friends) {
            MovementTimeLine timeLine = new MovementTimeLine();
            timeLine.setMovementId(movementId);
            timeLine.setUserId(friend.getUserId());
            timeLine.setFriendId(friend.getFriendId());
            timeLine.setCreated(System.currentTimeMillis());
            mongoTemplate.save(timeLine);
        }
    }
}
