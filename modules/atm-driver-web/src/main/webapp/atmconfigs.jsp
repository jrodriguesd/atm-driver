<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String pageTitle = "ATM-CB";
    String pageName  = "ATM Configutaion Builder / Configurations";
%>
<jsp:include page="partials/_template.jsp">
    <jsp:param name="pageTitle" value="<%=pageTitle%>" />
    <jsp:param name="pageName"  value="<%=pageName%>" />
    <jsp:param name="entity"    value="atmconfig" />
</jsp:include>
