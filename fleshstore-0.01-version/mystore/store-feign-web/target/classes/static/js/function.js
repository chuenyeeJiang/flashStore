function cycle(){
$(function() {
	// 初始化轮播
	$("#div_log2Back").carousel('cycle');
});
}
function resize() {
	// 初始化轮播
	if (window.innerHeight > 800) {
		// 800+window.innerHeight/800
		$("#div_log2Back img").css('width', 1920 * window.innerHeight / 800);
		$("#div_log2Back img").css('margin-top',
				-100 * window.innerHeight / 800);
	}
	/* $("#carousel-inner img").css('hight','130%'); */
};

function readUsername(){
	// 初始化用户名
	$.ajax({
		url : "readUsername.do",
		success : function(result) {
			if (result != null && result != "" && result != "null") {
			$(".input-remenberUsername").val(result);
			$(".checkbox-remenberUsername").attr("checked", true);
				// 自动登陆
		/*		$.ajax({
					url : "readPassword.do",
					success : function(result) {
						$(".input-autoLogin").val(result);
						if (result != null && result != "") {
							$(".checkbox-autoLog").attr("checked", true);
							if ("${noLog}" != 1) {
								$(".form-login").submit();
							}
						}
					}
				});*/
			}
		}
	});
	// 已登陆
//	$.ajax({
//		url : "hasLogin.do",
//		success : function(result) {
//			if (result != 1) {
//				alert("账号已经成功登录!");
//				document.write(result);
//			}
//		}
//	});

}

// 复选框处理（自动登陆等于拥有记住用户名功能 ）
/*function checkbox_checked(){
$(document).ready(
		function() {
			$(".checkbox-remenberUsername").click(function() {
				if (!$(this).is(":checked") && $("#autoLog").is(":checked")) {
					$(".checkbox-autoLog").removeProp("checked");
				} else if ($(".checkbox-autoLog").is(":checked")) {
					$(".checkbox-autoLog").removeProp("checked");
					$(this).removeProp("checked")
				}
			});

			$(".checkbox-autoLog").click(
					function() {
						if ($(this).is(":checked")
								&& !$(".checkbox-remenberUsername").is(
										":checked")) {
							$(".checkbox-remenberUsername").prop("checked",
									true);
						}
					});
		});
}*/

// 登陆聚焦
function autoFocus(){
var focusIndex = null;
$(document).ready(function() {
	var autoComonent = $(".form-autoFocus .input-autoFocus");

	/*
	 * $(autoComonent).each(function(index){ alert($(this).attr("name")); });
	 */
	function PointerHeaderAddressByOneListener(){
		document.addEventListener("keyup",firstFocus
			,true);
	}
	PointerHeaderAddressByOneListener();
	autoComonent.blur(function(){
		PointerHeaderAddressByOneListener();
	});
	
	

	
	function firstFocus(event){
		if (event.which == 13) {
		$(autoComonent)[0].focus();
		document.removeEventListener("keyup",firstFocus,true);
		}
	}
	
	
	$(autoComonent).keyup(function(event) {
		var key = event.which;
		if (key == 13) {
			focusIndex = $(this).attr("focus-index");
			if (focusIndex == null) {
				return;
			}
			if ($(this).hasClass("input-submit")) {
				$(this).onclick();
			}
			// 下一位置
			focusIndex = eval(focusIndex) + 1;
			$(autoComonent)[focusIndex].focus();
		}
	});
});
}

//  提交
function submitformautoFocus() {
	$(".form-autoFocus").submit();
}


function ChangeValidatecode() {
	var image = new Image();
	image.src = "aJax_validatecode.do?date=" + new Date();
	image.className = "img-validatecode";
	$(".img-validatecode").replaceWith(image);
}

// 数目增减
function numIncreaseOrReduce(){
var NumBtnId = null;
var NumId = null;
$(document).ready(function() {
	$(".btn-numReduce").click(function() {
		// 获取组件标志
		if ($(this).attr("id") == null)
			NumBtnId = "btn-num";

		NumId = "#" + NumBtnId.substr(4, NumBtnId.length);

		if (eval($(NumId).text()) > 1)
			$(NumId).text(eval($(NumId).text()) - 1);
	});
});

$(document).ready(function() {

	$(".btn-numIncrease").click(function() {
		// 获取组件标志
		NumBtnId = $(this).attr("id");
		if (NumBtnId == null)
			NumBtnId = "btn-num";

		NumId = "#" + NumBtnId.substr(4, NumBtnId.length);
		$(NumId).text(eval($(NumId).text()) + 1);
	});
});
}

