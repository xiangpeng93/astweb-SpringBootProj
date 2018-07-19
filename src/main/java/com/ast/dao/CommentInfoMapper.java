package com.ast.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xiangpeng on 2018/7/5.
 */
@Mapper
public interface CommentInfoMapper {
    @Select("SELECT * FROM comments_info")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "communityId", column = "communityId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "authorName", column = "authorName"),
            @Result(property = "commentBody", column = "commentBody"),
            @Result(property = "commentFavour", column = "commentFavour")
    })
    List<CommentInfo> getAllCommentInfo();

    @Select("SELECT * FROM comments_info WHERE communityId=#{param1}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "communityId", column = "communityId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "authorName", column = "authorName"),
            @Result(property = "commentBody", column = "commentBody"),
            @Result(property = "commentFavour", column = "commentFavour")
    })
    List<CommentInfo> getCommentByCommunityId(int id);

    @Select("select * from comments_info  ORDER BY id DESC LIMIT #{param1},#{param2}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "communityId", column = "communityId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "authorName", column = "authorName"),
            @Result(property = "commentBody", column = "commentBody"),
            @Result(property = "commentFavour", column = "commentFavour")
    })
    List<CommentInfo> getCommentsByStartNumAndCount(int communityBegin, int communityCount);

    @Insert("INSERT INTO comments_info(communityId,authorName,commentBody) VALUES( #{communityId}, #{authorName}, #{commentBody})")
    int insert(CommentInfo info);

    @Update("UPDATE comments_info SET communityId=#{communityId},authorName=#{authorName},commentBody=#{commentBody},commentFavour=#{commentFavour} WHERE id =#{id}")
    int update(CommentInfo info);

    @Delete("DELETE FROM comments_info WHERE id = #{param1}")
    int delete(int id);
}
