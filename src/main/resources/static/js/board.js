let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{	
			this.save();
		});
		$("#btn-delete").on("click", ()=>{	
			this.deleteById();
		});
		$("#btn-update").on("click", ()=>{	
			this.update();
		});
		$("#btn-reply-save").on("click", ()=>{	
			this.replySave();
		});
	},

	save: function(){
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",	
			dataType: "json"
		}).done(function(resp){	
			alert("글쓰기가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}, 
	
	deleteById: function(){
		let id = $("#id").text();
			console.log(id);
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json"
		}).done(function(resp){	
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}, 
	
	update: function(){
		let id = $("#id").val();
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "PUT",
			url: "/api/board/" + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",	
			dataType: "json"
		}).done(function(resp){	
			alert("글수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}, 
	
	replySave: function(){
		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};
		
		console.log(data);

		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,		// 자바스크립트 문자열과 변수를 같이 쓸 때 편하게 사용하기 위하여 ` (백틱)을 사용한다
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",	
			dataType: "json"
		}).done(function(resp){	
			alert("댓글작성이 완료되었습니다.");
			location.href = `/board/${data.boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
	
}

index.init();