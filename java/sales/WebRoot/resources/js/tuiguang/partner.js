Ext.onReady(function(){
	
	var mainContainer = Ext.getCmp('mainContainer');
	var treeNode = Ext.getCmp('t01');
	
	treeNode.on("click", function(){
		
		mainContainer.removeAll(true);
		
		var user = global_parameter_user;
		
		var typeStore = Ext.create('Ext.data.Store', {
			autoLoad: true,
			fields:['categoryName', 'categoryId'],
			proxy: {
			    type: 'ajax',
			    url: 'partner/getAllCategorys',
			    actionMethods : 'post',
			    reader: {
			        type: 'json',
			        totalProperty: 'tocalCount',
			        root: 'root'
			    }
			}
		});
		
		var form = Ext.create('Ext.form.Panel', {
			frame: true,
			bodyStyle:'padding:5px 5px 0',
	        fieldDefaults: {
	            msgTarget: 'side',
	            labelWidth: 55
	        },
	        defaultType: 'textfield',
	        defaults: {
	            anchor: '100%'
	        },
			items: [{
	            xtype:'fieldset',
	            title: '查询字段',
	            collapsed: false,
	            layout: 'anchor',
	            defaults: {
	                anchor: '100%'
	            },
	            items: [{
		            xtype: 'container',
		            anchor: '100%',
		            layout:'column',
					items: [{
						xtype: 'container',
						columnWidth: .25,
						layout: 'anchor',
						border: false,
						items: [{xtype: 'textfield', name: 'companyName', fieldLabel: '公司名称'}]
					}, {
						xtype: 'container',
						columnWidth: .25,
						layout: 'anchor',
						border: false,
						items: [{
							xtype: 'combo', 
							listConfig: {
							    emptyText: '未找到匹配值',
							    maxHeight: 150
							},
							name: 'categoryId',
							fieldLabel: '公司类型',
							triggerAction: 'all',
							store: typeStore,
							displayField: 'categoryName',
							valueField: 'categoryId',
							queryMode: 'local',
							editable: false,
							forceSelection: true,
							typeAhead: true,
							value: ''
						}]
					}, {
						xtype: 'container',
						columnWidth: .1,
						layout: 'anchor',
						border: false,
						items: [{
							xtype: 'button', 
							text: '查询',
							itemId: 'btn_searchPartner',
							request: 'partner/getAllPartners',
							style: {margin: '0px 10px 0px 0px'},
							width: 100,
							handler: function(){
								companyGridStore.loadPage(1);
							}
						}]
					}, {
						xtype: 'container',
						columnWidth: .1,
						layout: 'anchor',
						border: false,
						items: [{
							xtype: 'button', 
							text: '重置',
							style: {margin: '0px 10px 0px 0px'},
							width: 100,
							handler: function(){
								form.getForm().reset();
							}
						}]
					}, {
						xtype: 'container',
						columnWidth: .1,
						layout: 'anchor',
						border: false,
						items: [{
							xtype: 'button', 
							text: '新增',
							style: {margin: '0px 10px 0px 0px'},
							width: 100,
							itemId: 'btn_createPartner',
							request: 'partner/create',
							handler: function(){
								showWindow('新增推广使用商', 'partner/create', true);
							}
						}]
					}]
	            }]
			}],
            listeners: {
                'render': function(){
                    var searchBtn = form.queryById("btn_searchPartner");
                    var createBtn = form.queryById("btn_createPartner");
                    var searchURL = searchBtn.request;
                    var createURL = createBtn.request;
                    
                    var hasSearchPerssion = false;
                    var hasCreatePerssion = false;
                    var tmpURL;
                    for (var i = 0; i < user.permissions.length; i++){
                        tmpURL = user.permissions[i].url;
                        if(tmpURL == searchURL){
                            hasSearchPerssion = true;
                        }else if (tmpURL == createURL){
                            hasCreatePerssion = true;
                        }
                    }
                    
                    if (!hasSearchPerssion){
                        searchBtn.hide();
                    }
                    
                    if (!hasCreatePerssion){
                        createBtn.hide();
                    }
                }
            }
		});
		

		var companyGridStore = Ext.create('Ext.data.Store', {
			autoLoad: {start: 0, limit: 2},
			fields:['companyId', 'companyName', 'categoryName', 'contact', 'telephone', 'mobilephone', 'email', 'lastChangeDateStr', 'description'],
			pageSize: 2,
			proxy: {
			    type: 'ajax',
			    url: 'partner/getAllPartners',
			    actionMethods: {read: 'POST'},
			    reader: {
			        type: 'json',
			        totalProperty: 'totalCount',
			        root: 'root'
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
			loadMask: true,
			store: companyGridStore,
			columns: [
				new Ext.grid.RowNumberer(),
			    {header: 'companyId', dataIndex: 'companyId',  hidden: true},
			    {header: '公司名称', width: 150, dataIndex: 'companyName', sortable: true},
			    {header: '公司类型', width: 90, dataIndex: 'categoryName', sortable: true},
			    {header: '联系人', width: 100, dataIndex: 'contact' },
			    {header: '联系电话', width: 130, dataIndex: 'telephone' },
			    {header: '手机号码', width: 100, dataIndex: 'mobilephone' },
			    {header: 'E-Mail', width: 150, dataIndex: 'email' },
			    {header: '修改时间', width: 150, dataIndex: 'lastChangeDateStr' },
			    {header: '备注',     width: 200, dataIndex: 'description' },
                {
	                menuDisabled: true,
	                sortable: false,
	                xtype: 'actioncolumn',
	                width: 90,
	                items: [{
	                    icon   : 'resources/images/user_edit.png',
	                    //iconCls: 'grid-last-column-icon-margin',
	                    tooltip: '编辑',
                        request: 'partner/update',
                        getClass: function(v, metadata, r, rowIndex, colIndex, store){
                            
                            //权限控制, 返回class样式
                            var cls = showOrHideIcon(this.items[0]);
                            if (undefined != cls){
                               return cls;
                            }else{
                               return 'grid-last-column-icon-margin';
                            }                            
                        },
	                    handler: function(grid, rowIndex, colIndex) {
	                		
	                	    var record = grid.getStore().getAt(rowIndex);
	                		var companyId = record.get('companyId');
	                		
	                		var params = {companyId: companyId};
	                		showWindow('修改推广合作商', 'partner/getPartner', false, params, 'partner/update');
	                		
	                    }
	                }]
                }
			],
	        bbar:new Ext.PagingToolbar({
	            pageSize: 2, //每页显示几条数据
	            store: companyGridStore, 
	            displayInfo:true, //是否显示数据信息
	            displayMsg:'显示第 {0} 条到 {1} 条记录，一共  {2} 条', //只要当displayInfo为true时才有效，用来显示有数据时的提示信息，{0},{1},{2}会自动被替换成对应的数据
	            emptyMsg: "没有记录" //没有数据时显示信息
	        })
		});
		
		
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
	    
	    
	    function showWindow(title, submitURL, isCreate, formLoadParams, updateURL){
	     	var win = Ext.create('Ext.window.Window', {
			        title: title,
			        closeAction: 'destroy',
			        id: 'addCompanyWindow',
			        width: 300,
			        height: 330,
			        plain: true,
			        resizable: false,
			        layout: 'fit',
			        modal: true,
			        listeners: {
	        			afterrender: function(){
	        				if (isCreate){
	        					return;
	        				}
	        				
	        				var addUserForm = win.getComponent('addPartnerForm');
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
			        	itemId: 'addPartnerForm',
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
				            fieldLabel: '公司名称',
				            name: 'companyName',
							blankText: '公司名称不能为空'
					    }, {
							xtype: 'combo', 
							listConfig: {
							    emptyText: '未找到匹配值',
							    maxHeight: 150
							},
							name: 'categoryId',
							fieldLabel: '公司类型',
							triggerAction: 'all',
							store: typeStore,
							displayField: 'categoryName',
							valueField: 'categoryId',
							queryMode: 'local',
							editable: false,
							forceSelection: true,
							typeAhead: true,
							blankText: '公司类型不能为空',
							value: ''
					    }, {
				            fieldLabel: '联系人',
				            name: 'contact',
				            blankText: '联系人不能为空'
					    }, {
				            fieldLabel: '电话号码',
				            name: 'telephone',
				            blankText: '电话号码不能为空'
					    }, {
							fieldLabel: '手机号码',
				            name: 'mobilephone',
				            allowBlank: true
					    }, {
							fieldLabel: 'E-Mail',
							allowBlank: true,
				            name: 'email'
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
				        		var addUserForm = win.getComponent('addPartnerForm');
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
				                		    companyGridStore.load();
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
	    
        function showOrHideIcon(iconBtn){
            var url = iconBtn.request;
            var hasPermission = false;
            
            for (var i = 0; i < user.permissions.length; i++){
                var tmpURL = user.permissions[i].url;
                
                if(tmpURL == url){
                    hasPermission = true;
                    break;
                }
            }
            
            if (!hasPermission){
                return 'x-hide-visibility';
            }
        }
	    
	}, this);
});