/**
 *
 *
 */
$(document).ready(function()
     {
		 var statesDataListValue = '000';
         var statesData = [];
         function getFITsData()
		 {
             $.ajax(
             {
                 // url: 'http://localhost/app/functions/get_fits_data.php',
                 url: '../app/functions/get_fits_data.php',
                 method: "GET",
                 success: function(data)
	             {
                     statesData = JSON.parse(data);
                     $.each(statesData, function(i, val)
	                 {
						 let selected = '';
						 if (val['fit_number'] === statesDataListValue) selected = 'selected';
	                     let optionStr = '<option value="' + i + '" ' + selected +'>' + val['fit_number']  + ' | ' + val['fit_desc'] + '</option>'
                         $('#fitsDataList').append(optionStr);
                     });
					 $('#fitsDataList').change();
                 }
             });
		 }

         $(window).load(function() 
         {
			 getFITsData();
         });

         $("#fitsDataList").change(function () 
         {
	     	 var fit = statesData[  $("#fitsDataList :selected").attr('value') ];

	     	 $('#fit_config_id').val( fit['fit_config_id'] );
	     	 $('#fit_number').val( fit['fit_number'] );
	     	 $('#fit_bin_prefix').val( fit['fit_bin_prefix'] );
	     	 $('#fit_desc').val( fit['fit_desc'] );
	     	 $('#fit_indirectstateidx-0').val( fit['fit_indirectstateidx-0'] );
	     	 $('#fit_indirectstateidx-1').val( fit['fit_indirectstateidx-1'] );
	     	 $('#fit_algoidx').val( fit['fit_algoidx'] );
	     	 $('#fit_langcodeidx').val( fit['fit_langcodeidx'] );
	     	 $('#fit_maxpinlen-0').val( fit['fit_maxpinlen-0'] );
	     	 $('#fit_maxpinlen-1').val( fit['fit_maxpinlen-1'] );
	     	 $('#fit_localpinchecklen-0').val( fit['fit_localpinchecklen-0'] );
	     	 $('#fit_localpinchecklen-1').val( fit['fit_localpinchecklen-1'] );
	     	 $('#fit_pinpad-0').val( fit['fit_pinpad-0'] );
	     	 $('#fit_pinpad-1').val( fit['fit_pinpad-1'] );
	     	 $('#fit_pinretrycount').val( fit['fit_pinretrycount'] );
	     	 $('#fit_pinoffsetidx').val( fit['fit_pinoffsetidx'] );
	     	 $('#fit_pinblkformat').val( fit['fit_pinblkformat'] );
	     	 $('#fit_panlocidx').val( fit['fit_panlocidx'] );
	     	 $('#fit_panlen-0').val( fit['fit_panlen-0'] );
	     	 $('#fit_panlen-1').val( fit['fit_panlen-1'] );
	     	 $('#fit_panpad-0').val( fit['fit_panpad-0'] );
	     	 $('#fit_panpad-1').val( fit['fit_panpad-1'] );
	     	 $('#fit_decimaltab').val( fit['fit_decimaltab'] );
	     	 $('#fit_encpinkey').val( fit['fit_encpinkey'] );

	     	 $('#fit_idxrefpoints-1').val( fit['fit_idxrefpoints-1'] );
	     	 $('#fit_idxrefpoints-2').val( fit['fit_idxrefpoints-2'] );
	     	 $('#fit_idxrefpoints-3').val( fit['fit_idxrefpoints-3'] );
	     	 $('#fit_idxrefpoints-4').val( fit['fit_idxrefpoints-4'] );
	     	 $('#fit_idxrefpoints-5').val( fit['fit_idxrefpoints-5'] );
	     	 $('#fit_idxrefpoints-6').val( fit['fit_idxrefpoints-6'] );

	     	 $("#fitsDataList").focus();
		 });

	 }
 );


