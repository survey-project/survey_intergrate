//10,20,30개씩 selectBox 클릭 이벤트
function changeSelectBox(currentPage, cntPerPage, pageSize){
    var selectValue = $("#cntSelectBox").children("option:selected").val();
    movePage(currentPage, selectValue, pageSize); 
}

//페이지 이동
function movePage(currentPage, cntPerPage, pageSize){
    
    let url = "/myList";
    url = url + "?currentPage="+currentPage;
    url = url + "&cntPerPage="+cntPerPage;
    url = url + "&pageSize="+pageSize;
    
    let srchTyp = $('#srchTyp').val();
	let keyword = $('#keyword').val();
 
    if(keyword != null) {
    	url = url + "&srchTyp=" + srchTyp;
   	 	url = url + "&keyword=" + keyword;
    }
    
    location.href=url;
}

function searchKw() {
	let srchTyp = $('#srchTyp').val();
	let keyword = $('#keyword').val();
	let selectValue = $("#cntSelectBox").children("option:selected").val();
	
	 let url = "/myList";
	 url = url + "?srchTyp=" + srchTyp;
	 url = url + "&keyword=" + keyword;
	 url = url + "&cntPerPage=" + selectValue;
	 
	 location.href=url;
}

function searchReset() {
	location.href="/myList";
}