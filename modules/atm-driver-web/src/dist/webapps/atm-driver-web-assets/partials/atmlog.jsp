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
                                        <input type="text" id="atmlog_luno" class="form-control" name="atmlog_luno" autocomplete="off" value="001" maxlength="9" tabindex="1" aria-hidden="true" disabled>
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
                                        <input type="text" id="atmlog_ip" class="form-control" name="atmlog_ip" autocomplete="off" value="127.0.0.1" maxlength="15" tabindex="2" aria-hidden="true" disabled>
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
                                        <input type="text" id="atmlog_message_class" class="form-control" name="atmlog_message_class" autocomplete="off" value="11" maxlength="2" tabindex="3" aria-hidden="true" disabled>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12" style="border: 0px solid black;">
                                <div class="col-sm-12">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item active">
                                            <a class="nav-link btn btn-outline-secondary active" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-0-tab" href="#nav-0">ATM Request</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-1-tab" href="#nav-1">ISO Request</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-2-tab" href="#nav-2">ISO Reply</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-3-tab" href="#nav-3">ATM Reply</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-3-tab" href="#nav-4">ATM Confirmation</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-3-tab" href="#nav-5">ATM Confirmation ISO request</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link btn btn-outline-secondary" style="color: rgb(33, 37, 41); outline: none; box-shadow: none;" data-toggle="tab" id="nav-3-tab" href="#nav-6">ATM Confirmation ISO reply</a>
                                        </li>
                                    </ul>
                                    <div class="container-fluid border-right border-left border-bottom">
                                        <br>
                                        <div class="tab-content">
                                            <div role="tabpanel" class="tab-pane fade show active" id="nav-0">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <span class="input-group-text">
                                                                    <i class="fa fa-edit fa-fw"></i>
                                                                </span>
                                                                <input type="text" id="atmlog_atm_request_dt" class="form-control" name="atmlog_atm_request_dt" autocomplete="off" value="2022-04-30 10:15:51.650000" maxlength="15" tabindex="2" aria-hidden="true" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
											            <textarea id="atmlog_atm_request" class="form-control" type="text" name="atmlog_atm_request" rows="16" disabled>
<fsdmsg schema='file:cfg/ndc/ndc-base'>
  message-class: '11'
  luno: '000'
  time-variant-number: '01A2E166'
  top-of-receipt-flag: '1'
  message-coordination-number: '1'
  track2: ';41073741454145=251210110000232?'
  operation-code-data: 'AIA     '
  amount-entered: '000000000000'
  buffer-A-pin: '>354619447=;078?'
  EOF: 'true'
</fsdmsg>
												        </textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane fade" id="nav-1">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <span class="input-group-text">
                                                                    <i class="fa fa-edit fa-fw"></i>
                                                                </span>
                                                                <input type="text" id="atmlog_iso_request_dt" class="form-control" name="atmlog_iso_request_dt" autocomplete="off" value="2022-04-30 10:15:51.650000" maxlength="15" tabindex="2" aria-hidden="true" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
											            <textarea id="atmlog_iso_request" class="form-control" type="text" name="atmlog_iso_request" rows="17" disabled>
<isomsg>
  <!-- org.jpos.iso.packager.XMLPackager -->
  <field id="0" value="0200"/>
  <field id="3" value="301000"/>
  <field id="7" value="220430"/>
  <field id="11" value="000002"/>
  <field id="12" value="101551"/>
  <field id="13" value="2204"/>
  <field id="15" value="2204"/>
  <field id="17" value="2204"/>
  <field id="18" value="6011"/>
  <field id="22" value="051"/>
  <field id="32" value="1111"/>
  <field id="35" value=";41073741454145=251210110000232?"/>
  <field id="41" value="29110001"/>
  <field id="42" value="1234567"/>
  <field id="43" value="SUCURSAL CENTRAL       CARACAS   VE"/>
  <field id="49" value="840"/>
  <field id="52" value="E354619447DB078F"/>
  <field id="61" value="91000000025008620000000000"/>
  <field id="63" value="CI2000000000"/>
</isomsg>
												        </textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div role="tabpane2" class="tab-pane fade" id="nav-2">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <span class="input-group-text">
                                                                    <i class="fa fa-edit fa-fw"></i>
                                                                </span>
                                                                <input type="text" id="atmlog_iso_reply_dt" class="form-control" name="atmlog_iso_reply_dt" autocomplete="off" value="2022-04-30 10:15:51.650000" maxlength="15" tabindex="2" aria-hidden="true" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
											            <textarea id="atmlog_iso_reply" class="form-control" type="text" name="atmlog_iso_reply" rows="17" disabled>
