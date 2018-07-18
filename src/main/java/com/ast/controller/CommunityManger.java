package com.ast.controller;

import com.ast.dao.CommunityInfo;
import com.ast.dao.CommunityInfoMapper;
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
public class CommunityManger {
    static int ADD_SUCCESS = 0;
    static int ADD_FAILED = -1;
    static int ALREADY_EXIST = -2;
    static int QUERY_USERCOUNT_FAILED = -3;
    static int DEL_FAILED = -4;
    static int MOD_FAILED = -5;

    @Autowired
    private CommunityInfoMapper m_Mapper;

    String LocalUrl = "*";

    /* *
    * ActiveAdd Test Url
    * http://127.0.0.1:8080/CommunityAdd?communityHead=testName&communityBody=12345&communityAuthor=xp&communityGroup=11
    * */
    @RequestMapping(value = "/CommunityAdd", method = RequestMethod.POST)
    public int activeAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        String communityHead = request.getParameter("communityHead");
        String communityBody = request.getParameter("communityBody");
        String communityAuthor = request.getParameter("communityAuthor");
        String communityGroup = request.getParameter("communityGroup");
        PrintlnLog("Info: " + "communityHead " + communityHead);
        PrintlnLog("Info: " + "communityBody " + communityBody);
        PrintlnLog("Info: " + "communityGroup " + communityGroup);
        int nRet = ADD_FAILED;
        CommunityInfo info = new CommunityInfo();
        info.communityHead = communityHead;
        info.communityBody = communityBody;
        info.communityAuthor = communityAuthor;
        info.communityGroup = communityGroup;
        try {
            if (request.getParameter("id") != null && request.getParameter("id") != "") {
                nRet = CommunityMod(request, response);
            } else {
                nRet = _checkActiveInfoExist(info);
                if (nRet == ADD_SUCCESS) {
                    m_Mapper.insert(info);
                    nRet = m_Mapper.getCommunityByName(info.communityHead).id;
                }
            }
        } catch (Exception e) {
            PrintlnLog("Error: " + e.toString());
        }
        return nRet;
    }

    /* *
   * QueryCommunityInfoById Test Url
   * http://127.0.0.1:8080/QueryCommunityInfoById?id=32
   * */
    @RequestMapping(value = "/QueryCommunityInfoById", method = RequestMethod.GET)
    public CommunityInfo QueryCommunityInfoById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        int id = Integer.parseInt(request.getParameter("id"));
        PrintlnLog("Info: " + "id " + id );
        long iStartTime = System.currentTimeMillis();
        try {
            CommunityInfo tActiveInfo = m_Mapper.getCommunityById(id);
            tActiveInfo.communityBrowersCount += 1;
            m_Mapper.update(tActiveInfo);
            PrintlnLog("Info: " + "QueryActiveInfoById Cost time :  " + (System.currentTimeMillis() - iStartTime));
            return tActiveInfo;
        } catch (Exception e) {
            PrintlnLog("Error: " + e.toString());
        }
        return null;
    }

    /* *
    * CommunityDel Test Url
    * http://127.0.0.1/CommunityDel?id=32
    * */
    @RequestMapping(value = "/CommunityDel", method = RequestMethod.GET)
    public int CommunityDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        int nRet = DEL_FAILED;
        try {
            int id = Integer.parseInt(request.getParameter("id")) ;
            PrintlnLog("Info: " + "id " + id);
            nRet = m_Mapper.delete(id);
        } catch (Exception e) {
            PrintlnLog("Error: " + e.toString());
        }
        return nRet;
    }

    /* *
    * QueryCommunityInfo Test Url
    * http://127.0.0.1/QueryCommunityInfo?Begin=0&Count=3
    * */
    @RequestMapping(value = "/QueryCommunityInfo", method = RequestMethod.GET)
    public List<CommunityInfo> QueryCommunityInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        int Begin = Integer.parseInt(request.getParameter("Begin"));
        int Count = Integer.parseInt(request.getParameter("Count"));

        PrintlnLog("Info: " + "Begin " + Begin);
        PrintlnLog("Info: " + "Count " + Count);
        long iStartTime = System.currentTimeMillis();
        try {
            List<CommunityInfo> lReturn =m_Mapper.getCommunityByStartNumAndCount(Begin, Count);
            return lReturn;
        } catch (Exception e) {
            PrintlnLog("Error: " + e.toString());
        }
        return null;
    }

    /* *
   * CommunityMod Test Url
   * http://127.0.0.1/CommunityMod?communityHead=testName&communityBody=12345&communityAuthor=xp&communityGroup=11&&id=33
   * */
    @RequestMapping(value = "/CommunityMod", method = RequestMethod.POST)
    public int CommunityMod(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        PrintlnLog(request.getParameter("id"));
        int id = Integer.parseInt(request.getParameter("id") == "" ? "0" : request.getParameter("id"));
        PrintlnLog("Info: " + "id " + id);
        String communityHead = request.getParameter("communityHead");
        PrintlnLog("Info: " + "communityHead " + communityHead);
        String communityBody = request.getParameter("communityBody");
        String communityAuthor = request.getParameter("communityAuthor");
        PrintlnLog("Info: " + "communityAuthor " + communityAuthor);
        String communityGroup = request.getParameter("communityGroup");

        int nRet = MOD_FAILED;
        CommunityInfo tInfo = new CommunityInfo();
        tInfo.id = id;
        tInfo.communityHead = communityHead;
        tInfo.communityBody = communityBody;
        tInfo.communityAuthor = communityAuthor;
        tInfo.communityGroup = communityGroup;
        try {
            nRet = m_Mapper.update(tInfo);
            nRet = id;
        } catch (Exception e) {
            PrintlnLog("Error: " + e.toString());
        }
        return nRet;
    }

    private void _addResponseHead(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", LocalUrl);
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private int _checkActiveInfoExist(CommunityInfo info) throws Exception {
        CommunityInfo tInfo = m_Mapper.getCommunityByName(info.communityHead);
        if (tInfo == null) {
            PrintlnLog("Info: active is null");
            return ADD_SUCCESS;
        } else {
            PrintlnLog("Error: ACTIVE_ALREADY_EXIST," + tInfo.communityHead);
            return ALREADY_EXIST;
        }
    }

    private void PrintlnLog(String msg) {
        System.out.println(msg);
    }


}
