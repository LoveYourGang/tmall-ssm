<!-- 模仿天猫整站j2ee 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<a href="${contextPath}">
		<img id="logo" src="img/site/logo.gif" class="logo">
	</a>
	
	<form action="fore/search.do" method="post" >
		<div class="searchDiv">
			<input name="keyword" type="text" value="${param.keyword}" placeholder="iPhone 12 Pro Max   99式主战坦克">
			<button  type="submit" class="searchButton">搜索</button>
			<div class="searchBelow">
				<c:forEach items="${cs}" var="c" varStatus="st">
					<c:if test="${st.count>=5 and st.count<=8}">
						<span>
							<a href="fore/category.do?cid=${c.id}">
								${c.name}
							</a>
							<c:if test="${st.count!=8}">				
								<span>|</span>				
							</c:if>
						</span>			
					</c:if>
				</c:forEach>		
			</div>
		</div>
	</form>	