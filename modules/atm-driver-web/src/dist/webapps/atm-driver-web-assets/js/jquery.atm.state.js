/**
 *
 *
 */

class ATMState
{
    constructor() 
	{
	    this.statesData       = '';
	    this.statesDefinition = '';
	    this.configId         = '0000';
	}

	LoadConfigIdSelect(configId) 
	{
        $.ajax(
        {
	    	url: 'api/atmconfigs/unique',
            method: "GET",
            success: function(data)
	        {
                let atmconfigs = JSON.parse( JSON.stringify(data) );
			    for (let i = 0; i < 4; i++)
	               $('#config_id-' + i).empty();

			    for (let i = 0; i < 4; i++)
                 $('#config_id-' + i).append('<option value=" "> </option>');

                $.each(atmconfigs, function(i, val)
	            {
				    let selected = '';
					if (val['atmcnf_config_id'] === configId) selected= 'selected';

	             	let optionStr = '<option value="' + val['atmcnf_config_id'] + '" ' + selected + '>' + val['atmcnf_config_id'] + ' | ' + val['atmcnf_protocol'] + '</option>'
			        for (let i = 0; i < 4; i++)
                       $('#config_id-' + i).append(optionStr);
                });
            }
        });
		$("#config_id-0").val(configId);
		$("#config_id-0").change();
	}

    /*
	 * Cambia Los Titulos y los Tipos de Input
	 */
    processStateListChangeElem(tabIndex, sta, index)
	{
	    let tag = 'sta_' + index;
		let suffix = '-' + tabIndex;
		// let name = 'std_s' + index + suffix; // Caniado JFRD 26-Mar-200 
		let id = 'std_s' + index + suffix;
		let name = 'std_s' + index;
		let inputTag = '';
		let order = (tabIndex * 13) + index + 3;

		$('#' + tag + '_title' + suffix).text( sta[tag]['title'] );

		if ( sta[tag]['values'] === undefined )
		{
		    inputTag = '<input type="text" id="' + id  + '" class="form-control" name="' + name  + '" autocomplete="off" value="000" maxlength="3" tabindex="' + order + '" aria-hidden="true">';
		}
		else
		{
			if ( sta[tag]['type'] === undefined )
			    inputTag  = '<select id="' + id  + '" class="form-control" name="' + name  + '" data-select2-id="input-group" tabindex="' + order + '" aria-hidden="true">';
			else
			    inputTag  = '<select id="' + id  + '" class="form-control" name="' + name  + '[]" data-select2-id="input-group" multiple="multiple" tabindex="' + order + '" aria-hidden="true">';
				 
           $.each(sta[tag]['values'], function(i, val)
	       {
		       let selected = '';
	            // inputTag += '<option value="' + i + '" selected>' + i + ' | ' + val + '</option>'
	           inputTag += '<option value="' + i + '" ' + selected + '>' + i + ' | ' + val + '</option>'
		   });
           inputTag += '</select>' 				 
		}
		$('#' + id).multiselect('destroy');
		// Cambio el Tipo de Input
		$('#' + id).replaceWith(inputTag);

		if ( ! ( sta[tag]['type'] === undefined ) )
		{
		    $('#' + id).multiselect(
			{
               inheritClass: true,
               buttonText:function (options, select) 
			    {
				    let value = 0;

  				  	if (sta[tag]['type'] === 'bit_stream')
					    value = 0;

  				  	if (sta[tag]['type'] === 'bit_stream_neg')
					    value = 255;

                   options.each(function () 
                   {
  				  	    if (sta[tag]['type'] === 'bit_stream')
					 	    value  += parseInt( $(this).attr('value') );

  				  	    if (sta[tag]['type'] === 'bit_stream_neg')
					 	    value  -= parseInt( $(this).attr('value') );
                   });
				
                   var valueStr = '' + value;
                   while (valueStr.length < 3) valueStr = '0' + valueStr;
				
			        return valueStr;
			    }
		
			});
		}

	}

    statesTabIndexListChange(sta, tabIndex)
	{
	    for (let i = 1; i < 9; i++)
		    this.processStateListChangeElem(tabIndex, sta, i);
	}

    processExtensionStatesElem(std, index, tabIndex)
	{
	    let tag_std = '#std_s' + index + '-' + tabIndex;
	    $(tag_std).val( std['std_s' + index] );
	}

    processExtensionStates(std, tabIndex)
	{
        let sta = this.statesDefinition[ std['std_type'] ]; 

		if (sta === undefined) return;

        // this.statesListChange(sta, tabIndex);

		$('#std_config_id-' + tabIndex).val( std['std_config_id'] );
		$('#std_number-' + tabIndex).val( std['std_number'] );
		$('#statesList-' + tabIndex).val( std['std_type'] ).change();
		$('#std_desc-' + tabIndex).val( std['std_desc'] );
		 
		for (let i = 1; i < 9; i++)
		    this.processExtensionStatesElem(std, i, tabIndex);
	}

