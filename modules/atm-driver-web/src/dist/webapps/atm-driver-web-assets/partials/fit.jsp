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
                                console.log("fit.jsp Line 11");
	                            const atmfit = new ATMFit();
	                            atmfit.init();
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
                                <select class="form-control" style="font-family:'Lucida Console', monospace; font-size: 11px;" size="45" id="fitsDataList"></select>
                                <!--
                                <option>000 | Time Out</option><option>001 | OffLine</option><option>002 | Out Of Service</option><option>003 | Administrative Process</option><option>010 | Welcome</option>
						        -->
                            </div>
                            <div class="col-9" style="border: 0px solid black;">
                                <!-- *************************************************************************************************************************** -->
								<!-- Mensaje de Respuesta - Inicio -->
                                <div id="ResponseMsg">
                                </div>
								<form id='fitsForm' action="javascript:alert( 'success!' );">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label>Configuration Id</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
								    				<!--
                                                    <input type="text" class="form-control" name="config_id" autocomplete="off" value="0850" placeholder="Configuration Id" disabled="">
                                                    <input type="text" class="form-control" id="config_id" name="config_id" autocomplete="off" value="0850" placeholder="Configuration Id">
								    				-->
                                                    <select class="form-control" id="config_id" name="fit_config_id">
								    				</select>
                                                </div>
                                            </div>
                                        </div>
									</div>
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>FIT Number</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_number" class="form-control" name="fit_number" autocomplete="off" value="001" maxlength="3" tabindex="1" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>BIN Prefix - PFIID</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_bin_prefix" class="form-control" name="fit_bin_prefix" autocomplete="off" value="542449FFFF" maxlength="10" tabindex="2" aria-hidden="true">
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
                                                    <input type="text" id="fit_desc" class="form-control" name="fit_desc" autocomplete="off" value="Descripcion FIT MC" maxlength="50" tabindex="3" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Indirect State Index - PSTDX</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <select id="fit_indirectstateidx-0" class="form-control" name="fit_indirectstateidx-0" tabindex="4" aria-hidden="true">
                                                        <option value="0">Logo 0</option>
                                                        <option value="1">Logo 1</option>
                                                        <option value="2">Logo 2</option>
                                                        <option value="3">Logo 3</option>
                                                        <option value="4">Logo 4</option>
                                                        <option value="5">Logo 5</option>
                                                        <option value="6">Logo 6</option>
                                                        <option value="7">Logo 7</option>
                                                        <option value="8">Logo 8</option>
                                                        <option value="9">Logo 9</option>
                                                        <option value="a">Logo 10</option>
                                                        <option value="b">Logo 11</option>
                                                        <option value="c">Logo 12</option>
                                                        <option value="d">Logo 13</option>
                                                        <option value="e">Logo 14</option>
                                                        <option value="f">Logo 15</option>
                                                    </select>
                                                    <select id="fit_indirectstateidx-1" class="form-control" name="fit_indirectstateidx-1" tabindex="5" aria-hidden="true">
                                                        <option value="0">Entry 0</option>
                                                        <option value="1">Entry 1</option>
                                                        <option value="2">Entry 2</option>
                                                        <option value="3">Entry 3</option>
                                                        <option value="4">Entry 4</option>
                                                        <option value="5">Entry 5</option>
                                                        <option value="6">Entry 6</option>
                                                        <option value="7">Entry 7</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Diebold Algorithm Index - PAGDX</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_algoidx" class="form-control" name="fit_algoidx" autocomplete="off" value="0" maxlength="1" tabindex="6" aria-hidden="true">
                                                    <select id="fit_idxrefpoints-1" class="form-control" name="fit_idxrefpoints-1" tabindex="7" aria-hidden="true">
                                                        <option value="0">0 - Trk 2 Start Sentinel Forwards</option>
                                                        <option value="1">1 - Trk 3 Start Sentinel Forwards</option>
                                                        <option value="2">2 - Trk 1 Start Sentinel Forwards</option>
                                                        <option value="4">4 - Trk 2 1st Field Separator Forwards</option>
                                                        <option value="5">5 - Trk 3 1st Field Separator Forwards</option>
                                                        <option value="6">6 - Trk 1 1st Field Separator Forwards</option>
                                                        <option value="8">8 - Trk 2 End Sentinel Backwards</option>
                                                        <option value="9">9 - Trk 3 End Sentinel Backwards</option>
                                                        <option value="a">A - Trk 1 End Sentinel Backwards</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Language Code Index - PLNDX</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_langcodeidx" class="form-control" name="fit_langcodeidx" autocomplete="off" value="0" maxlength="1" tabindex="8" aria-hidden="true">
                                                    <select id="fit_idxrefpoints-5" class="form-control" name="fit_idxrefpoints-5" tabindex="9" aria-hidden="true">
                                                        <option value="0">0 - Trk 2 Start Sentinel Forwards</option>
                                                        <option value="1">1 - Trk 3 Start Sentinel Forwards</option>
                                                        <option value="2">2 - Trk 1 Start Sentinel Forwards</option>
                                                        <option value="4">4 - Trk 2 1st Field Separator Forwards</option>
                                                        <option value="5">5 - Trk 3 1st Field Separator Forwards</option>
                                                        <option value="6">6 - Trk 1 1st Field Separator Forwards</option>
                                                        <option value="8">8 - Trk 2 End Sentinel Backwards</option>
                                                        <option value="9">9 - Trk 3 End Sentinel Backwards</option>
                                                        <option value="a">A - Trk 1 End Sentinel Backwards</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Max PIN Length - PMXPN</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <select id="fit_maxpinlen-0" class="form-control" name="fit_maxpinlen-0" tabindex="10" aria-hidden="true">
                                                        <option value="0">0 - Diebold</option>
                                                        <option value="4">4 - Specified by PBFMT (PIN Block Format)</option>
                                                        <option value="8">8 - ISO Format 0</option>
                                                        <option value="c">C - BANKSYS</option>
                                                    </select>
                                                    <select id="fit_maxpinlen-1" class="form-control" name="fit_maxpinlen-1" tabindex="11" aria-hidden="true">
                                                        <option value="0">0</option>
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                        <option value="6">6</option>
                                                        <option value="7">7</option>
                                                        <option value="8">8</option>
                                                        <option value="9">9</option>
                                                        <option value="a">10</option>
                                                        <option value="b">11</option>
                                                        <option value="c">12</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Local PIN Check Length - PCKLN</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <select id="fit_localpinchecklen-0" class="form-control" name="fit_localpinchecklen-0" tabindex="12" aria-hidden="true">
                                                        <option value="0">0 - DES (Local or Remote)</option>
                                                        <option value="1">1 - VISA (Local only)</option>
                                                        <option value="2">2 - DIEBOLD (Not supported in NDC)</option>
                                                    </select>
                                                    <select id="fit_localpinchecklen-1" class="form-control" name="fit_localpinchecklen-1" tabindex="13" aria-hidden="true">
                                                        <option value="0">0</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                        <option value="6">6</option>
                                                        <option value="7">7</option>
                                                        <option value="8">8</option>
                                                        <option value="9">9</option>
                                                        <option value="a">10</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>PIN Pad - PINPD</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <select id="fit_pinpad-0" class="form-control" name="fit_pinpad-0" tabindex="14" aria-hidden="true">
                                                        <option value="0">0 - Single Encryption COM Key</option>
                                                        <option value="2">2 - Double Encryption 1 Master Key 2 COM Key</option>
                                                        <option value="3">3 - Double Encryption 1 PEKEY 2 COM Key</option>
                                                        <option value="8">8 - Single Encryption COM Key</option>
                                                        <option value="a">A - Double Encryption 1 Master Key 2 COM Key</option>
                                                        <option value="b">B - Double Encryption 1 PEKEY 2 COM Key</option>
                                                    </select>
                                                    <select id="fit_pinpad-1" class="form-control" name="fit_pinpad-1" tabindex="15" aria-hidden="true">
                                                        <option value="0">0</option>
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                        <option value="6">6</option>
                                                        <option value="7">7</option>
                                                        <option value="8">8</option>
                                                        <option value="9">9</option>
                                                        <option value="a">A</option>
                                                        <option value="b">B</option>
                                                        <option value="c">C</option>
                                                        <option value="d">D</option>
                                                        <option value="e">E</option>
                                                        <option value="f">F</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>PIN Retry Count - PRCNT</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_pinretrycount" class="form-control" name="fit_pinretrycount" autocomplete="off" value="0" maxlength="1" tabindex="16" aria-hidden="true">
                                                    <select id="fit_idxrefpoints-3" class="form-control" name="fit_idxrefpoints-3" tabindex="17" aria-hidden="true">
                                                        <option value="0">0 - Trk 2 Start Sentinel Forwards</option>
                                                        <option value="1">1 - Trk 3 Start Sentinel Forwards</option>
                                                        <option value="2">2 - Trk 1 Start Sentinel Forwards</option>
                                                        <option value="4">4 - Trk 2 1st Field Separator Forwards</option>
                                                        <option value="5">5 - Trk 3 1st Field Separator Forwards</option>
                                                        <option value="6">6 - Trk 1 1st Field Separator Forwards</option>
                                                        <option value="8">8 - Trk 2 End Sentinel Backwards</option>
                                                        <option value="9">9 - Trk 3 End Sentinel Backwards</option>
                                                        <option value="a">A - Trk 1 End Sentinel Backwards</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>PIN Offset Index - POFDX</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_pinoffsetidx" class="form-control" name="fit_pinoffsetidx" autocomplete="off" value="0" maxlength="1" tabindex="18" aria-hidden="true">
                                                    <select id="fit_idxrefpoints-6" class="form-control" name="fit_idxrefpoints-6" tabindex="19" aria-hidden="true">
                                                        <option value="0">0 - Trk 2 Start Sentinel Forwards</option>
                                                        <option value="1">1 - Trk 3 Start Sentinel Forwards</option>
                                                        <option value="2">2 - Trk 1 Start Sentinel Forwards</option>
                                                        <option value="4">4 - Trk 2 1st Field Separator Forwards</option>
                                                        <option value="5">5 - Trk 3 1st Field Separator Forwards</option>
                                                        <option value="6">6 - Trk 1 1st Field Separator Forwards</option>
                                                        <option value="8">8 - Trk 2 End Sentinel Backwards</option>
                                                        <option value="9">9 - Trk 3 End Sentinel Backwards</option>
                                                        <option value="a">A - Trk 1 End Sentinel Backwards</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>PIN Block Format - PBFMT</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <select id="fit_pinblkformat" class="form-control" name="fit_pinblkformat" tabindex="20" aria-hidden="true">
                                                        <option value="0">0 - Diebold without co-ordination number</option>
                                                        <option value="1">1 - Diebold with co-ordination number (Not supported)</option>
                                                        <option value="2">2 - ISO format 0</option>
                                                        <option value="3">3 - ISO format 1</option>
                                                        <option value="4">4 - ISO format 2</option>
                                                        <option value="5">5 - BANKSYS</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>PAN Location Index - PANDX</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_panlocidx" class="form-control" name="fit_panlocidx" autocomplete="off" value="0" maxlength="1" tabindex="21" aria-hidden="true">
                                                    <select id="fit_idxrefpoints-4" class="form-control" name="fit_idxrefpoints-4" tabindex="22" aria-hidden="true">
                                                        <option value="0">0 - Trk 2 Start Sentinel Forwards</option>
                                                        <option value="1">1 - Trk 3 Start Sentinel Forwards</option>
                                                        <option value="2">2 - Trk 1 Start Sentinel Forwards</option>
                                                        <option value="4">4 - Trk 2 1st Field Separator Forwards</option>
                                                        <option value="5">5 - Trk 3 1st Field Separator Forwards</option>
                                                        <option value="6">6 - Trk 1 1st Field Separator Forwards</option>
                                                        <option value="8">8 - Trk 2 End Sentinel Backwards</option>
                                                        <option value="9">9 - Trk 3 End Sentinel Backwards</option>
                                                        <option value="a">A - Trk 1 End Sentinel Backwards</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>PAN Length - PANLN</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <select id="fit_panlen-0" class="form-control" name="fit_panlen-0" tabindex="23" aria-hidden="true">
                                                        <option value="0">0 - No Local PIN checking</option>
                                                        <option value="1">1 - Local PIN checking</option>
                                                    </select>
                                                    <select id="fit_panlen-1" class="form-control" name="fit_panlen-1" tabindex="24" aria-hidden="true">
                                                        <option value="0">0</option>
                                                        <option value="5">5</option>
                                                        <option value="6">6</option>
                                                        <option value="7">7</option>
                                                        <option value="8">8</option>
                                                        <option value="9">9</option>
                                                        <option value="a">A</option>
                                                        <option value="b">B</option>
                                                        <option value="c">C</option>
                                                        <option value="d">D</option>
                                                        <option value="e">E</option>
                                                        <option value="f">F</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>PAN Pad - PANPD</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <select id="fit_panpad-0" class="form-control" name="fit_panpad-0" tabindex="25" aria-hidden="true">
                                                        <option value="0">0 - use the PEKEY for encryption</option>
                                                        <option value="8">8 - use the Master key for encryption</option>
                                                    </select>
                                                    <select id="fit_panpad-1" class="form-control" name="fit_panpad-1" tabindex="26" aria-hidden="true">
                                                        <option value="0">0</option>
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                        <option value="6">6</option>
                                                        <option value="7">7</option>
                                                        <option value="8">8</option>
                                                        <option value="9">9</option>
                                                        <option value="a">A</option>
                                                        <option value="b">B</option>
                                                        <option value="c">C</option>
                                                        <option value="d">D</option>
                                                        <option value="e">E</option>
                                                        <option value="f">F</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Decimalization Table - PDCTB</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_decimaltab" class="form-control" name="fit_decimaltab" autocomplete="off" value="0000000000000000" maxlength="16" tabindex="27" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Encrypted PIN Key - PEKEY</label>
                                                <div class="input-group">
                                                    <span class="input-group-text">
                                                        <i class="fa fa-edit fa-fw"></i>
                                                    </span>
                                                    <input type="text" id="fit_encpinkey" class="form-control" name="fit_encpinkey" autocomplete="off" value="0000000000000000" maxlength="16" tabindex="29" aria-hidden="true">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
                                    <br />
								</form>
                                <br>
                            </div>
                        </div>
                    </div>
