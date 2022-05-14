<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.UUID" %>
<%
    UUID uuid = UUID.randomUUID();
    String uuidStr   = uuid.toString();
    String pageTitle = "ATM-CB";
	String entity    = "atmlog";
    String entityJsp = "partials/_" +  entity + ".jsp";
%>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <jsp:include page="partials/_head.jsp">
            <jsp:param name="pageTitle" value="<%=pageTitle%>" />
        </jsp:include>
    </head>
    <body style="background-color:#fafafa;">
        <div id="<%=uuidStr%>" class="ml-3 mr-4">
		    <jsp:include page="<%=entityJsp%>" />
		</div>
        <%@ include file="partials/_script_js.jsp" %>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.atm.<%=entity%>.js"></script>
		<script>
		    $("<%=uuidStr%>").ready(function() 
			{
                console.log("<%=entity%>.jsp Line 11");
				const queryString = window.location.search;
                const urlParams = new URLSearchParams(queryString);
                const id = urlParams.get('id')
	            const atmlog = new ATMLog();
				atmlog.setId(id);
	            atmlog.setJWT("qwerty-dummy-jwt");
	            atmlog.setBaseURL("/jpos/api");
	            atmlog.init();
            });
		</script>
    </body>
</html>