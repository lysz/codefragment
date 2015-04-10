Ext.onReady(function(){
	var win = null;
	
	var mainContainer = Ext.getCmp('mainContainer');
	var treeNode = Ext.getCmp('u01');
	

	
	var roleStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
		fields:['roleName', 'roleId'],
		proxy: {
		    type: 'ajax',
		    url: 'user/getAllRoles',
		    actionMethods : 'post',
		    reader: {
		        type: 'json',
		        totalProperty: 'tocalCount',
		        root: 'root'
		    }
		}
	});
		
	var deptStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
		fields:['deptId', 'deptName'],
		proxy: {
		    type: 'ajax',
		    url: 'user/getAllDepts',
		    actionMethods : 'post',
		    reader: {
		        type: 'json',
		        totalProperty: 'tocalCount',
		        root: 'root'
		    }
		}
	});
	
	Ext.apply(Ext.form.VTypes, {
	    repetition: function(val, field) {     //返回true，则验证通过，否则验证失败
	        if (field.repetition) {               //如果表单有使用repetition配置，repetition配置是一个JSON对象，该对象提供了一个名为targetCmpId的字段，该字段指定了需要进行比较的另一个组件ID。
	            var cmp = Ext.getCmp(field.repetition.targetCmpId);   //通过targetCmpId的字段查找组件
	            if (Ext.isEmpty(cmp)) {      //如果组件（表单）不存在，提示错误
	                Ext.MessageBox.show({
	                    title: '错误',
	                    msg: '发生异常错误，指定的组件未找到',
	                    icon: Ext.Msg.ERROR,
	                    buttons: Ext.Msg.OK
	                 });
	                 return false;
	             }
	            
	             if (val == cmp.getValue()) {  //取得目标组件（表单）的值，与宿主表单的值进行比较。
	                 return true;
	             } else {
	                 return false;
	             }
	         }
	     },
	     repetitionText: '两个值不一样'
	 })
	
	//菜单点击事件
	treeNode.on("click", function(){
		mainContainer.removeAll(true);
		
		Ext.apply(Ext.form.field.VTypes,{
			checkUser: function(val, field){
                var result = false;
                
			    Ext.Ajax.request({
			    	async : false,
                    url: 'user/checkUserExistByUserName',
                    method: 'POST',
                    params: { userName: val},
                    success: function(response){
                        var json = Ext.decode(response.responseText);
                        if (json.hasExist){
                            result = false;
                        }else{
                        	result = true;
                        }
                    }
                });
                
			    return result
			},
			checkUserText: '该用户名已存在！'
		});
		
		
		//--------------------------------start form-------------------------
		
		var form = Ext.create('Ext.form.Panel', {
			frame: true,
	        fieldDefaults: {
	            msgTarget: 'side',
	            labelWidth: 35
	        },
	        defaultType: 'textfield',
	        defaults: {
	            margin: "10"
	        },
	        items: [{
	            xtype:'fieldset',
	            title: '查询字段',
	            collapsed: false,
		        items: [{
		            xtype: 'container',
		            anchor: '100%',
		            layout:'column',
		            items: [{
		                xtype: 'container',
		                columnWidth: .2,
		                items: [{
		                    xtype:'textfield',
		                    fieldLabel: '姓名',
		                    name: 'name'
		                }]
		            }, {
		                xtype: 'container',
		                columnWidth: .2,
		                items: [{
							xtype: 'combo', 
							listConfig: {
							    emptyText: '未找到匹配值',
							    maxHeight: 150
							},
							name: 'deptId',
							fieldLabel: '部门',
							store: deptStore,
							displayField: 'deptName',
							valueField: 'deptId',
							triggerAction: 'all',
							editable: false,
							queryMode: 'local',
							forceSelection: true,
							typeAhead: true
		                }]
		            }, {
		                xtype: 'container',
		                columnWidth: .2,
		                items: [{
							xtype: 'combo', 
							listConfig: {
							    emptyText: '未找到匹配值',
							    maxHeight: 150
							},
							name: 'roleId',
							fieldLabel: '角色',
							triggerAction: 'all',
							store: roleStore,
							displayField: 'roleName',
							valueField: 'roleId',
							queryMode: 'local',
							editable: false,
							forceSelection: true,
							typeAhead: true,
							value: ''
		                }]
		            },{
		                xtype: 'container',
		                columnWidth:.4,
		                layout: 'column',
		                bodyStyle:'5',
		                items: [{
		                	xtype: 'button',
		                	columnWidth: .2,
		                	style: {margin: '0px 10px 0px 0px'},
		                	text: '查询',
		                	handler: function(){
		                		gridStore.loadPage(1);
		                	}
		                }, {
		                	xtype: 'button',
		                	columnWidth: .2,
		                	style: {margin: '0px 10px 0px 0px'},
		                	text: '重置',
		                	handler: function(){
		                		form.getForm().reset();
		                	}
		                },{
		                	xtype: 'button',
		                	columnWidth: .2,
		                	text: '新增',
		                	handler: function(){
		                		showWindow('新增用户', 'user/create', true);
		                	}
		                }]
		            }]
		        }]
	        }]
		});
		//-----------------------end form------------------------------------
		
		
		//-----------------------start grid----------------------------------
		
		
		var gridStore = Ext.create("Ext.data.Store", {
			autoLoad: {start: 0, limit: 2},
			fields: ['userId', 'name', 'userName', 'roleName', 'deptName', 'telephone', 'mobilephone', 'email', 'description'],
			pageSize: 2,
			proxy: {
				type: 'ajax',
				actionMethods: {read: 'POST'},
				url: 'user/getAllUsers',
				reader: {
				    type: 'json',
				    root: 'root',
				    totalProperty: 'totalCount'
				}
			},
			listeners: {
				beforeload: function(store,records,options){            		
            		var params = Ext.decode(Ext.encode(form.getForm().getValues()))
            		this.proxy.extraParams = params;
				}
			}
		});
		
		var grid = Ext.create('Ext.grid.Panel', {
			frame: true,
			viewConfig: {
			    forceFit: true,
			    stripeRows: true
			},
			store: gridStore,
			loadMask: true,
			id: 'userGrid',
			columns: [
				new Ext.grid.RowNumberer(),
			    {header: 'id',        width: 50,   dataIndex: 'userId',  hidden: true},
			    {header: '姓名',      width: 100,   dataIndex: 'name', sortable: true},
			    {header: '帐号',      width: 150,   dataIndex: 'userName', sortable: true},
			    {header: '角色',      width: 100,   dataIndex: 'roleName', sortable: true},
			    {header: '部门',      width: 100,   dataIndex: 'deptName', sortable: true},
			    {header: '电话号码',  width: 100,   dataIndex: 'telephone', sortable: true},
			    {header: '手机号码',  width: 100,   dataIndex: 'mobilephone', sortable: true},
			    {header: 'E-Mail',    width: 100,   dataIndex: 'email', sortable: true},
			    {header: '备注',      width: 300,   dataIndex: 'description', sortable: false},
                {
	                menuDisabled: true,
	                sortable: false,
	                xtype: 'actioncolumn',
	                width: 90,
	                items: [{
	                    icon   : 'resources/images/user_edit.png',
	                    iconCls: 'grid-last-column-icon-margin',
	                    tooltip: '编辑',
	                    handler: function(grid, rowIndex, colIndex) {
	                		
	                	    var record = grid.getStore().getAt(rowIndex);
	                		var userId = record.get('userId');
	                		
	                		var params = {userId: userId};
	                		showWindow('修改用户', 'user/getUser', false, params, 'user/update');
	                		
	                    }
	                }, {
	                    icon   : 'resources/images/user_delete.png', 
	                    tooltip: '删除',
	                    handler: function(grid, rowIndex, colIndex) {
	                	    Ext.MessageBox.confirm('警告','你确认删除该数据吗？', function(btn){
	                	    	if(btn == 'yes'){	                	    		
	                	    	    var record = grid.getStore().getAt(rowIndex);
	                	    	    var userId = record.get('userId');
	                	    	    
	                	    	    Ext.Ajax.request({
	                	    	    	url: 'user/delete',
	                	    	    	params: {userId: userId},
	                	    	    	method: 'post',
	                	    	    	success: function(resp,opts){
	                	    	    		gridStore.loadPage(1);
	                	    	    	},
	                	    	    	failure: function(resp, action){
									        switch (action.failureType) {
									            case Ext.form.action.Action.CLIENT_INVALID:
									                Ext.Msg.alert('警告', '操作失败！');
									                break;
									            case Ext.form.action.Action.CONNECT_FAILURE:
									                Ext.Msg.alert('警告', '网络连接错误！');
									                break;
									            case Ext.form.action.Action.SERVER_INVALID:
									               Ext.Msg.alert('警告', action.result.msg);
									        }
	                	    	    	}
	                	    	    });
	                	    	}
	                	    });
	                    }
	                }]
                }
			],
	        bbar:new Ext.PagingToolbar({
	            pageSize: 2, //每页显示几条数据
	            store: gridStore, 
	            displayInfo:true, //是否显示数据信息
	            displayMsg:'显示第 {0} 条到 {1} 条记录，一共  {2} 条', //只要当displayInfo为true时才有效，用来显示有数据时的提示信息，{0},{1},{2}会自动被替换成对应的数据
	            emptyMsg: "没有记录" //没有数据时显示信息
	        })
		});
		//--------------------------------------------------
		
		var panel = Ext.create('Ext.panel.Panel', {
			frame: true,
			bodyPadding: 5,
			autoScroll: true,
    		layout:{
    			type:'vbox',
    			align:'stretch'
    		},
			items: [form, grid]
		});
		
		
		mainContainer.add(panel);
		mainContainer.doLayout();
		
		
		
		/**
		 * 新增/修改用户
		 */
		function showWindow(title, submitURL, isCreate, formLoadParams, updateURL){
			
        	var win = Ext.create('Ext.window.Window', {
		        title: title,
		        closeAction: 'destroy',
		        id: 'addUserWindow',
		        width: 350,
		        height: 370,
		        plain: true,
		        resizable: false,
		        layout: 'fit',
		        modal: true,
		        listeners: {
        			beforeshow: function(){
        		        var userName = win.down('#userName');
        		        Ext.apply(userName, {vtype: 'checkUser'});
        		        Ext.form.VTypes.repetitionText = "输入的两次密码不一样";
        			},
        			afterrender: function(){
        				if (isCreate){
        					return;
        				}
        				
        				var addUserForm = win.getComponent('addUserForm');
                		addUserForm.load({
                			params: formLoadParams,
                			url: submitURL,
                			method: 'POST',
                			waitTitle: '提示',
                			waitMsg: '正在加载，请稍等...',
                			success: function(form, action){
                				
                			},
                			failure: function(form, action){
                				
                			}
                		});
        			}
		        },
		        items: [{
		        	xtype: 'form',
		        	itemId: 'addUserForm',
					frame: true,
					bodyPadding: 5,
				    fieldDefaults: {
				        msgTarget: 'side',
				        allowBlank: false,
				        labelWidth: 55
				    },
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'textfield',
				    items: [{
			            fieldLabel: '姓名',
			            name: 'name',
						blankText: '姓名不能为空'
				    }, {
			            fieldLabel: '帐号',
			            name: 'userName',
			            itemId: 'userName',
			            blankText: '帐号不能为空'
				    }, {
			            fieldLabel: '密码',
			            name: 'password',
			            hidden: isCreate ? false : true, 
			            disabled: isCreate ? false : true,
			            id: 'password',
			            inputType: 'password',
			            blankText: '密码不能为空'
				    }, {
			            fieldLabel: '确认密码',
			            name: 'password1',
			            id: 'password1',
			            hidden: isCreate ? false : true,
			            disabled: isCreate ? false : true,
			            inputType: 'password',
			            blankText: '确认密码不能为空',
			            vtype: 'repetition',
			            repetition: {targetCmpId: 'password'}
				    },{
						xtype: 'combo', 
						listConfig: {
						    emptyText: '未找到匹配值',
						    maxHeight: 150
						},
						name: 'roleId',
						fieldLabel: '角色',
						triggerAction: 'all',
						store: roleStore,
						displayField: 'roleName',
						valueField: 'roleId',
						queryMode: 'local',
						editable: false,
						forceSelection: true,
						typeAhead: true,
						value: '',
						blankText: '角色不能为空'
				    }, {
						xtype: 'combo', 
						listConfig: {
						    emptyText: '未找到匹配值',
						    maxHeight: 150
						},
						name: 'deptId',
						fieldLabel: '部门',
						store: deptStore,
						displayField: 'deptName',
						valueField: 'deptId',
						triggerAction: 'all',
						queryMode: 'local',
						editable: false,
						forceSelection: true,
						typeAhead: true,
						value: '',
						blankText: '部门不能为空'
				    }, {
						fieldLabel: '电话号码',
			            name: 'telephone',
			            blankText: '电话号码不能为空'
				    }, {
						fieldLabel: '手机号码',
			            name: 'mobilephone',
			            blankText: '手机号码不能为空'
				    }, {
						fieldLabel: 'E-Mail',
			            name: 'email',
			            blankText: 'E-Mail不能为空'
				    }, {
			            xtype: 'textareafield',
			            name: 'description',
			            allowBlank: true,
			            fieldLabel: '备注',
			            value: ''
				    }],
			        buttons: [{
			            text: '确定',
			            handler: function() {
			        	    var url = isCreate ? submitURL : updateURL;
			        		var addUserForm = win.getComponent('addUserForm');
			        	    if (addUserForm.isValid()) {
			        	    	
			        	    	if (!isCreate){
			        	    		addUserForm.add(
			        	    			Ext.create("Ext.form.field.Hidden", {
			        	    			    name: "userId",
			        	    			    value: formLoadParams.userId
			        	    			})
			        	    		);
			        	    	}
			        	    	
			                	addUserForm.submit({
			                		url: url, 
			                		method: 'post',
			                		waitTitle : "提示",
			                		waitMsg : '正在提交，请稍后 ……',
				                    success : function(form, action) {  
			                		    win.close();
				                        Ext.getCmp('userGrid').getStore().load();
				                    },  
				                    failure : function(form, action) {
								        switch (action.failureType) {
								            case Ext.form.action.Action.CLIENT_INVALID:
								                Ext.Msg.alert('警告', '表单验证失败！');
								                break;
								            case Ext.form.action.Action.CONNECT_FAILURE:
								                Ext.Msg.alert('警告', '网络连接错误！');
								                break;
								            case Ext.form.action.Action.SERVER_INVALID:
								               Ext.Msg.alert('警告', action.result.msg);
								        }
								        
								        win.close();
				                    }
			                	});
			                }
			            }
			        }, {
			            text: '取消',
			            handler: function() {
			                win.close();
			            }
			        }]
		        }]
		    });
        	
           win.doLayout();
           win.show();        
    }
		
	}, this);
});