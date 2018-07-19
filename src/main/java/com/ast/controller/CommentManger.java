package com.ast.controller;

import com.ast.dao.CommentInfo;
import com.ast.dao.CommentInfoMapper;
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
public class CommentManger {
    static int ADD_SUCCESS = 0;
    static int ADD_FAILED = -1;
    static int ALREADY_EXIST = -2;
    static int QUERY_USERCOUNT_FAILED = -3;
    static int DEL_FAILED = -4;
    static int MOD_FAILED = -5;

    @Autowired
    private CommentInfoMapper m_Mapper;

    String LocalUrl = "*";

    /* *
    * CommentAdd Test Url
    * http://127.0.0.1:8080/CommentAdd?communityId=1&authorName=123&commentBody=xp
    * */
    @RequestMapping(value = "/CommentAdd", method = RequestMethod.POST)
    public int CommentAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        int communityId = Integer.parseInt(request.getParameter("communityId"));
        String authorName = request.getParameter("authorName");
        String commentBody = request.getParameter("commentBody");
        PrintlnLog("Info: " + "communityId " + communityId);
        PrintlnLog("Info: " + "authorName " + authorName);
        PrintlnLog("Info: " + "commentBody " + commentBody);
        int nRet = ADD_FAILED;
        CommentInfo info = new CommentInfo();
        info.communityId = communityId;
        info.authorName = authorName;
        info.commentBody = commentBody;
        try {
            m_Mapper.insert(info);
            nRet = ADD_SUCCESS;
        }
        catch (Exception e)
        {
            PrintlnLog("Error: " + e.toString());
        }
        return nRet;
    }

    /* *
   * QueryCommentInfoByCommunityId Test Url
   * http://127.0.0.1:8080/QueryCommentInfoByCommunityId?communityId=1
   * */
    @RequestMapping(value = "/QueryCommentInfoByCommunityId", method = RequestMethod.GET)
    public List<CommentInfo> QueryCommentInfoByCommunityId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        int communityId = Integer.parseInt(request.getParameter("communityId"));
        PrintlnLog("Info: " + "communityId " + communityId);
        long iStartTime = System.currentTimeMillis();
        try {
            List<CommentInfo> tInfos = m_Mapper.getCommentByCommunityId(communityId);
            PrintlnLog("Info: " + "QueryActiveInfoById Cost time :  " + (System.currentTimeMillis() - iStartTime));
            return tInfos;
        } catch (Exception e) {
            PrintlnLog("Error: " + e.toString());
        }
        return null;
    }

    /* *
    * CommentDel Test Url
    * http://127.0.0.1/CommentDel?id=1
    * */
    @RequestMapping(value = "/CommentDel", method = RequestMethod.GET)
    public int CommentDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        int nRet = DEL_FAILED;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            PrintlnLog("Info: " + "id " + id);
            nRet = m_Mapper.delete(id);
        } catch (Exception e) {
            PrintlnLog("Error: " + e.toString());
        }
        return nRet;
    }

    /* *
    * QueryCommunityInfo Test Url
    * http://127.0.0.1/QueryCommentInfo?Begin=0&Count=3
    * */
    @RequestMapping(value = "/QueryCommentInfo", method = RequestMethod.GET)
    public List<CommentInfo> QueryCommentInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _addResponseHead(response);
        int Begin = Integer.parseInt(request.getParameter("Begin"));
        int Count = Integer.parseInt(request.getParameter("Count"));

        PrintlnLog("Info: " + "Begin " + Begin);
        PrintlnLog("Info: " + "Count " + Count);
        try {
            List<CommentInfo> lReturn = m_Mapper.getCommentsByStartNumAndCount(Begin, Count);
            return lReturn;
        } catch (Exception e) {
            PrintlnLog("Error: " + e.toString());
        }
        return null;
    }

    private void _addResponseHead(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", LocalUrl);
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private void PrintlnLog(String msg) {
        System.out.println(msg);
    }


}
