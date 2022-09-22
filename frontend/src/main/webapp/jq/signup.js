$(function(){
	 // id & id 중복확인 show / hide
    let $id = $('input[name=id]');
    let $submitBtn = $('input[type=submit]');
    let $idDuplicationCheckBtn = $('input[value=아이디중복확인]');
    $id.focus(function(){
       $submitBtn.css("display", "none");
    });
    $idDuplicationCheckBtn.click(function(){
        let url = `${backPath}/idduplicationcheck`;
       	let data = {id: $id.val()}; // user가 입력한 id value
        console.log('id : ' + $id.val());
        $.ajax({
			url: url,
			method: 'post',
			data: data,
			success: function(jsonObj) {
                if(jsonObj.status == 1) { //중복없음
                    $submitBtn.css("display", "inline");
                } else {
                    alert(jsonObj.message);
                } 
			},
			error: function(jqXHR) {
				alert('에러코드 : ' + jqXHR.status);
			}
		});   
    });
	
	// 주소 찾기 & 입력
    let $postalCodeBtn = $('input[value=우편번호찾기]');
    $postalCodeBtn.click(function(){
        var url = '/frontend/html/searchzip.html';
        var target = 'first'; // 해당 페이지를 중복해서 띄울 수 없게 설정
        var feature = 'width=400px, height=400px';
        windowID = window.open(url, target, feature);
    });	
	
	
    //가입버튼 클릭 이벤트 발생 ->
    //form submit이벤트 발생 -> 기본처리 (전송)
    //form 객체 찾기
    let $form = $('form.signup');
    $form.submit(function(){
        //비밀번호 일치 확인
        let $pwd = $('form.signup input[name=pwd]');
        let $pwd1 = $('form.signup input.confirm');
        if($pwd.val() != $pwd1.val()) {
            alert('비밀번호가 일치하지 않습니다.');           
            $pwd.focus();
            return false;
        }
        alert('비밀번호 일치합니다');
        
        /*
        let $idValue = $('input[name=id]').val();
        let $pwdValue = $('input[name=pwd]').val();
        let $nameValue = $('input[name=name]').val();
        let $addrValue = $('input[name=addr]').val();
        let $buildingnoValue = $('input[name=buildingno]').val();
        */
        let url = `${backPath}/signup`;
        /*let data = {id: $idValue, 
        			pwd: $pwdValue, 
        			name: $nameValue, 
        			addr: $addrValue, 
        			buildingno:$buildingnoValue};*/
       	let data = $(this).serialize();
        $.ajax({
			url: url,
			method: 'post',
			data: data,
			success: function(jsonObj) {
				alert(jsonObj.message);
			},
			error: function(jqXHR) {
				alert('에러코드 : ' + jqXHR.status);
			}
		});
		return false;
    });
});