Ext.onReady(function(){
	var mainContainer = Ext.getCmp('mainContainer');
	
	var btn06 = Ext.getCmp("t06");   // CP推广上架更新申请(商务)
	var btn07 = Ext.getCmp("t07");   // CP推广测试审核(测试)
	
	btn06.on('click', appUploadApply);
	btn07.on('click', appUploadApply);	
	
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
        
        var dividedModeStore = Ext.create('Ext.data.Store', {
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
        });
        
        var appMainCategoryStore = Ext.create('Ext.data.Store', {
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
                            name: 'isMainCategory',
                            fieldLabel: '是否大类',
                            store: appMainCategoryStore,
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '请选择...'
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
                            name: 'appstatus',
                            fieldLabel: '合作状态',
                            store: friendStatusStore,
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '请选择...'
                        }]
                    }, {
                        xtype: 'container',
                        items: [{
                            width: 200,
                            labelWidth: 50,
                            xtype: 'textfield',
                            fieldLabel: '应用Id',
                            name: 'appId',
                        }]
                    }, {
                        xtype:'hidden',
                        name:'from',
                        value: from
                    }, {
                        xtype:'hidden',
                        name:'type',
                        value: 'offline'
                    },{
                        xtype: 'container',
                        items: [{
                            width: 200,
                            xtype: 'textfield',
                            fieldLabel: '应用名/包名',
                            name: 'appName'
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
                            name: 'dividedMode',
                            itemId: 'dividedMode',
                            fieldLabel: '分成模式',
                            store: dividedModeStore,
                            displayField: 'dictName',
                            valueField: 'dictId',
                            triggerAction: 'all',
                            editable: false,
                            queryMode: 'local',
                            forceSelection: true,
                            typeAhead: true,
                            emptyText: '选择分成模式...',
                            blankText: '请选择分成模式'
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
                        },{
                            xtype: 'button',
                            itemId: 'btn_addUploadApply',
                            width: 100,
                            text: '新增',
                            request: 'appupload/addAppUploadApply',
                            handler: function(){
                                showWindow('新增', 'appupload/addAppUploadApply', true);
                            }
                        }]
                    }]
                }]
            }],
            listeners: {
                'render': function(){
                    var searchBtn = searchForm.queryById("btn_getApps");
                    var createBtn = searchForm.queryById("btn_addUploadApply");
                    var searchURL = searchBtn.request;
                    var createURL = createBtn.request;
                    
                    var hasSearchPerssion = false;
                    var hasCreatePerssion = false;
                    
                    if (from != 'business'){
                    	createBtn.hide();
                    	return;
                    }
                    
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
    //--------------------------- 搜索框结束 ----------------------------------------    

        
    //--------------------------- Grid panel ---------------------------------------- 
        
        var appGridStore = Ext.create('Ext.data.Store', {
            autoLoad: {start: 0, limit: 3},
            fields:['appId', 'appName', 'companyName', 'packageName', 'apkPath', 'platformURL', 'platformPwd', 'dividedModeName', 'price', 'mainCategoryName', 'status', 'testResult', 'cptSpreadDate', 'onlineDateStr', 'offlineDate'],
            pageSize: 3,
            proxy: {
                type: 'ajax',
                url: 'appupload/getApps',
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
            store: appGridStore,
            columns: [
                new Ext.grid.RowNumberer(),
                {header: 'appId', dataIndex: 'appId',  hidden: true},
                {header: '应用名称', dataIndex: 'appName',  hidden: true},
                {header: '公司名称', flex: 0.8,  dataIndex: 'companyName', sortable: true},
                {header: '包名', flex: 1, dataIndex: 'packageName', sortable: true},
                {header: 'CP包位置', flex: 1, dataIndex: 'apkPath' },
                {header: '查询后台', flex: 0.8, dataIndex: 'platformURL' },
                {header: '密码', flex: 0.8, dataIndex: 'platformPwd' },
                {header: '分成模式', flex: 0.6,  dataIndex: 'dividedModeName' },
                {header: '单价/位置',  flex: 0.8,  dataIndex: 'price' },
                {header: '是否大类', flex: 0.6, dataIndex: 'mainCategoryName' },
                {header: '合作状态', flex: 0.8,  dataIndex: 'status' },
                {header: '测试结果', flex: 0.8,  dataIndex: 'testResult' },
                {header: 'CPT推广日期', flex: 1,  dataIndex: 'cptSpreadDate'},
                {header: '上架时间', flex: 0.8, dataIndex: 'onlineDateStr'},
                {header: '下架时间', flex: 0.8,  dataIndex: 'offlineDate'},
                {
                    menuDisabled: true,
                    sortable: false,
                    xtype: 'actioncolumn',
                    itemId: 'actionList',
                    width: 90,
                    items: [{
                        icon   : 'resources/images/stop.png',
                        //iconCls: 'grid-last-column-icon-margin',
                        tooltip: '停止推广',
                        request: 'appupload/auditAppOfflineForBusiness',
                        getClass: function(v, metadata, r, rowIndex, colIndex, store){
                    	    var status = r.get('status');
                    	    
                    	    //如果app的状态不是'新增加'就不能对它进行修改, 直接隐藏该按钮
                    	    if ('已上架' != status){
                    	    	return 'x-hide-visibility';
                    	    }
                    	    
                            //权限控制, 返回class样式
                            var cls = showOrHideIcon(this.items[0]);
                            if (undefined != cls){
                               return cls;
                            }else{
                               return 'grid-last-column-icon-margin';
                            }
                        },
                        handler: function(grid, rowIndex, colIndex) {
                                                        
                            Ext.MessageBox.confirm('警告','你确认停止合作推广吗？', function(btn){
                                if(btn == 'yes'){                                   
		                            var record = grid.getStore().getAt(rowIndex);
		                            var appId = record.get('appId');
		                            var params = {appId: appId};
                                    
                                    Ext.Ajax.request({
                                        url: 'appupload/auditAppOfflineForBusiness',
                                        params: params,
                                        method: 'post',
                                        success: function(resp,opts){
                                            appGridStore.loadPage(1);
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
                    }, {
                        icon   : 'resources/images/stop.png',
                        //iconCls: 'grid-last-column-icon-margin',
                        tooltip: '下架确认',
                        request: 'appupload/auditAppOfflineForOperation',
                        getClass: function(v, metadata, r, rowIndex, colIndex, store){
                            var status = r.get('status');
                            
                            //如果app的状态不是'新增加'则就不能对它进行审核操作, 直接隐藏该按钮
                            if (status != '待运营审核'){
                                return 'x-hide-visibility';
                            }
                            
                            //权限控制, 返回class样式
                            var cls = showOrHideIcon(this.items[1]);
                            console.log(cls);
                            if (undefined != cls){
                               return cls;
                            }else{
                               return 'grid-last-column-icon-margin';
                            }
                        },
                        handler: function(grid, rowIndex, colIndex) {
                        
                            Ext.MessageBox.confirm('警告','是否确认停止合作推广？', function(btn){
                                if(btn != 'yes'){
                                    return;
                                }
                                
                                var record = grid.getStore().getAt(rowIndex);
                                var appId = record.get('appId');
                                var params = {appId: appId};
                                
                                Ext.Ajax.request({
                                    url: 'appupload/auditAppOfflineForOperation',
                                    method: 'POST',
                                    params: params,
                                    success: function(response){
                                        appGridStore.load();
                                    }
                                });
                            });
                        }
                    }]
                }
            ],
            bbar:new Ext.PagingToolbar({
                pageSize: 3, //每页显示几条数据
                store: appGridStore, 
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
        
       
        function showWindow(title, submitURL, isCreate, formLoadParams, updateURL){
            var spreadDate = {};
            var spreadMonth = null;
            var grid = null;
            
            var win = Ext.create("Ext.window.Window",{
                title: title,
                closeAction: 'destroy',
                width: 350,
                height: 270,
                plain: true,
                resizable: false,
                layout: 'fit',
                modal: true,
                listeners: {
                    beforeshow: function(){
                        
                        if (isCreate){
                            return;
                        }
                                                
                        var addUserForm = win.getComponent('appUploadApplyForm');
                        
                        addUserForm.load({
                            params: formLoadParams,
                            url: submitURL,
                            method: 'POST',
                            success: function(form, action){
                            
                                loadGridData(formLoadParams.appId, spreadMonth.getRawValue(), grid);
                            },
                            failure: function(form, action){
                                
                            }
                        });
                    }
                },
                items: [{
                    xtype: 'form',
                    itemId: 'appUploadApplyForm',
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
                        blankText: '请选择公司',
                        emptyText: '选择公司...'
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '应用名称',
                        blankText: '请输入应用名称',
                        name: 'appName'
                    }, {
                        xtype: 'combo', 
                        listConfig: {
                            emptyText: '未找到匹配值',
                            maxHeight: 150
                        },
                        name: 'dividedMode',
                        itemId: 'dividedMode',
                        fieldLabel: '分成模式',
                        store: dividedModeStore,
                        displayField: 'dictName',
                        valueField: 'dictId',
                        triggerAction: 'all',
                        editable: false,
                        queryMode: 'local',
                        forceSelection: true,
                        typeAhead: true,
                        emptyText: '选择分成模式...',
                        blankText: '请选择分成模式',
                        listeners: {
                            change: function(field, newValue, oldValue, opts){
                                if (null == spreadMonth || undefined == spreadMonth){
                                    spreadMonth = win.queryById('spreadMonth');
                                }
                                
                                if (null == grid || undefined == grid){
                                    grid = win.queryById('cptGrid');
                                }
                                
                                if (field.getRawValue() == 'CPT'){
                                    
                                    win.setHeight(530);
                                    
                                    spreadMonth.show();
                                    spreadMonth.allowBlank = false;
                                    spreadMonth.blankText = '请选择推广月分';
                                    grid.show();
                                    
                                }else{
                                    spreadMonth.hide();
                                    spreadMonth.allowBlank = true;
                                    grid.hide();
                                    win.setHeight(270);
                                    
                                }
                                
                                win.center();
                            }
                        }
                    }, {
                        xtype: 'combo', 
                        listConfig: {
                            emptyText: '未找到匹配值',
                            maxHeight: 150
                        },
                        name: 'mainCategory',
                        fieldLabel: '是否大类',
                        store: appMainCategoryStore,
                        displayField: 'dictName',
                        valueField: 'dictId',
                        triggerAction: 'all',
                        editable: false,
                        queryMode: 'local',
                        forceSelection: true,
                        typeAhead: true,
                        blankText: '请选择是否大类',
                        emptyText: '是否大类...'
                    }, {
                        xtype: 'textareafield',
                        name: 'apkPath',
                        fieldLabel: '存放位置',
                        blankText: '请输入存放位置',
                        value: ''
                    }, {
                        xtype: 'combo', 
                        hidden: true,
                        itemId: 'spreadMonth',
                        listConfig: {
                            emptyText: '未找到匹配值',
                            maxHeight: 150
                        },
                        name: 'spreadMonth',
                        itemId: 'spreadMonth',
                        fieldLabel: '推广月分',
                        store: spreadMonthStore,
                        displayField: 'dictName',
                        valueField: 'dictId',
                        triggerAction: 'all',
                        editable: false,
                        queryMode: 'local',
                        forceSelection: true,
                        typeAhead: true,
                        allowBlank: true,
                        emptyText: '选择推广月分...',
                        listeners: {
                            change: function(field, newValue, oldValue, opts){
                                
                                if (!isCreate){
                                    loadGridData(formLoadParams.appId, spreadMonth.getRawValue(), grid);
                                }
                                
                                initGrid(newValue, spreadDate, grid);
                                
                                win.center();
                            }
                        }
                    }, {
                        xtype: 'gridpanel',
                        frame: true,
                        itemId: 'cptGrid',
                        height: 245,
                        hidden: true,
                        anchor: '100%',
                        columnLines : true,
                        viewConfig: {
                            forceFit : true,
                            stripeRows: true
                        },
                        selType: 'cellmodel',
                        store: {
                            fields: ['date', 'money', 'cptSpreadId'],
                            proxy: {
                                type: 'memory',
                                data: [],
                                reader: 'array'
                            },
                            autoLoad: true
                        },
                        columns: [
                            {header: '日期', flex: 1, dataIndex: 'date' },
                            {header: '金额', flex: 1, dataIndex: 'money', editor: {xtype: 'textfield', allowBlank: true}},
                            {header: 'cptSpreadId', flex: 1, dataIndex: 'cptSpreadId', hidden: true}
                        ],
                        plugins: [{
                            ptype: 'cellediting',
                            clicksToEdit: 2,
                            listeners: {
                                edit: function(editor, e){
                                    
                                    var data = spreadDate[spreadMonth.getRawValue()];
                                    if (data == undefined){
                                        data = [];
                                        spreadDate[spreadMonth.getRawValue()] = data;
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
                                    
                                    var obj = {};
                                    obj['date'] = day;
                                    obj['money'] = e.value;
                                    obj['cptSpreadId'] = e.record.get('cptSpreadId');
                                    
                                    data.push(obj);
                                }
                            }
                        }]
                    }]
                }],
                buttons: [{
                    text: '确定',
                    handler: function(){
                        var addForm = win.getComponent('appUploadApplyForm');
                        if (addForm.isValid()){
                            
                            //组装成这样的格式传给server: month:day#money,day#money@month:day#money
                            var params = "";
                            if (addForm.getComponent('dividedMode').getRawValue() == 'CPT'){
                                //p是月份
                                for (var p in spreadDate){
                                    var monthData = spreadDate[p];
                                    params += p + ":";
                                    
                                    //每个日期之间用,分隔
                                    for (var i = 0; i < monthData.length; i++){
                                        var data = monthData[i];
                                        //日期与价格之间用#分隔
                                        params += data['date']+ "#" + data['money'] + "#" + data['cptSpreadId'] +",";
                                    }
                                    
                                    params = params.replace(/,$/, '');
                                    params += "@";
                                }
                                
                                params = params.replace(/@$/, '');
                            }
                            
                            var url =  isCreate ? submitURL : updateURL;
                            var param = isCreate ? {spreadDates: params} : {spreadDates: params, appId: formLoadParams.appId};
                            
                            addForm.submit({
                                url: url, 
                                params: param,
                                method: 'post',
                                waitTitle : "提示",
                                waitMsg : '正在提交，请稍后 ……',
                                success : function(form, action) {  
                                    win.close();
                                    appGridStore.load();
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
        
        function operationAudit(params){
        	var win = Ext.create("Ext.window.Window", {
                title: '运营审核',
                closeAction: 'destroy',
                width: 320,
                height: 260,
                plain: true,
                resizable: false,
                layout: 'fit',
                modal: true,
                items: [{
                    xtype: 'form',
                    itemId: 'operationAuditForm',
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
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '公司名称',
                        readOnly: true,
                        name: 'companyName',
                        value: params.companyName
                    }, {
                        xtype:'hidden',
                        name:'appId',
                        value: params.appId
                    }, {
                        xtype:'hidden',
                        name:'isOnline',
                        itemId: 'isOnline',
                        value: ''
                    },{
                        xtype: 'textfield',
                        fieldLabel: '应用名称',
                        readOnly: true,
                        name: 'appName',
                        value: params.appName
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '包名',
                        blankText: '请输入包名',
                        name: 'packageName'
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '应用ID',
                        blankText: '请输入应用ID'
                        //name: 'packageName'
                    }, {
                        xtype: 'textareafield',
                        itemId: 'description',
                        name: 'description',
                        allowBlank: true,
                        fieldLabel: '备注',
                        value: ''
                    }]
                }], 
                buttons: [{
                	text: '上架',
                	handler: function(){
                	    var txt = win.queryById('isOnline');
                	    txt.setValue('true');
                	    var form = win.queryById('operationAuditForm');
                        form.submit({
                            url: params.url, 
                            params: params,
                            method: 'post',
                            waitTitle : "提示",
                            waitMsg : '正在提交，请稍后 ……',
                            success : function(form, action) {  
                                win.close();
                                appGridStore.load();
                            }
                        });
                	}
                }, {
                	text: '不上架', 
                	handler: function(){
                        var txt = win.queryById('isOnline');
                        txt.setValue('false');
                        var form = win.queryById('operationAuditForm');
                        form.submit({
                            url: params.url, 
                            params: params,
                            method: 'post',
                            waitTitle : "提示",
                            waitMsg : '正在提交，请稍后 ……',
                            success : function(form, action) {  
                                win.close();
                                appGridStore.load();
                            }
                        });
                	}
                }]
        	});
        	
        	win.show();
        }
        
        
        
        
        function testAudit(params){
        	
        	var win = Ext.create("Ext.window.Window", {
        		title: '测试审核',
                closeAction: 'destroy',
                width: 320,
                height: 230,
                plain: true,
                resizable: false,
                layout: 'fit',
                modal: true,
                items: [{
                	xtype: 'form',
                	itemId: 'testAuditForm',
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
                    items: [{
                    	xtype: 'textfield',
                    	fieldLabel: '公司名称',
                    	readOnly: true,
                    	name: 'companyName',
                    	value: params.companyName
                    }, {
				        xtype:'hidden',
				        name:'appId',
				        value: params.appId
					}, {
                        xtype: 'textfield',
                        fieldLabel: '应用名称',
                        readOnly: true,
                        name: 'appName',
                        value: params.appName
                    }, {
                        xtype: 'combo', 
                        listConfig: {
                            emptyText: '未找到匹配值',
                            maxHeight: 150
                        },
                        name: 'testResult',
                        fieldLabel: '测试结果',
                        store: {
                        	xtype: 'store',
				            autoLoad: true,
				            fields:['dictName', 'dictValue'],
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
                        valueField:   'dictValue',
                        triggerAction: 'all',
                        editable: false,
                        queryMode: 'local',
                        forceSelection: true,
                        selectOnFocus:true,
                        typeAhead: true,
                        allowBlank: false,
                        blankText: '请选择测试结果',
                        emptyText: '选择测试结果...',
                        listeners: {
                            select: function(field, newValue, oldValue, opts){
				        	    var rawValue = field.getRawValue();
				        	    var desc = win.queryById('description');
				        	    
				        	    if ("通过" == rawValue){
				        	    	desc.allowBlank = true;
				        	    	desc.blankText = '';
				        	    }else if ("不通过" == rawValue){
                                    desc.allowBlank = false;
                                    desc.blankText = '请说明不通过的原因';
				        	    }
				        	}
				        }
                    }, {
                        xtype: 'textareafield',
                        itemId: 'description',
                        name: 'description',
                        allowBlank: true,
                        fieldLabel: '备注',
                        value: ''
                    }]
                }],
                buttons: [{
                	text: '确定',
                	handler: function(){
                	    var form = win.queryById('testAuditForm');
                	    
                	    if (form.isValid()){
                	    	form.submit({
	                            url: params.url, 
	                            params: params,
	                            method: 'post',
	                            waitTitle : "提示",
	                            waitMsg : '正在提交，请稍后 ……',
	                            success : function(form, action) {  
	                                win.close();
	                                appGridStore.load();
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
        
        
        /**
         * 初始化grid里的天数
         * @param {Object} newValue
         * @param {Object} spreadDate
         * @param {Object} grid
         */
        function initGrid(newValue, spreadDate, grid){
            var year = newValue.split('.')[0];
            var month = newValue.split('.')[1];
            
            var date = new Date(year, month, 0);
            
            //得到天数
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
                        gridData[i - 1] = [t, obj['money'], obj['cptSpreadId']];
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
                url: 'appupload/getSpreadDate',
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
                        newData[i] = [storeItem.data.date, storeItem.data.money, storeItem.data.cptSpreadId];
                        
                        for (var j = 0; j < len; j++){
                            var spreadDate = json.data[j].spreadDate;
                            
                            if (storeItem.data.date == spreadDate){
                                newData[i][1] = json.data[j].price;
                                newData[i][2] = json.data[j].cptSpreadId;
                            }
                        }
                    }
                    
                    grid.getStore().loadData(newData);
                }
            });
        }
    }



});