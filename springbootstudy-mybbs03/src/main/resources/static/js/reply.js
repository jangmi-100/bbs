$(function(){
	
	$(".btnCommend").click(function(){
		var com=$(this).attr("id");
		console.log("com : "+com);
		
		$.ajax({
			url:"recommend.ajax",
			type:"post",
			data : {recommend:com,no:$("#no").val()},
			dataType:"json",
			success:function(data){
				var msg=com=='commend'?"추천이":"땡큐가";
				alert(msg+"반영되었습니다.");
				$("#commend>.recommend").text("("+data.recommend+")");
				$("#thank>.recommend").text("("+data.thank+")");
			},
			error:function(xhr,status,error){
				alert("error:"+xhr.statusText+","+status+","+error);
			}
		});
	});
});