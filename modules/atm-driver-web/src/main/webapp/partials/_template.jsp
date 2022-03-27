<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <jsp:include page="_head.jsp">
            <jsp:param name="pageTitle" value='<%= request.getParameter("pageTitle") %>' />
        </jsp:include>
    </head>
    <body>
        <div class="container-scroller">
            <!-- top navbar -->
            <jsp:include page="_top_navbar.jsp">
                <jsp:param name="pageTitle" value='<%= request.getParameter("pageTitle") %>' />
                <jsp:param name="pageName"  value='<%= request.getParameter("pageName") %>' />
            </jsp:include>
            <!-- partial -->
            <div class="container-fluid page-body-wrapper">
                <!-- sidebar nav -->
				<%@ include file="_sidebar.jsp" %>
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
                        <%
                            String entity = "_" + request.getParameter("entity") + ".jsp";
                        %>
						<jsp:include page="<%=entity%>" />
                        <!-- content-wrapper ends -->
                    </div>
                    <!-- footer begins -->
                    <jsp:include page="_footer.jsp">
                        <jsp:param name="pageTitle" value='<%= request.getParameter("pageTitle") %>' />
                    </jsp:include>
                    <!-- footer ends -->
                </div>
                <!-- main-panel ends -->
            </div>
            <!-- page-body-wrapper ends -->
        </div>
        <%@ include file="_included_scripts.jsp" %>
        <script type="text/javascript" src="assets/js/jquery.atm.<%= request.getParameter("entity") %>.js"></script>
    </body>
</html>