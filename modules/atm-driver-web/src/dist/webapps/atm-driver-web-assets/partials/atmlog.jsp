<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.UUID" %>
<%
    UUID uuid = UUID.randomUUID();
    String uuidStr = uuid.toString();
%>
                    <div id="<%=uuidStr%>" class="ml-3 mr-4">
                        <div class="row page-title-header">
                            <div class="col-md-12">
                                <div class="page-header-toolbar">
                                    <div class="filter-wrapper"></div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>LUNO</label>
                                    <div class="input-group">
                                        <span class="input-group-text">
                                            <i class="fa fa-edit fa-fw"></i>
                                        </span>
                                        <input type="text" id="atmcnf_languageindex" class="form-control" name="atmcnf_languageindex" autocomplete="off" value="0" maxlength="9" tabindex="1" aria-hidden="true" disabled>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>IP</label>
                                    <div class="input-group">
                                        <span class="input-group-text">
                                            <i class="fa fa-edit fa-fw"></i>
                                        </span>
                                        <input type="text" id="atmcnf_languageindex" class="form-control" name="atmcnf_languageindex" autocomplete="off" value="0" maxlength="15" tabindex="2" aria-hidden="true" disabled>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Message Class</label>
                                    <div class="input-group">
                                        <span class="input-group-text">
                                            <i class="fa fa-edit fa-fw"></i>
                                        </span>
                                        <input type="text" id="atmcnf_languageindex" class="form-control" name="atmcnf_languageindex" autocomplete="off" value="0" maxlength="2" tabindex="3" aria-hidden="true" disabled>
                                    </div>
                                </div>
                            </div>
						
                        </div>
                    </div>
