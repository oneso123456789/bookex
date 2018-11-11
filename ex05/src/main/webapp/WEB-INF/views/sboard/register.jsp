
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

<script type="text/javascript" src="/resources/js/upload.js"></script>

<style>
.fileDrop{
	width: 80%;
	height: 100px;
	border: 1px dotted gray;
	background-color: lightslategrey;
	margin: auto;

}
</style>


<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER BOARD</h3>
				</div>
				<!-- /.box-header -->

<form id='registerForm' role="form" method="post">
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">Title</label> 
			<input type="text"
				name='title' class="form-control" placeholder="Enter Title">
		</div>
		<div class="form-group">
			<label for="exampleInputPassword1">Content</label>
			<textarea class="form-control" name="content" rows="3"
				placeholder="Enter ..."></textarea>
		</div>
		
		<div class="form-group">
			<label for="exampleInputEmail1">Writer</label> 
			<input type="text"
				name="writer" class="form-control" placeholder="Enter Writer">
		</div>
		
		<div class="form-group">
			<label for="exampleInputEmail1">File DROP Here</label>
			<div class="fileDrop"></div>
		</div>
	</div>
	<!-- /.box-body -->

	<div class="box-footer">
		<div>
			<hr>
		</div>
		<ul class="mailbox-attachments clearfix uploadedList">
		</ul>
		<button type="submit" class="btn btn-primary">Submit</button>
	</div>
</form>


			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<script id="template" type="text/x-handlebars-template">
<li>
	<span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
	<div class="mailbox-attachment-info">
		<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
		<a href="{{fullName}}"
			class="btn btn-default btn-xs pull-right delbtn"><i class="fa fa-fw fa-remove"></i></a>
		</span>
	</div>
</li>
</script>

<script>
	var template = Handlebars.compile($("#template").html());
	
	$(".fileDrop").on("dragenter dragover", function(event){
		event.preventDefault();
	});
	
	$(".fileDrop").on("drop", function(event){
		event.preventDefault();
		
		var files = event.originalEvent.dataTransfer.files;
		
		var file = files[0];
		
		var formData = new FormData();
		
		formData.append("file", file);
		
		$.ajax({
			url: '/uploadAjax',
			data: formData,
			dataType: 'text',
			processData: false,
			contentType: false,
			type: 'POST',
			success: function(data){
				
				var fileInfo = getFileInfo(data);
				
				var html = template(fileInfo);
				
				$(".uploadedList").append(html);
			}
		});
	});
//	<form>태그의 submit은 먼저 기본 동작을 막고 (code 133), 현재까지 업로드 된 파일들을 <input type='hidden'>으로 추가합니다(code 140)
//	이때 각 파일은 files[0]과 같은 이름으로 추가되는데, 이 배열 표시를 이용해서 컨트롤러에서는 BoardVO의 files 피라미터를 수집
//	모든 파일의 정보를 <input type='hidden'> dmfh todtjdgks gndpsms <form> 데이터의 submit을 호출해서 서브를 호출 
// jQuery의 get(0)은 순후한 DOM 객체를 얻어내기 위해서 사용
//	만일 마지막의 submit() (code 149)부분을 주석 처리하고  submit()하지 않는다면, 여러개의 파일이 존재할 때 DOM 요소의 변화를 아래 화면처럼 확인 가능
	$("#registerForm").submit(function(event){	
		event.preventDefault();
	
	var that = $(this);
	
	var str = "";
	
	$(".uploadedList .delbtn").each(function(index){
		str += "<input type='hidden' name='files["+index+"]' value='"+$(this).attr("href") +"'> ";
	});
	
	that.append(str);
	
	that.get(0).submit();
	});
	
	
	$(".uploadedList").on("click",  ".delbtn" , function(event){
		event.preventDefault();
		
		var that = $(this);
		
		$.ajax({
			url:"/deleteFile",
			type: "post",
			data: {fileName:$(this).attr("href")},
			dataType: "text",
			success:function(result){
				console.log("RESULT: " + result);
				if(result == 'deleted'){	
					that.closest("li").remove();
				}
			}
		});
	});
</script>
<%@include file="../include/footer.jsp"%>
