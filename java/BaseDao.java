package com.aora.wifi.dao.impl;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.orm.ibatis.support.*;


import com.aora.wifi.action.interfaces.func1xxAction;
import com.aora.wifi.util.Tools;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;



import java.io.Reader;
import java.lang.reflect.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class BaseDao {
	
	private final static Logger logger = Logger.getLogger(BaseDao.class);	
	private Connection getConnection() throws Exception {
		 Connection conn=null;
		 Context envCtx = null;
		 InitialContext initCtx = null;
		DataSource ds = null;
		
		
//		 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	     conn=   ds.getConnection(); 
       //Class.forName("com.mysql.jdbc.Driver");//jdbc:mysql://127.0.0.1:3306/eatinggod?useUnicode=true&characterEncoding=gbk gb2312
	     //conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/senco?useUnicode=true&characterEncoding=gbk","root","password");

		 initCtx = new InitialContext();
		 envCtx = (Context) initCtx.lookup("java:comp/env");
	     ds = (DataSource) envCtx.lookup("jdbc/wifi");
	     conn = ds.getConnection();
		     
		 return conn;		
	}
	
	private Connection getLogerConnection() throws Exception {
		 Connection conn=null;
		 Context envCtx = null;
		 InitialContext initCtx = null;
		DataSource ds = null;
		
		
//		 javax.sql.DataSource ds= (javax.sql.DataSource)ctx.lookup("G3JNDI");   
//	     conn=   ds.getConnection(); 
      //Class.forName("com.mysql.jdbc.Driver");//jdbc:mysql://127.0.0.1:3306/eatinggod?useUnicode=true&characterEncoding=gbk gb2312
	     //conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/senco?useUnicode=true&characterEncoding=gbk","root","password");

		 initCtx = new InitialContext();
		 envCtx = (Context) initCtx.lookup("java:comp/env");
	     ds = (DataSource) envCtx.lookup("jdbc/wifilog");
	     conn = ds.getConnection();
		     
		 return conn;		
	}	
	
	public  static void main(String[] arg){
		try{
			//Connection conn=	new BaseDao().getConnection();
			List para =new ArrayList();
			String data="'aaa','bbb'";
			//para.add("'aaa','bbb'");
			//para.add("2013-01-01");
			//para.add("2013-07-11");
			//para.add("");
//			para.add("");
//			para.add("");
			//List ls=new BaseDao().getData("{ call sp_t1(?)}",para.toArray());
			//List ls=new BaseDao().getData("{call sp_report05_roleC_Analysis(?,?,?,?,?,?)}",para.toArray());	
			List ls=new BaseDao().getData("select * from tbl_wifi_share_account where mac_address in("+data+")",para.toArray());
			for(int i=0;i<ls.size();i++){
				System.out.println(ls.get(i));
			}
			
			System.out.println("succ");
			}catch(Exception e){
				System.out.println("fail");
				e.printStackTrace();
			}
	}

	  private  List convertList(ResultSet rs) throws SQLException {    
		    //Container 
		    List list = new ArrayList();    
		    //Get the element of resultSet 
		    ResultSetMetaData md = rs.getMetaData();    
		    //Map rowData; 
		    int columnCount = md.getColumnCount();     
		    //Use Map 
		    while (rs.next()) { 
		      Map rowData = new HashMap();    
		      //Fill up 
		      for (int i = 1; i <= columnCount; i++) {    
		        //rowData.put(md.getColumnName(i), rs.getObject(i));    
		    	  rowData.put(md.getColumnLabel(i), rs.getObject(i));
		      }    
		      //Fill up 
		      list.add(rowData);    
		    }    
		    //back 
		    return list;    
		}    
	  
	  
	  
	  public List getData(String sql,Object[] paras) throws Exception{
			Connection conn=null;
			PreparedStatement state=null;
			CallableStatement cstate = null;
	 		ResultSet rs=null;
	 		List ls=null;
			try{
				conn=getConnection();
				//System.out.println(sql);				
				if(sql.toLowerCase().indexOf("call ")>=0){
					cstate = conn.prepareCall(sql);
					for(int i=0;i<paras.length;i++){
						Object obj=paras[i];
						if(obj instanceof String){
							cstate.setString(i+1, obj.toString());
						}else if (obj instanceof Integer){
							cstate.setString(i+1, obj.toString());
						}else if (obj instanceof Date){
							cstate.setDate(i+1, (java.sql.Date)obj);
						}else{
							cstate.setObject(i+1, obj);
						}
					}
					rs = cstate.executeQuery();
				}else{
					state=conn.prepareStatement(sql);
					for(int i=0;i<paras.length;i++){
						Object obj=paras[i];
						if(obj instanceof String){
							state.setString(i+1, obj.toString());
						}else if (obj instanceof Integer){
							state.setString(i+1, obj.toString());
						}else if (obj instanceof Date){
							state.setDate(i+1, (java.sql.Date)obj);
						}else{
							state.setObject(i+1, obj);
						}
					}
					rs=state.executeQuery();
				}
				ls=convertList(rs);
				
			}
			finally{
				try{rs.close();rs=null;}catch(Exception e){}
				try{if(state!=null){state.close();state=null;}}catch(Exception e){}
				try{if(cstate!=null){cstate.close();cstate=null;}}catch(Exception e){}
				try{conn.close();conn=null;}catch(Exception e){}
			}
		  return ls;
	  }
	  

	  public boolean executedSql(String sql,Object[] paras) throws Exception{
			Connection conn=null;
			PreparedStatement state=null;
	 		ResultSet rs=null;
	 		boolean ls=false;
			try{
				conn=getConnection();
				state=conn.prepareStatement(sql);
				for(int i=0;i<paras.length;i++){
					Object obj=paras[i];
					if(obj instanceof String){
						state.setString(i+1, obj.toString());
					}else if (obj instanceof Integer){
						state.setString(i+1, obj.toString());
					}else if (obj instanceof Date){
						state.setDate(i+1, (java.sql.Date)obj);
					}else{
						state.setObject(i+1, obj);
					}
				}
				
				ls=state.execute();
				
				
			}
			finally{
				try{rs.close();rs=null;}catch(Exception e){}
				try{state.close();state=null;}catch(Exception e){}
				try{conn.close();conn=null;}catch(Exception e){}
			}
		  return ls;
	  }
	  
	  public int executedUpdateSql(String sql,Object[] paras) throws Exception{
			Connection conn=null;
			PreparedStatement state=null;
	 		ResultSet rs=null;
	 		int ls=0;
			try{
				conn=getConnection();
				state=conn.prepareStatement(sql);
				for(int i=0;i<paras.length;i++){
					Object obj=paras[i];
					if(obj instanceof String){
						state.setString(i+1, obj.toString());
					}else if (obj instanceof Integer){
						state.setString(i+1, obj.toString());
					}else if (obj instanceof Date){
						state.setDate(i+1, (java.sql.Date)obj);
					}else{
						state.setObject(i+1, obj);
					}
				}
				
				ls=state.executeUpdate();
				
				
			}
			finally{
				try{rs.close();rs=null;}catch(Exception e){}
				try{state.close();state=null;}catch(Exception e){}
				try{conn.close();conn=null;}catch(Exception e){}
			}
		  return ls;
	  }	  
	  
	  
	  private boolean executedLogerSql(String sql,Object[] paras) throws Exception{
			Connection conn=null;
			PreparedStatement state=null;
	 		ResultSet rs=null;
	 		boolean ls=false;
			try{
				conn=getLogerConnection();
				state=conn.prepareStatement(sql);
				for(int i=0;i<paras.length;i++){
					Object obj=paras[i];
					if(obj instanceof String){
						state.setString(i+1, obj.toString());
					}else if (obj instanceof Integer){
						state.setString(i+1, obj.toString());
					}else if (obj instanceof Date){
						state.setDate(i+1, (java.sql.Date)obj);
					}else{
						state.setObject(i+1, obj);
					}
				}
				
				ls=state.execute();
				
				
			}
			finally{
				try{rs.close();rs=null;}catch(Exception e){}
				try{state.close();state=null;}catch(Exception e){}
				try{conn.close();conn=null;}catch(Exception e){}
			}
		  return ls;
	  }
	  	  
	  
	  public void action_log(String uid,String loginName,String type,String header,String content,String channel){
		  
		  List para=new ArrayList();
		  
		  para.add(uid==null?"":uid);
		  para.add(loginName==null?"":loginName);
		  para.add(type==null?"":type);
		  para.add(header==null?"":header);
		  para.add(channel==null?"":channel);
		  //System.out.println("content:"+content.length());
		  
		  if(content==null || content.length()<=300){
			  para.add(content==null?"":content);
			  para.add("");
			  para.add("");			  
		  }else if(content.length()>300 && content.length()<=600){
			  para.add(content.substring(0,300));
			  para.add(content.substring(300,content.length()));
			  para.add("");
		  }else if(content.length()>600){
			  para.add(content.substring(0,300));
			  para.add(content.substring(300,600));
			  para.add(content.substring(600,(content.length()>900?900:content.length())));
		  }else{
			  para.add(content==null?"":content);
			  para.add("");
			  para.add("");			  
		  }
	
		  String sql="insert into tbl_wifi_logs(used_client_id,used_login_id,action,header,channel,remark1,remark2,remark3,indate) values(?,?,?,?,?,?,?,?,now())";

		  
		  try{
			  this.executedLogerSql(sql, para.toArray());
			  
			  sql="insert into tbl_wifi_logs(used_client_id,used_login_id,action,header,channel,remark1,remark2,remark3,indate) values('"+
		  	   para.get(0).toString().replaceAll("'","").replaceAll("\n","").replaceAll("\r","") +"','" +
		  	   para.get(1).toString().replaceAll("'","").replaceAll("\n","").replaceAll("\r","")  +"','" +
		  	   para.get(2).toString().replaceAll("'","").replaceAll("\n","").replaceAll("\r","")  +"','" +
		  	   para.get(3).toString().replaceAll("'","").replaceAll("\n","").replaceAll("\r","")  +"','" +
		  	   para.get(4).toString().replaceAll("'","").replaceAll("\n","").replaceAll("\r","")  +"','" +
		  	   para.get(5).toString().replaceAll("'","").replaceAll("\n","").replaceAll("\r","")  +"','" +
		  	   para.get(6).toString().replaceAll("'","").replaceAll("\n","").replaceAll("\r","")  +"','" +			   
		  	   para.get(7).toString().replaceAll("'","").replaceAll("\n","").replaceAll("\r","")  +"','" +
		  	   Tools.date2String(new Date(), "yyyy-MM-dd HH:mm:ss")+"')";	
			  
			  //logger.info(sql);
			  
//			  com.aora.wifi.util.Log.info(sql);
			  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  
	  }

	  private  List populate(ResultSet rs,Class clazz) throws Exception{ 
        //Get the element of resultSet 
		  ResultSetMetaData metaData = rs.getMetaData();    
        //Get count 
        int colCount = metaData.getColumnCount();                     
        //Container 
        List ret = new ArrayList();             
        
        //Get Object attributes 
        Field[] fields = clazz.getDeclaredFields();        
        //Construct it 
        while(rs.next()){ 
                Object newInstance = clazz.newInstance();     
                for(int i=1;i<=colCount;i++){     
                        try{ 
                                Object value = rs.getObject(i); 
                                for(int j=0;j<fields.length;j++){ 
                                        Field f = fields[j];
                                       // System.out.println(metaData.getColumnLabel(i)+","+ ","+f.getName());
                                        if(f.getName().equalsIgnoreCase(metaData.getColumnLabel(i))){
                                        		
                                                BeanUtils.copyProperty(newInstance,f.getName(),value);
                                                break;
                                        } 
                                } 
                                //System.out.println("*******************");
                        }catch (Exception e) { 
                                // TODO: handle exception 
                                e.printStackTrace(); 
                        } 
                } 
                //Fill into 
                ret.add(newInstance); 
        } 
        //Back 
        return ret; 
} 

	  public List getData(String sql,Object[] paras,Class obj_class) throws Exception{
			Connection conn=null;
			PreparedStatement state=null;
	 		ResultSet rs=null;
	 		List ls=null;
			try{
				conn=getConnection();
				state=conn.prepareStatement(sql);
				for(int i=0;i<paras.length;i++){
					Object obj=paras[i];
					if(obj instanceof String){
						state.setString(i+1, obj.toString());
					}else if (obj instanceof Integer){
						state.setString(i+1, obj.toString());
					}else if (obj instanceof Date){
						state.setDate(i+1, (java.sql.Date)obj);
					}else{
						state.setObject(i+1, obj);
					}
				}
				
				rs=state.executeQuery();
				ls=populate(rs,obj_class);
				
			}
			finally{
				try{rs.close();rs=null;}catch(Exception e){}
				try{state.close();state=null;}catch(Exception e){}
				try{conn.close();conn=null;}catch(Exception e){}
			}
		  return ls;
	  }
	  	
	
	
}
