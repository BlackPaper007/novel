$(function(){
	$("#btnSearch").click(search);
	$("#keyword").click(function(e) {
		$("#keyword").select();
	});
});

function search() {
	$("#list").html("");
	var wd = $("#keyword").val().trim();
	var site = $('input:radio:checked').val();
	if (!wd) {
		alert("请输入小说的名字或者作者的名字...");
		return;
	}
	$("#loadModal").css("display","block");
	if (wd.indexOf("http") == 0) {
		searchByUrl(wd);
	} else if (site != 0) {
		DDsearchBywd(wd);
	} else {
		searchBywd(wd);
	}
};

function searchByUrl(url) {
	window.open("./chapterList?url=" + url);
	$("#loadModal").css("display","none");
}
function searchBywd(wd) {
	$.ajax({
		url : "./keywordSearch/" + wd,
		type : "get",
		dataType : "json",
		error : function() {
			alert("小说抓取失败");
			$("#loadModal").css("display","none");
		},
		success : function(result) {
			if(result.length==0){
				DDsearchBywd(wd);
			}else{
				$("#loadModal").css("display","none");
				$(result).each(function(index, novel) {
					var $tr = $("<tr><td>"+ (index + 1)
						+ "</td><td><a href='./chapterList?url="
						+ novel.url	+ "'>"
						+ novel.name + "</a></td><td>"
						+ novel.author + "</td><td>"
						+ isLatelychapterNull(novel.latelychapter)+ "</td><td>"
						+ getPlatformId(novel.platformId) + "</td><td><a>下载</a></td></tr>");
					//./download?url="+ novel.url + "
					$("#list").append($tr);
				});
			}
		}
	});
};

function DDsearchBywd(wd){
	$.ajax({
		url : "./chapterListByKeyword/" + wd,
		type : "get",
		error : function() {
			alert("小说抓取失败");
			$("#loadModal").css("display","none");
		},
		success : function(result) {
			$("#loadModal").css("display","none");
			var gettype=Object.prototype.toString;
			if(result.length==0){
				alert("暂时没有该小说！");
			} else{
				$("#list").html("");
				$(result).each(function(index, novel) {
					var $tr = $("<tr><td>"+ (index + 1)
						+ "</td><td><a href='./chapterList?url="
						+ novel.url	+ "'>"
						+ novel.name + "</a></td><td>"
						+ novel.author + "</td><td>"
						+ isLatelychapterNull(novel.latelychapter)+ "</td><td>"
						+ getPlatformId(novel.platformId) + "</td></tr>");
					$("#list").append($tr);
				});
			}
		}
	});
};

function isLatelychapterNull(latelychapter) {
	if (latelychapter == null) {
		return "未知";
	} else {
		return latelychapter;
	}
};
function getPlatformId(platformId) {
	if (platformId == 1) {
		return "顶点小说";
	} else if (platformId == 2) {
		return "uu书盟";
	} else if (platformId == 3) {
		return "新笔趣阁";
	} else if (platformId == 4) {
		return "笔趣阁";
	} else if (platformId == 5) {
		return "笔趣库";
	} else {
		return "未知";
	}
};
