/**
 *
 *
 */
$(document).ready(function()
     {
         function loadAtmconfigs() 
	     {
             $.ajax(
             {
                 url: 'api/atmconfigs/getall',
                 method: "GET",
                 success: function(data)
	             {
                     configsData = JSON.parse( JSON.stringify(data) );
	                 $('#atmconfigsDataList').empty();
                     $.each(configsData, function(i, val)
	                 {
						 let selected = '';
	                     let optionStr = '<option value="' + i + '" ' + selected + '>' + val['atmcnf_configid'] + ' | ' + val['atmcnf_desc'] + '</option>';
                         $('#atmconfigsDataList').append(optionStr);
                     });
					 $('#atmconfigsDataList').change();
                 }
             });
	     }

         $(window).load(function() 
         {
			 loadAtmconfigs();
         });

         $("#atmconfigsDataList").change(function () 
         {
	     	 var atmconfig = configsData[  $("#atmconfigsDataList :selected").attr('value') ];
			 
			 if (atmconfig != null)
			 {
	     	     $('#atmcnf_configid').val( atmconfig['atmcnf_configid'] );
	     	     $('#atmcnf_desc').val( atmconfig['atmcnf_desc'] );
	     	     $('#atmcnf_languageindex').val( atmconfig['atmcnf_languageindex'] );
	     	     $('#atmcnf_languageatm').val( atmconfig['atmcnf_languageatm'] );
	     	     $('#atmcnf_language639').val( atmconfig['atmcnf_language639'] );
	     	     $('#atmcnf_screengroupbase').val( atmconfig['atmcnf_screengroupbase'] );
			 }

	     	 $("#atmconfigsDataList").focus();
		 });


         function getValues()
		 {
			 atmconfig = {};

	     	 atmconfig['atmcnf_configid']        = $('#atmcnf_configid').val();
	     	 atmconfig['atmcnf_desc']            = $('#atmcnf_desc').val();
	     	 atmconfig['atmcnf_languageindex']   = $('#atmcnf_languageindex').val();
	     	 atmconfig['atmcnf_languageatm']     = $('#atmcnf_languageatm').val();
	     	 atmconfig['atmcnf_language639']     = $('#atmcnf_language639').val();
	     	 atmconfig['atmcnf_screengroupbase'] = $('#atmcnf_screengroupbase').val( );

			 return atmconfig;
		 }

         function createDismissableMsg(msg, level)
	     {
	     	str  = "";
	     	str += "<div class=\"alert alert-" + level + " alert-dismissible fade show\" role=\"alert\">";
	     	str += msg;
	     	str += "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">";
	     	str += "        <span aria-hidden=\"true\">&times;</span>";
	     	str += "    </button>";
	     	str += "</div>";
	     	return str;
	     }

         function handleSubmit(event) 
	     {
             event.preventDefault();

		     let action = document.activeElement['value'];      // Value of the submit button clicked
             console.log( ' action ' + action );
			 formData = getValues();
             console.log( formData );

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
				     $('#ResponseMsg').append( createDismissableMsg(msg, "success") );
					 loadAtmconfigs();
                 },
                 error: function(xhr, resp, text) 
		     	{
                     console.log("error :" + xhr.responseText);
				     let resultObj = JSON.parse(xhr.responseText);
				     $('#ResponseMsg').empty();
				     let msg = "Error : " + resultObj["msg"];
				     $('#ResponseMsg').append( createDismissableMsg(msg, "danger") );
                 }
             });
		 }

         $("#fitsForm").submit(handleSubmit);

	 }
 );