    /*
	 * Cambia El Valor
	 */
    processStatesDataListChangeElem(std, index)
	{
	    let tag_std = 'std_s' + index + '-0';

        let sta = this.statesDefinition[ std['std_type'] ];
		if (sta == null) console.log("No encontro la definicion");
		let tag_sta = 'sta_' + index;
		let extensionState = std['std_s' + index];
		let sta_min = '999';
		let sta_max = '000'
		
		if  ( !  ( sta[tag_sta]['extension_state'] === undefined ) )
		{
		    sta_min = sta[tag_sta]['extension_state']['min_val']
		    sta_max = sta[tag_sta]['extension_state']['max_val']
		}

		if  ( ( !  ( sta[tag_sta]['extension_state'] === undefined ) )
			    && (extensionState >= sta_min)  
		        && (extensionState <= sta_max)  
		    )
		{
		    // Mostrar las Pestaña
			 let tabIndex = sta[tag_sta]['extension_state']['num'];
			 
           $("#nav-" + tabIndex + "-tab").show();

           // Buscar el Extension State				 
           let extensionStateDataIndex = 0;
           $.each(this.statesData, function(i, val)
	       {
				 if (val['std_number'] === extensionState)
				     extensionStateDataIndex = i;
           });					    

           // No Encontro el Extension State				 
			 if (extensionStateDataIndex === 0)
			     return;

		    let std_1 = this.statesData[ extensionStateDataIndex ];
			let stateTypeSufix = std['std_type'] + tabIndex;

			if (std_1['std_type'].length === 1)
			{
			    this.statesData[ extensionStateDataIndex ]['std_type'] = std_1['std_type'] + stateTypeSufix;
		        std_1 = this.statesData[ extensionStateDataIndex ];
			}

            this.processExtensionStates(std_1, tabIndex);
		}

		if  ( !  ( sta[tag_sta]['type'] === undefined ) )
		{
			 let valArr = [];
			 
			 for (let i = 1; i < 129; i=i*2)
			 {
		        // console.log('JFRD jquery.atm.state.js 143 ');
				 let testCond = i & parseInt( std['std_s' + index]);

				 if ( 
				         (testCond !== 0)
  					  && (sta[tag_sta]['type'] === 'bit_stream')
				    )
				 {
                   let iStr = '' + i;
                   while (iStr.length < 3) iStr = '0' + iStr;
			         valArr.push(iStr);
				 }

  				 if ( 
				         (testCond === 0)
  					  && (sta[tag_sta]['type'] === 'bit_stream_neg')
  				    )
  				 {
                   let iStr = '' + i;
                   while (iStr.length < 3) iStr = '0' + iStr;
  			         valArr.push(iStr);
  				 }
					 
			 }	 

	       $('#' + tag_std ).val( valArr );
	       $('#' + tag_std ).multiselect('refresh');
		}
        else
		 {
		     // console.log('JFRD jquery.atm.state.js 144 #' + tag_std + ' tag_sta ' + tag_sta + ' val ' + std['std_s' + index] );
	        $('#' + tag_std ).val( std['std_s' + index] );
		 }
			 
	}
	
    statesDataListChange(e)
    {
	    let state = e.data.state;
		var std = state.statesData[  $("#statesDataList :selected").attr('value') ];

        if (std != null)
		{
            $("#nav-1-tab").hide();
            $("#nav-2-tab").hide();
            $("#nav-3-tab").hide();
            $("#nav-0-tab").click();
		    
		    $('#std_config_id-0').val( std['std_config_id'] );
		    $('#std_number-0').val( std['std_number'] );
		    $('#statesList-0').val( std['std_type'] ).change();
		    $('#std_desc-0').val( std['std_desc'] );
		    
		    for (let i = 1; i < 9; i++)
		    {
		        // console.log('JFRD jquery.atm.state.js 164 ' + i );
		        state.processStatesDataListChangeElem(std, i);
		    }
		}

		$("#statesDataList").focus();
    }


    getStatesData(configId)
	{
		let state = this;  // para poder usarlo en otro contexto (ajax)
		this.configId = configId;

        $.ajax(
        {
            url: 'api/states/' + configId,
            method: "GET",
            success: function(data)
	        {
                state.statesData = JSON.parse( JSON.stringify(data) );
			    $('#statesDataList').empty();
                $.each(state.statesData, function(i, val)
	            {
					let selected = '';
					// if (val['std_number'] === statesDataListValue) selected = 'selected';
	                let optionStr = '<option value="' + i + '" ' + selected +'>' + val['std_number']  + ' | ' + val['std_desc'] + '</option>'
                    $('#statesDataList').append(optionStr);
                });
				$('#statesDataList').change();
            }
        });
	}

