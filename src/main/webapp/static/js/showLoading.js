var showLoading = function(object) {
	let tipTxt = object.title;
	let time;
	if (!object.duration) {
		time = 15000;
	} else {
		time = object.duration
	}
	let mask;
	if (!object.mask) {
		mask = false
	} else {
		mask = object.mask
	}
	let icon;
	if (!object.icon) {
		icon = "none"
	} else {
		icon = object.icon;
	}
	let image;
	if (object.image) {
		image = object.image;
	}
	let success = object.success;
	let fail = object.fail;
	let complete = object.complete;
	var result;
	try {
		var popToastHtml = "";
		popToastHtml += '<div class="xsw_showToast">';
		if (mask == true) {
			popToastHtml +=
				'<div class="xsw_bei"></div>'
		}
		popToastHtml +=
			'<div class="pop-toast">';
		if (image) {
			popToastHtml += '<img src="' + image + '" style="width: 50px;margin-bottom:10px;">'
			popToastHtml +=
				'<div class="toast-tip" >' + tipTxt + '</div></div></div>';
		} else {
			if (icon == "none") {
				popToastHtml += '<img  style="width: 50px;margin-top: 12px;display: none">'
				popToastHtml +=
					'<div class="toast-tip">' + tipTxt + '</div></div></div>';
			} else {
				if (icon == "success") {
					popToastHtml += '<div class="check_success_icon"></div>';
				} else if (icon == "loading") {
					popToastHtml +=
						'<div id="xsw_canvas" style="background: rgb(255, 254, 220);background: linear-gradient(0deg, rgba(86, 86, 86, 0.4) 30%, rgb(255, 255, 255) 100%);"><div class="circle-core"></div></div>';
				}
				popToastHtml +=
					'<div class="toast-tip">' + tipTxt + '</div></div></div>';
			}

		}
		$("body").append(popToastHtml);
		$(".xsw_showToast .xsw_bei").css({
			"position": "fixed",
			'left': '0',
			'top': '0',
			'background-color': 'rgba(90,90,90,0)',
			'z-index': '10000',
			'width': '100%',
			'height': '100%'
		});

		$('.xsw_showToast .pop-toast').css({
			'position': 'fixed',
			'max-width': '140px',
			'padding': '15px',
			'background-color': '#555',
			'border-radius': '10px',
			'top': '50%',
			'left': '50%',
			'z-index': '100000',
			'box-shadow': ' 0 2px 8px #555 ',
			'text-align': 'center',
			'transform': 'translate(-50%,-50%)',
			'-webkit-transform': 'translate(-50%,-50%)',
			'-moz-transform': 'translate(-50%,-50%)',
			'-o-transform': 'translate(-50%,-50%)'
		});
		$('.xsw_showToast .circle-core').css({
			'width': '100%',
			'height': '100%',
			'background-color': '#555',
			'border-radius': '50%',
		});

		$('.xsw_showToast .toast-tip').css({
			'font-size': '14px',
			'text-align': 'center',
			'color': '#fff',
			'word-wrap': 'break-word'
		});
		if (icon == "success") {
			$('.check_success_icon').css({
				'width': '25px',
				'height': '50px',
				'margin': '0 auto 15px',
				'border-color': '#fff',
				'border-radius': '4px',
				'border-style': 'solid',
				'border-width': '0 5px 5px 0',
				'transform': 'rotate(45deg)',
				'-webkit-transform': 'rotate(45deg)',
				'-moz-transform': 'rotate(45deg)',
				'-o-transform': 'rotate(45deg)'
			})
		} else if (icon == "loading") {
			$('#xsw_canvas').css({
				'width': '50px',
				'height': '50px',
				'border-radius': '50%',
				'margin': '0 auto 15px',
				'padding': '3px'
			})
			var zhuan;
			var zzz = 0;
			zhuan = setInterval(function() {
				zzz = zzz + 5;
				$('#xsw_canvas').css({
					'transform': 'rotate(' + zzz + 'deg)',
					'-webkit-transform': 'rotate(' + zzz + 'deg)',
					'-moz-transform': 'rotate(' + zzz + 'deg)',
					'-o-transform': 'rotate(' + zzz + 'deg)'
				});
			}, 20);
		}
		////时间
		if (time != "" || time != 0) {
			setTimeout(function() {
				$(".xsw_showToast").remove();
				clearInterval(zhuan)
			}, time);
		}
		result = {
			showToast: "ok"
		};
		if (success) {
			success(result);
		}
		if (complete) {
			complete(result);
		}

	} catch (e) {
		result = {
			errMsg: e.message
		};
		if (fail) {
			fail(result);
		}
		if (complete) {
			complete(result);
		}
	}

};

