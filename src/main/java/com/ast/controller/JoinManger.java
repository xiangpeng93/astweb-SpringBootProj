package com.ast.controller;

import com.ast.dao.JoinInfo;
import com.ast.dao.JoinInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xiangpeng on 2018/7/5.
 */
@RestController
@EnableAutoConfiguration
public class JoinManger {
    static int JOIN_ADD_SUCCESS = 0;
    static int JOIN_ADD_FAILED = -1;
    static int JOIN_ALREADY_EXIST = -2;
    static int DEL_FAILED = -5;

    @Autowired
    private JoinInfoMapper m_joinMapper;

    /* *
    * JoinAdd Test Url
    * http://127.0.0.1:8080/JoinAdd?userName=testName&userAge=1&activeName=testActive&payNum=0&payOrder=0&otherInfo=123&activeId=12
    * */
    @RequestMapping(value = "/JoinAdd",method = RequestMethod.GET)
    public int JoinAdd(HttpServletRequest request){
        String userName=request.getParameter("userName");
        String userAge=request.getParameter("userAge");
        String activeName=request.getParameter("activeName");
        String payNum=request.getParameter("payNum");
        String payOrder=request.getParameter("payOrder");
        String otherInfo=request.getParameter("otherInfo");
        int activeId=Integer.parseInt(request.getParameter("activeId"));
        PrintlnLog("Info: "+"userName "+userName);
        PrintlnLog("Info: "+"userAge "+userAge);
        PrintlnLog("Info: "+"activeName "+activeName);
        PrintlnLog("Info: "+"activeId "+activeId);
        PrintlnLog("Info: "+"payNum "+payNum);
        PrintlnLog("Info: "+"payOrder "+payOrder);
        PrintlnLog("Info: "+"otherInfo "+otherInfo);
        int nRet =  JOIN_ADD_FAILED;
        JoinInfo info =  new JoinInfo();
        info.userName = userName;
        info.userAge = userAge;
        info.activeName = activeName;
        info.payNum = payNum;
        info.payOrder = payOrder;
        info.otherInfo = otherInfo;
        info.activeId = activeId;
        try{
            nRet = _checkJoinInfoExist(info);
            if (nRet == JOIN_ADD_SUCCESS)
            {
                nRet = m_joinMapper.insert(info);
            }
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return nRet;
    }

    /* *
    * QueryJoinInfo Test Url
    * http://127.0.0.1:8080/QueryJoinInfo?activeId=12
    * */
    @RequestMapping(value = "/QueryJoinInfo",method = RequestMethod.GET)
    public List<JoinInfo> QueryJoinInfo(HttpServletRequest request){
        int activeId=Integer.parseInt(request.getParameter("activeId"));
        PrintlnLog("Info: "+"activeId "+activeId);
        try{
            return m_joinMapper.getJoinsByActiveId(activeId);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return null;
    }

    /* *
   * DelJoinInfo Test Url
   * http://127.0.0.1:8080/DelJoinInfo?id=2
   * */
    @RequestMapping(value = "/DelJoinInfo",method = RequestMethod.GET)
    public int DelJoinInfo(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("id"));
        PrintlnLog("Info: "+"id "+id);
        try{
            return m_joinMapper.delete(id);
        }
        catch (Exception e){
            PrintlnLog("Error: "+e.toString());
        }
        return DEL_FAILED;
    }

    private int _checkJoinInfoExist(JoinInfo joinInfo)throws Exception {
        List<JoinInfo> tInfo = m_joinMapper.checkUserJoinActive(joinInfo.userName,joinInfo.activeId);
        if (tInfo.size() == 0) {
            PrintlnLog("Info: JoinInfo is null");
            return JOIN_ADD_SUCCESS;
        } else {
            PrintlnLog("Error: JOIN_ALREADY_EXIST," + tInfo.size());
            return JOIN_ALREADY_EXIST;
        }
    }

    private  void PrintlnLog(String msg){
        System.out.println(msg);
    }

}
