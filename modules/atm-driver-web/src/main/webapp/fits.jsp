<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String pageTitle = "ATM-CB";
    String pageName  = "ATM Configutaion Builder / Fits";
%>
<jsp:include page="partials/_template.jsp">
    <jsp:param name="pageTitle" value="<%=pageTitle%>" />
    <jsp:param name="pageName"  value="<%=pageName%>" />
    <jsp:param name="entity"    value="fit" />
</jsp:include>
