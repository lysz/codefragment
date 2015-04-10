package com.aora.wifi.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.aora.wifi.bean.Ref;
import com.aora.wifi.dao.impl.BaseDao;

public class ChongZhiCallbackServlet extends HttpServlet{
    
    private final static Logger logger = Logger.getLogger(ChongZhiCallbackServlet.class);
    
    private static final long serialVersionUID = 6566116376475640921L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        doPost(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        callback(req);
    }
    
    private void callback(HttpServletRequest request){
        
        Map<String,String> map = new HashMap<String,String>();
        try {
            
            request.setCharacterEncoding("UTF-8");
            
            Enumeration<String> e = request.getParameterNames();
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                String value = request.getParameter(name);
                map.put(name, value);
            }
            
            ChongZhiAction cz = new ChongZhiAction();
            
            Ref<Integer> success_qty = new Ref<Integer>();
            Ref<Integer> fail_qty = new Ref<Integer>();
            Ref<String> msg = new Ref<String>();
            Ref<String> out_trade_id = new Ref<String>();
            
            cz.VerifyNotify(map, out_trade_id, success_qty, fail_qty, msg);
            
            int result = 99;
            if (map.get("status").equals("fail") && fail_qty.getValue() > 0){
                result = 1;
            }
            
            if (map.get("status").equals("success") && fail_qty.getValue() == 0){
                result = 0;
            }
            
            BaseDao dao = new BaseDao();
            
            String callbackdata = map.toString();
            String sql  = "update tbl_chongzhi set status = ?, callbackdata= ?, callbacktime=NOW() where out_trade_id=?";
            dao.executedSql(sql, new String[]{result+"", callbackdata, out_trade_id.getValue()});
            
            //充值失败, 将扣除用户的钱加回去
            if (result == 1){
                String trade_id = map.get("out_trade_id");
                List ls = dao.getData("select used_login_id, price from tbl_chongzhi where out_trade_id = ?", new String[]{trade_id});
                if (ls != null && !ls.isEmpty()){
                    Map rs = (Map)ls.get(0);
                    String reg_account = rs.get("used_login_id").toString();
                    int price = (Integer)rs.get("price");
                    
                    sql = "update tbl_reg_account set score = score + ? where account=?";
                    
                    dao.executedSql(sql, new String[]{price + "", reg_account});
                    
                    sql = "insert into tbl_chongzhi_money_return (tradeId, account, price, createtime) values(?, ?, ?, now())";
                    dao.executedSql(sql, new String[]{trade_id, reg_account, price + ""});
                }
            }
            
        } catch (Exception e1) {
            logger.error("Chong Zhi callback failed. data->" + map.toString(), e1);
        }
    }
}
