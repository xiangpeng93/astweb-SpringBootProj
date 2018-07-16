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
            @Result(property = "activeCount", column = "activeCount"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activeTypeName", column = "activeTypeName"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    List<ActiveInfo> getAllActivesInfo();

    @Select("SELECT * FROM actives_info WHERE activeHead=#{activeHead}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "activeCount", column = "activeCount"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activeTypeName", column = "activeTypeName"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    ActiveInfo getActiveByHead(String activeHead);

    @Select("SELECT * FROM actives_info WHERE id=#{param1}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "activeCount", column = "activeCount"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activeTypeName", column = "activeTypeName"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    ActiveInfo getActiveById(int id);

    @Select("SELECT * FROM actives_info WHERE activeHead=#{param1}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "activeCount", column = "activeCount"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activeTypeName", column = "activeTypeName"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    ActiveInfo getActiveByActiveName(String name);


    @Select("select * from actives_info where activeTypeName=#{param3} ORDER BY id DESC LIMIT #{param1},#{param2}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "activeCount", column = "activeCount"),
            @Result(property = "activeHead", column = "activeHead"),
            @Result(property = "activeBody", column = "activeBody"),
            @Result(property = "activeAuthor", column = "activeAuthor"),
            @Result(property = "activeUserCount", column = "activeUserCount"),
            @Result(property = "activeBrowersCount", column = "activeBrowersCount"),
            @Result(property = "activeTypeName", column = "activeTypeName"),
            @Result(property = "activePublishDate", column = "activePublishDate")
    })
    List<ActiveInfo> getActiveByStartNumAndCount(int activeBegin,int activeCount,String activeTypeName);

    @Select("SELECT activeUserCount FROM actives_info WHERE id=#{param1}")
    @Results({
            @Result(property = "activeUserCount", column = "activeUserCount")
    })
    ActiveInfo getActiveUsercountById(int id);

    @Insert("INSERT INTO actives_info(activeHead,activeCount,activeBody,activeAuthor,activeUserCount,activeTypeName) VALUES( #{activeHead}, #{activeCount}, #{activeBody}, #{activeAuthor},#{activeUserCount},#{activeTypeName})")
    int insert(ActiveInfo user);

    @Update("UPDATE actives_info SET activeHead=#{activeHead},activeCount=#{activeCount},activeBody=#{activeBody},activeAuthor=#{activeAuthor},activeUserCount=#{activeUserCount},activeTypeName=#{activeTypeName} WHERE id =#{id} or activeHead =#{activeHead} ")
    int update(ActiveInfo user);

    @Delete("DELETE FROM actives_info WHERE id = #{param1}")
    int delete(int id);
}
