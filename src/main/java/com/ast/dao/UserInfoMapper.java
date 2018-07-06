package com.ast.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xiangpeng on 2018/7/4.
 */
@Mapper
public interface UserInfoMapper {
    @Select("SELECT * FROM users_info")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "userName",  column = "userName"),
            @Result(property = "passWord",  column = "passWord"),
            @Result(property = "userSex",  column = "userSex"),
            @Result(property = "userAge",  column = "userAge"),
            @Result(property = "userRegisterDate",  column = "userRegisterDate"),
            @Result(property = "userHomeRegion",  column = "userHomeRegion"),
            @Result(property = "otherInfo", column = "otherInfo"),
            @Result(property = "emailAddr", column = "emailAddr"),
            @Result(property = "userPhoneNum", column = "userPhoneNum"),
            @Result(property = "userPrivilege", column = "userPrivilege")
    })
    List<UserInfo> getAllUsersInfo();

    @Select("SELECT * FROM users_info WHERE userName = #{arg0}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "userName",  column = "userName"),
            @Result(property = "passWord",  column = "passWord"),
            @Result(property = "userSex",  column = "userSex"),
            @Result(property = "userAge",  column = "userAge"),
            @Result(property = "userRegisterDate",  column = "userRegisterDate"),
            @Result(property = "userHomeRegion",  column = "userHomeRegion"),
            @Result(property = "otherInfo", column = "otherInfo"),
            @Result(property = "emailAddr", column = "emailAddr"),
            @Result(property = "userPhoneNum", column = "userPhoneNum"),
            @Result(property = "userPrivilege", column = "userPrivilege")
    })
    UserInfo getUserByName(String userName);

    @Insert("INSERT INTO users_info(userName,passWord,userSex,userAge,userHomeRegion,otherInfo,emailAddr,userPhoneNum,userPrivilege) VALUES( #{userName}, #{passWord}, #{userSex},#{userAge}, #{userHomeRegion}, #{otherInfo},#{emailAddr}, #{userPhoneNum}, #{userPrivilege})")
    int insert(UserInfo user);

    @Update("UPDATE users_info SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
    int update(UserInfo user);

    @Delete("DELETE FROM users_infos WHERE id =#{id}")
    int delete(Long id);

}