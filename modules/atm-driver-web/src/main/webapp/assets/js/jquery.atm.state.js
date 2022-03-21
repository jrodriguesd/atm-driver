/**
 *
 *
 */
$(document).ready(function()
     {
		 var statesDataListValue = '000';
         var statesData = [];
         function getStatesData()
		 {
             $.ajax(
             {
                 url: '../app/functions/get_states_data.php',
                 method: "GET",
                 success: function(data)
	             {
                     statesData = JSON.parse(data);
                     $.each(statesData, function(i, val)
	                 {
						 let selected = '';
						 if (val['std_number'] === statesDataListValue) selected = 'selected';
	                     let optionStr = '<option value="' + i + '" ' + selected +'>' + val['std_number']  + ' | ' + val['std_desc'] + '</option>'
                         $('#statesDataList').append(optionStr);
                     });
					 $('#statesDataList').change();
                 }
             });
		 }

         var states = [];
         function getStates()
		 {
             $.ajax(
             {
                 url: '../app/functions/get_states.php',
                 method: "GET",
                 success: function(data)
	             {
                     states = JSON.parse(data);
                     $('#statesList').append('<option value="0">No State Type</option>');
                     $('#statesList').append('<option disabled="disabled">-----</option>');
                     $.each(states, function(i, val)
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

         $(window).load(function() 
         {
			 getStates();
			 getStatesData();
         });


         function drawStateScreen(stateNmmber, tabIndex, sateData)
		 {
		 }

         function processExtensionStatesElem(std, index, tabIndex)
		 {
		     let tag_std = '#std_' + index + '-' + tabIndex;
	         $(tag_std).val( std['std_' + index] );
		 }

         function processExtensionStates(std, tabIndex)
		 {
             let sta = states[ std['std_type'] ]; 

			 if (sta === undefined) return;

             statesListChange(sta, tabIndex);

	     	 $('#std_config_id-' + tabIndex).val( std['std_config_id'] );
	     	 $('#std_number-' + tabIndex).val( std['std_number'] );
	     	 $('#statesList-' + tabIndex).val( std['std_type'] ).change();
	     	 $('#std_desc-' + tabIndex).val( std['std_desc'] );
			 
			 for (let i = 1; i < 9; i++)
			     processExtensionStatesElem(std, i, tabIndex);
		 }

         /*
		  * Cambia El Valor
		  */
         function processStatesDataListChangeElem(std, index)
		 {
		     let tag_std = 'std_' + index + '-0';

             let sta = states[ std['std_type'] ]; 
			 let tag_sta = 'sta_' + index;
			 let extensionState = std['std_' + index];
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
                 $.each(statesData, function(i, val)
	             {
					 if (val['std_number'] === extensionState)
					     extensionStateDataIndex = i;
                 });					    

                 // No Encontro el Extension State				 
				 if (extensionStateDataIndex === 0)
				     return;

	     	     let std_1 = statesData[ extensionStateDataIndex ];
				 let stateTypeSufix = std['std_type'] + tabIndex;

				 if (std_1['std_type'].length === 1)
				 {
				     statesData[ extensionStateDataIndex ]['std_type'] = std_1['std_type'] + stateTypeSufix;
	     	         std_1 = statesData[ extensionStateDataIndex ];
				 }

                 processExtensionStates(std_1, tabIndex);
			 }

			 if  ( !  ( sta[tag_sta]['type'] === undefined ) )
			 {
				 let valArr = [];
				 
				 for (let i = 1; i < 129; i=i*2)
				 {
			         // console.log('JFRD jquery.atm.state.js 143 ');
					 let testCond = i & parseInt( std['std_' + index]);

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
			     // console.log('JFRD jquery.atm.state.js 144 #' + tag_std + ' tag_sta ' + tag_sta + ' val ' + std['std_' + index] );
	             $('#' + tag_std ).val( std['std_' + index] );
			 }
				 
		 }

         $("#statesDataList").change(function () 
         {
	     	 var std = statesData[  $("#statesDataList :selected").attr('value') ];

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
			     processStatesDataListChangeElem(std, i);
			 }
			 
	     	 $("#statesDataList").focus();
         });	

         /*
		  * Cambia Los Titulos y los Tipos de Input
		  */
         function processStateListChangeElem(tabIndex, sta, index)
		 {
		     let tag = 'sta_' + index;
			 let suffix = '-' + tabIndex;
			 let name = 'std_' + index + suffix;
			 let inputTag = '';
			 let order = (tabIndex * 13) + index + 3;
			 
	     	 $('#' + tag + '_title' + suffix).text( sta[tag]['title'] );

			 if ( sta[tag]['values'] === undefined )
			 {
			 	 inputTag = '<input type="text" id="' + name  + '" class="form-control" name="' + name  + '" autocomplete="off" value="000" maxlength="3" tabindex="' + order + '" aria-hidden="true">';
			 }
			 else
			 {
				 if ( sta[tag]['type'] === undefined )
			 	     inputTag  = '<select id="' + name  + '" class="form-control" name="' + name  + '" data-select2-id="input-group" tabindex="' + order + '" aria-hidden="true">';
				 else
			 	     inputTag  = '<select id="' + name  + '" class="form-control" name="' + name  + '[]" data-select2-id="input-group" multiple="multiple" tabindex="' + order + '" aria-hidden="true">';
					 
                 $.each(sta[tag]['values'], function(i, val)
	             {
					 let selected = '';
	                 // inputTag += '<option value="' + i + '" selected>' + i + ' | ' + val + '</option>'
	                 inputTag += '<option value="' + i + '" ' + selected + '>' + i + ' | ' + val + '</option>'
				 });
                 inputTag += '</select>' 				 
			 }
			 $('#' + name).multiselect('destroy');
			 // Cambio el Tipo de Input
			 $('#' + name).replaceWith(inputTag);

			if ( ! ( sta[tag]['type'] === undefined ) )
			{
			    $('#' + name).multiselect(
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

         function statesListChange(sta, tabIndex)
		 {
			 for (let i = 1; i < 9; i++)
			     processStateListChangeElem(tabIndex, sta, i);
		 }

         $("#statesList-0").change(function () 
         {
			 let label = "#statesList-0 :selected";
	     	 let sta = states[  $(label).attr('value') ];

			 if (sta === undefined) return;

		     statesListChange(sta, 0);
         });	

         $("#statesList-1").change(function () 
         {
			 let label = "#statesList-1 :selected";
	     	 let sta = states[  $(label).attr('value') ];

			 if (sta === undefined) return;

		     statesListChange(sta, 1);
         });	

         $("#statesList-2").change(function () 
         {
			 let label = "#statesList-1 :selected";
	     	 let sta = states[  $(label).attr('value') ];

			 if (sta === undefined) return;

		     statesListChange(sta, 2);
         });	

         $("#statesList-3").change(function () 
         {
			 let label = "#statesList-1 :selected";
	     	 let sta = states[  $(label).attr('value') ];

			 if (sta === undefined) return;

		     statesListChange(sta, 3);
         });	

         $("#nav-1-tab").hide();
         $("#nav-2-tab").hide();
         $("#nav-3-tab").hide();
         $("#nav-0-tab").click();
     }
 );

