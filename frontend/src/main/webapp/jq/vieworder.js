$(function(){
	//주문목록 추가
	 $.ajax({
        url: `${backPath}/vieworder`,
        success: function(jsonObj){
            if (jsonObj.status == 1) { // 주문이 있는 경우
            	console.log(jsonObj.orderinfos);
                let $itemObj = $('div.vieworder>div.vieworderlist');
                $(jsonObj).each(function(index, element){
                    $copyObj = $itemObj.clone();
                    //let orderedProductNo = element.orderinfos.lines.orderP.productName;
                    let orderinfo = element.orderinfos;
                    //let orderedQuantity = element.orderinfos.lines.orderQuantity;
                    let order = '<ul>';
                    //order += '<li> 상품명 : ' + orderedProductNo + '</li>';
                    order += '<li>' + JSON.stringify(orderinfo) + '</li>';
                    //order += '<li> 주문수량 : ' + orderedQuantity + '</li>';
                    order += '</ul>';
                 
                    $copyObj.find('div.eachorderlist').html(order);
                    $('div.vieworder').append($copyObj);
                });
            } else { // 주문이 없는 경우
                alert(jsonObj.message);
            }
           
        },
        error: function(jqXHR){
            alert('오류 : ' + jqXHR.status);
        }
    });
});