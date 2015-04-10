Ext.onReady(function(){
    var mainContainer = Ext.getCmp('mainContainer');
    
    var s01 = Ext.getCmp("s01");
    var s02 = Ext.getCmp("s02");
    var s03 = Ext.getCmp("s03");
    var s04 = Ext.getCmp("s04");
    var s05 = Ext.getCmp("s05");
    
    s01.on('click', appUploadApply);
    s02.on('click', appUploadApply);
    s03.on('click', appUploadApply);
    s04.on('click', appUploadApply);
    s05.on('click', appUploadApply);
        
    function appUploadApply(eventSource, event){
        var from = eventSource.name;
                
        var user = global_parameter_user;
            
        mainContainer.removeAll(true);
        
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
        
        
        var spreadMonthStore = Ext.create('Ext.data.Store', {
            autoLoad: true,
            fields:['dictName', 'dictId'],
            proxy: {
                type: 'ajax',
                url: 'appupload/getSpreadMonth',
                actionMethods : 'post',
                reader: {
                    type: 'json',
                    totalProperty: 'tocalCount',
                    root: 'root'
                }
            }
        });
        
        var invoiceCategoryStore = Ext.create('Ext.data.Store', {
            autoLoad: true,
            fields:['dictName', 'dictId'],
            proxy: {
                type: 'ajax',
                url: 'appupload/getDictByType',
                extraParams: {dictType: "invoicecategory"},
                actionMethods : 'post',
                reader: {
                    type: 'json',
                    totalProperty: 'tocalCount',
                    root: 'root'
                }
            }
        });
        
        var settlementStatusStore = Ext.create('Ext.data.Store', {
            autoLoad: true,
            fields:['dictName', 'dictId'],
            proxy: {
                type: 'ajax',
                url: 'appupload/getDictByType',
                extraParams: {dictType: "settlementstatus"},
                actionMethods : 'post',
                reader: {
                    type: 'json',
                    totalProperty: 'tocalCount',
                    root: 'root'
                }
            }
        });
        
        //合作状态
        var friendStatusStore = Ext.create('Ext.data.Store', {
            autoLoad: true,
            fields:['dictName', 'dictId'],
            proxy: {
                type: 'ajax',
                url: 'appupload/getDictByType',
                extraParams: {dictType: "appstatus"},
                actionMethods : 'post',
                reader: {
                    type: 'json',
                    totalProperty: 'tocalCount',
                    root: 'root'
                }
            }
        });
             
    //--------------------------- 搜索框开始 ------------------------------------
        var searchForm = Ext.create('Ext.form.Panel', {
            frame: true,
            fieldDefaults: {
                msgTarget: 'side',
                labelWidth: 70
            },
            defaultType: 'textfield',
            items: [{
                xtype: 'fieldset',
                title: '查询字段',
                collapsed: false,
                items: [{
                    anchor: '100%',
                    xtype: 'container',
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
                            width: 200,
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
                        xtype: 'container',
                        items: [{
                        	width: 200,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'companyCategoryId',
                            fieldLabel: '公司类型',
                            triggerAction: 'all',
                            store: typeStore,
                            displayField: 'categoryName',
                            valueField: 'categoryId',
                            queryMode: 'local',
                            editable: false,
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择公司类型...'
                        }]
                    }, {
                        xtype: 'container',
                        items: [{
                            width: 210,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'settlementstatus',
                            fieldLabel: '结算状态',
                            store: settlementStatusStore,
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '请选择结算状态...'
                        }]
                    }, {
                        width: 200,
                        xtype: 'textfield',
                        fieldLabel: '发票编号',
                        name: 'invoiceCode'
                    }, {
                        xtype:'hidden',
                        name:'from',
                        value: from
                    }, {
                        xtype:'container',
                        items: [{
                            width: 200,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'invoiceCategory',
                            itemId: 'invoiceCategory',
                            fieldLabel: '发票形式',
                            store: invoiceCategoryStore,
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择发票形式...',
                            blankText: '请选发票形式'
                        }]
                    }, {
                        xtype:'container',
                        items: [{
                            width: 200,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'billMonth',
                            itemId: 'billMonth',
                            fieldLabel: '帐单月份',
                            store: {
					            autoLoad: true,
					            fields:['billMonth', 'billMonth'],
					            proxy: {
					                type: 'ajax',
					                url: 'settlementApply/getAllBillMonth',
					                actionMethods : 'post',
					                reader: {
					                    type: 'json',
					                    totalProperty: 'tocalCount',
					                    root: 'root'
					                }
					            }
                            },
                            displayField: 'billMonth',
                            valueField: 'billMonth',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择帐单月份...',
                            blankText: '请选帐单月份'
                        }]
                    }, {
                        xtype: 'fieldcontainer',
                        fieldLabel: '起始日期',
                        layout: 'column',
                        colspan: 2,
                        items: [{
                            columnWidth: .5,
                            hideLabel: true,
                            width: 150,
                            xtype     : 'datefield',
                            name      : 'startDate',
                            maxValue: new Date(),
                            autoFitErrors: false,
                            format: 'Y-m-d'
                        }, {
                            fieldLabel: '到',
                            columnWidth: .5,
                            labelWidth: 18,
                            width: 100,
                            labelSeparator: '',
                            xtype     : 'datefield',
                            name      : 'endDate',
                            maxValue: new Date(),
                            autoFitErrors: false,
                            format: 'Y-m-d'
                        }]
                    }, {
                        xtype: 'container',
                        colspan: 2,
                        items: [{
                            xtype: 'button',
                            width: 100,
                            style: {margin: '0px 10px 0px 0px'},
                            request: 'appupload/getApps',
                            itemId: 'btn_getApps',
                            text: '查询',
                            handler: function(){
                                appGridStore.loadPage(1);
                            }
                        }, {
                            xtype: 'button',
                            width: 100,
                            style: {margin: '0px 10px 0px 0px'},
                            text: '重置',
                            handler: function(){
                                searchForm.getForm().reset();
                            }
                        }]
                    }]
                }]
            }]
        });
    //--------------------------- 搜索框结束 ----------------------------------------    

        
    //--------------------------- Grid panel ---------------------------------------- 
        
        var showSettlementApplyGridStore = Ext.create('Ext.data.Store', {
            autoLoad: {start: 0, limit: 15},
            fields:['billId', 'companyId', 'companyName', 'companyCategoryName', 'billMonth', 'totalMoney', 'requiredMoney', 'actualMoney', 'statusName', 'invoicecategory', 'lastChangeDateStr'],
            pageSize: 15,
            proxy: {
                type: 'ajax',
                url: 'settlementApply/showSettlementApply',
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
            region:'center',
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
            store: showSettlementApplyGridStore,
            columns: [
                new Ext.grid.RowNumberer(),
                {header: 'billId', dataIndex: 'billId',  hidden: true},
                {header: 'companyId', dataIndex: 'companyId',  hidden: true},
                {header: '公司名称', flex: 0.6,  dataIndex: 'companyName', sortable: true},
                {header: '公司类型', flex: 0.4, dataIndex: 'companyCategoryName', sortable: true},
                {header: '帐单月', flex: 0.4, dataIndex: 'billMonth' },
                {header: '流水汇总', flex: 0.4, dataIndex: 'totalMoney' },
                {header: '应收帐款', flex: 0.4, dataIndex: 'requiredMoney' },
                {header: '结算金额', flex: 0.4,  dataIndex: 'actualMoney' },
                {header: '结算状态',  flex: 0.5,  dataIndex: 'statusName' },
                {header: '发票形式',  flex: 0.6,  dataIndex: 'invoicecategory' },
                {header: '修改时间', flex: 0.7, dataIndex: 'lastChangeDateStr' },
                {
                    menuDisabled: true,
                    sortable: false,
                    xtype: 'actioncolumn',
                    itemId: 'actionList',
                    width: 90,
                    items: [{
                        icon   : 'resources/images/select.png',
                        iconCls: 'grid-last-column-icon-margin',
                        tooltip: '查看明细',
                        request: 'appupload/auditAppOfflineForBusiness',
                        handler: function(grid, rowIndex, colIndex) {
                            var record = grid.getStore().getAt(rowIndex);
                            var billId = record.get('billId');
                            var companyId = record.get('companyId');
                            var billMonth = record.get('billMonth');
                            
                            showDetails(billId, companyId, billMonth);
                        	
                        }
                    }, {
                        icon   : 'resources/images/apply_audit.png',
                        tooltip: '商务提交审核',
                        request: 'settlementApply/auditSettlementApplyForBusiness',
                        getClass: function(v, metadata, r, rowIndex, colIndex, store){
                    	    var status = r.get('statusName');
                            if (status != '商务未提交')
                                return 'x-hide-visibility';
                            
                            //权限控制, 返回class样式来决定按钮是否隐藏
                            var cls = showOrHideIcon(this.items[1]);
                            if (undefined != cls){
                               return cls;
                            }  
                        },
                        handler: function(grid, rowIndex, colIndex) {
                            var record = grid.getStore().getAt(rowIndex);
                            var billId = record.get('billId');
                        	auditSettlementApply(billId, grid);
                        }
                    }, {
                        icon   : 'resources/images/apply_audit.png',
                        tooltip: '待运营审核',
                        request: 'settlementApply/auditSettlementApplyForOperation',
                        getClass: function(v, metadata, r, rowIndex, colIndex, store){
                            var status = r.get('statusName');
                            if (status != '待运营审核')
                            	return 'x-hide-visibility';
                            
                            //权限控制, 返回class样式
                            var cls = showOrHideIcon(this.items[2]);
                            if (undefined != cls){
                               return cls;
                            }  
                        },
                        handler: function(grid, rowIndex, colIndex) {
                            var record = grid.getStore().getAt(rowIndex);
                            var billId = record.get('billId');
                            auditSettlement(billId, grid);
                        }
                    }, {
                        icon   : 'resources/images/apply_audit.png',
                        tooltip: '待运营总监审核',
                        request: 'settlementApply/auditSettlementApplyForOperationDirector',
                        getClass: function(v, metadata, r, rowIndex, colIndex, store){
                            var status = r.get('statusName');
                            if (status != '待运营总监审核')
                                return 'x-hide-visibility';
                            
                            //权限控制, 返回class样式
                            var cls = showOrHideIcon(this.items[3]);
                            if (undefined != cls){
                               return cls;
                            }  
                        },
                        handler: function(grid, rowIndex, colIndex) {
                            var record = grid.getStore().getAt(rowIndex);
                            var billId = record.get('billId');
                            auditSettlement(billId, grid);
                        }
                    }]
                }
            ],
            bbar:new Ext.PagingToolbar({
                pageSize: 15, //每页显示几条数据
                store: showSettlementApplyGridStore, 
                displayInfo:true, //是否显示数据信息
                displayMsg:'显示第 {0} 条到 {1} 条记录，一共  {2} 条', //只要当displayInfo为true时才有效，用来显示有数据时的提示信息，{0},{1},{2}会自动被替换成对应的数据
                emptyMsg: "没有记录" //没有数据时显示信息
            })
        });
        
        
        var panel = Ext.create('Ext.panel.Panel', {
            frame: true,
            bodyPadding: 5,
            autoScroll: true,
            autoScroll: true,
            layout:{
                type:'vbox',
                align:'stretch'
            },
            items: [searchForm, grid]
        });
        
        
        mainContainer.add(panel);
        mainContainer.doLayout();
        
        
        
        
        
     //-----------------------------------------------------------------------------  
     //        detail   
     //-----------------------------------------------------------------------------   
                
        function showDetails(billId, companyId, billMonth){
        	
	        var detailSearchForm = Ext.create('Ext.form.Panel', {
	            frame: true,
	            fieldDefaults: {
	                msgTarget: 'side',
	                labelWidth: 70
	            },
	            defaultType: 'textfield',
	            items: [{
	                xtype: 'fieldset',
	                title: '查询字段',
	                collapsed: false,
	                items: [{
	                    anchor: '100%',
	                    xtype: 'container',
	                    layout: {
	                        type: 'table',
	                        columns: 5
	                    },
	                    defaults: {
	                        margin: '10'
	                    },
	                    items: [{
                            xtype:'hidden',
                            name:'billId',
                            value: billId
	                    }, {
                            width: 200,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'appId',
                            fieldLabel: '应用名称',
                            store: {
                                autoLoad: true,
                                fields:['appName', 'appId'],
                                proxy: {
                                    type: 'ajax',
                                    url: 'appupload/getAllOnlineAppsByCompanyId',
                                    extraParams: {companyId: companyId},
                                    actionMethods : 'post',
                                    reader: {
                                        type: 'json',
                                        totalProperty: 'tocalCount',
                                        root: 'root'
                                    }
                                }
                            },
                            displayField: 'appName',
                            valueField: 'appId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择应用...'
	                    }, {
                            width: 200,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'dividedMode',
                            itemId: 'dividedMode',
                            fieldLabel: '分成模式',
                            store: {
                                autoLoad: true,
                                fields:['dictName', 'dictId'],
                                proxy: {
                                    type: 'ajax',
                                    url: 'appupload/getDictByType',
                                    extraParams: {dictType: "appdividedMode"},
                                    actionMethods : 'post',
                                    reader: {
                                        type: 'json',
                                        totalProperty: 'tocalCount',
                                        root: 'root'
                                    }
                                }
                            },
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择分成模式...',
                            blankText: '请选择分成模式'
	                    }, {
                            width: 200,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'isMainCategory',
                            fieldLabel: '是否大类',
                            store: {
                                autoLoad: true,
                                fields:['dictName', 'dictId'],
                                proxy: {
                                    type: 'ajax',
                                    url: 'appupload/getDictByType',
                                    extraParams: {dictType: "isappmaincategory"},
                                    actionMethods : 'post',
                                    reader: {
                                        type: 'json',
                                        totalProperty: 'tocalCount',
                                        root: 'root'
                                    }
                                }
                            },
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '请选择...'
	                    }, {
                            width: 200,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'appstatus',
                            fieldLabel: '合作状态',
                            store: {
					            autoLoad: true,
					            fields:['dictName', 'dictId'],
					            proxy: {
					                type: 'ajax',
					                url: 'appupload/getDictByType',
					                extraParams: {dictType: "appstatus"},
					                actionMethods : 'post',
					                reader: {
					                    type: 'json',
					                    totalProperty: 'tocalCount',
					                    root: 'root'
					                }
					            }
                            },
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '请选择...'
	                    }, {
	                        xtype:'hidden',
	                        name:'from',
	                        value: from
	                    }, {
                            width: 200,
                            xtype: 'combo', 
                            listConfig: {
                                emptyText: '未找到匹配值',
                                maxHeight: 150
                            },
                            name: 'invoiceCategory',
                            itemId: 'invoiceCategory',
                            fieldLabel: '发票形式',
                            store: invoiceCategoryStore,
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择发票形式...',
                            blankText: '请选发票形式'
	                    }, {
	                        xtype:'fieldcontainer',
	                        fieldLabel: '帐单月份',
	                        layout: 'column',
	                        items: [{
	                        	columnWidth: .5,
	                            xtype: 'combo', 
	                            listConfig: {
	                                emptyText: '未找到匹配值',
	                                maxHeight: 150
	                            },
	                            name: 'year',
	                            itemId: 'year',
	                            hidenLabel: true,
	                            store: {
	                                autoLoad: true,
	                                fields:['year', 'year'],
	                                data: [
	                                	  {'year': '2010', 'year': '2010'},
	                                	  {'year': '2011', 'year': '2011'},
	                                	  {'year': '2012', 'year': '2012'},
	                                	  {'year': '2013', 'year': '2013'},
	                                	  {'year': '2014', 'year': '2014'}
	                                	]
	                            },
	                            displayField: 'year',
	                            valueField: 'year',
	                            triggerAction: 'all',
	                            editable: false,
	                            queryMode: 'local',
	                            forceSelection: true,
	                            typeAhead: true,
	                            emptyText: '请选择...',
	                            blankText: '请选择...'
	                        }, {
	                        	
	                        },{
                                columnWidth: .5,
                                xtype: 'combo', 
                                listConfig: {
                                    emptyText: '未找到匹配值',
                                    maxHeight: 150
                                },
                                name: 'month',
                                itemId: 'month',
                                hidenLabel: true,
                                fieldLabel: '到',
                                labelWidth: 15,
                                store: {
                                    autoLoad: true,
                                    fields:['month', 'month'],
                                    data: [
                                      {'month': '1', 'month': '1'},
                                      {'month': '2', 'month': '2'},
                                      {'month': '3', 'month': '3'},
                                      {'month': '4', 'month': '4'},
                                      {'month': '5', 'month': '5'},
                                      {'month': '6', 'month': '6'},
                                      {'month': '7', 'month': '7'},
                                      {'month': '8', 'month': '8'},
                                      {'month': '9', 'month': '9'},
                                      {'month': '10', 'month': '10'},
                                      {'month': '11', 'month': '11'},
                                      {'month': '12', 'month': '12'}
                                    ]
                                },
                                displayField: 'month',
                                valueField: 'month',
                                triggerAction: 'all',
                                editable: false,
                                queryMode: 'local',
                                forceSelection: true,
                                typeAhead: true,
                                emptyText: '请选择...',
                                blankText: '请选择...'
	                        }]
	                    }, {
	                        xtype: 'container',
	                        colspan: 2,
	                        items: [{
	                            xtype: 'button',
	                            width: 100,
	                            style: {margin: '0px 10px 0px 0px'},
	                            request: 'appupload/getApps',
	                            itemId: 'btn_getApps',
	                            text: '查询',
	                            handler: function(){
	                                appYieldStore.loadPage(1);
	                            }
	                        }, {
	                            xtype: 'button',
	                            width: 100,
	                            style: {margin: '0px 10px 0px 0px'},
	                            text: '重置',
	                            handler: function(){
	                                detailSearchForm.getForm().reset();
	                            }
	                        }]
	                    }]
	                }]
	            }]
	        });
        	        	
	        var appYieldStore = Ext.create('Ext.data.Store', {
	            autoLoad: {start: 0, limit: 15},
	            fields:['billDetailId', 'billId', 'appId', 'appName', 'packageName', 'mainCategoryName', 'dividedModeName', 'price', 'billMonth', 'spreadDate', 'totalMoney', 'expression', 'requiredMoney', 'downloadCount', 'yield'],
	            pageSize: 15,
	            proxy: {
	                type: 'ajax',
	                url: 'settlementApply/showDetails',
	                actionMethods: {read: 'POST'},
	                reader: {
	                    type: 'json',
	                    totalProperty: 'totalCount',
	                    root: 'root'
	                }
	            },
	            listeners: {
	                beforeload: function(store,records,options){                    
	                    var params = Ext.decode(Ext.encode(detailSearchForm.getForm().getValues()))
	                    this.proxy.extraParams = params;
	                }
	            }
	        });
        	
	        var detailGrid = Ext.create('Ext.grid.Panel', {
	            region:'center',
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
	            store: appYieldStore,
	            columns: [
	                new Ext.grid.RowNumberer(),
	                {header: 'billDetailId',       dataIndex: 'billDetailId',  hidden: true},
	                {header: 'billId',             dataIndex: 'billId',  hidden: true},
	                {header: 'appId',              dataIndex: 'appId',  hidden: true},
	                {header: '应用名称',  flex: 0.6, dataIndex: 'appName', sortable: true},
	                {header: 'Icon',     flex: 0.6, dataIndex: 'packageName', sortable: true},
	                {header: '是否大类',  flex: 0.4, dataIndex: 'mainCategoryName', sortable: true},
	                {header: '分成模式',  flex: 0.4, dataIndex: 'dividedModeName' },
	                {header: '单价',      flex: 0.4, dataIndex: 'price' },
	                {header: '结算月份',  flex: 0.4, dataIndex: 'billMonth', renderer: function(value, record){
	                	
	                	return billMonth;
	                } },
	                {header: '推广周期',  flex: 0.6,  dataIndex: 'spreadDate' },
	                {header: '流水汇总',  flex: 0.5,  dataIndex: 'totalMoney' },
	                {header: '计算公式',  flex: 0.7,  dataIndex: 'expression' , editor: {xtype: 'textfield', allowBlank: true}},
	                {header: '应收账款',  flex: 0.5, dataIndex: 'requiredMoney' },
	                {header: '下载量',    flex: 0.5, dataIndex: 'downloadCount' },
	                {header: '单次下载收益率', flex: 0.6, dataIndex: 'yield' },
	                {
	                    menuDisabled: true,
	                    sortable: false,
	                    xtype: 'actioncolumn',
	                    items: [{
	                        icon   : 'resources/images/select.png',
	                        tooltip: '编辑',
	                        request: 'appupload/auditAppOfflineForBusiness',
	                        getClass: function(v, metadata, r, rowIndex, colIndex, store){
                                var dividedModeName = r.get('dividedModeName');
                                if (dividedModeName == 'CPT'){
                                	return 'x-hide-visibility';
                                }else{
                                	return 'grid-last-column-icon-margin';
                                }
	                        },
	                        handler: function(grid, rowIndex, colIndex) {
	                            var record = grid.getStore().getAt(rowIndex);
	                            
	                            var appName = record.get('appName');
	                            var appId = record.get('appId');
	                            var billMonth = record.get('billMonth');
	                            
	                            showIncomeDetails(appName, appId, billMonth);
	                        }
	                    }]
	                }
	            ],
                plugins: [{
                    ptype: 'cellediting',
                    clicksToEdit: 2,
                    listeners: {
                        edit: function(editor, e){
                            var expression = e.record.get('expression');
                            expression = Ext.String.trim(expression);
                            if ("" == expression){
                            	return ;
                            }
                            
                            var reg = /^\d+([.]\d+){0,1}$/;
                            if (!reg.test(expression)){
                            	Ext.Msg.alert('提示', '格式错误！ (样例: 0.35)');
                            	e.record.reject();
                            	return false;
                            }
                            
                            var billDetailId = e.record.get('billDetailId');
                            
                            saveExpressionToDB(billDetailId, expression);
                        }
                    }
                }],
	            bbar:new Ext.PagingToolbar({
	                pageSize: 15, //每页显示几条数据
	                store: appYieldStore, 
	                displayInfo:true, //是否显示数据信息
	                displayMsg:'显示第 {0} 条到 {1} 条记录，一共  {2} 条', //只要当displayInfo为true时才有效，用来显示有数据时的提示信息，{0},{1},{2}会自动被替换成对应的数据
	                emptyMsg: "没有记录" //没有数据时显示信息
	            })
	        });
	        
        	var win = Ext.create('Ext.window.Window',{
                title: '收入明细',
                closeAction: 'destroy',
                width: 1200,
                height: 520,
                plain: true,
                resizable: false,
                layout: 'fit',
                modal: true,
                items: [{
                	xtype: 'container',
		            frame: true,
		            bodyPadding: 5,
		            autoScroll: true,
		            autoScroll: true,
		            layout:{
		                type:'vbox',
		                align:'stretch'
		            },
		            items: [detailSearchForm, detailGrid]
                }]
        	});
        	
        	win.show();
        }
        
        function saveExpressionToDB(detailId, expression){
            Ext.Ajax.request({
                url: 'settlementApply/saveExpression',
                method: 'POST',
                params: {
                    billDetaillId: detailId,
                    expression: expression
                },
                success: function(response){
                	
                }
            });
        }
        
        function showIncomeDetails(appName, appId, billMonth){
        	
        	var grid;
        	var spreadDate = {};
        	
            var win = Ext.create('Ext.window.Window', {
                title: '本月收入明细(' + appName +')',
                closeAction: 'destroy',
                width: 320,
                height: 410,
                plain: true,
                resizable: false,
                layout: 'fit',
                modal: true,
                listeners: {
                    afterrender: function(){
            	        grid = win.queryById('incomeGrid');
            	      
            	        initGrid(billMonth, spreadDate, grid);
            	        loadGridData(appId, billMonth, grid);
                    }
                },
            	items: [{
                    xtype: 'gridpanel',
                    frame: true,
                    itemId: 'incomeGrid',
                    height: 245,
                    anchor: '100%',
                    columnLines : true,
                    viewConfig: {
                        forceFit : true,
                        stripeRows: true
                    },
                    selType: 'cellmodel',
                    store: {
                        fields: ['date', 'money', 'cpasId'],
                        proxy: {
                            type: 'memory',
                            data: [],
                            reader: 'array'
                        }
                    },
                    columns: [
                        {header: '日期', flex: 1, dataIndex: 'date' },
                        {header: '收入', flex: 1, dataIndex: 'money', editor: {xtype: 'textfield', allowBlank: true}},
                        {header: 'cpasId', flex: 1, dataIndex: 'cpasId', hidden: true}
                    ],
                    plugins: [{
                        ptype: 'cellediting',
                        clicksToEdit: 2,
                        listeners: {
                            edit: function(editor, e){
                                var data = spreadDate[billMonth];
                                if (data == undefined){
                                    data = [];
                                    spreadDate[billMonth] = data;
                                }
                                
                                var day = e.record.get('date');
                                //如果data已经有当天的数据,则直接修改之
                                for (var i = 0; i < data.length; i++){
                                    var tmp = data[i];
                                    if (day == tmp['date']){
                                        tmp['money'] = e.value;
                                        return;
                                    }
                                }
                                
                                var cpasId = e.record.get('cpasId');
                                if (cpasId == undefined){
                                	cpasId = '';
                                }
                                
                                var obj = {};
                                obj['date'] = day;
                                obj['money'] = e.value;
                                obj['cpasId'] = cpasId;
                                
                                data.push(obj);
                            }
                        }
                    }]
            	}],
            	buttons: [{
            		text: '确定',
            		handler: function(){
            		
            		    var params = '';
                        for (var p in spreadDate){
                        	
                            var monthData = spreadDate[p];
                            params += p + ":";
                            
                            //每个日期之间用,分隔
                            for (var i = 0; i < monthData.length; i++){
                                var data = monthData[i];
                                //日期与价格之间用#分隔
                                params += data['date']+ "#" + data['money'] + "#" + data['cpasId'] +",";
                            }
                            
                            params = params.replace(/,$/, '');
                            params += "@";
                        }
                        
                        params = params.replace(/@$/, '');
                        saveCPASpread(params, grid, appId, win);
                        
            		}
            	}, {
                    text: '取消',
                    handler: function(){
                        win.close();
                    }
            	}]
            });	
            
            win.show();
        }
        
        
        function saveCPASpread(data, grid, appId, win){
        	if (data == ''){
        		win.close();
        		return;
        	}
        	
            Ext.Ajax.request({
                url: 'settlementApply/addCPASpread',
                method: 'POST',
                params: {
                    appId: appId,
                    spread: data
                },
                success: function(response){
                	win.close();
                    grid.getStore().loadPage(1);
                }
            });
        }
        
        /**
         * 初始化grid里的天数
         * @param {Object} newValue
         * @param {Object} spreadDate
         * @param {Object} grid
         */
        function initGrid(newValue, spreadDate, grid){
        	
            var year = newValue.split('.')[0];
            var month = newValue.split('.')[1];
                        
            //得到天数
            var date = new Date(year, month, 0);
            var day = date.getDate();
            
            var gridData = [];
            
            outerloop:
            for (var i = 1; i <= day; i++){
                var t = month + "." + i;
                
                //如果spreadDate里没有当月的数据
                if (undefined == spreadDate[newValue]){
                    gridData[i - 1] = [t, "", ""];
                    continue;
                }
                
                //如果spreadDate里己经有当月的数据, 则在初始化的时候, 将spreadDate里的数据填充进去
                var data = spreadDate[newValue];
                for(var k = 0; k < data.length; k++ ){
                    var obj = data[k];
                    if (t == obj['date']){
                        gridData[i - 1] = [t, obj['money'], obj['cpasId']];
                        continue outerloop;
                    }
                }
                
                gridData[i - 1] = [t, "", ""]; 
            }
            
            grid.getStore().loadData(gridData);            
        }
        
        /**
         * 从server加载数据填充grid
         * @param {Object} appId
         * @param {Object} month
         * @param {Object} grid
         */
        function loadGridData(appId, month, grid){
            
            Ext.Ajax.request({
                url: 'settlementApply/getCPASpread',
                method: 'POST',
                params: {
                    appId: appId,
                    month: month
                },
                success: function(response){
                    
                    var json = Ext.decode(response.responseText);
                    
                    var len = json.data.length;
                    
                    var newData = [];
                    
                    var items = grid.getStore().data.items;
                    for (var i = 0; i < items.length; i++){
                        var storeItem = items[i];                                           
                        newData[i] = [storeItem.data.date, storeItem.data.money, storeItem.data.cpasId];
                        
                        for (var j = 0; j < len; j++){
                            var spreadDate = json.data[j].spreadDate;
                            
                            if (storeItem.data.date == spreadDate){
                                newData[i][1] = json.data[j].price;
                                newData[i][2] = json.data[j].cpasId;
                            }
                        }
                    }
                    
                    grid.getStore().loadData(newData);
                }
            });
        }
        
        
        function auditSettlementApply(billId, grid){
            var win = Ext.create('Ext.window.Window',{
                title: '提示',
                closeAction: 'destroy',
                width: 330,
                height: 220,
                plain: true,
                resizable: false,
                layout: 'fit',
                modal: true,
                items: [{
                    xtype: 'form',
                    itemId: 'auditForm',
                    frame: true,
                    bodyStyle:'padding: 5',
                    fieldDefaults: {
                        msgTarget: 'side',
                        width: 300,
                        allowBlank: false,
                        labelWidth: 55
                    },
                    items: [{
                        xtype: 'label',
                        text: '确认提交审核？'
                    }, {
                        xtype: 'hidden',
                        name: 'billId',
                        value: billId
                    }, {
                        xtype: 'hidden',
                        name: 'from',
                        value: from
                    },{
                        xtype: 'textfield',
                        name: 'actualMoney',
                        padding: '10px 0px 0px 0px',
                        fieldLabel: '结算金额',
                        blankText: '请输入结算金额'
                    }, {
                        xtype: 'combo', 
                        listConfig: {
                            emptyText: '未找到匹配值',
                            maxHeight: 150
                        },
                        name: 'invoiceCategory',                                   
                        fieldLabel: '发票类型',
                        store: {
                            autoLoad: true,
                            fields:['dictName', 'dictId'],
                            proxy: {
                                type: 'ajax',
                                url: 'appupload/getDictByType',
                                extraParams: {dictType: "invoicecategory"},
                                actionMethods : 'post',
                                reader: {
                                    type: 'json',
                                    totalProperty: 'tocalCount',
                                    root: 'root'
                                }
                            }
                        },
                        displayField: 'dictName',
                        valueField: 'dictId',
                        triggerAction: 'all',
                        editable: false,
                        queryMode: 'local',
                        forceSelection: true,
                        typeAhead: true,
                        blankText: '请选择发票类型'
                    }, {
                        xtype: 'textareafield',
                        name: 'description',
                        allowBlank: true,
                        fieldLabel: '备注',
                        value: ''
                    }]
                }],
                buttons: [{
                    text: '确定',
                    handler: function(){
                        var form = win.down('#auditForm');
                        if (form.isValid()){
                        	
                            form.submit({
                                url: 'settlementApply/auditSettlementApply', 
                                method: 'post',
                                waitTitle : "提示",
                                waitMsg : '正在提交，请稍后 ……',
                                success : function(form, action) {  
                                    win.close();
                                    grid.getStore().loadPage(1);
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
                    handler: function(){
                        win.close();
                    }
                }]
            });
            win.show();
        }
        
        function auditSettlement(billId, grid){
            var win = Ext.create('Ext.window.Window',{
                title: '提示',
                closeAction: 'destroy',
                width: 320,
                height: 180,
                plain: true,
                resizable: false,
                layout: 'fit',
                modal: true,
                items: [{
                    xtype: 'form',
                    itemId: 'auditForm',
                    frame: true,
                    bodyStyle:'padding: 5',
                    fieldDefaults: {
                        msgTarget: 'side',
                        width: 280,
                        allowBlank: false,
                        labelWidth: 55
                    },
                    items: [{
                        xtype: 'combo', 
                        listConfig: {
                            maxHeight: 150
                        },
                        name: 'auditResult',
                        itemId: 'auditResult',
                        fieldLabel: '审核结果',
                        store: {
			                autoLoad: true,
			                fields:['dictName', 'dictId'],
			                proxy: {
			                    type: 'ajax',
			                    url: 'appupload/getDictByType',
			                    extraParams: {dictType: "auditresult"},
			                    actionMethods : 'post',
			                    reader: {
			                        type: 'json',
			                        totalProperty: 'tocalCount',
			                        root: 'root'
			                    }
			                }
                        },
                        displayField: 'dictName',
                        valueField:   'dictName',
                        triggerAction: 'all',
                        editable: false,
                        queryMode: 'local',
                        forceSelection: true,
                        selectOnFocus:true,
                        typeAhead: true,
                        allowBlank: false,
                        blankText: '请选择审核结果',
                        emptyText: '请选择审核结果...',
                        listeners: {
                            change: function(field, newValue, oldValue, opts){
                                var rawValue = field.getRawValue();
                                var desc = win.queryById('description');
                                
                                if ("通过" == rawValue){
                                	desc.clearInvalid();
                                    desc.allowBlank = true;
                                    desc.blankText = '';
                                }else if ("不通过" == rawValue){
                                    desc.allowBlank = false;
                                    desc.blankText = '请说明不通过的原因';
                                }
                            }
                        }
                    },{
                        xtype: 'hidden',
                        name: 'billId',
                        value: billId
                    }, {
                        xtype: 'hidden',
                        name: 'from',
                        value: from
                    }, {
                        xtype: 'textareafield',
                        name: 'description',
                        itemId: 'description',
                        allowBlank: true,
                        fieldLabel: '备注',
                        value: ''
                    }]
                }],
                buttons: [{
                    text: '确定',
                    handler: function(){
                        var form = win.down('#auditForm');
                        if (form.isValid()){
                            form.submit({
                                url: 'settlementApply/auditSettlementApply', 
                                method: 'post',
                                waitTitle : "提示",
                                waitMsg : '正在提交，请稍后 ……',
                                success : function(form, action) {  
                                    win.close();
                                    grid.load();
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
        
    }
});