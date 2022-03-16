<?php
require_once('./database/db_credentials.php');
$pageName = "ATM Configutaion Builder / States";
$pageTitle = 'ATM-CB'; 

function showButtons(string $index)
{
	$suffix = '-' . $index;
	$tabindex = ($index * 13) + 11;
?>	
    <!-- *************************************************************************************************************************** -->
	<div class="row">
        <div class="col-sm-4" >
            <button type="button" class="btn btn-success" name="Action<?php echo $suffix; ?>" value="Create" id="Create<?php echo $suffix; ?>" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">Create</button>
        </div>
        <div class="col-sm-4">
            <button type="submit" class="btn btn-success" name="Action<?php echo $suffix; ?>" value="Update" id="Update<?php echo $suffix; ?>" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">Update</button>
        </div>
        <div class="col-sm-4">
            <button type="button" class="btn btn-danger" name="Action<?php echo $suffix; ?>" value="Delete" id="Delete<?php echo $suffix; ?>" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">Delete</button>
        </div>
    </div>
    <!-- *************************************************************************************************************************** -->
<?php
}

function showState(string $index)
{
	$suffix = '-' . $index;
	$tabindex = ($index * 13) + 1;
?>	
    <!-- *************************************************************************************************************************** -->
	<div class="row">
        <div class="col-sm-6">
            <div class="form-group">
                <label>State Number</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_number<?php echo $suffix; ?>" class="form-control" name="std_number<?php echo $suffix; ?>" autocomplete="off" value="000" maxlength="3" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
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
                    <select id="statesList<?php echo $suffix; ?>" class="form-control js-select2 select2-hidden-accessible" name="std_type<?php echo $suffix; ?>" data-select2-id="input-group" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                    </select>
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
                    <input type="text" id="std_desc<?php echo $suffix; ?>" class="form-control" name="std_desc<?php echo $suffix; ?>" autocomplete="off" value="Time Out" placeholder="Description" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label id="sta_1_title<?php echo $suffix; ?>">Screen Number</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_1<?php echo $suffix; ?>" class="form-control" name="std_1<?php echo $suffix; ?>" autocomplete="off" value="000" placeholder="Screen Numbe" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label id="sta_2_title<?php echo $suffix; ?>">Good Read Next State</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_2<?php echo $suffix; ?>" class="form-control" name="std_2<?php echo $suffix; ?>" autocomplete="off" value="000" placeholder="Good Read Next State" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label id="sta_3_title<?php echo $suffix; ?>">Error Screen Number</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_3<?php echo $suffix; ?>" class="form-control" name="std_3<?php echo $suffix; ?>" autocomplete="off" value="000" placeholder="Error Screen Number" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label id="sta_4_title<?php echo $suffix; ?>">Read Condition 1</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_4<?php echo $suffix; ?>" class="form-control" name="std_4<?php echo $suffix; ?>" autocomplete="off" value="000" placeholder="Read Condition 1" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label id="sta_5_title<?php echo $suffix; ?>">Read Condition 2</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_5<?php echo $suffix; ?>" class="form-control" name="std_5<?php echo $suffix; ?>" autocomplete="off" value="000" placeholder="Read Condition 2" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label id="sta_6_title<?php echo $suffix; ?>">Read Condition 3</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_6<?php echo $suffix; ?>" class="form-control" name="std_6<?php echo $suffix; ?>" autocomplete="off" value="000" placeholder="Read Condition 3" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label id="sta_7_title<?php echo $suffix; ?>">Card Return Flag</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_7<?php echo $suffix; ?>" class="form-control" name="std_7<?php echo $suffix; ?>" autocomplete="off" value="000" placeholder="Card Return Flag" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label id="sta_8_title<?php echo $suffix; ?>">No FIT Match Next State</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <input type="text" id="std_8<?php echo $suffix; ?>" class="form-control" name="std_8<?php echo $suffix; ?>" autocomplete="off" value="000" placeholder="No FIT Match Next State" tabindex="<?php echo $tabindex++; ?>" aria-hidden="true">
                </div>
            </div>
        </div>
    </div>
    <!-- *************************************************************************************************************************** -->
<?php
}
?>
<!DOCTYPE html>
<html lang="en">
    <!-- head --> <?php include("{$_SERVER['DOCUMENT_ROOT']}/app/partials/_head.php");?> <body>
        <div class="container-scroller">
            <!-- top navbar --> <?php include("{$_SERVER['DOCUMENT_ROOT']}/app/partials/_top_navbar.php");?>
            <!-- partial -->
            <div class="container-fluid page-body-wrapper">
                <!-- sidebar nav --> <?php include("{$_SERVER['DOCUMENT_ROOT']}/app/partials/_sidebar.php");?> <div class="main-panel">
                    <div class="content-wrapper">
                        <!-- page title header starts-->
                        <div class="row page-title-header">
                            <div class="col-md-12">
                                <div class="page-header-toolbar">
                                    <div class="filter-wrapper"></div>
                                </div>
                            </div>
                        </div>
                        <!-- page title header ends-->
                        <div class="row">
                            <div class="col-3" style="border: 0px solid black;">
                                <select class="form-control" style="font-family:'Lucida Console', monospace; font-size: 11px;" size="41" id="statesDataList"></select>
                            </div>
                            <div class="col-9" style="border: 0px solid black;">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Configuration Id</label>
                                            <div class="input-group">
                                                <span class="input-group-text">
                                                    <i class="fa fa-edit fa-fw"></i>
                                                </span>
                                                <input type="text" id="std_config_id" class="form-control" name="std_config_id" autocomplete="off" value="0850" placeholder="Configuration Id" disabled="">
                                            </div>
                                        </div>
                                    </div>
									<!-- ********************************** States Tabs Inicio ********************************** -->
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
                                               <div role="tabpanel" class="tab-pane active" id="nav-0">
											       <form action="receive.php" method="post">
					                                   <?php showState('0'); ?>
					                                   <?php showButtons('0'); ?>
											       </form>
                                               </div>
                                               <div role="tabpanel" class="tab-pane fade" id="nav-1">
											       <form action="receive.php" method="post">
					                                   <?php showState('1'); ?>
					                                   <?php showButtons('1'); ?>
											       </form>
                                               </div>
                                               <div role="tabpanel" class="tab-pane fade" id="nav-2">
											       <form action="receive.php" method="post">
					                                   <?php showState('2'); ?>
					                                   <?php showButtons('2'); ?>
											       </form>
                                               </div>
                                               <div role="tabpanel" class="tab-pane fade" id="nav-3">
											       <form action="receive.php" method="post">
					                                   <?php showState('3'); ?>
					                                   <?php showButtons('3'); ?>
											       </form>
                                               </div>
                                           </div>
										   <br>
                                       </div>
									</div>
									<!-- ********************************** States Tabs Fin    ********************************** -->
								</div>
                            </div>
                        </div>
                        <!-- content-wrapper ends -->
                    </div>
                    <!-- footer --> <?php include("{$_SERVER['DOCUMENT_ROOT']}/app/partials/_footer.php");?>
                </div>
                <!-- main-panel ends -->
            </div>
            <!-- page-body-wrapper ends -->
        </div>
        <?php include("{$_SERVER['DOCUMENT_ROOT']}/app/partials/_included_scripts.php"); ?>
        <script type="text/javascript" src="../app/assets/js/jquery.atm.state.js?v=<?=filemtime($_SERVER['DOCUMENT_ROOT'].'\app\assets\js\jquery.atm.state.js')?>"></script>
    </body>
</html>