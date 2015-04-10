package com.aora.wifi.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alipay.util.AlipayNotify;
import com.aora.wifi.dao.impl.BaseDao;

public class AlipayCallbackServlet extends HttpServlet {

    private final static Logger logger = Logger.getLogger(AlipayCallbackServlet.class);
    
    private static final long serialVersionUID = -5183259520620746780L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/xml;UTF-8");
        PrintWriter out = response.getWriter(); 
        logger.info("----alipaycallback---");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        logger.info("callback data:" + params.toString());
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        String seller_id = request.getParameter("seller_id");
        String seller_email = request.getParameter("seller_email");
        String buyer_id = request.getParameter("buyer_id");
        String buyer_email = request.getParameter("buyer_email");
        String total_fee = request.getParameter("total_fee");
        String quantity = request.getParameter("quantity");
        String gmt_create = request.getParameter("gmt_create");
        String gmt_payment = request.getParameter("gmt_payment");
        String use_coupon = request.getParameter("use_coupon");
        String discount = request.getParameter("discount");
        String refund_status = request.getParameter("refund_status");
        String gmt_refund = request.getParameter("gmt_refund");
        
        BaseDao dao = new BaseDao();
        
        String sql  = "insert into tbl_card_trade (out_trade_no, trade_no, status, seller_id, seller_email, buyer_id, buyer_email, total_fee, quantity, gmt_create, gmt_payment, use_coupon, discnts, refund_status, gmt_refund, verify_result, indate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, now())";                
//        String sql  = "insert into tbl_card_trade (out_trade_no, trade_no, status, seller_id, seller_email, buyer_id, buyer_email, total_fee, quantity, gmt_create, gmt_payment, use_coupon) values(?,?,?,?,?,?,?,?,?,?,?,?)";  
        logger.info("begin to verify...");
        
        if(AlipayNotify.verify(params)){//验证成功
            logger.info("success");
            try {
                dao.executedSql(sql, new String[]{out_trade_no, trade_no, trade_status, seller_id, seller_email, buyer_id, buyer_email, total_fee, quantity, gmt_create, gmt_payment, use_coupon, discount, refund_status, gmt_refund, "0"});
            } catch (Exception e) {
                logger.error(e);
            }
            
            logger.info("saved to db.");
            
            out.println("success"); 
        }else{//验证失败
            logger.info("fail");
            try {
                dao.executedSql(sql, new String[]{out_trade_no, trade_no, trade_status, seller_id, seller_email, buyer_id, buyer_email, total_fee, quantity, gmt_create, gmt_payment, use_coupon, discount, refund_status, gmt_refund, "1"});
            } catch (Exception e) {
                logger.error(e);
            }
            
            logger.info("saved to db.");
            out.println("fail");
        }
    }

}
