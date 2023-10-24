$(function(){
	$.ajax({
		type: 'post',
		url: '/user/getUserList',
		data: 'pg=' + $('#pg').val(),
		dataType: 'json',
		success: function(data){
			$.each(data.list, function(index, items){ 
				console.log(items.name); 		  
				$('<tr/>').append($('<td/>', { 	  
					align: 'center',
					text : items.name
				})).append($('<td/>',{
				}).append($('<a/>', { 
					href:'#', 
					text : items.id,
					class: 'subjectA'
					}))
				).append($('<td/>', {
					align: 'center',
					text : items.pwd
				})).appendTo($('#userListTable'));
			}); // $.each
			
			//페이징 처리
			$('#userPagingDiv').html(data.userPaging.pagingHTML);
			
			// 아이디를 클릭했을 때
			$('.subjectA').click(function(){
				location.href='/user/updateForm?id=' + $(this).text() + '&pg=' + $('#pg').val(); // 해당 주소로 넘겨받은 파라미터 값 출력
			});
		},
		error: function(e){
			console.log(e);
		}
	});
});