//外部验证
function  loginCl()
{
       var usernameCl=/^[\u4E00-\u9FA5a-zA-Z]*$/;
       //用户名不合法
       if(!usernameCl.test($("#username").val())){
       $("#log_font_msg").text("用户名不合法,只支持由中文或字母组成!");
       return false;
       }
       var passwordCl=/^[a-zA-Z0-9\ |\~|\`|\!|\@|\#|\$|\%|\^|\&|\*||||\-|\_|\+|\=|\||\\||\{|\}|\;|\:|\"|\'|\,|\<|\.|\>|\/|\?]{6,14}$/;
       if(!passwordCl.test($("#password").val())){
       $("#log_font_msg").text("密码不合法!");
       return false;
       }
}
function  regisCl()
{
	alert("regisCl");
       var usernameCl=/^[\u4E00-\u9FA5a-zA-Z]*$/;
       //用户名不合法
       if(!usernameCl.test($("#regisUsername").val())){
       $("#regis_font_msg").text("用户名不合法,只支持由中文或字母组成!");
       return false;
       }
       var passwordCl=/^[a-zA-Z0-9\ |\~|\`|\!|\@|\#|\$|\%|\^|\&|\*||||\-|\_|\+|\=|\||\\||\{|\}|\;|\:|\"|\'|\,|\<|\.|\>|\/|\?]{6,14}$/;
       if(!passwordCl.test($("#regisPassword").val())){
       $("#regis_font_msg").text("密码不合法!");
       return false;
       }
       if($("#regisPassword").val()!=$("#confirmPassword").val()){
       $("#regis_font_msg").text("密码不一致!");
       return false;
       }
}

/*
   控件拖拽
 */
function draggable(className,edgeProcessing) {
	/*
	  初始化
	 */
	//取消图片原有拖拽
	if ($("." + className).prop("tagName") === "IMG") {
		$("." + className).attr("ondragstart", "return false");
		$("." + className).attr("onselect", "return false");
	}
	$("." + className).css("position", "fixed");
	var children = $("." + className).find("IMG");
	children.attr("ondragstart", "return false");
	children.attr("onselect", "return false");
	//修复黏鼠问题
	$("input").bind("click",function (ev) {
		isMove = false;
	});
	$("." + className).bind("click",function (e) {
		isMove = false;
	});

	/*
	   变量
	 */
	var clickValue = $("." + className).attr("onclick");
	var hrefValue = $("." + className).attr("href");
	var isMove = false;
	var elementX, elementY;
	var left0, top0;
	var x, y;
	var reg = new RegExp("px", "g");
	var theWidthPxValue, theHeightPxValue, theWidthValue, theHeightValue;

	/*
	方法
	 */


	/*
	   拖拽监听
	 */
	$("." + className).bind('mousedown', function (e) {
		isMove = true;
		left0 = e.pageX;
		top0 = e.pageY;
		elementX = parseInt(e.pageX) - parseInt($("." + className).css("margin-left"));
		elementY = parseInt(e.pageY) - parseInt($("." + className).css("margin-top"));
	})

	$("." + className).bind('touchstart', function (event) {
		console.log("touchstart");
		isMove = true;
		left0 = event.originalEvent.targetTouches[0].pageX;
		top0 = event.originalEvent.targetTouches[0].pageY;
		elementX = parseInt(event.originalEvent.touches[0].pageX) - parseInt($("." + className).css("margin-left"));
		elementY = parseInt(event.originalEvent.touches[0].pageY) - parseInt($("." + className).css("margin-top"));


	})

	$(document).bind('mousemove', function (e) {
		console.log("mousemove");
		if (isMove) {
			x = parseInt(e.pageX - elementX);
			y = parseInt(e.pageY - elementY);

			//获取控件宽高
			theWidthPxValue = $("." + className).css("width");
			theHeightPxValue = $("." + className).css("height");
			theWidthValue = theWidthPxValue.replace(reg, "");
			theHeightValue = theHeightPxValue.replace(reg, "");

			if (edgeProcessing === "触碰") {
				if (x < 0)
					x = 1;
				if (y < 0)
					y = 1;
				if (x > parseInt(document.body.clientWidth - theWidthValue))
					x = parseInt(document.body.clientWidth - theWidthValue);
				if (y > parseInt(window.innerHeight - theHeightValue))
					y = parseInt(window.innerHeight - theHeightValue);
			}
			if (edgeProcessing === "露角") {
				if (x <  -theWidthValue/2)
					x = (-parseInt($("." + className).css("width")) + 5);
				if (y < 0)
					y = 1;
				if (x > parseInt(document.body.clientWidth - theWidthValue/2))
					x = parseInt(document.body.clientWidth - 5);
				if (y > parseInt(window.innerHeight - theHeightValue))
					y = parseInt(window.innerHeight - theHeightValue);
			}
			$("." + className).css("margin-left", parseInt(x));
			$("." + className).css("margin-top", parseInt(y));
		}
	});
	$("." + className).bind('touchmove', function (event) {
		event.preventDefault();
		//event.propertyIsEnumerable()
		console.log("touchmove");
		console.log($("." + className));
		if (isMove) {

			x = parseInt(event.originalEvent.targetTouches[0].pageX - elementX);
			y = parseInt(event.originalEvent.targetTouches[0].pageY - elementY);

			//获取控件宽高
			theWidthPxValue = $("." + className).css("width");
			theHeightPxValue = $("." + className).css("height");
			theWidthValue = theWidthPxValue.replace(reg, "");
			theHeightValue = theHeightPxValue.replace(reg, "");

			if (edgeProcessing === "触碰") {
				if (x < 0)
					x = 1;
				if (y < 0)
					y = 1;


				if (x > parseInt(document.body.clientWidth - theWidthValue))
					x = parseInt(document.body.clientWidth - theWidthValue);
				if (y > parseInt(window.innerHeight - theHeightValue))
					y = parseInt(window.innerHeight - theHeightValue);
			}
			if (edgeProcessing === "露角") {

				if (x <  -theWidthValue/2)
					x = (-parseInt($("." + className).css("width")) + 5);
				if (y < 0)
					y = 1;
				if (x > parseInt(document.body.clientWidth - theWidthValue/2))
					x = parseInt(document.body.clientWidth - 5);
				if (y > parseInt(window.innerHeight - theHeightValue))
					y = parseInt(window.innerHeight - theHeightValue);
			}
			$("." + className).css("margin-left", parseInt(x));
			$("." + className).css("margin-top", parseInt(y));
		}
	})

	$(document).bind('mouseup', function (e) {
		console.log("touchend");
		isMove = false;
		if (e.pageX !== left0 || e.pageY !== top0) {
			$("." + className).removeAttr("onclick");
			$("." + className).removeAttr("href");


		} else {
			$("." + className).attr("onclick", clickValue);
			$("." + className).attr("href", hrefValue);

		}
	});
	$(document).bind( 'touchend', function (e) {
		console.log("touchend");
		isMove = false;
		if (e.pageX !== left0 || e.pageY !== top0) {
			$("." + className).removeAttr("onclick");
			$("." + className).removeAttr("href");
		} else {
			$("." + className).attr("onclick", clickValue);
			$("." + className).attr("href", hrefValue);
		}
	});


}


/*
       图片上传
*/
//三个参数为按钮触发,用于文件上传INPUT,需要显示上传图片的IMG
function pictureUpload(ClassName_input,ClassName_img) {
    /*
    变量
     */
	var eleFile = document.querySelector("." + ClassName_input);
	var file ;

	/*
	初始化
	 */

	$("." + ClassName_input).bind("change",function (ev) {
		file = eleFile.files[0];
		var reader = new FileReader();
		reader.readAsDataURL(file);
		//加载图片
		reader.onload = function (e) {
			$("." + ClassName_img).attr("src", this.result);
			reader.flush;
			reader.close;
		}
	});

		/*
		方法
		 */
	this.pictureUploadStart = function pictureUploadStart(ex) {
		$("." + ClassName_input).click();
	};
	//this.pictureUploadStart();

}




 /*   //XML功能 (只支持IE)
	function LoadXML() {
		var xmlDoc = new ActiveXObject("Msxml2.DOMDocument");
		xmlDoc.async = false;
		xmlDoc.resolveExternals = false;
		xmlDoc.load("xml/test.xml");

		this.queryNode = function  () {
			     var theme = xmlDoc.selectSingleNode("/resouce/theme[name='theme1']");

			     alert(theme.selectSingleNode("/background").text);


		}
	}*/
 /*
   XML 读取
  */
 function LoadXML(theme){
	 var name;

	 var result;
 	$.ajax({
		url:"XML/test.xml",
		type: 'GET',
		dataType: 'xml',
		timeout: 1000,
		async: false,
		cache:false,
		success: function (xml) {
			$(xml).find("theme").each(function (i) {
				name=$(this).children("name");
				console.log(theme);
				console.log(name.text()===theme);
				if(name.text()===theme){
					result = $(this);
				}

			});

		}
	});
	 return result;
 }


 function fullScreenHeight(className){
 	$("."+className).css("Height",window.innerHeight);
 }