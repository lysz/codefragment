public class DBUtils{
    
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dbpath = "jdbc:mysql://192.168.0.36:3306/wifiapp2?useUnicode=true&characterEncoding=utf-8";
        Connection conn = DriverManager.getConnection(dbpath, "wifi", "wifiapp");
        
        return conn;
    }
    
    public static List getData(String sql,Object[] paras) throws Exception{
        Connection conn=null;
        PreparedStatement state=null;
        CallableStatement cstate = null;
        ResultSet rs=null;
        List ls=null;
        try{
            conn=getConnection();
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
            try{if (rs != null){ rs.close();rs=null;}}catch(Exception e){}
            try{if(state!=null){state.close();state=null;}}catch(Exception e){}
            try{if(cstate!=null){cstate.close();cstate=null;}}catch(Exception e){}
            try{if (conn!=null){conn.close();conn=null;}}catch(Exception e){}
        }
        
        if (ls==null){
            ls = new ArrayList();
        }
        
      return ls;
  }
    
    private static  List convertList(ResultSet rs) throws SQLException {    
        List list = new ArrayList();    
        ResultSetMetaData md = rs.getMetaData();    
        int columnCount = md.getColumnCount();     
        while (rs.next()) { 
          Map rowData = new HashMap();    
          for (int i = 1; i <= columnCount; i++) {      
              rowData.put(md.getColumnLabel(i), rs.getObject(i));
          }    
          list.add(rowData);    
        }    
        return list;    
    } 
    
    public static boolean executedSql(String sql,Object[] paras) throws Exception{
        Connection conn=null;
        PreparedStatement state=null;
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
            try{state.close();state=null;}catch(Exception e){}
            try{conn.close();conn=null;}catch(Exception e){}
        }
      return ls;
  }
}