//��̬�˵�
function menuOnclick(thisUi) {
	console.log(thisUi);
	var thisUiParent = $(thisUi).parent("ul");
	var liList = $(thisUiParent).children("li");
	$(liList).slideToggle();
}

// �û�ȫѡ��ȡ����
function Appuser_AllChecked() {
	var nl = document.getElementsByName("appuser_checkbox");
	$("#Appuser_CencelAll").attr("checked", false);
	for ( var i = 0; i < nl.length; i++) {
		nl[i].checked = true;
	}
}

function Appuser_AllCencel() {
	var nl = document.getElementsByName("appuser_checkbox");
	$("#Appuser_CheckAll").attr("checked", false);
	for ( var i = 0; i < nl.length; i++) {
		nl[i].checked = false;
	}
}

// ��Ϣ��ʾ
function msg() {
	var msg = $
	{
		msg
	}
	;
	if (msg != null || msg != "") {
		alert(msg);
	}
}
