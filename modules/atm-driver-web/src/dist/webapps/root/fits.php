<?php
require_once('./database/db_credentials.php');
$pageName = "ATM Configutaion Builder / FIT's";
$pageTitle = 'ATM-CB'; 

$fieldCount = 31;

function showButtons(string $index)
{
	global $fieldCount;

	$suffix = '-' . $index;
	$tabIndex = ($index * $fieldCount) + ($fieldCount - 1);

?>	
    <!-- *************************************************************************************************************************** -->
	<div class="row">
        <div class="col-sm-4" >
            <button type="button" class="btn btn-success" name="Action<?=$suffix; ?>" value="Create" id="Create<?=$suffix; ?>" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">Create</button>
        </div>
        <div class="col-sm-4">
            <button type="submit" class="btn btn-success" name="Action<?=$suffix; ?>" value="Update" id="Update<?=$suffix; ?>" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">Update</button>
        </div>
        <div class="col-sm-4">
            <button type="button" class="btn btn-danger" name="Action<?=$suffix; ?>" value="Delete" id="Delete<?=$suffix; ?>" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">Delete</button>
        </div>
    </div>
    <!-- *************************************************************************************************************************** -->
<?php
}

function showField(string $suffix, string $title, string $n, $value, int $maxLength, int $tabIndex)
{
    $name = $n . $suffix;
    $id = $name;
?>	
    <div class="form-group">
        <label><?=$title; ?></label>
        <div class="input-group">
            <span class="input-group-text">
                <i class="fa fa-edit fa-fw"></i>
            </span>
            <input type="text" id="<?=$id; ?>" class="form-control" name="<?=$name; ?>" autocomplete="off" value="<?=$value; ?>" maxlength="<?=$maxLength; ?>" tabindex="<?=$tabIndex; ?>" aria-hidden="true">
        </div>
    </div>
<?php
}

function showIndexField(string $suffix, string $title, string $n, $value, int $tabIndex)
{
	$fieldNameArr = ['', 'PAGDX', 'PIDDX', 'PRCNT', 'PANDX', 'PLNDX', 'POFDX'];
    $name = $n . $suffix;
    $id = $name;
	$fieldName = substr($title, -5);
	$i = 0;
	$i = array_search($fieldName, $fieldNameArr);
?>	
    <div class="form-group">
        <label><?=$title; ?></label>
        <div class="input-group">
            <span class="input-group-text">
                <i class="fa fa-edit fa-fw"></i>
            </span>
            <input type="text" id="<?=$id; ?>" class="form-control" name="<?=$name; ?>" autocomplete="off" value="<?=$value[0]; ?>" maxlength="1" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
            <select id="fit_idxrefpoints-<?=$i; ?>" class="form-control" name="fit_idxrefpoints-<?=$i; ?>" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
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
<?php
}

