/**
 *
 *
 */
class debug
{
    getAllEvents(element) 
    {
        var result = [];
        for (var key in element) 
    	{
            if (key.indexOf('on') === 0) 
    		{
                result.push(key.slice(2));
            }
        }
        return result.join(' ');
    }

	// var el = $(divId);
    // el.bind(getAllEvents(el[0]), function(e) 
    // {
	//     console.log("jquery.atm.atmconfig.js 23 atmconfigs_wrap " + event.type);
    // });

    showMsg()
	{
	    console.log("jquery.atm.atmconfig.js 20 atmconfigs_wrap load");
	}
}
 
class ATMConfig
{
	static #DATA_LIST_DIV_ID = "#atmconfigsDataList"; 
	static #FORM_DIV_ID      = "#atmconfigsForm";

    constructor() 
	{
	    this.atmconfigs = [];
	}

    LoadProtocolSelect(protocol) 
	{
        $.ajax(
        {
	    	url: 'api/atmprotocols/unique',
            method: "GET",
            success: function(data)
	        {
                let atmprotocols = JSON.parse( JSON.stringify(data) );
	            $('#atmcnf_protocol').empty();
                $('#atmcnf_protocol').append('<option value=" "> </option>');
                $.each(atmprotocols, function(i, val)
	            {
				    let selected = '';
					if (val['atmprt_name'] === protocol) selected= 'selected';
					console.log("");

	            	let optionStr = '<option value="' + val['atmprt_name'] + '" ' + selected + '>' + val['atmprt_name'] + '</option>'
                   $('#atmcnf_protocol').append(optionStr);
                });
            }
        });
	}

    configsDataListChange(e)
	{
	    console.log("jquery.atm.atmconfig.js 41 configsDataListChange");
		let atmconfig = e.data.atmconfig;  // para poder usarlo en otro contexto (Toda La Funcion)
		let id = "#atmconfigsDataList :selected";
		let index = parseInt( $(id).attr('value') );
		let atmcnf = atmconfig.atmconfigs[ index ];
		 
		 if (atmcnf != null)
		 {
		     $('#atmcnf_config_id').val( atmcnf['atmcnf_config_id'] );
		     $('#atmcnf_protocol').val( atmcnf['atmcnf_protocol'] );
		     $('#atmcnf_desc').val( atmcnf['atmcnf_desc'] );
		     $('#atmcnf_languageindex').val( atmcnf['atmcnf_languageindex'] );
		     $('#atmcnf_languageatm').val( atmcnf['atmcnf_languageatm'] );
		     $('#atmcnf_language639').val( atmcnf['atmcnf_language639'] );
		     $('#atmcnf_screengroupbase').val( atmcnf['atmcnf_screengroupbase'] );
		 }

		 $(this.DATA_LIST_DIV_ID).focus();
	}

    loadAtmconfigs() 
	{
		let atmconfig = this;  // para poder usarlo en otro contexto (Toda La Funcion)
	    console.log("jquery.atm.atmconfig.js 61 loadAtmconfigs");
        $.ajax(
        {
            url: 'api/atmconfigs/getall',
            method: "GET",
            success: function(data)
	        {
                atmconfig.atmconfigs = JSON.parse( JSON.stringify(data) );
	            $(ATMConfig.#DATA_LIST_DIV_ID).empty();
                $.each(atmconfig.atmconfigs, function(i, val)
	            {
				    let selected = '';
	                let optionStr = '<option value="' + i + '" ' + selected + '>' + val['atmcnf_config_id'] + ' | ' + val['atmcnf_protocol'] + ' | ' + val['atmcnf_desc'] + '</option>';
                    $(ATMConfig.#DATA_LIST_DIV_ID).append(optionStr);
                });
				// ATMConfig.configsDataListChange();
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
		let atmconfig = e.data.atmconfig;                  // para poder usarlo en otro contexto (Toda La Funcion)
        e.preventDefault();

	    let action = document.activeElement['value'];      // Value of the submit button clicked
        console.log( ' action ' + action );

	 	let formData = $(this).formToJson();

        Object.keys(formData).forEach(key =>               // Eliminar los Campos que no son parte del Objeto Screen
	 	{
	 	    if (! key.startsWith("atmcnf_"))
	 	        delete formData[key];
        });

        $.ajax({
            type : "POST",                                 // type of action POST || GET
            url: 'api/atmconfigs/' + action,                   // url where to submit the request
	    	contentType: 'application/json;charset=utf-8',
            dataType : 'json',                             // data type
            data : JSON.stringify( formData ),             // post data || get data
            success : function(resultObj) 
	    	 {
                console.log("success :" + resultObj);
                console.log( resultObj );
			    $('#ResponseMsg').empty();
			    let msg = "Success : " + resultObj["msg"];
			    $('#ResponseMsg').append( atmconfig.createDismissableMsg(msg, "success") );
				atmconfig.loadAtmconfigs();
            },
            error: function(xhr, resp, text) 
	    	{
                console.log("error :" + xhr.responseText);
			    let resultObj = JSON.parse(xhr.responseText);
			    $('#ResponseMsg').empty();
			    let msg = "Error : " + resultObj["msg"];
			    $('#ResponseMsg').append( atmconfig.createDismissableMsg(msg, "danger") );
            }
        });
	}
	
    init() 
	{
        this.LoadProtocolSelect("");
		this.loadAtmconfigs();
        $(ATMConfig.#DATA_LIST_DIV_ID).change( {atmconfig : this}, this.configsDataListChange )
        $(ATMConfig.#FORM_DIV_ID).submit( {atmconfig : this}, this.handleSubmit );
	}

}
