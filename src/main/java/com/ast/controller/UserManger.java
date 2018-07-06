package com.ast.controller;

import com.ast.dao.UserInfo;
import com.ast.dao.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiangpeng on 2018/7/5.
 */
@RestController
@EnableAutoConfiguration
public class UserManger {
    private static int REGISTER_SUCCESS = 0;
    private static int REGISTER_FAILED = -1;
    private static int USER_ALREADY_EXIST = -2;

    private static int LOGIN_SUCCESS = 0;
    private static int LOGIN_FAILED = -1;
    private static int USER_DONT_EXIST = -2;

    @Autowired
    private UserInfoMapper m_userMapper;
    private  String LocalUrl = "*";
    /* *
    * UserRegister Test Url
    * http://127.0.0.1:8080/UserRegister?userName=testName&passWord=123456&userSex=boy&userAge=10&userHomeRegion=西湖&otherInfo=none
    * */
    @RequestMapping(value="/UserRegister",method=RequestMethod.GET)
    public int UserRegister(HttpServletRequest request, HttpServletResponse response)throws Exception{
        _addResponseHead(response);

        String username=request.getParameter("userName");
        String passWord=request.getParameter("passWord");
        String userSex=request.getParameter("userSex");
        String userAge=request.getParameter("userAge");
        String userHomeRegion=request.getParameter("userHomeRegion");
        String otherInfo=request.getParameter("otherInfo");
        String emailAddr=request.getParameter("emailAddr");
        String userPhoneNum=request.getParameter("userPhoneNum");
        String userPrivilege=request.getParameter("userPrivilege");
        PrintlnLog("Info: "+"username "+username);
        PrintlnLog("Info: "+"password "+passWord);
        PrintlnLog("Info: "+"userSex "+userSex);
        PrintlnLog("Info: "+"userAge "+userAge);
        PrintlnLog("Info: "+"userHomeRegion "+userHomeRegion);
        PrintlnLog("Info: "+"otherInfo "+otherInfo);
        int nRet =  REGISTER_FAILED;
        UserInfo user =  new UserInfo();
        user.userName = username;
        user.passWord = passWord;
        user.userSex = userSex;
        user.userAge = userAge;
        user.userHomeRegion = userHomeRegion;
        user.otherInfo = otherInfo;
        user.emailAddr = emailAddr;
        user.userPhoneNum = userPhoneNum;
        user.userPrivilege = userPrivilege;
        try{
            nRet = _checkUserInfoExist(user);
            if(nRet == REGISTER_SUCCESS)
            {
                m_userMapper.insert(user);
                nRet = REGISTER_SUCCESS;
            }
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return nRet;
    }
    /* *
       * UserLogin Test Url
       * http://127.0.0.1:8080/UserLogin?userName=testName&passWord=12345
       * */
    @RequestMapping(value="/UserLogin",method=RequestMethod.GET)
    public int UserLogin(HttpServletRequest request,HttpServletResponse response)throws Exception{
        _addResponseHead(response);

        String username=request.getParameter("userName");
        String passWord=request.getParameter("passWord");
        PrintlnLog("Info: "+"username "+username);
        PrintlnLog("Info: "+"password "+passWord);
        int nRet =  REGISTER_FAILED;
        UserInfo user =  new UserInfo();
        user.userName = username;
        user.passWord = passWord;
        try{
            nRet = _checkUserInfoLogin(user);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return nRet;
    }

    /* *
    * GetUserInfo Test Url
    * http://127.0.0.1:8080/UserLogin?userName=testName&passWord=12345
    * */
    @RequestMapping(value="/GetUserInfo",method=RequestMethod.GET)
    public UserInfo GetUserInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        _addResponseHead(response);

        String username=request.getParameter("userName");
        PrintlnLog("Info: "+"username "+username);
        try{
            return  m_userMapper.getUserByName(username);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return null;
    }

    private  void PrintlnLog(String msg){
        System.out.println(msg);
    }

    private int _checkUserInfoExist(UserInfo info){
        UserInfo tUserInfo = m_userMapper.getUserByName(info.userName);
        if (tUserInfo == null)
        {
            PrintlnLog("Info: REGISTER_SUCCESS");
            return REGISTER_SUCCESS;
        }
        else {
            PrintlnLog("Error: USER_ALREADY_EXIST,"+tUserInfo.userName);
            return USER_ALREADY_EXIST;
        }
    }

    private  void _addResponseHead(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", LocalUrl);
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    };

    private int _checkUserInfoLogin(UserInfo info){
        UserInfo tUserInfo = m_userMapper.getUserByName(info.userName);
        if (tUserInfo == null)
        {
            PrintlnLog("Info: USER_DONT_EXIST");
            return USER_DONT_EXIST;
        }
        else {
            if (info.passWord.equals(tUserInfo.passWord))
            {
                PrintlnLog("Info: LOGIN_SUCCESS");
                return LOGIN_SUCCESS;
            }
            PrintlnLog("Error: LOGIN_FAILED,Please check password,"+tUserInfo.passWord);
            return LOGIN_FAILED;
        }
    }
}
