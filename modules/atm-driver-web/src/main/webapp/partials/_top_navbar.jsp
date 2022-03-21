<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
            <nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
                <div class="text-center navbar-brand-wrapper d-flex align-items-top justify-content-center">
                    <a id="navbar-brand" class="navbar-brand brand-logo p-3" href="index.php"><%= request.getParameter("pageTitle") %><i class="fa fa-cogs"></i>
                    </a>
                    <a id="navbar-brand" class="navbar-brand brand-logo-mini" href="index.php"><%= request.getParameter("pageTitle") %></a>
                </div>
                <div class="navbar-menu-wrapper d-flex align-items-center">
                    <ul class="navbar-nav">
                        <span class="menu-title"><%= request.getParameter("pageName") %></span>
                    </ul>
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item dropdown d-none d-xl-inline-block user-dropdown">
                            <a class="nav-link dropdown-toggle" id="UserDropdown" href="#" data-toggle="dropdown" aria-expanded="false">
                                <!-- <img class="img-xs rounded-circle" src="assets/images/faces/face8.jpg" alt="Profile image"></a> -->
                                <i class="fa fa-user-o"></i>
                                <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="UserDropdown">
                                    <div class="dropdown-header text-center">
                                        <!-- <img class="img-md rounded-circle" src="assets/images/faces/face8.jpg" alt="Profile image"> -->
                                        <p class="mb-1 mt-3 font-weight-semibold">José Rodríguez</p>
                                        <p class="font-weight-light text-muted mb-0">jr@gmail.com</p>
                                    </div>
                                    <a href="#" class="dropdown-item">Sign Out <i class="dropdown-item-icon ti-power-off"></i>
                                    </a>
                                </div>
                            </a>
                        </li>
                    </ul>
                    <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                        <span class="mdi mdi-menu"></span>
                    </button>
                </div>
            </nav>
