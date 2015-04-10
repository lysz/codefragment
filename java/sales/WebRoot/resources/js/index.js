var global_parameter_user;

Ext.onReady(function(){
	Ext.QuickTips.init();
	
    Ext.Ajax.request({
    	async : false,
        url: 'user/getCurrentUser',
        method: 'POST',
        success: function(response){            
            saveUserInfo(response);
            createMainPage();
        }
    });
    
    function saveUserInfo(response){
        var data = Ext.decode(response.responseText);
        console.log(data);
        
        global_parameter_user = {
                "userinfo": data.user,
                "permissions": data.permissions
            };
    }
	
    function createMainPage(){
    	var loginUser = global_parameter_user.userinfo.userName;
    	
        Ext.create('Ext.Viewport', {
	        layout: 'border',
	        items: [{
	            region: 'center',
	            xtype: 'panel',
	            id: 'mainContainer',
	            layout: 'fit'
	        },{
	            region: 'west',
	            split: true,
	            width: 200,
	            minWidth: 175,
	            maxWidth: 400,
	            collapsible: true,
	            animCollapse: true,
	            margins: '0 0 0 5',
	            layout: 'accordion',
	            items: [{
	                title: '用户管理',
	                hidden: loginUser != 'admin' ? true: false,
	                layout: {
	                    type: 'vbox',
	                    padding: '5',
	                    align: 'stretch'
	                },
	                items: [{
	                    xtype: 'button',
	                    text: '用户管理',
	                    id: 'u01',
	                    flex: 1
	                }]
	            }, {
	                title: '推广需求管理',
	                hidden: loginUser == 'admin' ? true: false,
	                layout: {
	                    type: 'vbox',
	                    padding: '5',
	                    align: 'stretch'
	                },
	                items: [{
	                    xtype: 'button',
	                    id: 't01',
	                    text: '推广合作商管理(商务)',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    id: 't02',
	                    text: '合同管理(商务)',
	                    flex: 1
	                },{
	                    xtype: 'button',
	                    id: 't03',
	                    name: 'business',
	                    text: 'CP推广上架更新申请(商务)',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: 'CP推广测试审核(测试)',
	                    name: 'test',
	                    id: 't04',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: 'CP推广上架审核(运营)',
	                    name: 'operation',
	                    id: 't05',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: 'CP推广下架申请(商务)',
	                    name: 'business',
	                    id: 't06',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: 'CP推广下架审核(运营)',
	                    name: 'operation',
	                    id: 't07',
	                    flex: 1
	                }]
	            }, {
	                title: '收款管理',
	                hidden: loginUser == 'admin' ? true: false,
	                layout: {
	                    type: 'vbox',
	                    padding: '5',
	                    align: 'stretch'
	                },
	                items: [{
	                    xtype: 'button',
	                    text: '结算申请(商务)',
	                    id: 's01',
	                    name: 'business',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '结算申请初审(运营)',
	                    id: 's02',
	                    name: 'operation',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '结算申请复审(运营总监)',
	                    id: 's03',
	                    name: 'operationDirector',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '结算申请财务审核(财务)',
	                    name: 'financeAudit',
	                    id: 's04',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '结算申请回款确认(财务)',
	                    name: 'financeConfirm',
	                    id: 's05',
	                    flex: 1
	                }]
	            }, {
	                title: '打款管理',
	                hidden: loginUser == 'admin' ? true: false,
	                layout: {
	                    type: 'vbox',
	                    padding: '5',
	                    align: 'stretch'
	                },
	                items: [{
	                    xtype: 'button',
	                    text: '渠道合作方管理(商务)',
	                    id: 'd01',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '付款申请(商务)',
	                    id: 'd02',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '付款提交审核(运营)',
	                    id: 'd03',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '总监审核(总监)',
	                    id: 'd04',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '商务对账确认(商务)',
	                    id: 'd05',
	                    flex: 1
	                }, {
	                    xtype: 'button',
	                    text: '付款确认(财务)',
	                    id: 'd06',
	                    flex: 1
	                }]
	            }, {
	                title: '邮件发送管理',
	                hidden: loginUser == 'admin' ? true: false,
	                layout: {
	                    type: 'vbox',
	                    padding: '5',
	                    align: 'stretch'
	                },
	                items: [{
	                    xtype: 'button',
	                    text: '邮件配置',
	                    id: 'y01',
	                    flex: 1
	                }]
	            }]
	        }]
	    });	
    }

});