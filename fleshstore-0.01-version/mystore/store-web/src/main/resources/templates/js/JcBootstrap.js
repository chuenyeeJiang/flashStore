$(function() {
	// 初始化轮播
	$("#div_log2Back").carousel('cycle');
});
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
window.onload = function() {
	// 初始化用户名
	$.ajax({
		url : "readUsername.do",
		success : function(result) {
			$(".input-remenberUsername").val(result);
			if (result != null && result != "") {
				$(".checkbox-remenberUsername").attr("checked", true);
				// 自动登陆
				$.ajax({
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
				});
			}
		}
	});

	// 已登陆
	$.ajax({
		url : "hasLogin.do",
		success : function(result) {
			if (result != 1) {
				alert("账号已经成功登录!");
				document.write(result);
			}
		}
	});
};

// 复选框处理（自动登陆等于拥有记住用户名功能 ）
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

// 登陆聚焦
var focusIndex = null;
$(document).ready(function() {
	var autoComonent = $(".form-autoFocus .input-autoFocus");

	/*
	 * $(autoComonent).each(function(index){ alert($(this).attr("name")); });
	 */

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

// 登陆触发
function log(thisUI) {
	$(".form-autoFocus").submit();
}

function ChangeValidatecode() {
	var image = new Image();
	image.src = "aJax_validatecode.do?date=" + new Date();
	image.className = "img-validatecode";
	$(".img-validatecode").replaceWith(image);
}

// 数目增减
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
