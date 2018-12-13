function changeToolBarStyle() {
	var btnReviewFlag = false;
	var btnStopFlag = false;
	var btnStartFlag = false;

	var selections = $("#table").datagrid("getSelections");
	$.each(selections, function(i, d) {
		if (d.western_status == '1') {
			btnReviewFlag = true;
		}
		if (d._enable == '0') {
			btnStopFlag = true;
		}
		if (d._enable == '1') {
			btnStartFlag = true;
		}
	})
	if (btnReviewFlag == true) {
		$('#btnReview').linkbutton('disable');
	} else {
		$('#btnReview').linkbutton('enable');
	}
	if (btnStopFlag == true) {
		$('#btnStop').linkbutton('disable');
	} else {
		$('#btnStop').linkbutton('enable');
	}
	if (btnStartFlag == true) {
		$('#btnStart').linkbutton('disable');
	} else {
		$('#btnStart').linkbutton('enable');
	}
}