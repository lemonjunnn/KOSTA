$(function(){
    //로그인여부를 확인할 servlet 요청 
    //응답형태
    //{"status":1} : logined - header>nav>a(첫번째)를 로그아웃으로 변경
    //{"status":0} : not logined
    let url = `${backPath}/loginstatus`;
    let method = 'get';
    $.ajax ({ 
        url: url,
        method:method,
        success:function(jsonObj) {
            let $navObj = $('header>nav');
            let $navObjHtml = '';
            if(jsonObj.status == 1) { // logined
                $navObjHtml += `<a href="${backPath}/logout">로그아웃</a>`;  
                $navObjHtml += '<a href="vieworder.html">주문목록</a>';  
            } else { // not logined
                $navObjHtml += '<a href="login.html">로그인</a>'; 
                $navObjHtml += '<a href="signup.html">가입</a>'; 
            }
            $navObjHtml += '<a href="product_list.html">상품</a>'; 
            $navObjHtml += '<a href="viewcart.html">장바구니</a>';
            $navObj.html($navObjHtml);
        },
        error: function(jqXHR) {
            alert('오류 : ' + jqXHR.status);
            console.log('오류 : ' + jqXHR.status);
        }
    });

    //메뉴 객체들(로그인, 가입, 상품 등)을 찾기
    let $menuObj = $('header>nav');
    //section객체의 첫번째 article 찾기
    let $firstArticleObj = $('section>article:first');
    
    //---메뉴 click start----
    //메뉴가 클릭되면 article영역의 innerHTML로 load
    $menuObj.on('click', 'a', function(){
        let url = $(this).attr('href');
        let title = $(this).html();
        $firstArticleObj.load(url, function(responseText, statusText, xhr){
            if (statusText != 'success') {
                if(xhr.status == 404) {
					let msg = title + ' 자원을 찾을 수 없습니다.';
					alert(msg);
				}
            }
        });
        return false; 
    }); 

    //trigger
    $('div.result>button.productlist').click(function(){
        $('nav>a[href="product_list.html"]').trigger('click');
    });
    $('div.result>button.viewcart').click(function(){
        $('nav>a[href="viewcart.html"]').trigger('click');
    });
});