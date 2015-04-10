package com.aora.wifi.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class SysAccountDaoImpl extends BaseDao{


	public List getInUsedWIFIAccount(String loginname){
		
		Map list=new HashMap();
		List para =new ArrayList();
		List ls=null;
		try {
			ls =this.getData("select * from tbl_wifi_account where (status=2 || status=3) and used_client_id=?", new String[]{loginname});
			//ls=getSqlMapClientTemplate().queryForList("system.getInUsedWIFIAccount", loginname);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ls;		
	}	


	
}
