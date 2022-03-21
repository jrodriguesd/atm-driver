<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String pageTitle = "ATM-CB";
    String pageName  = "ATM Configutaion Builder / Screens";
%>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <jsp:include page="partials/_head.jsp">
            <jsp:param name="pageTitle" value="<%=pageTitle%>" />
        </jsp:include>
    </head>
    <body>
        <div class="container-scroller">
            <!-- top navbar -->
            <jsp:include page="partials/_top_navbar.jsp">
                <jsp:param name="pageTitle" value="<%=pageTitle%>" />
                <jsp:param name="pageName"  value="<%=pageName%>" />
            </jsp:include>
            <!-- partial -->
            <div class="container-fluid page-body-wrapper">
                <!-- sidebar nav -->
				<%@ include file="partials/_sidebar.jsp" %>
                <div class="main-panel">
                    <div class="content-wrapper">
                        <!-- page title header starts-->
                        <div class="row page-title-header">
                            <div class="col-md-12">
                                <div class="page-header-toolbar">
                                    <div class="filter-wrapper"></div>
                                </div>
                            </div>
                        </div>
                        <!-- page title header ends-->
                        <!-- content-wrapper begins -->
                        <%@ include file="partials/_screens.jsp" %>
                        <!-- content-wrapper ends -->
                    </div>
                    <!-- footer -->
                    <jsp:include page="partials/_footer.jsp">
                        <jsp:param name="pageTitle" value="<%=pageTitle%>" />
                    </jsp:include>
                </div>
                <!-- main-panel ends -->
            </div>
            <!-- page-body-wrapper ends -->
        </div>
        <%@ include file="partials/_included_scripts.jsp" %>
        <script type="text/javascript" src="assets/js/jquery.atm.screen.js"></script>
    </body>
</html>