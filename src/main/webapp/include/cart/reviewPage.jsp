<!-- 模仿天猫整站j2ee 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script>
function check() {
	var text = document.getElementsByName("content");
	for(i in text) {
		if(text[i].value == "") {
			alert("评价内容不能为空！");
			return false;
		}
	}
	alert("评价成功！");
	return true;
}
</script>

<form method="post" action="fore/doreview.do" onsubmit="return check();">
<c:forEach items="${ois }" var="oi" varStatus="st">	
<div class="reviewDiv">
	<div class="reviewProductInfoDiv">
		<div class="reviewProductInfoImg"><img width="400px" height="400px" src="img/productSingle/${oi.product.firstProductImage.id}.jpg"></div>
		<div class="reviewProductInfoRightDiv">
			<div class="reviewProductInfoRightText">
				${oi.product.name}
			</div>
			<table class="reviewProductInfoTable">
				<tr>
					<td width="75px">价格:</td>
					<td><span class="reviewProductInfoTablePrice">￥<fmt:formatNumber type="number" value="${oi.product.orignalPrice}" minFractionDigits="2"/></span> 元 </td>
				</tr>
				<tr>
					<td>配送</td>
					<td>快递:  0.00</td>
				</tr>
				<tr>
					<td>月销量:</td>
					<td><span class="reviewProductInfoTableSellNumber">${oi.product.saleCount}</span> 件</td>
				</tr>
			</table>
			
			<div class="reviewProductInfoRightBelowDiv">
				<span class="reviewProductInfoRightBelowImg"><img1 src="img/site/reviewLight.png"></span>
				<span class="reviewProductInfoRightBelowText" >现在查看的是 您所购买商品的信息
于<fmt:formatDate value="${o.createDate}" pattern="yyyy年MM月dd日 HH:mm:ss"/>下单购买了此商品 </span>
			
			</div>
		</div>
		<div style="clear:both"></div>
	</div>
	<div class="reviewStasticsDiv">
		<div class="reviewStasticsLeft">
				<div class="reviewStasticsLeftTop"></div>
				<div class="reviewStasticsLeftContent">累计评价 <span class="reviewStasticsNumber"> ${oi.product.reviewCount}</span></div>
				<div class="reviewStasticsLeftFoot"></div>
		</div>
		<div class="reviewStasticsRight">
			<div class="reviewStasticsRightEmpty"></div>
			<div class="reviewStasticsFoot"></div>
		</div>
	</div>		
	
	<c:if test="${param.showonly==true}">
	<div class="reviewDivlistReviews">
		<c:forEach items="${reviews.get(st.index)}" var="r">
			<div class="reviewDivlistReviewsEach">
				<div class="reviewDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
				<div class="reviewContent">${r.content}</div>
				<div class="reviewUserInfo pull-right">${r.user.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span></div>
			</div>
		</c:forEach>
	</div>
	</c:if>
	
	<c:if test="${param.showonly!=true}">
		<div class="makeReviewDiv">
		
			<div class="makeReviewText">其他买家，需要你的建议哦！</div>
			<table class="makeReviewTable">
				<tr>
					<td class="makeReviewTableFirstTD">评价商品</td>
					<td><textarea name="content"></textarea></td>
				</tr>
			</table>
			<div class="makeReviewButtonDiv">
				<input type="hidden" name="oid" value="${o.id}">
				<input type="hidden" name="pid" value="${oi.product.id}">
				
			</div>
		
		</div>	
	</c:if>

</div>
</c:forEach>

<div class="makeReviewButtonDiv">
	<c:if test="${param.showonly!=true}">
		<button type="submit">提交评价</button>
	</c:if>
		<a href="fore/bought.do"><button type="button">我的订单</button></a>
</div>


</form>