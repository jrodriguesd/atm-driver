                        <!-- _states.jsp - Inicio -->
                        <div class="row">
                            <div class="col-3" style="border: 0px solid black;">
                                <select class="form-control" style="font-family:'Lucida Console', monospace; font-size: 11px;" size="43" id="statesDataList"></select>
                            </div>
                            <div class="col-9" style="border: 0px solid black;">
                                <div class="col-sm-12">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item active">
                                            <a class="nav-link btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-0-tab" href="#nav-0">State</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-1-tab" href="#nav-1">Extension State</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-2-tab" href="#nav-2">Extension State</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-3-tab" href="#nav-3">Extension State</a>
                                        </li>
                                    </ul>
                                    <div class="container-fluid border-right border-left border-bottom">
                                        <br>
                                        <div class="tab-content">
                                            <!-- Tab tabpanel nav-0 -->
						                    <jsp:include page="_state_n.jsp">
                                                <jsp:param name="tabIndex" value="0" />
                                                <jsp:param name="tabClass" value="active" />
                                            </jsp:include>
                                            <!-- Tab tabpanel nav-1 -->
						                    <jsp:include page="_state_n.jsp">
                                                <jsp:param name="tabIndex" value="1" />
                                                <jsp:param name="tabClass" value="fade" />
                                            </jsp:include>
                                            <!-- Tab tabpanel nav-2 -->
						                    <jsp:include page="_state_n.jsp">
                                                <jsp:param name="tabIndex" value="2" />
                                                <jsp:param name="tabClass" value="fade" />
                                            </jsp:include>
                                            <!-- Tab tabpanel nav-3 -->
						                    <jsp:include page="_state_n.jsp">
                                                <jsp:param name="tabIndex" value="3" />
                                                <jsp:param name="tabClass" value="fade" />
                                            </jsp:include>
                                        </div>
                                    </div>
                                </div>
                                <br>
                            </div>
                        </div>
                        <!-- _states.jsp - Fin -->
