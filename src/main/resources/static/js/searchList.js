$('#searchListBtn').click(function(){
	if($('#value').val() == ''){		
		alert('검색어를 입력하세요.');
	}else{
		$.ajax({
			type: 'post',
			url: '/user/getUserSearchList',
			data:  {'columnName' : $('#columnName').val(),
					'value' : $('#value').val()}, //columnName, value
			dataType: 'json',
			success: function(data){	
				console.log(JSON.stringify)
				
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
			
			},
			error : function(e){
				console.log(e);
			}
		});// $.ajax
	}
});