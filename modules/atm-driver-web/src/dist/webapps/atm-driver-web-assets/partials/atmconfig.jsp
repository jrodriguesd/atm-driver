<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.UUID" %>
<%
    UUID uuid = UUID.randomUUID();
    String uuidStr = uuid.toString();
%>
                    <div id="<%=uuidStr%>" class="ml-3 mr-4">
					    <script>
						    $("<%=uuidStr%>").ready(function() 
							{
                                console.log("atmconfigs.jsp Line 4");
	                            const atmconfig = new ATMConfig();
	                            atmconfig.init();
                            });
					    </script>
                        <div class="row page-title-header">
                            <div class="col-md-12">
                                <div class="page-header-toolbar">
                                    <div class="filter-wrapper"></div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-3" style="border: 0px solid black;">
                                <select class="form-control" style="font-family:'Lucida Console', monospace; font-size: 11px;" size="25" id="atmconfigsDataList"></select>
                                <!--
                                <option>000 | Time Out</option><option>001 | OffLine</option><option>002 | Out Of Service</option><option>003 | Administrative Process</option><option>010 | Welcome</option>
						        -->
                            </div>
                            <div class="col-9" style="border: 0px solid black;">
                                <!-- *************************************************************************************************************************** -->
								<!-- Mensaje de Respuesta - Inicio -->
                                <div id="ResponseMsg">
                                </div>
								<form id='atmconfigsForm' action="javascript:alert( 'success!' );">
                                    <div class="row">

                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Configuration Id</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="atmcnf_config_id" class="form-control" name="atmcnf_config_id" autocomplete="off" value="0800" maxlength="4" tabindex="3" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Protocol</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
													<select class="form-control" id="atmcnf_protocol" name="atmcnf_protocol">
								    				</select>
                                                </div>
                                            </div>
                                        </div>
										
									</div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label>Description</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="atmcnf_desc" class="form-control" name="atmcnf_desc" autocomplete="off" value="Descripcion ATMConfig" maxlength="50" tabindex="4" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Language Index</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="atmcnf_languageindex" class="form-control" name="atmcnf_languageindex" autocomplete="off" value="0" maxlength="1" tabindex="5" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Language ATM</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="atmcnf_languageatm" class="form-control" name="atmcnf_languageatm" autocomplete="off" value="A" maxlength="1" tabindex="6" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Language ISO 639-2</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="atmcnf_language639" class="form-control" name="atmcnf_language639" autocomplete="off" value="eng" maxlength="3" tabindex="7" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Language Screen Group Base</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="atmcnf_screengroupbase" class="form-control" name="atmcnf_screengroupbase" autocomplete="off" value="0" maxlength="3" tabindex="8" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
									</div>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <button type="submit" class="btn btn-success" value="create" tabindex="9" aria-hidden="true">Create</button>
                                        </div>
                                        <div class="col-sm-4">
                                            <button type="submit" class="btn btn-success" value="update" tabindex="10" aria-hidden="true">Update</button>
                                        </div>
                                        <div class="col-sm-4">
                                            <button type="submit" class="btn btn-danger" value="delete" tabindex="11" aria-hidden="true">Delete</button>
                                        </div>
                                    </div>
                                    <br />
								</form>
                                <br>
                            </div>
                        </div>
                    </div>
