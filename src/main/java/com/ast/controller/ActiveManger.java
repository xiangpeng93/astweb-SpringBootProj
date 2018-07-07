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

    String LocalUrl = "*";
    /* *
    * ActiveAdd Test Url
    * http://127.0.0.1:8080/ActiveAdd?activeHead=testName&activeBody=12345&activeAuthor=xp&activeUserCount=20&activeBrowersCount=0
    * */
    @RequestMapping(value = "/ActiveAdd",method = RequestMethod.GET)
    public int activeAdd(HttpServletRequest request,HttpServletResponse response)throws Exception{
        _addResponseHead(response);
        String activeHead= request.getParameter("activeHead");
        String activeBody=request.getParameter("activeBody");
        String activeAuthor=request.getParameter("activeAuthor");
        int activeUserCount=Integer.parseInt(request.getParameter("activeUserCount") == null ? "0" : request.getParameter("activeUserCount"));
        int activeCount=Integer.parseInt(request.getParameter("activeCount") == null ? "1" : request.getParameter("activeCount"));
        PrintlnLog("Info: "+"activeHead "+activeHead);
        PrintlnLog("Info: "+"activeBody "+activeBody);
        PrintlnLog("Info: "+"activeAuthor "+activeAuthor);
        PrintlnLog("Info: "+"activeCount "+activeCount);
        PrintlnLog("Info: "+"activeUserCount "+activeUserCount);
        int nRet =  ACTIVE_ADD_FAILED;
        ActiveInfo active =  new ActiveInfo();
        active.activeHead = activeHead;
        active.activeBody = activeBody;
        active.activeAuthor = activeAuthor;
        active.activeUserCount = activeUserCount;
        active.activeCount = activeCount;
        try{
            if (request.getParameter("id") != "" )
            {
                nRet = ActiveMod(request,response);
            }
            else
            {
                nRet = _checkActiveInfoExist(active);
                if (nRet == ACTIVE_ADD_SUCCESS )
                {
                    m_activeMapper.insert(active);
                    nRet = ACTIVE_ADD_SUCCESS;
                }
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
    public int queryActiveUserCount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        _addResponseHead(response);
        int id= Integer.parseInt(request.getParameter("id") == "" ? "0" : request.getParameter("id"));
        PrintlnLog("Info: "+"id "+id);
        int nRet =  ACTIVE_QUERY_USERCOUNT_FAILED;
        try{
                nRet = m_activeMapper.getActiveUsercountById(id).activeUserCount;
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
    public ActiveInfo QueryActiveInfoById(HttpServletRequest request,HttpServletResponse response)throws Exception{
        _addResponseHead(response);
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
    public int ActiveDel(HttpServletRequest request,HttpServletResponse response)throws Exception{
        _addResponseHead(response);
        int nRet =  ACTIVE_DEL_FAILED;
        try{
            int id=Integer.parseInt( request.getParameter("id"));
            PrintlnLog("Info: "+"id "+id);
            nRet = m_activeMapper.delete(id);
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
    public  List<ActiveInfo> QueryActivesInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        _addResponseHead(response);
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
    public int ActiveMod(HttpServletRequest request,HttpServletResponse response)throws Exception{
        _addResponseHead(response);
        PrintlnLog(request.getParameter("id"));
        int id = Integer.parseInt( request.getParameter("id") == "" ? "0" :request.getParameter("id"));
        PrintlnLog("Info: "+"id "+id);
        String activeHead=request.getParameter("activeHead");
        PrintlnLog("Info: "+"activeHead "+activeHead);
        String activeBody=request.getParameter("activeBody");
        PrintlnLog("Info: "+"activeBody "+activeBody);
        String activeAuthor=request.getParameter("activeAuthor");
        PrintlnLog("Info: "+"activeAuthor "+activeAuthor);
        int activeUserCount=Integer.parseInt(request.getParameter("activeUserCount") == "" ? "0" : request.getParameter("activeUserCount"));
        PrintlnLog("Info: "+"activeUserCount "+activeUserCount);
        int activeCount=Integer.parseInt(request.getParameter("activeCount") == "" ? "1" : request.getParameter("activeCount"));
        PrintlnLog("Info: "+"activeCount "+activeCount);
        int nRet =  ACTIVE_MOD_FAILED;
        ActiveInfo active =  new ActiveInfo();
        active.id = id;
        active.activeHead = activeHead;
        active.activeBody = activeBody;
        active.activeAuthor = activeAuthor;
        active.activeUserCount = activeUserCount;
        active.activeCount = activeCount;
        try{
            nRet = m_activeMapper.update(active);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return nRet;
    }

    private  void _addResponseHead(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", LocalUrl);
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    };

    private int _checkActiveInfoExist(ActiveInfo info)throws Exception {
        ActiveInfo tActiveInfo = m_activeMapper.getActiveByActiveName(info.activeHead);
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