function showFIT(string $index)
{
	global $fieldCount;
	
	// $suffix = '-' . $index;
	$suffix = '';
	$tabIndex = ($index * $fieldCount) + 1;
?>	
    <!-- *************************************************************************************************************************** -->
	<div class="row">
        <div class="col-sm-6">
			<?php showField($suffix, "Index - PIDDX", "fit_number", "001", 3, $tabIndex++); ?>
        </div>
        <div class="col-sm-6">
			<?php showField($suffix, "BIN Prefix - PFIID", "fit_bin_prefix", "542449FFFF", 10, $tabIndex++); ?>
        </div>
        <div class="col-sm-12">
			<?php showField($suffix, "Description", "fit_desc", "Descripcion FIT MC", 50, $tabIndex++); ?>
        </div>
        <div class="col-sm-4">
			<?php // showField($suffix, "Indirect State Index - PSTDX", "fit_indirectstateidx", "01", 2, $tabIndex++); ?>
            <div class="form-group">
                <label>Indirect State Index - PSTDX</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <select id="fit_indirectstateidx-0" class="form-control" name="fit_indirectstateidx-0" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
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
                    <select id="fit_indirectstateidx-1" class="form-control" name="fit_indirectstateidx-1" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
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
			<?php showIndexField($suffix, "Diebold Algorithm Index - PAGDX", "fit_algoidx", "00", $tabIndex); $tabIndex += 2; ?>
        </div>
        <div class="col-sm-4">
			<?php showIndexField($suffix, "Language Code Index - PLNDX", "fit_langcodeidx", "00", $tabIndex); $tabIndex += 2; ?>
        </div>
        <div class="col-sm-4">
			<?php // showField($suffix, "Max PIN Length", "fit_maxpinlen", "00", 2, $tabIndex++); ?>
            <div class="form-group">
                <label>Max PIN Length - PMXPN</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <select id="fit_maxpinlen-0" class="form-control" name="fit_maxpinlen-0" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
                        <option value="0">0 - Diebold</option>
                    	<option value="4">4 - Specified by PBFMT (PIN Block Format)</option>
                    	<option value="8">8 - ISO Format 0</option>
                    	<option value="c">C - BANKSYS</option>
                    </select>
                    <select id="fit_maxpinlen-1" class="form-control" name="fit_maxpinlen-1" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
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
			<?php // showField($suffix, "Local PIN Check Length - PCKLN", "fit_localpinchecklen", "00", 2, $tabIndex++); ?>
            <div class="form-group">
                <label>Local PIN Check Length - PCKLN</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <select id="fit_localpinchecklen-0" class="form-control" name="fit_localpinchecklen-0" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
                        <option value="0">0 - DES (Local or Remote)</option>
                    	<option value="1">1 - VISA (Local only)</option>
                    	<option value="2">2 - DIEBOLD (Not supported in NDC)</option>
                    </select>
                    <select id="fit_localpinchecklen-1" class="form-control" name="fit_localpinchecklen-1" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
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
			<?php // showField($suffix, "PIN Pad - PINPD", "fit_pinpad", "F", 1, $tabIndex++); ?>
            <div class="form-group">
                <label>PIN Pad - PINPD</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <select id="fit_pinpad-0" class="form-control" name="fit_pinpad-0" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
                        <option value="0">0 - Single Encryption COM Key</option>
                    	<option value="2">2 - Double Encryption 1 Master Key  2 COM Key</option>
                    	<option value="3">3 - Double Encryption 1 PEKEY 2 COM Key</option>
                        <option value="8">8 - Single Encryption COM Key</option>
                    	<option value="a">A - Double Encryption 1 Master Key  2 COM Key</option>
                    	<option value="b">B - Double Encryption 1 PEKEY 2 COM Key</option>
                    </select>
                    <select id="fit_pinpad-1" class="form-control" name="fit_pinpad-1" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
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
			<?php showIndexField($suffix, "PIN Retry Count - PRCNT", "fit_pinretrycount", "00", $tabIndex); $tabIndex += 2; ?>
        </div>
        <div class="col-sm-4">
			<?php showIndexField($suffix, "PIN Offset Index - POFDX", "fit_pinoffsetidx", "00", $tabIndex); $tabIndex += 2; ?>
        </div>
        <div class="col-sm-4">
			<?php // showField($suffix, "PIN Block Format - PBFMT", "fit_pinblkformat", "00", 2, $tabIndex++); ?>
            <div class="form-group">
                <label>PIN Block Format - PBFMT</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <select id="fit_pinblkformat" class="form-control" name="fit_pinblkformat" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
                        <option value="0">0 - Diebold without co‐ordination number</option>
                    	<option value="1">1 - Diebold with co‐ordination number (Not supported)</option>
                    	<option value="2">2 - ISO format 0</option>
                    	<option value="3">3 - ISO format 1</option>
                    	<option value="4">4 - ISO format 2</option>
                    	<option value="5">5 - BANKSYS</option>
                    </select>
				</div>
            </div>
        </div>
        <div class="col-sm-4">
			<?php showIndexField($suffix, "PAN Location Index - PANDX", "fit_panlocidx", "00", $tabIndex); $tabIndex += 2; ?>
        </div>
        <div class="col-sm-4">
			<?php // showField($suffix, "PAN Length - PANLN", "fit_panlen", "00", 2, $tabIndex); $tabIndex += 2; ?>
            <div class="form-group">
                <label>PAN Length - PANLN</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <select id="fit_panlen-0" class="form-control" name="fit_panlen-0" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
                        <option value="0">0 - No Local PIN checking</option>
                        <option value="1">1 - Local PIN checking</option>
                    </select>
                    <select id="fit_panlen-1" class="form-control" name="fit_panlen-1" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
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
			<?php // showField($suffix, "PAN Pad - PANPD", "fit_panpad", "00", 2, $tabIndex++); ?>
            <div class="form-group">
                <label>PAN Pad - PANPD</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <i class="fa fa-edit fa-fw"></i>
                    </span>
                    <select id="fit_panpad-0" class="form-control" name="fit_panpad-0" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
                        <option value="0">0 - use the PEKEY for encryption</option>
                        <option value="8">8 - use the Master key for encryption</option>
                    </select>
                    <select id="fit_panpad-1" class="form-control" name="fit_panpad-1" tabindex="<?=$tabIndex++; ?>" aria-hidden="true">
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
			<?php showField($suffix, "Decimalization Table - PDCTB", "fit_decimaltab", "0000000000000000", 16, $tabIndex); $tabIndex += 2; ?>
        </div>
        <div class="col-sm-6">
			<?php showField($suffix, "Encrypted PIN Key - PEKEY", "fit_encpinkey", "0000000000000000", 16, $tabIndex); $tabIndex += 2; ?>
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
                                <select class="form-control" style="font-family:'Lucida Console', monospace; font-size: 11px;" size="41" id="fitsDataList"></select>
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
                                </div>
								<!-- ********************************** States Tabs Inicio ********************************** -->
								<form action="receive.php" method="post">
					                <?php showFIT('0'); ?>
					                <?php showButtons('0'); ?>
								</form>
								<!-- ********************************** States Tabs Fin    ********************************** -->
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
		<?php include("{$_SERVER['DOCUMENT_ROOT']}/app/partials/_included_scripts.php");?>
	    <script type="text/javascript" src="../app/assets/js/jquery.atm.fit.js?<?=date('Y-m-d_H:i:s'); ?>"></script>
    </body>
</html>