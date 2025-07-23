$(function(){
	let rowCount = 10;
	let currentPage;
	let count;
	/*-------------------------
	 * 댓글 목록
	 *-------------------------*/
	function selectList(pageNum){
		currentPage = pageNum;
		
		//로딩 이미지 노출
		$('#loading').show();
		
		//서버와 통신
		$.ajax({
			url:'listReply.do',
			type:'get',
			data:{pageNum:pageNum,
				  rowCount:rowCount,
				  board_num:$('#board_num').val()},
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				count = param.count;
				
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을
					//제거
					$('#output').empty();
				}
				
				//댓글 수 읽어 오기
				displayReplyCount(param);
				
				/*
				템플릿 리터럴(Template Literal) : 기존의 
				문자열은 '또는 "로 감싸고 + 기호로 연결해야
				변수 사용 가능이 가능하지만 템플릿 리터럴은
				백틱(`,어금문자)를 이용해 문자열을 보다 간결하게
				작성.
				문자열 안에서 변수 삽입 (Express Interpolation)
				${변수명} 사용해 변수나 표현식을 문자열에 삽입
				*/
				
				//댓글 목록 작업
				$(param.list).each(function(index,item){
					let output = `
								 <div class="item">
								   <h4>${item.id}</h4>
								   <div class="sub-item">
								     <p>${useBrNoHtml(item.re_content)}</p>
					`;
					
					if(item.re_mdate){
						output += `<span class="modify-date">최근 수정일 : ${item.re_mdate}</span>`;
					}else{
						output += `<span class="modify-date">등록일 : ${item.re_date}</span>`;
					}
					
					//좋아요 설정
					if(param.user_num==item.mem_num){
						//로그인한 회원번호와 댓글 작성자 회원번호 일치
						output +=`
								 <input type="button" data-num="${item.re_num}" data-mem="${item.mem_num}" value="수정" class="modify-btn">
								 <input type="button" data-num="${item.re_num}" data-mem="${item.mem_num}" value="삭제" class="delete-btn">
						`;
					}
					
					output += `
					    	    <hr size="1" noshade>
							   </div>
							  </div>
					`;
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				
				//paging button 처리
				if(currentPage>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
				
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류');
			}
		});
		
	}
	//다음 댓글 보기 버튼 클릭시 데이터 추가
	$('.paging-button input').click(function(){
		selectList(currentPage+1);
	});
	
	/*-------------------------
	 * 댓글 수정
	 *-------------------------*/
	//댓글 수정 버튼 클릭시 수정 폼 노출
	$(document).on('click','.modify-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-num');
		//작성자 회원번호
		let mem_num = $(this).attr('data-mem');
		//댓글 내용
		let content = $(this).parent().find('p')
		                              .html()
									  .replace(/<br>/gi,'\n');
		//댓글 수정 폼 UI
		let modifyUI = `
						<form id="mre_form">
							<input type="hidden" name="re_num" value="${re_num}">
							<input type="hidden" name="mem_num" value="${mem_num}">
							<textarea rows="3" cols="50" name="re_content" id="mre_content" class="rep-content">${content}</textarea>
							<div id="mre_first">
								<span class="letter-count">300/300</span>
							</div>	
							<div id="mre_second" class="align-right">
								<input type="submit" value="수정">
								<input type="button" value="취소" class="re-reset">
							</div>
							<hr size="1" noshade width="96%">
						</form>	
		`;
		
		//이전에 이미 수정하는 댓글이 있을 경우 수정 버튼을 클릭하면
		//숨김 sub-item를 환원시키고 수정 폼을 초기화함
		initModifyForm();
		//지금 클릭해서 수정하고자 하는 데이터는 감추기
		//수정 버튼을 감싸고 있는 div
		$(this).parent().hide();
		
		//수정 폼을 수정하고자하는 데이터가 있는 div에 노출
		$(this).parents('.item').append(modifyUI);
		
		//입력한 글자수 셋팅
		let inputLength = $('#mre_content').val().length;
		let remain = 300 - inputLength;
		remain += '/300';
		
		//문서 객체에 반영
		$('#mre_first .letter-count').text(remain);
									  
	});
	//수정 폼에서 취소 버튼 클릭시 수정 폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});
	//댓글 수정 폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mre_form').remove();
	}
	//댓글 수정
	$(document).on('submit','#mre_form',function(event){
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#mre_content').val('').focus();
			return false;
		}
		
		//폼에 입력한 데이터 반환
		let formData = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'updateReply.do',
			type:'post',
			data:formData,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result == 'success'){
					$('#mre_form').parent().find('p')
					              .html(useBrNoHtml($('#mre_content').val()));
					$('#mre_form').parent().find('.modify-date')
					                       .text('최근 수정일 : 5초미만');
			        //수정 폼 초기화
					initModifyForm();							   			  
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글은 수정할 수 없습니다.');
				}else{
					alert('댓글 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
		
		//기본 이벤트 제거
		event.preventDefault();
	});
	
	/*-------------------------
	 * 댓글 수 표시
	 *-------------------------*/	
	function displayReplyCount(param){
		let output;
		if(param.count==0){
			output = '댓글수(0)';
		}else{
			output = '댓글수('+param.count+')';
		}
		//문서 객체에 추가
		$('#output_rcount').text(output);
	}
	
	/*-------------------------
	 * HTML 태그를 허용하지 않고 줄바꿈 처리
	 *-------------------------*/
	function useBrNoHtml(str){
		if(!str) str;
		return str.replace(/</g,'&lt;')
		          .replace(/>/g,'&gt;')
				  .replace(/\r\n/g,'<br>')
				  .replace(/\r/g,'<br>')
				  .replace(/\n/g,'<br>')
	}
	
	/*-------------------------
	 * 댓글 등록
	 *-------------------------*/
	$('#re_form').submit(function(event){
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#re_content').focus();
			return false;
		}
		
		let formData = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'writeReply.do',
			type:'post',
			data:formData,
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result=='success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서
					//첫 번째 페이지의 게시글들을 다시 호출함
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});	
		//기본 이벤트 제거
		event.preventDefault();
	});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	
	/*-------------------------
	 * 댓글 등록,수정 공통 글자수 체크
	 *-------------------------*/
	//textarea에 내용 입력시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구하기
		let inputLength = $(this).val().length;
		
		if(inputLength>300){//300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		}else{//300자 이하인 경우
			//남은 글자수 구하기
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id')=='re_content'){
				//등록 폼 글자수
				$('#re_first .letter-count').text(remain);
			}else{
				//수정 폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
		
	});
	
	//초기 데이터(목록) 호출
	selectList(1);	
});










