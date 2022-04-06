/**
 *
 * https://github.com/jweisbeck/Crossword
 *
 */
$(document).ready(function()
{
	var util = 
	{
		highlightEntry: function() 
		{
			// this routine needs to be smarter because it doesn't need to fire every time, only
			// when activePosition changes
			$actives = $('.active');
			$actives.removeClass('active');
			$actives = $('.position-' + activePosition + ' input').addClass('active');
			$actives.eq(0).focus();
			$actives.eq(0).select();
		},
		
		highlightClue: function() {
			var clue;				
			$('.clues-active').removeClass('clues-active');
			// $(clueLiEls + '[data-position=' + activePosition + ']').addClass('clues-active');
			
			// if (mode === 'interacting') {
			// 	clue = $(clueLiEls + '[data-position=' + activePosition + ']');
			// 	activeClueIndex = $(clueLiEls).index(clue);
			// };
		},
		
		getClasses: function(light, type) {
			if (!light.length) return false;
			
			var classes = $(light).prop('class').split(' '),
			classLen = classes.length,
			positions = []; 

			// pluck out just the position classes
			for(var i=0; i < classLen; ++i){
				if (!classes[i].indexOf(type) ) {
					positions.push(classes[i]);
				}
			}
			
			return positions;
		},

		getActivePositionFromClassGroup: function(el)
		{
			classes = util.getClasses($(el).parent(), 'position');
			
			// console.log("JFRD Line 53 " + classes);

			if(classes.length > 1)
			{
				// get orientation for each reported position
				e1Ori = $(clueLiEls + '[data-position=' + classes[0].split('-')[1] + ']').parent().prop('id');
				e2Ori = $(clueLiEls + '[data-position=' + classes[1].split('-')[1] + ']').parent().prop('id');

				// test if clicked input is first in series. If so, and it intersects with
				// entry of opposite orientation, switch to select this one instead
				e1Cell = $('.position-' + classes[0].split('-')[1] + ' input').index(el);
				e2Cell = $('.position-' + classes[1].split('-')[1] + ' input').index(el);

				if(mode === "setting ui"){
					currOri = e1Cell === 0 ? e1Ori : e2Ori; // change orientation if cell clicked was first in a entry of opposite direction
				}

				if(e1Ori === currOri){
					activePosition = classes[0].split('-')[1];		
				} else if(e2Ori === currOri){
					activePosition = classes[1].split('-')[1];
				}
			} 
			else 
			{
				activePosition = classes[0].split('-')[1];						
			}
			
			// console.log('getActivePositionFromClassGroup activePosition: ' + activePosition);
		},
		
		checkSolved: function(valToCheck) {
			for (var i=0, s=solved.length; i < s; i++) {
				if(valToCheck === solved[i]){
					return true;
				}

			}
		},
		
		getSkips: function(position) {
			if ($(clueLiEls[position]).hasClass('clue-done')){
				activeClueIndex = position === clueLiEls.length-1 ? 0 : ++activeClueIndex;
				util.getSkips(activeClueIndex);						
			} else {
				return false;
			}
		}
		
	}; // end util object

	var nav = {
		
		nextPrevNav: function(e, override) {

			var len = $actives.length,
				struck = override ? override : e.which,
				el = $(e.target),
				p = el.parent(),
				ps = el.parents(),
				selector;
		
			util.getActivePositionFromClassGroup(el);
			util.highlightEntry();
			util.highlightClue();
			
			$('.current').removeClass('current');
			
			selector = '.position-' + activePosition + ' input';
			
			//console.log('nextPrevNav activePosition & struck: '+ activePosition + ' '+struck);
				
			// move input focus/select to 'next' input
			switch(struck) {
				case 39:
					p
						.next()
						.find('input')
						.addClass('current')
						.select();

					break;
				
				case 37:
					p
						.prev()
						.find('input')
						.addClass('current')
						.select();

					break;

				case 40:
					var position = parseInt(activePosition) + 32;
			        console.log("JFRD Line 147 >" + position + "<");
					$('#' + position).select();
					break;

				case 38:
					var position = parseInt(activePosition) - 32;
			        console.log("JFRD Line 153 >" + position + "<");
					$('#' + position).select();
					break;

				default:
				break;
			}
													
		},
	
		updateByNav: function(e) {
			var target;
			
			$('.clues-active').removeClass('clues-active');
			$('.active').removeClass('active');
			$('.current').removeClass('current');
			currIndex = 0;

			target = e.target;
			activePosition = $(e.target).data('position');
			
			util.highlightEntry();
			util.highlightClue();
								
			$('.active').eq(0).focus();
			$('.active').eq(0).select();
			$('.active').eq(0).addClass('current');
			
			// store orientation for 'smart' auto-selecting next input
			currOri = $('.clues-active').parent('ol').prop('id');
								
			activeClueIndex = $(clueLiEls).index(e.target);
			//console.log('updateByNav() activeClueIndex: '+activeClueIndex);
			
		},
	
		// Sets activePosition var and adds active class to current entry
		updateByEntry: function(e, next) 
		{
			var classes, next, clue, e1Ori, e2Ori, e1Cell, e2Cell;

			if (e.keyCode == null)
			{
			    console.log("JFRD Line 196 " + e.keyCode);
				// e.keyCode = 39;
			}

			if(e.keyCode === 9 || next)
			{
				// handle tabbing through problems, which keys off clues and requires different handling		
				// activeClueIndex = activeClueIndex === clueLiEls.length-1 ? 0 : ++activeClueIndex;
			
				// $('.clues-active').removeClass('.clues-active');
										
				// next = $(clueLiEls[activeClueIndex]);
				// currOri = next.parent().prop('id');
				activePosition = $(next).data('position');
										
				// skips over already-solved problems
				// util.getSkips(activeClueIndex);
				// activePosition = $(clueLiEls[activeClueIndex]).data('position');
				
																						
			} 
			else 
			{
				// activeClueIndex = activeClueIndex === clueLiEls.length-1 ? 0 : ++activeClueIndex;
			
				util.getActivePositionFromClassGroup(e.target);
				
				// clue = $(clueLiEls + '[data-position=' + activePosition + ']');
				// activeClueIndex = $(clueLiEls).index(clue);
				
				// currOri = clue.parent().prop('id');
				
			}
				
			util.highlightEntry();
			// util.highlightClue();
			
			//$actives.eq(0).addClass('current');	
			//console.log('nav.updateByEntry() reports activePosition as: '+activePosition);	
		}
		
	}; // end nav object

	var rows = 16;
	var cols = 32;

	/*
		Build the table markup
		- adds [data-coords] to each <td> cell
	*/
	function buildTable() 
	{
		var tbl = ['<table class="screen">'];
		var coords = '@ABCDEFGHIJKLMNO0123456789:;<=>?';

		tbl.push('<tbody>');
		tbl.push('<tr>');
		tbl.push('<td class="position-' + x +'"> </td>');		
		for (var x=0; x < cols; x++) 
		{
			tbl.push('<td class="position-' + x +'">' + coords.charAt(x) + '</td>');
		};
		tbl.push('</tr>');

		for (var i=0; i < rows; i++) 
		{
		    tbl.push('<tr>');
			tbl.push('<td class="entry-' + i + '">' + coords.charAt(i) + '</td>');		
			for (var x=0; x < cols; x++) 
			{
			    var position = (i * cols) + x;

				var inp = '<input maxlength="1" name="n' + position + '" id="' + position + '" value=" " type="text" tabindex="-1">';
			    tbl.push('<td data-coords="' + x + ',' + i + '" class="entry-' + i + ' position-' + position +'">' + inp + '</td>');		
			};
			tbl.push('</tr>');
		};

		tbl.push('</tbody>');
		tbl.push('</table>');
		// document.getElementById('puzzle-wrapper').innerHTML += tbl.join('');
		$("#atm-screen").append(tbl.join(''));
	}

	/*
		- Checks current entry input group value against answer
		- If not complete, auto-selects next input for user
	*/
	function checkAnswer (e) 
	{
		var valToCheck, currVal;
		
		currOri = 'across';
		
		// console.log("JFRD Line 292 ");
		util.getActivePositionFromClassGroup($(e.target));

        /*	
		// valToCheck = puzz.data[activePosition].answer.toLowerCase();
		valToCheck = false;
    
	    var content = '';
		for (var i=0; i < (rows * cols); i++)
		{
		    currVal = $('#' + i)
		    	.map(function() 
				{
		      		return $(this).val();
		    	})
		    	.get()
		    	.join('');
		    // console.log("currVal >" + currVal + "<");
		    content += currVal;
		}

		// console.log("content " + content);
		if(valToCheck === currVal){	
			$('.active')
				.addClass('done')
				.removeClass('active');
		
			$('.clues-active').addClass('clue-done');

			solved.push(valToCheck);
			solvedToggle = true;
			return;
		}
		*/

		if ( (e.keyCode === 16) ||
			 (e.keyCode === 17) ||
			 (e.keyCode === 18) ||
			 (e.keyCode === 20))
		{	
			// ignore this key
		}
		else
		    currOri === 'across' ? nav.nextPrevNav(e, 39) : nav.nextPrevNav(e, 40);

		//z++;
		//console.log(z);
		//console.log('checkAnswer() solvedToggle: '+solvedToggle);
	}				

    function c2i(c)
	{
		var coords = '@ABCDEFGHIJKLMNO0123456789:;<=>?';
		for (var i=0; i <= coords.length; i++)
			if (coords.charAt(i) == c)
				return i;
	}

    function i2c(i)
	{
		var coords = '@ABCDEFGHIJKLMNO0123456789:;<=>?';
		return coords.charAt(i);
	}

    function writeChr2Screen(position, c)
	{
		$('#' + position)
		    .map(function() 
			{
		       $(this).val( c );
		    });
	}

    function writeStr2Screen(cy, cx, str)
	{
		var y = c2i(cy);
		var x = c2i(cx);
		var position = 0;

		for (var i=0; i <= (str.length - 1); i++)
		{
	        position = (y * cols) + (x + i);

		    $('#' + position)
		        .map(function() 
		    	{
		           $(this).val( str.charAt(i) );
		       });
		}
		position = (y * cols) + x;
		activePosition = '' + position;
		util.highlightEntry();
	}

    function imageSuffix(cy, cx)
	{
		var imgNameSuffix = ' ' + cy + '_' + cx;
		imgNameSuffix = imgNameSuffix.trim();
		return imgNameSuffix;
	}

    function writeImg2Screen(cy, cx, imgName)
	{
		var maxWidth  = 640;
		var maxHeight = 367;

		var y = c2i(cy);
		var x = c2i(cx);

		var left   = (x * 20) + 20;
		var top    = (y * 20) + 23; // <-----

        const img = new Image();
		var imageLocation = 'assets/images/atm/' + imgName; //  + '.jpg';
        img.src = imageLocation;

        img.onload = function() 
        {
		    var width  = this.width;
		    var height = this.height;
		    
		    var scaleWidth  = 1;
		    var scaleHeight = 1;
		    
		    if (width > maxWidth)
		    	scaleWidth = width / maxWidth;
		    
		    if (height > maxHeight)
		    	scaleHeight = height / maxHeight;
		    
            if (scaleWidth > scaleHeight)
		    {
                width  = Math.floor(width / scaleWidth);
		    	height = Math.floor(height / scaleWidth);
		    }
		    else
		    {
                width  = Math.floor(width / scaleHeight);
		    	height = Math.floor(height / scaleHeight);
		    }
		    
		    var tbl = ['<div id="imgContainer" style="position: absolute; left: ' + left + 'px; top: ' + top + 'px; width: ' + width + 'px; height: ' + height + 'px; z-index: 2;">'];
		    tbl.push('<img id="img' + imageSuffix( c2i(cy), c2i(cx) ) + '" src="' + imageLocation + '" width="' + width + '" height="' + height + '">');
		    tbl.push('</div>');
		    $("#atm-screen").append(tbl.join(''));
        }
	}

	/*
		- Clear Screen
	*/
	function clearScreen() 
	{
		$('#imgContainer').remove();
		for (var position=0; position <= (rows * cols); position++)
		{
		    $('#' + position)
		        .map(function() 
		    	{
		           $(this).val( ' ' );
		       });
		}
	}

    function isLetter(char) 
    {
      return (/[a-zA-Z]/).test(char)
    }

    function isNumeric(value)
    {
        return /^\d+$/.test(value);
    }

    function getCy(position)
    {
		let y = Math.floor(position / cols);
		return i2c(y);
    }

    function getCx(position)
    {
	    let x = position % cols;
		return i2c(x);
    }

    function isPrintable(char)
	{
        return !( /[\x00-\x08\x0E-\x1F\x80-\xFF]/.test(char));
    }

	/*
		- Draw the Screen based in the definitions
	*/
	function putScreen(screen) 
	{
        clearScreen();

		screen = screen.replaceAll('\u240c', '\x0c'); //0c
		screen = screen.replaceAll('\u240f', '\x0f'); //0f
		screen = screen.replaceAll('\u240e', '\x0e'); //0e
		screen = screen.replaceAll('\u241b', '\x1b'); //1b

		let cy = '@';
		let cx = '@';
		let y = c2i(cy);
		let x = c2i(cx);
	    let position = (y * cols) + x;

		let i = 0;
        while (i < screen.length)
		{
		    if (screen[i] === '\x0c')         // FF
			{
				i++;
			    cy = '@';
			    cy = '@';
		        y = c2i(cy);
		        x = c2i(cx);
	            position = (y * cols) + x;
			}
		    else if (screen[i] === '\x0f')    // SI
			{
				i++;
			    cy  = screen[i];
				i++;
			    cx  = screen[i];
				i++;
		        y = c2i(cy);
		        x = c2i(cx);
	            position = (y * cols) + x;
			}
		    else if (screen[i] === '\x0e')    // SO
			{
				i++
				// Creo el Numero de la Pantalla
				let screenNum = screen[i];
                i++;
				screenNum += screen[i]
                i++;
				screenNum += screen[i]
                i++;

                // No es Numerico Creamos que es una Imagen
                if (! isNumeric(screenNum) )
				{
					// Image Name Extension .jpg, .pcx, etc
					// while (isPrintable(screen[i]))
					// {
				    //     screenNum += screen[i]
                    //     i++;
					// }
                    writeImg2Screen(getCy(position), getCx(position), screenNum);
					continue;
				}

				// Buscar La Pantalla
				let screenId = 0;
                $.each(screens, function(i, val)
	            {
					if (val['scr_number'] === screenNum)
					    screenId = i;
                });					    
				
				if (screenId !== 0)
				{
				    scr = screens[screenId];
				    putScreen(scr['scr_data']);
				}
			}
		    else if (screen[i] === '\x1b')    // ESC
			{
				i++
				if (screen[i] === '[')
				{
					i++;
					var chars = '\x0c\x0f\x0e\x1b';
					while ( chars.indexOf(screen[i]) < 0)
						i++;
				}
				else if (screen[i] === 'P')
				{
					i++;
					if (screen[i] === 'E')
					{
				        i++
				        // Creo el Numero de la Pantalla
				        let screenNum = screen[i];
                        i++;
				        screenNum += screen[i]
                        i++;
				        screenNum += screen[i]
                        i++;
				        
                        // No es Numerico Creamos que es una Imagen
                        if (! isNumeric(screenNum) )
				        {
							// Image Name Extension .jpg, .pcx, etc
							while (isPrintable(screen[i]))
							{
				                screenNum += screen[i]
                                i++;
							}
                            writeImg2Screen(getCy(position), getCx(position), screenNum);
				        	continue;
				        }
				        
				        // Buscar La Pantalla
				        let screenId = 0;
                        $.each(screens, function(i, val)
	                    {
				        	if (val['scr_number'] === screenNum)
				        	    screenId = i;
                        });					    
				        
				        if (screenId !== 0)
				        {
				            scr = screens[screenId];
				            putScreen(scr['scr_data']);
				        }

					}
				}
				else
				{
					
					// while ( chars.indexof     isPrintable(screen[i]) )
					
					var chars = '\x0c\x0f\x0e\x1b';
					while ( chars.indexOf(screen[i]) < 0)
						i++;
				}
			}
			else
			{
			    writeChr2Screen(position, screen[i]);
			    position++;
			    i++;
			}

		}
		return;
        //------------------------------------------------------------------
		// var screenCmds = screen.split('');
		// for (var i=0; i <= (screenCmds.length - 1); i++)
		// {
		// 	var cy  = screenCmds[i].charAt(0);
		// 	var cx  = screenCmds[i].charAt(1);
		// 	if (screenCmds[i].charAt(2) != '' )
		// 	{
		// 	    var str = screenCmds[i].substring(2);
		// 	    writeStr2Screen(cy, cx, str);
		// 	}
		// 	else
		// 	{
		// 		// Es una Imagen
		// 		var imgName = screenCmds[i].substring(3);
        //         writeImg2Screen(cy, cx, imgName)
		// 	}
		// }
    }

	/*
		- Get Screen comands
	*/
	function getScreen() 
	{
        var cmds = '␌';
		for (var y = 0; y < rows; y++)
		{
			let str  = '';
			let beginX = -1;
			let beginY = 0;
		    for (var x = 0; x < cols; x++) 
			{
				var i = (y * cols) + x;

		        currVal = $('#' + i)
		        	.map(function() 
			    	{
		          		return $(this).val();
		        	})
		        	.get()
		        	.join('');

                str += currVal;				

		        // console.log("JFRD Line 469 currVal >" + currVal + "< >" + i + "< >" + i2c(x) + "< x >" + x + "< cols >" + cols + "<");
			
				if (x === (cols - 1))
				{
					if (str.trim().length > 0)
					{
						var imageCmd = '';
						var imageIndex = 0;
		                for (var i = 0; i < cols; i++)
						{
						    var imageSrc = $('#img' + imageSuffix(beginY, i) ).attr('src');
		                    if (typeof(imageSrc) != "undefined")
							{
								let lastIndexOf = 0;
								let wrkStr = imageSrc;
				                while (wrkStr.indexOf('/') > -1)
								{
									lastIndexOf = wrkStr.indexOf('/') + 1;
									wrkStr = wrkStr.substring(lastIndexOf , wrkStr.indexOf('.') + 1);
								}

								var imageName = wrkStr.substring(0, wrkStr.indexOf('.') )
								imageCmd = '' + i2c(beginY) + i2c(i) + '' + imageName;
								imageIndex = i;
								
								break;
							}
						}

                        var line = '' + i2c(beginY) + i2c(beginX) + str.trim();

                        if (beginX > imageIndex)
						    cmds += imageCmd + line;
						else
						    cmds += line + imageCmd;
					}
					break;
				}
				// console.log("Line 717 currVal >" + currVal + "< beginX >" + beginX + "<");
				if ( (currVal != ' ') && (beginX == -1) )
				{
					beginX = x;
					beginY = y;
					str = currVal;
				}
		        // console.log("JFRD Line 469 currVal >" + currVal + "< >" + i + "< >" + i2c(x) + "<");
				// console.log("JFRD Line 470 str >" + str + "<");

		        //  content += currVal;
			
			}
			console.log("str " + str);
			// break;
		}
		if (typeof(activePosition) != "undefined")
		{
		    var position = parseInt(activePosition);
		    var y = Math.floor( position / cols);
		    var x = position % cols;
		    cmds += '' + i2c(y) + i2c(x);
		}

		cmds = cmds.replaceAll('\x0c', '\u240c'); //0c
		cmds = cmds.replaceAll('\x0f', '\u240f'); //0f
		cmds = cmds.replaceAll('\x0e', '\u240e'); //0e
		cmds = cmds.replaceAll('\x1b', '\u241b'); //1b

		return cmds;
	}

	// Set keyup handlers for the 'entry' inputs that will be added presently
	$("#atm-screen").delegate('input', 'keyup', function(e){
		mode = 'interacting';
		
		// need to figure out orientation up front, before we attempt to highlight an entry
		switch(e.which) 
		{
		    case 39:
			case 37:
				currOri = 'across';
				break;
			case 38:
			case 40:
			    console.log("JFRD Line 744");
				currOri = 'down';
				break;
			default:
				break;
		}
		
		if ( e.keyCode === 9) {
			return false;
		} else if (
			e.keyCode === 37 ||
			e.keyCode === 38 ||
			e.keyCode === 39 ||
			e.keyCode === 40 ||
			e.keyCode === 8 ||
			e.keyCode === 46 ) {			
								

			
			if (e.keyCode === 8 || e.keyCode === 46) {
				currOri === 'across' ? nav.nextPrevNav(e, 37) : nav.nextPrevNav(e, 38); 
			} else {
				nav.nextPrevNav(e);
			}
			
			e.preventDefault();
			return false;
		} 
		else 
		{
			// console.log('Line 787 input keyup: ');
			checkAnswer(e);
		}

		e.preventDefault();
		return false;					
	});
	
	// tab navigation handler setup
	$("#atm-screen").delegate('input', 'keydown', function(e) {

		if ( e.keyCode === 9) {
			
			mode = "setting ui";
			// if (solvedToggle) solvedToggle = false;

			checkAnswer(e)
			nav.updateByEntry(e);
			
		} else {
			return true;
		}
								
		e.preventDefault();
					
	});

    $("#Mostrar").click(function() 
	{
		var txt = $("#cmdTxtArea").val();
	    putScreen(txt);
    });

    $("#Capturar").click(function() 
	{
		$("#cmdTxtArea").val( getScreen() );
    });

    // $("#showImg").onChange( function() 

    $('input[type="checkbox"]').change(function () 
	{
        if (this.checked) 
			$('table.screen td').css({"z-index":"1"});
		else
			$('table.screen td').css({"z-index":"3"});
    });

	// tab navigation handler setup
	$("#atm-screen").delegate('input', 'click', function(e) {
		mode = "setting ui";

		console.log('Line 826 input click: ');
	
		nav.updateByEntry(e);
		e.preventDefault();
	});

	// tab navigation handler setup
	$("#atm-screen").delegate('input', 'keydown', function(e) 
	{

		if ( e.keyCode === 9) {
			
			mode = "setting ui";
			// if (solvedToggle) solvedToggle = false;

			checkAnswer(e)
			nav.updateByEntry(e);
			
		} else {
			return true;
		}
								
		e.preventDefault();
					
	});

    $(window).load(function() 
    {
		buildTable();
    });

    function LoadConfigIdSelect() 
	{
        $.ajax(
        {
	    	url: 'api/atmconfigs/unique',
            method: "GET",
            success: function(data)
	        {
                atmconfigs = JSON.parse( JSON.stringify(data) );
	            $('#config_id').empty();
                $('#config_id').append('<option value=" "> </option>');
                $.each(atmconfigs, function(i, val)
	            {
	            	let optionStr = '<option value="' + val['atmcnf_configid'] + '">' + val['atmcnf_configid'] + '</option>'
                $('#config_id').append(optionStr);
                });
            }
        });
	}
	LoadConfigIdSelect();

    var screens = [];
	var configId = '0000';

    function configIdChange() 
	{
	    console.log('configId: ' + configId);
        $.ajax(
        {
	    	url: 'api/screens/' + configId,
            method: "GET",
            success: function(data)
	        {
                screens = JSON.parse( JSON.stringify(data) );
				$('#screenList').empty();
                $.each(screens, function(i, val)
	            {
	    	        val['scr_data'] = val['scr_data'].replaceAll('\x0c', '\u240c');
	    	        val['scr_data'] = val['scr_data'].replaceAll('\x0f', '\u240f');
	    	        val['scr_data'] = val['scr_data'].replaceAll('\x0e', '\u240e');
	    	        val['scr_data'] = val['scr_data'].replaceAll('\x1b', '\u241b');
	    
	            	let optionStr = '<option value="' + i + '">' + val['scr_number'] + ' | ' + val['scr_desc'] + '</option>'
                    $('#screenList').append(optionStr);
                });
                clearScreen();
	            putScreen($("#cmdTxtArea").val());
				$('#screenList').prop('selectedIndex',-1);
		        $("#screenList").focus();
            }
        });
	}

    $("#config_id").change( function() {
		configId = this.value;
		configIdChange();
    });

    $("#screenList").change(function () 
    {
	    console.log('scrIndex: ' + $("#screenList :selected").attr('value') );
		let scr = screens[  $("#screenList :selected").attr('value') ];
		
		$('#cmdTxtArea').val( scr['scr_data'] );
		$('#scr_number').val( scr['scr_number'] );
		$('#scr_desc').val( scr['scr_desc'] );

		if (scr['scr_data'].length > 0)
	        putScreen(scr['scr_data'] );
		$("#screenList").focus();
    });	

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
		let action = document.activeElement['value'];      // Value of the submit button clicked
		formData = $(this).formToJson();

		formData['scr_data'] = getScreen();                // Actualizar scr_data a partir del dibujo que esta en la Pantalla

        Object.keys(formData).forEach(key =>               // Eliminar los Campos que no son parte del Objeto Screen
		{
		    if (! key.startsWith("scr_"))
		        delete formData[key];
        });

        console.log( formData );

        event.preventDefault();

        $.ajax({
            type : "POST",                                 // type of action POST || GET
            url: 'api/screens/' + action,                  // url where to submit the request
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
		        configIdChange();
		        $('#cmdTxtArea').val('');
		        $('#scr_number').val('');
		        $('#scr_desc').val('');
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

    $("#screensForm").submit(handleSubmit);

});
