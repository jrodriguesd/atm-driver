<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.UUID" %>
<%
    UUID uuid = UUID.randomUUID();
    String uuidStr   = uuid.toString();
    String pageTitle = "ATM-CB";
	String entity    = "atmconfig";
    String entityJsp = "partials/_" +  entity + ".jsp";
%>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <jsp:include page="partials/_head.jsp">
            <jsp:param name="pageTitle" value="<%=pageTitle%>" />
        </jsp:include>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/css/ui.jqgrid.min.css">
        <style type="text/css" media="screen">
            /* Bump up the font-size in the grid */
            .ui-jqgrid,
            .ui-jqgrid .ui-jqgrid-view,
            .ui-jqgrid-sortable,
			tr.jqgrow,
            .ui-jqgrid .ui-jqgrid-pager,
            .ui-jqgrid .ui-pg-input {
                font-size: 14px;
                font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
            }
		</style>
    </head>
    <body style="background-color:#fafafa;">
        <%@ include file="partials/_script_js.jsp" %>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/jquery.jqgrid.min.js"></script>
    	<!-- 
    	  *******************************************************************************
    	  * https://free-jqgrid.github.io/getting-started/index.html
    	  *******************************************************************************
    	  -->
        <script>
        //<![CDATA[
        function linkFormatter (cellvalue, options, rowObject)
        {
    	   return "<a href=\"atmlog.jsp?id=" + cellvalue + "\">" + cellvalue + "</a>";
        }
    
        $(function () {
            "use strict";
            $("#list_records").jqGrid({
                url: 'http://localhost:8080/jpos/api/atmlogs/atmLoGrid', 
                mtype: 'GET',
                datatype: 'json',
                colModel: [
    				{ name: "atmlog_id",             label: "Id",            align: "center", width:  "80", formatter:linkFormatter },
    				{ name: "atmlog_ip",             label: "Ip",            align: "center", width: "120"},
                    { name: "atmlog_luno",           label: "Luno",          align: "center", width:  "80"},
                    { name: "atmlog_message_class",  label: "Message Class", align: "center", width:  "80"},
                    { name: "atmlog_atm_request_dt", label: "Request Time",  align: "left",   width: "310"},
                    { name: "atmlog_card",           label: "Card Number",   align: "left",   width: "160"},
                    { name: "atmlog_amount",         label: "Amount",        align: "rigth",  width: "120"}
                ],
			    pager: "#perpage",
			    rowNum: 20,
			    rowList: [10,20],
			    sortname: "atmlog_id",
			    sortorder: "asc",
			    height: 'auto',
			    viewrecords: true,
			    gridview: true,
			    caption: "",
                guiStyle: "bootstrap",
                iconSet: "fontAwesome"
            }).jqGrid("filterToolbar");
        });
        //]]>
        </script>
        <div class="row page-title-header">
            <div class="col-md-12">
                <div class="page-header-toolbar">
                    <div class="filter-wrapper"></div>
                </div>
            </div>
        </div>
		<center>
            <table id="list_records"><tr><td></td></tr></table> 
		    <div id="perpage"></div>
		</center>
    </body>
</html>