<isomsg direction="incoming">
  <!-- org.jpos.iso.packager.XMLPackager -->
  <field id="0" value="0210"/>
  <field id="3" value="301000"/>
  <field id="7" value="220430"/>
  <field id="11" value="000002"/>
  <field id="12" value="101551"/>
  <field id="13" value="2204"/>
  <field id="15" value="2204"/>
  <field id="17" value="2204"/>
  <field id="18" value="6011"/>
  <field id="22" value="051"/>
  <field id="32" value="1111"/>
  <field id="35" value=";41073741454145=251210110000232?"/>
  <field id="39" value="00"/>
  <field id="41" value="29110001"/>
  <field id="42" value="1234567"/>
  <field id="43" value="SUCURSAL CENTRAL       CARACAS   VE"/>
  <field id="49" value="840"/>
  <field id="52" value="E354619447DB078F"/>
  <field id="54" value="1001356C0000000630001002356C000000063000"/>
  <field id="61" value="91000000025008620000000000"/>
  <field id="63" value="CI2000000000"/>
</isomsg>
												        </textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div role="tabpane3" class="tab-pane fade" id="nav-3">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <span class="input-group-text">
                                                                    <i class="fa fa-edit fa-fw"></i>
                                                                </span>
                                                                <input type="text" id="atmlog_atm_reply_dt" class="form-control" name="atmlog_atm_reply_dt" autocomplete="off" value="2022-04-30 10:15:51.650000" maxlength="15" tabindex="2" aria-hidden="true" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
											            <textarea id="atmlog_atm_reply" class="form-control" type="text" name="atmlog_atm_reply" rows="17" disabled>
<fsdmsg schema='file:cfg/ndc/ndc-base'>
  message-class: '4'
  luno: '000'
  time-variant-number: '01A2E166'
  next-state-number: '171'
  number-notes-dispense: '00000000'
  transaction-serial-number: '0002'
  function-identifier: '5'
  screen-number: '171'
  message-coordination-number: '1'
  card-return-retain-flag: '0'
  printer-flag: '2'
  screen-display-update: '172FN630.00 '
  printer-data: 'BANCO DE PRUEBA
CAJERO: 000
TRACE: 0002

COMPROBANTE DE OPERACION

NUMERO DE TARJETA   ---FECHA-- --HORA--
41073741454145      30-04-2022 10:15:51

SEQ. ----TRANSACCION----- AUTORIZACION
0001 BALANCE INQUIRY SAVINGS

MONTO OPERACION :
SALDO DISPONIBLE: 630.00
COM BANCO EMISOR:

OPERACION EXITOSA

CONSERVE PARA SU CONTROL
'
</fsdmsg>
												        </textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div role="tabpanel" class="tab-pane fade" id="nav-4">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <span class="input-group-text">
                                                                    <i class="fa fa-edit fa-fw"></i>
                                                                </span>
                                                                <input type="text" id="atmlog_atm_confirmation_dt" class="form-control" name="atmlog_atm_confirmation_dt" autocomplete="off" value="2022-04-30 10:15:51.650000" maxlength="15" tabindex="2" aria-hidden="true" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
											            <textarea id="atmlog_atm_confirmation" class="form-control" type="text" name="atmlog_atm_confirmation" rows="16" disabled>
<fsdmsg schema='file:cfg/ndc/ndc-base'>
  message-class: '22'
  luno: '000'
  status-descriptor: '9'
</fsdmsg>
												        </textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div role="tabpanel" class="tab-pane fade" id="nav-5">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <span class="input-group-text">
                                                                    <i class="fa fa-edit fa-fw"></i>
                                                                </span>
                                                                <input type="text" id="atmlog_iso_confirmation_request_dt" class="form-control" name="atmlog_iso_confirmation_request_dt" autocomplete="off" value="" maxlength="15" tabindex="2" aria-hidden="true" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
											            <textarea id="atmlog_iso_confirmation_request" class="form-control" type="text" name="atmlog_iso_confirmation_request" rows="16" disabled>
												        </textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div role="tabpanel" class="tab-pane fade" id="nav-6">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <span class="input-group-text">
                                                                    <i class="fa fa-edit fa-fw"></i>
                                                                </span>
                                                                <input type="text" id="atmlog_iso_confirmation_reply_dt" class="form-control" name="atmlog_iso_confirmation_reply_dt" autocomplete="off" value="" maxlength="15" tabindex="2" aria-hidden="true" disabled>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
											            <textarea id="atmlog_iso_confirmation_reply" class="form-control" type="text" name="atmlog_iso_confirmation_reply" rows="16" disabled>
												        </textarea>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <br>
                            </div>
                        </div>
                    </div>
