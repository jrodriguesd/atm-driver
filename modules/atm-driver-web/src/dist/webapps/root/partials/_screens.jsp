                        <div class="row">
                            <div class="col-3" style="border: 0px solid black;">
                                <select class="form-control" style="font-family:'Lucida Console', monospace; font-size: 11px;" size="49" id="screenList"></select>
                                <!--
                                <option>000 | Time Out</option><option>001 | OffLine</option><option>002 | Out Of Service</option><option>003 | Administrative Process</option><option>010 | Welcome</option>
						        -->
                            </div>
                            <div class="col-7" style="border: 0px solid black;">
                                <!-- *************************************************************************************************************************** -->
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>Configuration Id</label>
                                            <div class="input-group">
                                                <span class="input-group-text">
                                                    <i class="fa fa-edit fa-fw"></i>
                                                </span>
                                                <input type="text" class="form-control" name="config_id" autocomplete="off" value="0850" placeholder="Configuration Id" disabled="">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>Screen Number</label>
                                            <div class="input-group">
                                                <span class="input-group-text">
                                                    <i class="fa fa-edit fa-fw"></i>
                                                </span>
                                                <input type="text" class="form-control" name="name" autocomplete="off" value="000" placeholder="Name">
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
                                                <input type="text" class="form-control" name="name" autocomplete="off" value="Time Out" placeholder="Name">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Screen</label>
                                            <textarea id="cmdTxtArea" class="form-control" type="text" name="description" rows="3">&#x240c;&#x240f;@1COPIN ATM&#x240f;@@&#x240e;G01&#x240f;BNENTER YOUR PIN&#x240f;DNAFTER PIN ENTERED&#x240f;F6PRESS -- >&#x240f;MNTO OBTAIN CARD&#x240f;ONPRESS CANCEL&#x240f;I0</textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-success" id="Create">Create</button>
                                    </div>
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-success" id="Update">Update</button>
                                    </div>
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-danger" id="Delete">Delete</button>
                                    </div>
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-primary" id="Capturar">Capture</button>
                                    </div>
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-primary" id="Mostrar">Show</button>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="checkbox" id="showImg" name="showImg" value=" " checked> Mostrar Imagenes <br>
                                    </div>
                                </div>
                                <br />
                                <!-- *************************************************************************************************************************** -->
                                <div class="row">
                                    <div class="col-sm-1 mx-0 px-1">
                                        <div class="float-right">
                                            <table class="screen">
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;">
                                                        <b style="background-color: #e9ecef; font-size: 17px;">I&nbsp;>&nbsp;</b>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;">
                                                        <b style="background-color: #e9ecef; font-size: 17px;">H&nbsp;>&nbsp;</b>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;">
                                                        <b style="background-color: #e9ecef; font-size: 17px;">G&nbsp;>&nbsp;</b>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;">
                                                        <b style="background-color: #e9ecef; font-size: 17px;">F&nbsp;>&nbsp;</b>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="col-sm-10 mx-0 px-0">
                                        <div id="tabContainer" style="position: relative;">
                                            <span id="atm-screen"></span>
                                        </div>
                                    </div>
                                    <div class="col-sm-1 mx-0 px-1">
                                        <div class="float-left">
                                            <table border="0" class="screen">
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;">
                                                        <b style="background-color: #e9ecef; font-size: 17px;">&nbsp;<&nbsp;A </b>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;">
                                                        <b style="background-color: #e9ecef; font-size: 17px;">&nbsp;<&nbsp;B </b>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;">
                                                        <b style="background-color: #e9ecef; font-size: 17px;">&nbsp;<&nbsp;C </b>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;"></td>
                                                </tr>
                                                <tr>
                                                    <td style="border: none;">
                                                        <b style="background-color: #e9ecef; font-size: 17px;">&nbsp;<&nbsp;D </b>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <br>
                            </div>
                        </div>
