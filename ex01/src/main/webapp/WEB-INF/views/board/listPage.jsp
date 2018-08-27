<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false"%>
<%@include file="../include/header.jsp"%>

<table class="table table-bordered">
	<tr>
		<th style="width: 10px">BNO</th>
		<th>TITLE</th>
		<th>WRITER</th>
		<th>REGDATE</th>
		<th style="width: 40px">VIEWCNT</th>
	</tr>
	<c:forEach items="${list}" var="boardVO">

		<tr>
			<td>${boardVO.bno}</td>
			<td><a href='/board/read?bno=${boardVO.bno}'>${boardVO.title}</a></td>
			<td>${boardVO.writer}</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
					value="${boardVO.regdate}" /></td>
			<td><span class="badge bg-red">${boardVO.viewcnt}</span></td>
		</tr>
	</c:forEach>
</table>

<div class="text_center" align="center">
	<ul class="pagination">

		<c:if test="${pageMaker.prev}">
			<li><a 
			href="listPage?page=${pageMaker.startPage - 1}">&laquo;</a></li>
		</c:if>

		<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}"
			var="idx">
			<li <c:out value="${pageMaker.cri.page == idx?'class =active' :''}"/>>
				<a href="listPage?page=${idx}">${idx}</a> <li>
			
		</c:forEach>
			
			<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
				<li><a href="listPage?page=${pageMaker.endPage +1}">&raquo;</a></li>
			</c:if>
		</ul>
	</div>

<script>
	var result = '${msg}';

	if (result == "SUCCESS") {
		alert("처리가 완료 되었습니당.")
	}
</script> <%@ include file="../include/footer.jsp"%>