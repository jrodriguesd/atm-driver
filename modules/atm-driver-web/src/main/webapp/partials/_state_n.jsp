                                            <!-- form de Estado - Inicio -->
                                            <div role="tabpanel" class="tab-pane <%= request.getParameter("tabClass") %>" id="nav-<%= request.getParameter("tabIndex") %>">
                                                <!-- *************************************************************************************************************************** -->
								                <!-- Mensaje de Respuesta - Inicio -->
                                                <div id="ResponseMsg-<%= request.getParameter("tabIndex") %>">
                                                </div>
            					                <!-- Mensaje de Respuesta - Fin -->
                                                <form id="statesForm-<%= request.getParameter("tabIndex") %>" action="javascript:alert( 'success!' );">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group">
                                                                <label>Configuration Id</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <select class="form-control" id="config_id-<%= request.getParameter("tabIndex") %>" name="std_config_id">
																	</select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label>State Number</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_number-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_number" autocomplete="off" value="000" maxlength="3" tabindex="1" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label>State Type</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <select id="statesList-<%= request.getParameter("tabIndex") %>" class="form-control js-select2 select2-hidden-accessible" name="std_type" data-select2-id="input-group" tabindex="2" aria-hidden="true"></select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12">
                                                            <div class="form-group">
                                                                <label>Description</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_desc-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_desc" autocomplete="off" value="Time Out" placeholder="Description" tabindex="3" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label id="sta_1_title-0">Screen Number</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_s1-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_s1" autocomplete="off" value="000" placeholder="Screen Number" tabindex="4" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label id="sta_2_title-0">Good Read Next State</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_s2-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_s2" autocomplete="off" value="000" placeholder="Good Read Next State" tabindex="5" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label id="sta_3_title-0">Error Screen Number</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_s3-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_s3" autocomplete="off" value="000" placeholder="Error Screen Number" tabindex="6" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label id="sta_4_title-0">Read Condition 1</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_s4-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_s4" autocomplete="off" value="000" placeholder="Read Condition 1" tabindex="7" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label id="sta_5_title-0">Read Condition 2</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_s5-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_s5" autocomplete="off" value="000" placeholder="Read Condition 2" tabindex="8" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label id="sta_6_title-0">Read Condition 3</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_s6-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_s6" autocomplete="off" value="000" placeholder="Read Condition 3" tabindex="9" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label id="sta_7_title-0">Card Return Flag</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_s7-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_s7" autocomplete="off" value="000" placeholder="Card Return Flag" tabindex="10" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group">
                                                                <label id="sta_8_title-0">No FIT Match Next State</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-text">
                                                                        <i class="fa fa-edit fa-fw"></i>
                                                                    </span>
                                                                    <input type="text" id="std_s8-<%= request.getParameter("tabIndex") %>" class="form-control" name="std_s8" autocomplete="off" value="000" placeholder="No FIT Match Next State" tabindex="11" aria-hidden="true">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- *************************************************************************************************************************** -->
                                                    <div class="row">
                                                        <div class="col-sm-4">
                                                            <button type="submit" class="btn btn-success" value="create" tabindex="11" aria-hidden="true">Create</button>
                                                        </div>
                                                        <div class="col-sm-4">
                                                            <button type="submit" class="btn btn-success" value="update" tabindex="12" aria-hidden="true">Update</button>
                                                        </div>
                                                        <div class="col-sm-4">
                                                            <button type="submit" class="btn btn-danger" value="delete" tabindex="13" aria-hidden="true">Delete</button>
                                                        </div>
                                                    </div>
                                                    <!-- *************************************************************************************************************************** -->
                                                </form>
                                            </div>
                                            <!-- form de Estado - Fin -->
