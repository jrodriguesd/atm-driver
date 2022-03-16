<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title><%= request.getParameter("pageTitle") %></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico" />
        <!-- plugins:css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/iconfonts/mdi/css/materialdesignicons.min.css">
        <!-- Jquery UI CSS -->
        <!--
        <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.1/themes/base/minified/jquery-ui.min.css" type="text/css" /> 
        -->
        <!-- endinject -->
        <!-- plugin css for this page -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/iconfonts/font-awesome/css/font-awesome.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/shared/style.css">
        <!-- endinject -->
        <!-- Layout styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo_1/style.css">
        <!-- End Layout styles -->
        <!-- Google Font -->
        <!--
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Ultra&display=swap">
        -->
        <!-- link rel="stylesheet" href="app/assets/vendors/bootstrap/css/bootstrap-4.5.2.min.css" -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/multiselect/css/bootstrap-multiselect.css">
        <style type="text/css" media="screen">
        /*
		Default puzzle styling
	    */
            table.screen {
                font: 12.2px monospace;
                border-collapse: collapse;
                border-spacing: 0;
                max-width: 100%;
            }

            table.screen tr {
                width: 100%;
            }

            table.screen td {
                width: 20px;
                height: 22.7px;
                border: 1px solid #eaeaea;
                padding: 0;
                margin: 0;
                background-color: #f4f4f4;
                position: relative;
                text-align: center;
                z-index: 1;
            }

            table.screen td input {
                width: 100%;
                height: 100%;
                padding: 0em;
                border: none;
                text-align: center;
                color: #666;
                background-color: #ffffff;
                font: bold 17px monospace;
            }

            table.screen td input:focus {
                /* background-color: #fff; */
            }

            td span {
                color: #444;
                position: absolute;
                top: -1px;
                left: 1px;
            }

            input.done {
                font-weight: bold;
                color: green;
            }
        </style>
