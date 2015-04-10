Ext.onReady(function(){
	
	var loginForm = Ext.create('Ext.form.Panel', {
		title: '登陆',
		bodyPadding: 5, 
		width: 400,
		height: 150, 
		defaultType: 'textfield',
		labelSeparator: "：",
		renderTo: Ext.getBody(),
		frame: true,
		items: [{
			fieldLabel: '用户名',
			name: 'userName',
			width: 300,
			allowBlank: false,
			blankText: '用户名不能为空',
			msgTarget: 'side',
			listeners: {
			    specialkey: function(field, e){
			        if (e.getKey() == Ext.EventObject.ENTER){
			        	login();
			        }
			    }
			}
		},{
			fieldLabel: "密码",
			name: 'password',
			width: 300,
			inputType: 'password',
			allowBlank: false,
			blankText: '密码不能为空',
			msgTarget: 'side',
			listeners: {
			    specialkey: function(field, e){
			        if (e.getKey() == Ext.EventObject.ENTER){
			        	login();
			        }
			    }
			}
		}],
		buttons: [{
			text: '重置',
			handler: function(){
			    loginForm.form.reset();
			}
		},{
			text: '登陆',
			handler: function(){
			    login();
			}
		}]
	});
	
	function login(){
	    var form = loginForm.getForm();			    
		if (form.isValid()){
			var username = form.findField("userName").getValue();
			var password = form.findField("password").getValue();
			form.submit({
				waitMsg : '正在登陆验证,请稍后...',
				url: 'user/login',
				method: 'post',
				params: {userName: username, password: password},
				success: function(form, action){
					var userId = action.result.userId;
					userId = Ext.decode(action.result.userId);
					Ext.util.Cookies.set('userId', userId);
					window.location = "user/redirect2MainPage";
					
				},
				failure: function(form, action){
			        switch (action.failureType) {
			            case Ext.form.action.Action.CLIENT_INVALID:
			                Ext.Msg.alert('警告', '用户名或密码格式错误！');
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
	}
	
	loginForm.center();
});