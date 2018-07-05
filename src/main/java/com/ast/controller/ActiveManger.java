package com.ast.controller;

import com.ast.dao.ActiveInfo;
import com.ast.dao.ActiveInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xiangpeng on 2018/7/5.
 */
@RestController
@EnableAutoConfiguration
public class ActiveManger {
    static int ACTIVE_ADD_SUCCESS = 0;
    static int ACTIVE_ADD_FAILED = -1;
    static int ACTIVE_ALREADY_EXIST = -2;
    static int ACTIVE_QUERY_USERCOUNT_FAILED = -3;
    static int ACTIVE_DEL_FAILED = -4;
    static int ACTIVE_MOD_FAILED = -5;

    @Autowired
    private ActiveInfoMapper m_activeMapper;

    /* *
    * ActiveAdd Test Url
    * http://127.0.0.1:8080/ActiveAdd?activeHead=testName&activeBody=12345&activeAuthor=xp&activeUserCount=20&activeBrowersCount=0
    * */
    @RequestMapping(value = "/ActiveAdd",method = RequestMethod.GET)
    public int activeAdd(HttpServletRequest request,HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String activeHead=request.getParameter("activeHead");
        String activeBody=request.getParameter("activeBody");
        String activeAuthor=request.getParameter("activeAuthor");
        int id=Integer.parseInt(request.getParameter("id") == null ? "0" : request.getParameter("id"));
        int activeUserCount=Integer.parseInt(request.getParameter("activeUserCount") == null ? "0" : request.getParameter("activeUserCount"));
        int activeBrowersCount=Integer.parseInt(request.getParameter("activeBrowersCount") == null ? "0" : request.getParameter("activeBrowersCount"));
        PrintlnLog("Info: "+"id "+id);
        PrintlnLog("Info: "+"activeHead "+activeHead);
        PrintlnLog("Info: "+"activeBody "+activeBody);
        PrintlnLog("Info: "+"activeAuthor "+activeAuthor);
        PrintlnLog("Info: "+"activeUserCount "+activeUserCount);
        PrintlnLog("Info: "+"activeBrowersCount "+activeBrowersCount);
        int nRet =  ACTIVE_ADD_FAILED;
        ActiveInfo active =  new ActiveInfo();
        active.id = id;
        active.activeHead = activeHead;
        active.activeBody = activeBody;
        active.activeAuthor = activeAuthor;
        active.activeUserCount = activeUserCount;
        active.activeBrowersCount = activeBrowersCount;
        try{
            nRet = _checkActiveInfoExist(active);
            if(nRet == ACTIVE_ADD_SUCCESS)
            {
                m_activeMapper.insert(active);
                nRet = ACTIVE_ADD_SUCCESS;
            }
            else
            {
                nRet = ActiveMod(request);
            }
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return nRet;
    }

    /* *
    * ActiveUserCount Test Url
    * http://127.0.0.1:8080/ActiveUserCount?activeHead=testName
    * */
    @RequestMapping(value = "/ActiveUserCount",method = RequestMethod.GET)
    public int queryActiveUserCount(HttpServletRequest request){
        String activeHead=request.getParameter("activeHead");
        PrintlnLog("Info: "+"activeHead "+activeHead);
        int nRet =  ACTIVE_QUERY_USERCOUNT_FAILED;
        ActiveInfo active =  new ActiveInfo();
        active.activeHead = activeHead;
        try{
                nRet = m_activeMapper.getActiveUsercountByHead(active.activeHead).activeUserCount;
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return nRet;
    }


    /* *
   * ActiveUserCount Test Url
   * http://127.0.0.1:8080/ActiveUserCount?activeHead=testName
   * */
    @RequestMapping(value = "/QueryActiveInfoById",method = RequestMethod.GET)
    public ActiveInfo QueryActiveInfoById(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        int id= Integer.parseInt(request.getParameter("id"));
        PrintlnLog("Info: "+"id "+id);
        try{
            return m_activeMapper.getActiveById(id);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return null;
    }

    /* *
    * ActiveDel Test Url
    * http://127.0.0.1:8080/ActiveDel?activeHead=testName
    * */
    @RequestMapping(value = "/ActiveDel",method = RequestMethod.GET)
    public int ActiveDel(HttpServletRequest request){
        String activeHead=request.getParameter("activeHead");
        PrintlnLog("Info: "+"activeHead "+activeHead);
        int nRet =  ACTIVE_DEL_FAILED;
        ActiveInfo active =  new ActiveInfo();
        active.activeHead = activeHead;
        try{
            nRet = m_activeMapper.delete(active.activeHead);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return nRet;
    }

    /* *
    * QueryActivesInfo Test Url
    * http://127.0.0.1:8080/QueryActivesInfo?activeBegin=0&activeCount=3
    * */
    @RequestMapping(value = "/QueryActivesInfo",method = RequestMethod.GET)
    public  List<ActiveInfo> QueryActivesInfo(HttpServletRequest request){
        int activeBegin=Integer.parseInt( request.getParameter("activeBegin"));
        int activeCount=Integer.parseInt( request.getParameter("activeCount"));
        PrintlnLog("Info: "+"activeBegin "+activeBegin);
        PrintlnLog("Info: "+"activeCount "+activeCount);
        try{
            return m_activeMapper.getActiveByStartNumAndCount(activeBegin,activeCount);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return null;
    }

    /* *
   * ActiveMod Test Url
   * http://127.0.0.1:8080/ActiveMod?id=3&activeHead=testName&activeBody=12345&activeAuthor=xp&activeUserCount=20&activeBrowersCount=0
   * */
    @RequestMapping(value = "/ActiveMod",method = RequestMethod.GET)
    public int ActiveMod(HttpServletRequest request){
        int id= Integer.parseInt(request.getParameter("id"));
        String activeHead=request.getParameter("activeHead");
        String activeBody=request.getParameter("activeBody");
        String activeAuthor=request.getParameter("activeAuthor");
        int activeUserCount=Integer.parseInt(request.getParameter("activeUserCount") == null ? "0" : request.getParameter("activeUserCount"));
        int activeBrowersCount=Integer.parseInt(request.getParameter("activeBrowersCount") == null ? "0" : request.getParameter("activeBrowersCount"));
        PrintlnLog("Info: "+"id "+id);
        PrintlnLog("Info: "+"activeHead "+activeHead);
        PrintlnLog("Info: "+"activeBody "+activeBody);
        PrintlnLog("Info: "+"activeAuthor "+activeAuthor);
        PrintlnLog("Info: "+"activeUserCount "+activeUserCount);
        PrintlnLog("Info: "+"activeBrowersCount "+activeBrowersCount);
        int nRet =  ACTIVE_MOD_FAILED;
        ActiveInfo active =  new ActiveInfo();
        active.id = id;
        active.activeHead = activeHead;
        active.activeBody = activeBody;
        active.activeAuthor = activeAuthor;
        active.activeUserCount = activeUserCount;
        active.activeBrowersCount = activeBrowersCount;
        try{
            nRet = m_activeMapper.update(active);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return nRet;
    }

    private int _checkActiveInfoExist(ActiveInfo info)throws Exception {
        ActiveInfo tActiveInfo = m_activeMapper.getActiveById(info.id);
        if (tActiveInfo == null) {
            PrintlnLog("Info: active is null");
            return ACTIVE_ADD_SUCCESS;
        } else {
            PrintlnLog("Error: ACTIVE_ALREADY_EXIST," + tActiveInfo.activeHead);
            return ACTIVE_ALREADY_EXIST;
        }
    }

    private  void PrintlnLog(String msg){
        System.out.println(msg);
    }


}
