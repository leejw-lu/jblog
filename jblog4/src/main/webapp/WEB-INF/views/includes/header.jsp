<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("#languages a").click(function(event){
		event.preventDefault();
		$(this).data("lang")
		
		document.cookie=
			"lang="+$(this).data("lang")+ "; " + 			//name=value
			"path=${pageContext.request.contextPath}; " + 	//path
			"max-age=" + (30*24*60*60)						//max-age
			
		//reload
		location.reload();
	});
});
</script>

<h1 class="logo">JBlog</h1>
	<div id="languages">
		<c:choose>
			<c:when test='${language=="en" }'>
				<a href="" data-lang="ko">KR</a><a href="" data-lang="en" class="active">EN</a>
			</c:when>
			<c:otherwise>
				<a href="" data-lang="ko" class="active">KR</a><a href="" data-lang="en">EN</a>
			</c:otherwise>
		</c:choose>
	</div>
	
	<ul class="menu">
	<c:choose>
		<c:when test='${empty authUser }'>
			<li><a href="${pageContext.request.contextPath}/user/login"><spring:message code="header.gnb.login" /></a></li>
			<li><a href="${pageContext.request.contextPath}/user/join"><spring:message code="header.gnb.join" /></a></li>
		</c:when>
	
		<c:otherwise>
			<li><a href="${pageContext.request.contextPath}/user/logout"><spring:message code="header.gnb.logout" /></a></li>
			<li><a href="${pageContext.request.contextPath}/${authUser.id }/"><spring:message code="header.gnb.myblog" /></a></li>
		</c:otherwise>
	</c:choose>
	</ul>