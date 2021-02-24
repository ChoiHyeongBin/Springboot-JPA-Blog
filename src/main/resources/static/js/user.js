let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{		// function(){} 를 안 쓰고, ()=>{} 를 쓰는 이유는 this 를 바인딩하기 위해
			this.save();
		});	// 1번째 파라미터 : 어떤 이벤트가 일어날 건지, 2번째 파라미터 : 무엇을 할껀지
		$("#btn-login").on("click", ()=>{		// function(){} 를 안 쓰고, ()=>{} 를 쓰는 이유는 this 를 바인딩하기 위해
			this.login();
		});
	},
	
	save: function(){
		// alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// console.log(data);
		
		// ajax 호출 시 default 가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json 으로 변경하여 insert 요청
		// ajax 가 통신을 성공하고 서버가 json 을 리턴해주면 자동으로 자바 오브젝트로 변환해 줌
		$.ajax({
			type: "POST",
			url: "/api/user",
			data: JSON.stringify(data),	// http body 데이터
			contentType: "application/json; charset=utf-8",		// body 데이터가 어떤 타입인지(MIME)
			dataType: "json"		// 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){	// 성공
			alert("회원가입이 완료되었습니다.");
			// console.log(resp);
			
			location.href = "/";
		}).fail(function(error){		// 실패
			alert(JSON.stringify(error));
		});
	}, 
	
	login: function(){
		// alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		};

		$.ajax({
			type: "POST",		// 로그인의 get 방식은 주소에 id, pw 정보가 나오므로 위험
			url: "/api/user/login",
			data: JSON.stringify(data),	// http body 데이터
			contentType: "application/json; charset=utf-8",		// body 데이터가 어떤 타입인지(MIME)
			dataType: "json"		// 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){	// 성공
			alert("로그인이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){		// 실패
			alert(JSON.stringify(error));
		});
	}
	
}

index.init();