package com.ast.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xiangpeng on 2018/7/5.
 */
@Mapper
public interface CommunityInfoMapper {
    @Select("SELECT * FROM community_info")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "communityHead", column = "communityHead"),
            @Result(property = "communityBody", column = "communityBody"),
            @Result(property = "communityAuthor", column = "communityAuthor"),
            @Result(property = "communityBrowersCount", column = "communityBrowersCount"),
            @Result(property = "communityGroup", column = "communityGroup"),
            @Result(property = "communityPrivilege", column = "communityPrivilege"),
            @Result(property = "communityPublishDate", column = "communityPublishDate")
    })
    List<CommunityInfo> getAllCommunityInfo();

    @Select("SELECT * FROM community_info WHERE id=#{param1}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "communityHead", column = "communityHead"),
            @Result(property = "communityBody", column = "communityBody"),
            @Result(property = "communityAuthor", column = "communityAuthor"),
            @Result(property = "communityBrowersCount", column = "communityBrowersCount"),
            @Result(property = "communityGroup", column = "communityGroup"),
            @Result(property = "communityPrivilege", column = "communityPrivilege"),
            @Result(property = "communityPublishDate", column = "communityPublishDate")
    })
    CommunityInfo getCommunityById(int id);

    @Select("SELECT * FROM community_info WHERE communityHead=#{param1}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "communityHead", column = "communityHead"),
            @Result(property = "communityBody", column = "communityBody"),
            @Result(property = "communityAuthor", column = "communityAuthor"),
            @Result(property = "communityBrowersCount", column = "communityBrowersCount"),
            @Result(property = "communityGroup", column = "communityGroup"),
            @Result(property = "communityPrivilege", column = "communityPrivilege"),
            @Result(property = "communityPublishDate", column = "communityPublishDate")
    })
    CommunityInfo getCommunityByName(String name);


    @Select("select * from community_info  ORDER BY id DESC LIMIT #{param1},#{param2}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "communityHead", column = "communityHead"),
            @Result(property = "communityBody", column = "communityBody"),
            @Result(property = "communityAuthor", column = "communityAuthor"),
            @Result(property = "communityBrowersCount", column = "communityBrowersCount"),
            @Result(property = "communityGroup", column = "communityGroup"),
            @Result(property = "communityPrivilege", column = "communityPrivilege"),
            @Result(property = "communityPublishDate", column = "communityPublishDate")
    })
    List<CommunityInfo> getCommunityByStartNumAndCount(int communityBegin, int communityCount);

    @Insert("INSERT INTO community_info(communityHead,communityBody,communityAuthor,communityGroup,communityPrivilege) VALUES( #{communityHead}, #{communityBody}, #{communityAuthor}, #{communityGroup},#{communityPrivilege})")
    int insert(CommunityInfo info);

    @Update("UPDATE community_info SET communityHead=#{communityHead},communityBody=#{communityBody},communityAuthor=#{communityAuthor},communityGroup=#{communityGroup},communityPrivilege=#{communityPrivilege} WHERE id =#{id}")
    int update(CommunityInfo info);

    @Delete("DELETE FROM community_info WHERE id = #{param1}")
    int delete(int id);
}