var hideLoading = function() {
	$(".xsw_showToast").remove();
};


var showModal = function(object) {
	let title = object.title;
	let content = object.content;
	let showCancel = object.showCancel;
	let cancelColor = object.cancelColor;
	let confirmColor = object.confirmColor;
	let cancelBgColor = object.cancelBgColor;
	let confirmBgColor = object.confirmBgColor;
	let cancelText;
	if (!object.cancelText) {
		cancelText = "取消";
	} else {
		cancelText = object.cancelText;
	}
	let confirmText;
	if (!object.confirmText) {
		confirmText = "确定";
	} else {
		confirmText = object.confirmText;
	}
	let success = object.success;
	let fail = object.fail;
	let complete = object.complete;
	var result;
	try {
		var showModalHtml = "";
		showModalHtml += '<div class="xsw_showModa">'
		showModalHtml +=
			'<div class="xsw_showModaBei"></div>'
		showModalHtml +=
			'<div class="xsw_modal-data"><div style="text-align: center;font-size: 18px;font-weight: 400">' + title +
			'</div><br><div style="text-align: center;font-size: 14px;margin-bottom: 30px;">' + content + '</div><br>';
		if (showCancel == false) {
			showModalHtml +=
				'<div class="xsw_modal_btn" style="position: absolute;bottom: 5px;"><div class="yesDian" style="background-color: gold;border-radius: 10px;">' +
				confirmText + '</div></div>'
		} else {
			showModalHtml +=
				'<div class="xsw_modal_btn" style="position: absolute;bottom: 0px;"><div class="noDian" style="background-color: #fff;border-radius: 0 0 0 10px;padding: 8px 0;color: #000000">' +
				cancelText +
				'</div><div class="yesDian" style="background-color: #fff;;border-radius: 0 0 10px 0;border-left:1px solid #eaeaea">' +
				confirmText + '</div></div>'
		}
		'></div></div>';
		$("body").append(showModalHtml);
		$('.xsw_showModaBei').css({
			"position": "fixed",
			'left': '0',
			'top': '0',
			'background-color': 'rgba(90,90,90,0.2)',
			'z-index': '10000',
			'width': '100%',
			'height': '100%'
		});
		$('.xsw_modal-data').css({
			'position': 'fixed',
			'width': '239px',
			'min-height': '135px',
			'padding': '15px 0 0 0',
			'background-color': '#fff',
			'border-radius': '10px',
			'top': '50%',
			'color': '#3d3d3d',
			'left': '50%',
			'z-index': '100000',
			'box-shadow': ' 0 2px 8px #555 ',
			'text-align': 'center',
			'transform': 'translate(-50%,-50%)',
			'-webkit-transform': 'translate(-50%,-50%)',
			'-moz-transform': 'translate(-50%,-50%)',
			'-o-transform': 'translate(-50%,-50%)'
		});
		$('.xsw_modal_btn').css({
			'position': 'absolute',
			'left': '0',
			'width': '100%',
		});
		$('.xsw_modal_btn div').css({
			'width': '119px',
			'display': 'inline-block',
			'margin-bottom': '0',
			'padding': '8px 0',
			'cursor': 'pointer',
		});
		if (cancelColor || cancelBgColor) {
			$('.xsw_modal_btn .noDian').css({
				'color': cancelColor,
				'background-color': cancelBgColor
			})
		};
		if (confirmColor || confirmBgColor) {
			$('.xsw_modal_btn .yesDian').css({
				'color': confirmColor,
				'background-color': confirmBgColor
			})
		};
		$('.xsw_modal_btn .noDian').click(function() {
			$(".xsw_showModa").remove();
			result = {
				cancel: true,
				showModal: "ok"
			};
			if (success) {
				success(result);
			}
			if (complete) {
				complete(result);
			}
		});
		$('.xsw_modal_btn .yesDian').click(function() {
			$(".xsw_showModa").remove();
			result = {
				confirm: true,
				showModal: "ok"
			};
			if (success) {
				success(result);
			}
			if (complete) {
				complete(result);
			}
		});
	} catch (e) {
		result = {
			errMsg: e.message
		};
		if (fail) {
			fail(result);
		}
		if (complete) {
			complete(result);
		}
	}
};
////调用
// 参数	        类型	   必填	   说明
// title	    String	    是	提示的标题
// content	    String	    是	提示的内容
// content	    Boolean	    否	是否显示取消按钮，默认为 true
// cancelText	String	    否	取消按钮的文字，默认为"取消"，最多 4 个字符
// cancelColor	HexColor	否	取消按钮的文字颜色，默认为"#000000"
// confirmText	String	    否	确定按钮的文字，默认为"确定"，最多 4 个字符
// confirmColor	HexColor	否	确定按钮的文字颜色，默认为"#000000"
// success	    Function	否	接口调用成功的回调函数
// fail	        Function	否	接口调用失败的回调函数
// complete	    Function	否	接口调用结束的回调函数（调用成功、失败都会执行)
// success返回参数说明
// 参数	类型	说明
// confirm	Boolean	为 true 时，表示用户点击了确定按钮
// cancel	Boolean	为 true 时，表示用户点击了取消