    getStatesDefinition()
	{
		let state = this;  // para poder usarlo en otro contexto (ajax)
        $.ajax(
        {
            url: 'api/states/definition',
            method: "GET",
            success: function(data)
	        {
                state.statesDefinition = JSON.parse( JSON.stringify(data) );
                $('#statesList').append('<option value="0">No State Type</option>');
                $('#statesList').append('<option disabled="disabled">-----</option>');
                $.each(state.statesDefinition, function(i, val)
	            {
	                let optionStr = '<option value="' + i + '">' + val['sta_desc']['title'] + '</option>'
                    $('#statesList-0').append(optionStr);
                    $('#statesList-1').append(optionStr);
                    $('#statesList-2').append(optionStr);
                    $('#statesList-3').append(optionStr);
                });					    
            }
        });
	}

	statesListChange(e)
    {
	    console.log(" fired by " + e.target.id );
	    let targetId = e.target.id;
		let stateListIndex = targetId[targetId.length - 1];
	    console.log(" stateListIndex " + stateListIndex );
	    let state = e.data.state;
	    let label = "#" + targetId + " :selected";
		let sta = state.statesDefinition[  $(label).attr('value') ];

		if (sta === undefined) return;

	    state.statesTabIndexListChange(sta, stateListIndex);
    }

    createDismissableMsg(msg, level)
	{
	    let str  = "";
		str += "<div class=\"alert alert-" + level + " alert-dismissible fade show\" role=\"alert\">";
		str += msg;
		str += "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">";
		str += "        <span aria-hidden=\"true\">&times;</span>";
		str += "    </button>";
		str += "</div>";
		return str;
	}

    handleSubmit(e) 
	{
	    let state = e.data.state;
        e.preventDefault();

	    let action = document.activeElement['value'];      // Value of the submit button clicked
        console.log( ' action ' + action );

	    let formData = $(this).formToJson();
        console.log( formData );

        $.ajax({
            type : "POST",                                 // type of action POST || GET
            url: 'api/states/' + action,                   // url where to submit the request
	    	contentType: 'application/json;charset=utf-8',
            dataType : 'json',                             // data type
            data : JSON.stringify( formData ),             // post data || get data
            success : function(resultObj) 
	    	{
			    console.log("event.data.index = " + e.data.index);
                console.log("success :" + resultObj);
                console.log( resultObj );
			    $("#ResponseMsg-" + e.data.index).empty();
			    let msg = "Success : " + resultObj["msg"];
			    $("#ResponseMsg-" + e.data.index).append( state.createDismissableMsg(msg, "success") );
	            state.configIdChange();
            },
            error: function(xhr, resp, text) 
	    	{
			    console.log("event.data.index = " + e.data.index);
                console.log("error :" + xhr.responseText);
                console.log("error :" + xhr.responseText);
			    let resultObj = JSON.parse(xhr.responseText);
			    $("#ResponseMsg-" + e.data.index).empty();
			    let msg = "Error : " + resultObj["msg"];
			    $("#ResponseMsg-" + e.data.index).append( state.createDismissableMsg(msg, "danger") );
            }
        });
	}

    configIdChange(e) 
	{
	    let state   = e.data.state;
	    let configId = this.value;
	    console.log('configId: ' + configId);


		if (configId.length > 0)
		{
	        for (let i = 1; i < 4; i++)
                $('#config_id-' + i).val(configId);
	 	    state.getStatesData(configId);
		    Cookies.set('configId', configId)
		}
		else
	    {
	        configId = Cookies.get('configId');
			if ( typeof(configId) != 'undefined' )
			{
	            for (let i = 1; i < 4; i++)
                    $('#config_id-' + i).val(configId);
	 	        state.getStatesData(configId);
			}
		}			
	}

    init() 
	{
        $("#nav-1-tab").hide();
        $("#nav-2-tab").hide();
        $("#nav-3-tab").hide();
        $("#nav-0-tab").click();

        this.getStatesDefinition()

	    for (let i = 1; i < 4; i++)
	        $('#config_id-' + i).prop('disabled', 'disabled');

        $("#config_id-0").change( {state: this}, this.configIdChange );

	    let configId = Cookies.get('configId');
		if ( typeof(configId) == 'undefined' )
		    configId = "";

        this.LoadConfigIdSelect(configId);

		$("#statesDataList").change( {state: this}, this.statesDataListChange );	

	    for (let i = 0; i < 4; i++)
            $("#statesList-" + i).change( {state: this}, this.statesListChange );	

	    for (let i = 0; i < 4; i++)
            $("#statesForm-" + i).submit( {state: this, index : i}, this.handleSubmit );
	}

}

$(document).ready(function()
     {
     }
 );

