function downloadfile(fileName, url){
	
    var form = Ext.create('Ext.form.Panel', {
        standardSubmit: true,
        method: "POST"
    });
    
    form.submit({
        target: '_blank', // Avoids leaving the page. 
        url: url,
        params: {fileName: fileName}
    });
    
    Ext.defer(function(){
        form.close();
    }, 100);
}

Ext.onReady(function(){
	
	
    var mainContainer = Ext.getCmp('mainContainer');
    var treeNode = Ext.getCmp('t02');
    
    var manContainerWidth = mainContainer.getWidth();
    
        
    treeNode.on('click', function(){
    	mainContainer.removeAll(true);
    	
    	var user = global_parameter_user;
    	
    	
	    var companyStore = Ext.create('Ext.data.Store', {
	        autoLoad: true,
	        fields:['companyName', 'companyId'],
	        proxy: {
	            type: 'ajax',
	            url: 'partner/getPartners',
	            actionMethods : 'post',
	            reader: {
	                type: 'json',
	                totalProperty: 'tocalCount',
	                root: 'root'
	            }
	        }
	    });
	    
	    var bankStore = Ext.create('Ext.data.Store', {
	        autoLoad: true,
	        fields:['dictId', 'dictName'],
	        proxy: {
	            type: 'ajax',
	            url: 'contract/getAllBanks',
	            actionMethods : 'post',
	            reader: {
	                type: 'json',
	                totalProperty: 'tocalCount',
	                root: 'root'
	            }
	        }
	    });
    	
    	var searchForm = Ext.create('Ext.form.Panel', {
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
                items: [{
                	xtype: 'container',
                	anchor: '100%',
                    layout: {
                        type: 'table',
                        columns: 5
                    },
                    defaults: {
                        margin: '10'
                    },
                    items: [{
                    	xtype: 'container',
                    	items: [{
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'company',
                            fieldLabel: '公司名称',
                            store: companyStore,
                            displayField: 'companyName',
                            valueField: 'companyId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择公司...',
                            width: 200
                    	}]
                    }, {
                    	xtype: 'container',
                    	items: [{
                            width: 200,
                            xtype: 'textfield',
                            fieldLabel: '合同编号',
                            name: 'contractCode'
                    	}]
                    }, {
                        xtype: 'container',
                        items: [{
                            width: 200,
                            xtype: 'textfield',
                            fieldLabel: '户头(帐户信息)',
                            name: 'userName'
                        }]
                    }, {
                    	xtype: 'container',
                    	items: [{                    		
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'bank',
                            fieldLabel: '开户行(帐户信息)',
                            store: bankStore,
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择开户行...',
                            width: 200
                    	}]
                    }, {
                        xtype: 'container',
                        items: [{
                            width: 200,
                            xtype: 'textfield',
                            fieldLabel: '帐号(帐户信息)',
                            name: 'accountNumber'
                        }]
                    }, {
                        xtype: 'container',
                        items: [{
                        	name: 'startDate',
                            fieldLabel: '生效日期',
                            xtype: 'datefield',
                            msgTarget: 'side',
                            maxValue: new Date(),
                            autoFitErrors: false,
                            format: 'Y-m-d',
                            width: 200,
                            value: ''
                        }]
                    },{
                        xtype: 'container',
                        items: [{
                        	name: 'endDate',
                            fieldLabel: '失效日期',
                            xtype: 'datefield',
                            msgTarget: 'side',
                            autoFitErrors: false,
                            maxValue: new Date(),
                            format: 'Y-m-d',
                            width: 200,
                            value: ''
                        }]
                    }, {
                    	xtype: 'container',
                    	colspan: 2,
                    	items: [{
	                        xtype: 'button',
	                        text: '查询',
	                        itemId: 'btn_searchContract',
	                        width: 100,
	                        request: 'contract/getAllContract',
	                        handler: function(){
	                            companyGridStore.loadPage(1);
	                        }
                    	}, {
                            xtype: 'button',
                            text: '重置',
                            width: 100,
                            style: {margin: '0px 15px 0px 15px'},
                            handler: function(){
                                searchForm.getForm().reset();
                            }
                    	}, {
	                        xtype: 'button',
	                        text: '新增',
	                        itemId: 'btn_createContract',
	                        width: 100,
	                        request: 'contract/saveOrUpdate',
	                        handler: function(){
                    		    
	                            showWindow('新增合同', 'contract/saveOrUpdate', true);
	                        }
                    	}]
                    }]
                }]
            }],
            listeners: {
                'render': function(){
                    var searchBtn = searchForm.queryById("btn_searchContract");
                    var createBtn = searchForm.queryById("btn_createContract");
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
            autoLoad: {start: 0, limit: 3},
            fields:['contractId', 'companyName', 'contractCode', 'attachmentPath', 'startTime', 'endTime', 'contact', 'telephone', 'searchURL', 'searchpwd', 'publicUserName', 'publicBankName', 'publicAccount', 'privateUserName', 'privateBankName', 'privateAccount', 'lastChangeDateStr', 'description'],
            pageSize: 3,
            proxy: {
                type: 'ajax',
                url: 'contract/getAllContract',
                actionMethods: {read: 'POST'},
                reader: {
                    type: 'json',
                    totalProperty: 'totalCount',
                    root: 'root'
                }
            },
            listeners: {
                beforeload: function(store,records,options){                    
                    var params = Ext.decode(Ext.encode(searchForm.getForm().getValues()))
                    this.proxy.extraParams = params;
                }
            }
        });
    	

        
        
        var grid = Ext.create('Ext.grid.Panel', {
            frame: true,
            anchor: '100%',
            columnLines : true,
            viewConfig: {
        	    forceFit : false,
        	    scroll:'horizontal',
        	    anchor: '100%',
        	    autoScroll:true
            },
            loadMask: true,
            store: companyGridStore,
            columns: [
                new Ext.grid.RowNumberer(),
                {header: 'contractId', dataIndex: 'contractId',  hidden: true},
                {header: '公司名称', flex: 1,  dataIndex: 'companyName', sortable: true},
                {header: '合同编号', flex: 1.5, dataIndex: 'contractCode', sortable: true},
                {header: '附件下载', flex: 1, dataIndex: 'attachmentPath', 
                	renderer: function(val){
                	    
                	    var s = val.replace(/(\\|\/)/g, "#");
                	    var arr = s.split("#");
                	    var fileName = arr[arr.length - 1];
                	    
                	    var path = val.replace(/\\/g, "/");
                	    
                	    return  '<a href="javascript: void(0)" onclick="downloadfile(\'' + path + '\', \'' + "contract/downloadContract" + '\');" >' + fileName + '</a>';
                }},
                {header: '合同生效日期', flex: 1.5, dataIndex: 'startTime' },
                {header: '合同失效日期', flex: 1.5, dataIndex: 'endTime' },
                {header: '联系人', flex: 1,  dataIndex: 'contact' },
                {header: '电话',  flex: 1,  dataIndex: 'telephone' },
                {header: '查询后台', flex: 1, dataIndex: 'searchURL' },
                {header: '查询密码', flex: 1,  dataIndex: 'searchpwd' },
                {header: '结算帐号(对公)', flex: 1,  dataIndex: 'publicAccount' },
                {header: '户头(对公)', flex: 1,  dataIndex: 'publicUserName'},
                {header: '银行(对公)', flex: 1, dataIndex: 'publicBankName' },
                
                {header: '结算帐号(对私)', flex: 1,  dataIndex: 'privateAccount' },
                {header: '户头(对私)', flex: 1, dataIndex: 'privateUserName'},
                {header: '银行(对私)', flex: 1, dataIndex: 'privateBankName' },
                
                {header: '修改时间', flex: 1, dataIndex: 'lastChangeDateStr'},
                {header: '备注',  flex: 1, dataIndex: 'description'},
                {
                    menuDisabled: true,
                    sortable: false,
                    xtype: 'actioncolumn',
                    width: 90,
                    items: [{
                        icon   : 'resources/images/user_edit.png',
                        //iconCls: 'grid-last-column-icon-margin',
                        tooltip: '编辑',
                        request: 'contract/saveOrUpdate',
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
                            var contractId = record.get('contractId');
                            
                            var params = {contractId: contractId};
                            showWindow('修改合同', 'contract/getContract', false, params, 'contract/saveOrUpdate');
                            
                        }
                    }]
                }
            ],
            bbar:new Ext.PagingToolbar({
                pageSize: 3, //每页显示几条数据
                store: companyGridStore, 
                displayInfo:true, //是否显示数据信息
                displayMsg:'显示第 {0} 条到 {1} 条记录，一共  {2} 条', //只要当displayInfo为true时才有效，用来显示有数据时的提示信息，{0},{1},{2}会自动被替换成对应的数据
                emptyMsg: "没有记录" //没有数据时显示信息
            })
        });
        
        var panel = Ext.create('Ext.panel.Panel', {
            frame: true,
            bodyPadding: 5,
            width: manContainerWidth,
            autoScroll: true,
            layout:{
                type:'vbox',
                align:'stretch'
            },
            items: [searchForm, grid]
        });
        
        mainContainer.add(panel);
        mainContainer.doLayout();
        
        
        
        function showWindow(title, submitURL, isCreate, formLoadParams, updateURL){
        	var win = Ext.create('Ext.window.Window',{
                    title: title,
                    closeAction: 'destroy',
                    id: 'addContractWindow',
                    width: 350,
                    height: 590,
                    plain: true,
                    resizable: false,
                    layout: 'fit',
                    modal: true,
	                listeners: {
	                    beforeshow: function(){
	                        if (isCreate){
	                            return;
	                        }
	                        
	                        var addContractForm = win.getComponent('addContractForm');
	                        addContractForm.load({
	                            params: formLoadParams,
	                            url: submitURL,
	                            method: 'POST',
	                            waitTitle: '提示',
	                            waitMsg: '正在加载，请稍等...',
	                            success: function(form, action){
	                        	
	                        	    addContractForm.queryById('attachment').setRawValue(action.result.data.attachment);
	                            },
	                            failure: function(form, action){
	                                
	                            }
	                        });
	                    }
	                },
                    items: [{
                        xtype: 'form',
                        itemId: 'addContractForm',
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
	                        xtype: 'container',
	                        layout: 'form',
	                        defaults: {
	                            anchor: '100%'
	                        },
	                        items: [{
	                            xtype: 'combo', 
	                            listConfig: {
	                                emptyText: '未找到匹配值',
	                                maxHeight: 150
	                            },
	                            name: 'companyId',
	                            fieldLabel: '公司名称',
	                            store: companyStore,
	                            displayField: 'companyName',
	                            valueField: 'companyId',
	                            triggerAction: 'all',
	                            editable: false,
	                            queryMode: 'local',
	                            forceSelection: true,
	                            typeAhead: true,
	                            emptyText: '选择公司...'
	                        }]
                        }, {
                        	fieldLabel: '合同编号',
                        	name: 'contractCode'
                        }, {
                            xtype: 'hidden',
                            name: 'contractIdStr'
                        }, {
			                xtype: 'fieldcontainer',
			                fieldLabel: '起始日期',
			                layout: 'column',
			                items: [{
			                	columnWidth: .5,
			                	hideLabel: true,
		                        xtype     : 'datefield',
		                        name      : 'startDate',
		                        allowBlank: false,
	                            maxValue: new Date(),
	                            autoFitErrors: false,
	                            format: 'Y-m-d'
			                }, {
			                	fieldLabel: '到',
			                	columnWidth: .5,
			                	labelWidth: 15,
			                	labelSeparator: '',
		                        xtype     : 'datefield',
		                        name      : 'endDate',
		                        allowBlank: false,
	                            maxValue: new Date(),
	                            autoFitErrors: false,
	                            format: 'Y-m-d'
			                }]
			            }, {
                            columnWidth: .8,
                            itemId: 'attachment',
                            fieldLabel: '合同附件',
                            xtype: 'filefield',
                            emptyText: '请选择附件...',
                            name: 'attachment',
                            buttonText: '',
                            buttonConfig: {
                                iconCls: 'upload-icon'
                            }
			            }, {
                            fieldLabel: '联系人',
                            name: 'contact'
			            }, {
                            fieldLabel: '电话',
                            name: 'telephone'
			            }, {
                            fieldLabel: '查询后台',
                            name: 'searchURL'
			            }, {
                            fieldLabel: '密码',
                            name: 'searchpwd'
			            }, {
			                xtype:'fieldset',
			                title: '结算帐户信息(对公)',
			                collapsed: false,
			                items: [{
			                	xtype: 'container',
			                	layout: 'form',
			                	items: [{
			                		xtype: 'hidden',
			                		name: 'publicAccountId'
			                	},{
			                		xtype: 'textfield',
			                		fieldLabel: '户头',
			                		name: 'publicUserName'
			                	}, {                                    
                                    xtype: 'combo', 
                                    listConfig: {
                                        emptyText: '未找到匹配值',
                                        maxHeight: 150
                                    },
                                    name: 'publicBank',
                                    fieldLabel: '开户行',
                                    store: bankStore,
                                    displayField: 'dictName',
                                    valueField: 'dictId',
                                    triggerAction: 'all',
                                    editable: false,
                                    queryMode: 'local',
                                    forceSelection: true,
                                    typeAhead: true,
                                    emptyText: '选择开户行...'
			                	}, {
                                    xtype: 'textfield',
                                    fieldLabel: '帐号',
                                    name: 'publicAccount'
			                	}]
			                }]
			            }, {
                            xtype:'fieldset',
                            title: '结算帐户信息(对私)',
                            collapsed: false,
                            items: [{
                                xtype: 'container',
                                layout: 'form',
                                items: [{
                                    xtype: 'hidden',
                                    name: 'privateAccountId'
                                }, {
                                    xtype: 'textfield',
                                    fieldLabel: '户头',
                                    name: 'privateUserName'
                                }, {
		                            xtype: 'combo', 
		                            listConfig: {
		                                emptyText: '未找到匹配值',
		                                maxHeight: 150
		                            },
		                            name: 'privateBank',
		                            fieldLabel: '开户行',
		                            store: bankStore,
		                            displayField: 'dictName',
		                            valueField: 'dictId',
		                            triggerAction: 'all',
		                            editable: false,
		                            queryMode: 'local',
		                            forceSelection: true,
		                            typeAhead: true,
		                            emptyText: '选择开户行...'
                                }, {
                                    xtype: 'textfield',
                                    fieldLabel: '帐号',
                                    name: 'privateAccount'
                                }]
                            }]
			            }, {
	                        xtype: 'textareafield',
	                        name: 'description',
	                        allowBlank: true,
	                        fieldLabel: '备注',
	                        value: ''
			            }]
                    }],
                    buttons: [{
                    	xtype:'button',
                    	text: '确定',
                    	handler: function(){
                            var url = isCreate ? submitURL : updateURL;
                            var addContractForm = win.getComponent('addContractForm');
							if (addContractForm.isValid()) {
							    addContractForm.submit({
	                                url: url, 
	                                method: 'post',
	                                headers : {'Content-Type':'multipart/form-data; charset=UTF-8'},
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
                        xtype:'button',
                        text: '取消',
                        handler: function(){
                            win.close();
                        }
                    }]
        	});
        	
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
    });
});