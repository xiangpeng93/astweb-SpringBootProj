package com.ast.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xiangpeng on 2018/7/5.
 */
@Mapper
public interface JoinInfoMapper {
    @Select("SELECT * FROM joins_info")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userName", column = "userName"),
            @Result(property = "userAge", column = "userAge"),
            @Result(property = "activeId", column = "activeId"),
            @Result(property = "activeName", column = "activeName"),
            @Result(property = "joinDate", column = "joinDate"),
            @Result(property = "payNum", column = "payNum"),
            @Result(property = "payOrder", column = "payOrder"),
            @Result(property = "otherInfo", column = "otherInfo")
    })
    List<JoinInfo> getAllJoinsInfo();

    @Select("SELECT * FROM joins_info WHERE activeId=#{param1} and activeSession=#{param2}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userName", column = "userName"),
            @Result(property = "userAge", column = "userAge"),
            @Result(property = "activeId", column = "activeId"),
            @Result(property = "activeName", column = "activeName"),
            @Result(property = "activeSession", column = "activeSession"),
            @Result(property = "joinDate", column = "joinDate"),
            @Result(property = "payNum", column = "payNum"),
            @Result(property = "payOrder", column = "payOrder"),
            @Result(property = "otherInfo", column = "otherInfo")
    })
    List<JoinInfo> getJoinsByActiveIdAndSession(int activeId,String activeSession);

    @Select("SELECT * FROM joins_info WHERE activeId=#{param1}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userName", column = "userName"),
            @Result(property = "userAge", column = "userAge"),
            @Result(property = "activeId", column = "activeId"),
            @Result(property = "activeName", column = "activeName"),
            @Result(property = "activeSession", column = "activeSession"),
            @Result(property = "joinDate", column = "joinDate"),
            @Result(property = "payNum", column = "payNum"),
            @Result(property = "payOrder", column = "payOrder"),
            @Result(property = "otherInfo", column = "otherInfo")
    })
    List<JoinInfo> getJoinsByActiveId(int activeId);

    @Select("SELECT * FROM joins_info WHERE userName=#{param1} and activeId=#{param2}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userName", column = "userName"),
            @Result(property = "userPhone", column = "userPhone"),
            @Result(property = "userAge", column = "userAge"),
            @Result(property = "activeId", column = "activeId"),
            @Result(property = "activeName", column = "activeName"),
            @Result(property = "activeSession", column = "activeSession"),
            @Result(property = "joinDate", column = "joinDate"),
            @Result(property = "payNum", column = "payNum"),
            @Result(property = "payOrder", column = "payOrder"),
            @Result(property = "otherInfo", column = "otherInfo")
    })
    List<JoinInfo> checkUserJoinActive(String userName,int activeId);

    @Insert("INSERT INTO joins_info(userName,userPhone,userAge,activeId,activeName,activeSession,otherInfo) VALUES( #{userName}, #{userPhone}, #{userAge}, #{activeId},#{activeName}, #{activeSession}, #{otherInfo})")
    int insert(JoinInfo info);

    @Update("UPDATE joins_info SET payNum=#{payNum},payOrder=#{payOrder} WHERE id =#{id}")
    int update(JoinInfo user);

    @Delete("DELETE FROM joins_info WHERE id = #{param1}")
    int delete(int id);
}