// showModal({ 
// 	title: "提示",  //提示的标题
// 	content: "确认要删除吗？",  //提示的内容
// 	showCancel: true,  //是否显示取消按钮，默认为 true
// 	cancelText: 'no',  //取消按钮的文字，默认为"取消"，最多 4 个字符
// 	cancelColor: "#ffffff",  //取消按钮的文字颜色，默认为"#000000"
// 	cancelBgColor: '#5a5a5a',  //取消按钮的背景颜色 
// 	confirmText: 'yes',
// 	confirmColor: '#ffffff',
// 	confirmBgColor: '#337ab7',
// 	success: function(res) {
// 		if (res.confirm) {
// 			console.log('yes');
// 		} else {
// 			console.log('no');
// 		}
// 	}
// });




//调用
// 参数	      类型	   必填	   说明
// title	 String	    是	 提示的内容
// icon	     String	    否	 图标，有效值 "success", "loading", "none"
// image	 String	    否	 自定义图标的本地路径，image 的优先级高于 icon
// duration	 Number	    否	 提示的延迟时间，单位毫秒，默认：1500
// mask  	 Boolean	是   否显示透明蒙层，防止触摸穿透，默认：false
// success	 Function	否	 接口调用成功的回调函数
// fail 	 Function	否	 接口调用失败的回调函数
// complete	 Function	否	 接口调用结束的回调函数（调用成功、失败都会执行)


// showLoading({
// 	title: "数据加载中...", //提示文字
// 	icon: 'loading', // 图标，有效值 "success", "loading", "none"
// 	image: '', // 自定义图标的本地路径，image 的优先级高于 icon
// 	duration: 3000, //提示的延迟时间，单位毫秒，默认：1500
// 	mask: true, // 是否显示透明蒙层，防止触摸穿透，默认：false
// 	success: function(res) { //接口调用成功的回调函数
// 		console.log(JSON.stringify(res))
// 	},

// });

