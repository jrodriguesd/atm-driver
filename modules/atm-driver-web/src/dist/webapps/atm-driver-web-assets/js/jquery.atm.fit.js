/**
 *
 *
 */
class ATMFit
{
    constructor() 
	{
	    this.configId = '0000';
		this.fitsData = '';
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
	            $('#config_id').empty();
                $('#config_id').append('<option value=" "> </option>');
                $.each(atmconfigs, function(i, val)
	            {
				    let selected = '';
					if (val['atmcnf_config_id'] === configId) selected= 'selected';

	            	let optionStr = '<option value="' + val['atmcnf_config_id'] + '" ' + selected + '>' + val['atmcnf_config_id'] + ' | ' + val['atmcnf_protocol'] + '</option>'
                   $('#config_id').append(optionStr);
                });
            }
        });
		 $("#config_id").val(configId);
		 $("#config_id").change();
	}

    screenListChange(e)
    {
		let fitObj = e.data.fit;  // para poder usarlo en otro contexto (Toda La Funcion)

	    let fit = fitObj.fitsData[  $("#fitsDataList :selected").attr('value') ];

		if (fit != null)
		{
	       $('#fit_number').val( fit['fit_number'] );
	       $('#fit_bin_prefix').val( fit['fit_bin_prefix'] );
	       $('#fit_desc').val( fit['fit_desc'] );
		   
	       $('#fit_indirectstateidx-0').val( fit['fit_indirectstateidx'][0] );
	       $('#fit_indirectstateidx-1').val( fit['fit_indirectstateidx'][1] );
		   
	       $('#fit_algoidx').val( fit['fit_algoidx'] );
		   
	       $('#fit_langcodeidx').val( fit['fit_langcodeidx'] );
		   
	       $('#fit_maxpinlen-0').val( fit['fit_maxpinlen'][0] );
	       $('#fit_maxpinlen-1').val( fit['fit_maxpinlen'][1] );
		   
	       $('#fit_localpinchecklen-0').val( fit['fit_localpinchecklen'][0] );
	       $('#fit_localpinchecklen-1').val( fit['fit_localpinchecklen'][1] );
		   
	       $('#fit_pinpad-0').val( fit['fit_pinpad'][0] );
	       $('#fit_pinpad-1').val( fit['fit_pinpad'][1] );
		   
	       $('#fit_pinretrycount').val( fit['fit_pinretrycount'] );
	       $('#fit_pinoffsetidx').val( fit['fit_pinoffsetidx'] );
	       $('#fit_pinblkformat').val( fit['fit_pinblkformat'] );
	       $('#fit_panlocidx').val( fit['fit_panlocidx'] );
		   
	       $('#fit_panlen-0').val( fit['fit_panlen'][0] );
	       $('#fit_panlen-1').val( fit['fit_panlen'][1] );
		   
	       $('#fit_panpad-0').val( fit['fit_panpad'][0] );
	       $('#fit_panpad-1').val( fit['fit_panpad'][1] );
		   
	       $('#fit_decimaltab').val( fit['fit_decimaltab'] );
	       $('#fit_encpinkey').val( fit['fit_encpinkey'] );
		   
	       $('#fit_idxrefpoints-1').val( fit['fit_idxrefpoints'][0] );
	       $('#fit_idxrefpoints-3').val( fit['fit_idxrefpoints'][2] );
	       $('#fit_idxrefpoints-4').val( fit['fit_idxrefpoints'][3] );
	       $('#fit_idxrefpoints-5').val( fit['fit_idxrefpoints'][4] );
	       $('#fit_idxrefpoints-6').val( fit['fit_idxrefpoints'][5] );
        }
	    $("#fitsDataList").focus();
	}

    getValues()
	{
		 let fit = {};
		 fit['fit_config_id'] = $('#config_id').val();
		 fit['fit_number'] = $('#fit_number').val();
		 fit['fit_bin_prefix'] = $('#fit_bin_prefix').val();
		 fit['fit_desc'] = $('#fit_desc').val();

		 fit['fit_indirectstateidx'] = $('#fit_indirectstateidx-0').val() + $('#fit_indirectstateidx-1').val();
		 
		 fit['fit_algoidx'] = $('#fit_algoidx').val();
		 
		 fit['fit_langcodeidx'] = $('#fit_langcodeidx').val();
		 
		 fit['fit_maxpinlen'] = $('#fit_maxpinlen-0').val() + $('#fit_maxpinlen-1').val();
		 
		 fit['fit_localpinchecklen'] = $('#fit_localpinchecklen-0').val() + $('#fit_localpinchecklen-1').val();
		 
		 fit['fit_pinpad'] = $('#fit_pinpad-0').val() + $('#fit_pinpad-1').val();
		 
		 fit['fit_pinretrycount'] = $('#fit_pinretrycount').val();
		 fit['fit_pinoffsetidx'] = $('#fit_pinoffsetidx').val();
		 fit['fit_pinblkformat'] = $('#fit_pinblkformat').val();
		 fit['fit_panlocidx'] = $('#fit_panlocidx').val();
		 
		 fit['fit_panlen'] = $('#fit_panlen-0').val() + $('#fit_panlen-1').val();
		 
		 fit['fit_panpad'] = $('#fit_panpad-0').val() + $('#fit_panpad-1').val();
		 
		 fit['fit_decimaltab'] = $('#fit_decimaltab').val();
		 fit['fit_encpinkey'] = $('#fit_encpinkey').val();

		 fit['fit_idxrefpoints'] = $('#fit_idxrefpoints-1').val() + 
		                           '0' + 
								   $('#fit_idxrefpoints-3').val() +
		                           $('#fit_idxrefpoints-4').val() +
		                           $('#fit_idxrefpoints-5').val() +
								   $('#fit_idxrefpoints-6').val();

		 return fit;
	}

    loadFitsList(configId) 
	{
		let fit = this;  // para poder usarlo en otro contexto (ajax)
		this.configId = configId;

	    console.log('configId: ' + configId);
        $.ajax(
        {
            url: 'api/fits/' + configId,
            method: "GET",
            success: function(data)
	        {
                fit.fitsData = JSON.parse( JSON.stringify(data) );
	            $('#fitsDataList').empty();
                $.each(fit.fitsData, function(i, val)
	            {
					let selected = '';
	                let optionStr = '<option value="' + i + '" ' + selected +'>' + val['fit_number']  + ' | ' + val['fit_desc'] + '</option>';
                    $('#fitsDataList').append(optionStr);
                });
				 $('#fitsDataList').change();
            }
        });
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
		let fit = e.data.fit;                              // para poder usarlo en otro contexto (Toda La Funcion)
		let action = document.activeElement['value'];      // Value of the submit button clicked
		let formData = fit.getValues();
        console.log( formData );

        e.preventDefault();

        $.ajax({
            type : "POST",                                 // type of action POST || GET
            url: 'api/fits/' + action,                     // url where to submit the request
			contentType: 'application/json;charset=utf-8',
            dataType : 'json',                             // data type
            data : JSON.stringify( formData ),             // post data || get data
            success : function(resultObj) 
			 {
	 		     $('#ResponseMsg').empty();
	 		     let msg = "Success : " + resultObj["msg"];
	 		     $('#ResponseMsg').append( fit.createDismissableMsg(msg, "success") );
			     let configId = formData["fit_config_id"];
				 fit.loadFitsList(configId);
            },
            error: function(xhr, resp, text) 
			{
	 			let resultObj = JSON.parse(xhr.responseText);
	 			$('#ResponseMsg').empty();
	 			let msg = "Error : " + resultObj["msg"];
	 			$('#ResponseMsg').append( fit.createDismissableMsg(msg, "danger") );
            }
        });

    }

	configIdChange(e)
	{
	   let fit   = e.data.fit;
	   let configId = this.value;

		if (configId.length > 0)
		{
		    fit.loadFitsList(configId);
		    Cookies.set('configId', configId)
		}
		else
	   {
	       configId = Cookies.get('configId');
			if ( typeof(configId) != 'undefined' )
		        fit.loadFitsList(configId);
		}			
    }

    init() 
	{
         $("#config_id").change( {fit: this}, this.configIdChange );

	     let configId = Cookies.get('configId');
		 if ( typeof(configId) == 'undefined' )
			 configId = "";

	     this.LoadConfigIdSelect( configId );
         $("#fitsDataList").change( {fit: this}, this.screenListChange );
         $("#fitsForm").submit( {fit: this}, this.handleSubmit );
	}
}
