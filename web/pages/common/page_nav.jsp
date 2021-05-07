<%--
  Created by IntelliJ IDEA.
  User: WJY
  Date: 2021/1/21
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="page_nav">

    <%--			如果是第一页，则不显示首页和上一页选项--%>
    <c:if test="${requestScope.page.pageNo > 1}">
        <a href="${requestScope.page.url}&pageNo=1">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo-1}">上一页</a>
    </c:if>

    <%-----------------------------页码输出开始--------------------------------%>
    <c:choose>
        <%-------------------------情况1：总页码小于5，则输出全部页码--------------------------%>
        <c:when test="${requestScope.page.pageTotal <= 5}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${requestScope.page.pageTotal}"/>
        </c:when>

        <%-------------------------------情况2：总页码大于5-------------------------------------%>
        <c:when test="${requestScope.page.pageTotal > 5}">
            <c:choose>
                <%--						情况2.1：当前页码为前三个.范围：1-5.本情况同情况1--%>
                <c:when test="${requestScope.page.pageNo <= 3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                </c:when>


                <%--						情况2.2：当前页码为后三个.范围：pageTotal-4 - pageTotal--%>
                <c:when test="${requestScope.page.pageNo > requestScope.page.pageTotal-3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal-4}"/>
                    <c:set var="end" value="${requestScope.page.pageTotal}"/>
                </c:when>


                <%--						情况2.3：当前页码为其他范围：pageNo-2-pageNo+2 --%>
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNo-2}"/>
                    <c:set var="end" value="${requestScope.page.pageNo+2}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

    <%--				抽取相同代码，精简代码--%>
    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i != requestScope.page.pageNo}">
            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
        </c:if>
        <c:if test="${i == requestScope.page.pageNo}">
            【${i}】
        </c:if>
    </c:forEach>


    <%--			如果是最后一页，则不显示末页和下一页选项--%>
    <c:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo+1}">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
    </c:if>

    共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录
    到第<input value="${requestScope.page.pageNo}" name="pn" id="pn_input"/>页
    <input type="button" value="确定" id="selectPageBtn">
</div>
