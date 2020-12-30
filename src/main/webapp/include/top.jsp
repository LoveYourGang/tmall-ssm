<!-- 模仿天猫整站j2ee 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="top ">
		<a href="fore/home.do">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
			天猫首页
		</a>	
		
		<span>喵，欢迎来天猫</span>
		
		<c:if test="${!empty user}">
			<a href="login.jsp">${user.name}</a>
			<a href="fore/logout.do">退出</a>
			<c:if test="${user.name eq 'xiagang' }">
				<a href="category/list.do">管理员页面</a>
			</c:if>	
		</c:if>
		
		<c:if test="${empty user}">
			<a href="login.jsp">请登录</a>
			<a href="register.jsp">免费注册</a>		
		</c:if>


		<span class="pull-right">
			<a href="fore/bought.do">我的订单</a>
			<a href="fore/cart.do">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
			购物车
			<c:if test="${!empty user}">
			<strong>${cartTotalItemNumber}</strong>件
			</c:if>
			</a>
			
		</span>
		
		
</nav>



