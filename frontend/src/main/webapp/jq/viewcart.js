$(function(){
    // backend가 아직 완성이 안된 상태일때 테스트하기위한 임시코드
    // let jsonObj=[{p: 'C0001', quantity: 1}, {p: 'C0002', quantity: 3}];
    // let $itemObj = $('div.item');
    // $(jsonObj).each(function(index, element){
    //     $copyObj = $itemObj.clone();
    //     let p = element.p;
    //     $copyObj.find('div.product').html(p);
    //     let quantity = element.quantity;
    //     $copyObj.find('div.quantity').html(quantity);
    //     $('div.viewcart').append($copyObj);
    // });
    
    $.ajax({
        url: `${backPath}/viewcart`,
        success: function(jsonObj){
            let $itemObj = $('div.viewcart>div.item');
            $(jsonObj).each(function(index, element){
                $copyObj = $itemObj.clone();
                let p = element.p;
                let q = element.quantity;
                let product = '<ul>';
                product += '<li class="product_no"> 상품번호 : ' + p.productNo + '</li>';
                product += '<li class="product_name"> 상품이름 : ' + p.productName + '</li>';
                product += '<li class="product_price"> 가격 : ' + p.productPrice + '</li>';
                product += '</ul>';
                let quantity = '<ul>';
                quantity += '<li class="quantity"> 수량 : ' + q + '</li>';
                quantity += '</ul>';               
                $copyObj.find('div.product').html(product);
                $copyObj.find('div.quantity').html(quantity);
                $('div.viewcart').append($copyObj);
            });
        },
        error: function(jqXHR){
            alert('오류 : ' + jqXHR.status);
        }
    });

    //주문하기 버튼 클릭
    $('div.viewcart>div.add_order>button.order_button').click(function(){
        $.ajax({
            url:`${backPath}/addorder`,
            success:function(jsonObj){
                if(jsonObj.status == 1) { //order success
                    alert(jsonObj.message);
                    $('nav>a[href="productlist.html"]').trigger('click');
                } else if(jsonObj.status == 0) { // not logined
                    alert(jsonObj.message);
                    $('nav>a[href="login.html"]').trigger('click');
                } else if(jsonObj.status == -1) { // order failure
                    alert(jsonObj.message);
                } 
            },
            error: function(jqXHR){
                alert('오류 : ' + jqXHR.status);
            }
        });
    });
});