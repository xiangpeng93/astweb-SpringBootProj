package com.ast.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * Created by xiangpeng on 2018/7/5.
 */
@Mapper
public interface ActiveInfoMapper {
    @Select("SELECT * FROM users_info")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    List<ActiveInfo> getAllActivesInfo();

    @Select("SELECT * FROM actives_info WHERE activeHead=#{activeHead}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    ActiveInfo getActiveByHead(String activeHead);

    @Select("SELECT * FROM actives_info WHERE id=#{arg0}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    ActiveInfo getActiveById(int id);


    @Select("select * from actives_info ORDER BY id DESC LIMIT #{arg0},#{arg1}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    List<ActiveInfo> getActiveByStartNumAndCount(int activeBegin,int activeCount);

    @Select("SELECT activeUserCount FROM actives_info WHERE activeHead=#{activeHead}")
    @Results({
            @Result(property = "activeUserCount", column = "activeUserCount")
    })
    ActiveInfo getActiveUsercountByHead(String activeHead);

    @Insert("INSERT INTO actives_info(activeHead,activeBody,activeAuthor,activeUserCount,activeBrowersCount) VALUES( #{activeHead}, #{activeBody}, #{activeAuthor},#{activeUserCount}, #{activeBrowersCount})")
    int insert(ActiveInfo user);

    @Update("UPDATE actives_info SET activeHead=#{activeHead},activeBody=#{activeBody},activeAuthor=#{activeAuthor},activeUserCount=#{activeUserCount},activeBrowersCount=#{activeBrowersCount} WHERE id =#{id}")
    int update(ActiveInfo user);

    @Delete("DELETE FROM actives_info WHERE activeHead = #{activeHead}")
    int delete(String activeHead);
}
