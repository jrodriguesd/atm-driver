/**
 *
 * https://github.com/jweisbeck/Crossword
 *
 */

'use strict'; 

/***************************************************************************************************
 * Class Util - Begins
 ***************************************************************************************************/
class Util
{
	activePosition = '';
	actives = '';
	
	highlightEntry() 
	{
		// this routine needs to be smarter because it doesn't need to fire every time, only
		// when activePosition changes
		this.actives = $('.active');
		this.actives.removeClass('active');
		this.actives = $('.position-' + this.activePosition + ' input').addClass('active');
		this.actives = $('.active');
		this.actives.eq(0).focus();
		this.actives.eq(0).select();
	}
	
	highlightClue() {
		var clue;				
		$('.clues-active').removeClass('clues-active');
		// $(clueLiEls + '[data-position=' + activePosition + ']').addClass('clues-active');
		
		// if (mode === 'interacting') {
		// 	clue = $(clueLiEls + '[data-position=' + activePosition + ']');
		// 	activeClueIndex = $(clueLiEls).index(clue);
		// };
	}
	
	getClasses(light, type) {
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
	}

	getActivePositionFromClassGroup(el)
	{
		let classes = this.getClasses($(el).parent(), 'position');
		
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
				this.currOri = e1Cell === 0 ? e1Ori : e2Ori; // change orientation if cell clicked was first in a entry of opposite direction
			}

			if(e1Ori === this.currOri)
			{
				this.activePosition = classes[0].split('-')[1];		
			} 
			else if(e2Ori === this.currOri)
			{
				this.activePosition = classes[1].split('-')[1];
			}
		} 
		else 
		{
			this.activePosition = classes[0].split('-')[1];						
		}
		
		// console.log('getActivePositionFromClassGroup activePosition: ' + activePosition);
	}
	
	checkSolved(valToCheck) {
		for (var i=0, s=solved.length; i < s; i++) {
			if(valToCheck === solved[i]){
				return true;
			}

		}
	}
	
	getSkips(position) {
		if ($(clueLiEls[position]).hasClass('clue-done')){
			activeClueIndex = position === clueLiEls.length-1 ? 0 : ++activeClueIndex;
			this.util.getSkips(activeClueIndex);						
		} else {
			return false;
		}
	}
}	
/***************************************************************************************************
 * Class Util - Ends
 ***************************************************************************************************/
 
/***************************************************************************************************
 * Class Nav - Begins
 ***************************************************************************************************/
class Nav
{
    constructor(util) 
	{
        this.util = util;
    }

	nextPrevNav(e, override) 
	{

		var len = this.util.actives.length,
			struck = override ? override : e.which,
			el = $(e.target),
			p = el.parent(),
			ps = el.parents(),
			selector;
	
		this.util.getActivePositionFromClassGroup(el);
		this.util.highlightEntry();
		this.util.highlightClue();
		
		$('.current').removeClass('current');
		
		selector = '.position-' + this.util.activePosition + ' input';
		
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
				var position = parseInt(this.util.activePosition) + 32;
				$('#' + position).select();
				break;

			case 38:
				var position = parseInt(this.util.activePosition) - 32;
				$('#' + position).select();
				break;

			default:
			break;
		}
												
	}

	updateByNav(e) 
	{
		var target;
		
		$('.clues-active').removeClass('clues-active');
		$('.active').removeClass('active');
		$('.current').removeClass('current');
		currIndex = 0;

		target = e.target;
		activePosition = $(e.target).data('position');
		
		this.util.highlightEntry();
		this.util.highlightClue();
							
		$('.active').eq(0).focus();
		$('.active').eq(0).select();
		$('.active').eq(0).addClass('current');
		
		// store orientation for 'smart' auto-selecting next input
		this.currOri = $('.clues-active').parent('ol').prop('id');
							
		activeClueIndex = $(clueLiEls).index(e.target);
		//console.log('updateByNav() activeClueIndex: '+activeClueIndex);
		
	}

	// Sets activePosition var and adds active class to current entry
	updateByEntry(e, next) 
	{
		var classes, next, clue, e1Ori, e2Ori, e1Cell, e2Cell;

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
		
			this.util.getActivePositionFromClassGroup(e.target);
			
			// clue = $(clueLiEls + '[data-position=' + activePosition + ']');
			// activeClueIndex = $(clueLiEls).index(clue);
			
			// currOri = clue.parent().prop('id');
			
		}
			
		this.util.highlightEntry();
		// util.highlightClue();
		
		//$actives.eq(0).addClass('current');	
		//console.log('nav.updateByEntry() reports activePosition as: '+activePosition);	
	}
		
}
/***************************************************************************************************
 * Class Nav - Ends
 ***************************************************************************************************/

class ATMScreen
{
	/***************************************************************************************************
	 * ATMScreen - Begins
	 ***************************************************************************************************/
	 static #SCREEN_LIST_DIV_ID = "#screenList"; 
	 static #ATM_SCREEN_DIV_ID  = "#atm-screen";
	 
	 static #FF  = '\x0c';
	 static #SI  = '\x0f';
	 static #SO  = '\x0e';
	 static #ESC = '\x1b';

	 static #FF_GLITCH  = '\u240c';
	 static #SI_GLITCH  = '\u240f';
	 static #SO_GLITCH  = '\u240e';
	 static #ESC_GLITCH = '\u241b';

	 static #rows = 16;
	 static #cols = 32;

     constructor() 
	 {
	     this.screens  = '';
	     this.currOri  = '';
	     this.mode     = '';
	     this.configId = '0000';

	     this.util = new Util();
         this.nav  = new Nav(this.util);
		 this.jwt  = "";
		 this.baseURL = "/jpos/api";
	 }

	 setJWT(jwt)
	 {
	     this.jwt = jwt;
	 }
	 
	 setBaseURL(baseURL)
	 {
	     this.baseURL = baseURL;
	 }

	 /*
	 	Build the table markup
	 	- adds [data-coords] to each <td> cell
	 */
	 buildTable() 
	 {
	 	let tbl = ['<table class="screen">'];
	 	let coords = '@ABCDEFGHIJKLMNO0123456789:;<=>?';
	 
	 	tbl.push('<tbody>');
	 	tbl.push('<tr>');
	 	tbl.push('<td class="position-0"> </td>');		
	 	for (let x=0; x < ATMScreen.#cols; x++) 
	 	{
	 		tbl.push('<td class="position-' + x +'">' + coords.charAt(x) + '</td>');
	 	};
	 	tbl.push('</tr>');
	 
	 	for (let i=0; i < ATMScreen.#rows; i++) 
	 	{
	 	    tbl.push('<tr>');
	 		tbl.push('<td class="entry-' + i + '">' + coords.charAt(i) + '</td>');		
	 		for (let x=0; x < ATMScreen.#cols; x++) 
	 		{
	 		    let position = (i * ATMScreen.#cols) + x;
	 
	 			let inp = '<input maxlength="1" name="n' + position + '" id="' + position + '" value=" " type="text" tabindex="-1">';
	 		    tbl.push('<td data-coords="' + x + ',' + i + '" class="entry-' + i + ' position-' + position +'">' + inp + '</td>');		
	 		};
	 		tbl.push('</tr>');
	 	};
	 
	 	tbl.push('</tbody>');
	 	tbl.push('</table>');
	 	$(ATMScreen.#ATM_SCREEN_DIV_ID).append(tbl.join(''));
	 }
	 
	 /*
	 	- Checks current entry input group value against answer
	 	- If not complete, auto-selects next input for user
	 */
	 checkAnswer (e) 
	 {
		this.currOri = 'across';

	 	this.util.getActivePositionFromClassGroup($(e.target));
	 
	 	if ( (e.keyCode === 16) ||
	 		 (e.keyCode === 17) ||
	 		 (e.keyCode === 18) ||
	 		 (e.keyCode === 20))
	 	{	
	 		// ignore this key
	 	}
	 	else
	 	    this.currOri === 'across' ? this.nav.nextPrevNav(e, 39) : this.nav.nextPrevNav(e, 40);
	 }				

     c2i(c)
	 {
	 	var coords = '@ABCDEFGHIJKLMNO0123456789:;<=>?';
	 	for (var i=0; i <= coords.length; i++)
	 		if (coords.charAt(i) == c)
	 			return i;
	 }
	 
     i2c(i)
	 {
	 	var coords = '@ABCDEFGHIJKLMNO0123456789:;<=>?';
	 	return coords.charAt(i);
	 }
	 
     writeChr2Screen(position, c)
	 {
	 	$('#' + position)
	 	    .map(function() 
	 		{
	 	       $(this).val( c );
	 	    });
	 }

     writeStr2Screen(cy, cx, str)
	 {
	 	let y = this.c2i(cy);
	 	let x = this.c2i(cx);
	 	let position = 0;
	 
	 	for (let i=0; i <= (str.length - 1); i++)
	 	{
	         position = (y * ATMScreen.#cols) + (x + i);
	 
	 	    $('#' + position)
	 	        .map(function() 
	 	    	{
	 	           $(this).val( str.charAt(i) );
	 	       });
	 	}
	 	position = (y * ATMScreen.#cols) + x;
	 	activePosition = '' + position;
	 	util.highlightEntry();
	 }

     imageSuffix(cy, cx)
	 {
	 	let imgNameSuffix = ' ' + cy + '_' + cx;
	 	imgNameSuffix = imgNameSuffix.trim();
	 	return imgNameSuffix;
	 }

     writeImg2Screen(cy, cx, imgName)
	 {
		let screen = this;   // para poder usarlo en otro contexto (onload)
	 	let maxWidth  = 640;
	 	let maxHeight = 367;
	 
	 	let y = this.c2i(cy);
	 	let x = this.c2i(cx);
	 
	 	let left = (x * 20) + 20;
	 	let top = (y * 20) + 23; // <-----

        const img = new Image();
	 	let imageLocation = '/atm-driver-web-assets/images/atm/' + imgName; //  + '.jpg';
        img.src = imageLocation;
	 
	    img.onerror = function() 
		{
	        console.log('jquery.atm.screen.js Line 411 fallo carga de Imagen ' + imageLocation);
		}

        img.onload = function() 
        {
	 	   let width  = this.width;
	 	   let height = this.height;

	 	   let scaleWidth  = 1;
	 	   let scaleHeight = 1;
	 	   
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

	 	   let tbl = ['<div id="imgContainer" style="position: absolute; left: ' + left + 'px; top: ' + top + 'px; width: ' + width + 'px; height: ' + height + 'px; z-index: 2;">'];
	 	   tbl.push('<img id="img' + screen.imageSuffix( screen.c2i(cy), screen.c2i(cx) ) + '" src="' + imageLocation + '" width="' + width + '" height="' + height + '">');
	 	   tbl.push('</div>');
	 	   $(ATMScreen.#ATM_SCREEN_DIV_ID).append(tbl.join(''));
        }
	 }

	 /*
	 	- Clear Screen
	 */
	 clearScreen() 
	 {
	 	$('#imgContainer').remove();
	 	for (var position=0; position <= (ATMScreen.#rows * ATMScreen.#cols); position++)
	 	{
	 	    $('#' + position)
	 	        .map(function() 
	 	    	{
	 	           $(this).val( ' ' );
	 	       });
	 	}
	 }
	 
     isLetter(char) 
     {
       return (/[a-zA-Z]/).test(char)
     }
	 
     isNumeric(value)
     {
         return /^\d+$/.test(value);
     }
	 
     getCy(position)
     {
	 	let y = Math.floor(position / ATMScreen.#cols);
	 	return this.i2c(y);
     }

     getCx(position)
     {
	     let x = position % ATMScreen.#cols;
	 	return this.i2c(x);
     }

     isPrintable(char)
	 {
         return !( /[\x00-\x08\x0E-\x1F\x80-\xFF]/.test(char));
     }

	 /*
	 	- Draw the Screen based in the definitions
	 */
	 putScreen(screen) 
	 {
         this.clearScreen();
	 
	 	 screen = screen.replaceAll(ATMScreen.#FF_GLITCH,  ATMScreen.#FF); //0c
	 	 screen = screen.replaceAll(ATMScreen.#SI_GLITCH,  ATMScreen.#SI); //0f
	 	 screen = screen.replaceAll(ATMScreen.#SO_GLITCH,  ATMScreen.#SO); //0e
	 	 screen = screen.replaceAll(ATMScreen.#ESC_GLITCH, ATMScreen.#ESC); //1b
		 
	 	 let cy = '@';
	 	 let cx = '@';
	 	 let y = this.c2i(cy);
	 	 let x = this.c2i(cx);
	     let position = (y * ATMScreen.#cols) + x;
		 
	 	 let i = 0;
         while (i < screen.length)
	 	 {
	 	    if (screen[i] === ATMScreen.#FF)         // FF
	 	 	{
	 	 		i++;
	 	 	    cy = '@';
	 	 	    cy = '@';
	 	        y = this.c2i(cy);
	 	        x = this.c2i(cx);
	            position = (y * ATMScreen.#cols) + x;
	 	 	}
	 	    else if (screen[i] === ATMScreen.#SI)    // SI
	 	 	{
	 	 		i++;
	 	 	    cy  = screen[i];
	 	 		i++;
	 	 	    cx  = screen[i];
	 	 		i++;
	 	         y = this.c2i(cy);
	 	         x = this.c2i(cx);
	              position = (y * ATMScreen.#cols) + x;
	 	 	}
	 	    else if (screen[i] === ATMScreen.#SO)    // SO
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
                if (! this.isNumeric(screenNum) )
	 	 		{
	 	 			// Image Name Extension .jpg, .pcx, etc
	 	 			// while (isPrintable(screen[i]))
	 	 			// {
	 	 		    //     screenNum += screen[i]
                    //     i++;
	 	 			// }
                    this.writeImg2Screen(this.getCy(position), this.getCx(position), screenNum);
	 	 			continue;
	 	 		}
		 
	 	 		// Buscar La Pantalla
	 	 		let screenId = 0;
                $.each(this.screens, function(i, val)
	            {
	 	 		    if (val['scr_number'] === screenNum)
	 	 		        screenId = i;
                });					    
	 	 		
	 	 		if (screenId !== 0)
	 	 		{
	 	 		    scr = this.screens[screenId];
	 	 		    this.putScreen(scr['scr_data']);
	 	 		}
	 	 	}
	 	    else if (screen[i] === ATMScreen.#ESC)    // ESC
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
                          if (! this.isNumeric(screenNum) )
	 	 		          {
	 	 				      // Image Name Extension .jpg, .pcx, etc
	 	 				      while (this.isPrintable(screen[i]))
	 	 				      {
	 	 		                    screenNum += screen[i]
                                      i++;
	 	 				      }
                              this.writeImg2Screen(this.getCy(position), this.getCx(position), screenNum);
	 	 		              continue;
	 	 		          }
	 	 		        
	 	 		          // Buscar La Pantalla
	 	 		          let screenId = 0;
                          $.each(this.screens, function(i, val)
	                      {
	 	 		              if (val['scr_number'] === screenNum)
	 	 		                  screenId = i;
                          });					    
	 	 		          
	 	 		          if (screenId !== 0)
	 	 		          {
	 	 		              scr = this.screens[screenId];
	 	 		              this.putScreen(scr['scr_data']);
	 	 		          }

	 	 			}
	 	 		}
	 	 		else
	 	 		{
	 	 			let chars = ATMScreen.#FF + ATMScreen.#SI + ATMScreen.#SO + ATMScreen.#ESC;
	 	 			while ( chars.indexOf(screen[i]) < 0)
	 	 				i++;
	 	 		}
	 	 	}
	 	 	else
	 	 	{
	 	 	    this.writeChr2Screen(position, screen[i]);
	 	 	    position++;
	 	 	    i++;
	 	 	}
		 
	 	 }
	 	 return;
     }

	 /*
	 	- Get Screen comands
	 */
	 getScreen() 
	 {
         let cmds = ATMScreen.#FF;
	 	 for (var y = 0; y < ATMScreen.#rows; y++)
	 	 {
	 	 	let str  = '';
	 	 	let beginX = -1;
	 	 	let beginY = 0;
	 	    for (var x = 0; x < ATMScreen.#cols; x++) 
	 	 	{
	 	 		let p = (y * ATMScreen.#cols) + x;
		 
	 	        let currVal = $('#' + p)
	 	         	.map(function() 
	 	 	    	{
	 	           		return $(this).val();
	 	         	})
	 	         	.get()
	 	         	.join('');
		 
                  str += currVal;				
		 
	 	         // console.log("JFRD Line 469 currVal >" + currVal + "< >" + i + "< >" + i2c(x) + "< x >" + x + "< cols >" + cols + "<");
	 	 	
	 	 		if (x === (ATMScreen.#cols - 1))
	 	 		{
	 	 			if (str.trim().length > 0)
	 	 			{
	 	 				let imageCmd = '';
	 	 				let imageIndex = 0;
	 	                for (var i = 0; i < ATMScreen.#cols; i++)
	 	 				{
	 	 				    let imageSrc = $('#img' + this.imageSuffix(beginY, i) ).attr('src');
	 	                    if (typeof(imageSrc) != "undefined")
	 	 					{
	 	 						let lastIndexOf = 0;
	 	 						let wrkStr = imageSrc;
	 	 		                while (wrkStr.indexOf('/') > -1)
	 	 						{
	 	 							lastIndexOf = wrkStr.indexOf('/') + 1;
	 	 							wrkStr = wrkStr.substring(lastIndexOf , wrkStr.indexOf('.') + 1);
	 	 						}
		 
	 	 						let imageName = wrkStr.substring(0, wrkStr.indexOf('.') )
	 	 						imageCmd = ATMScreen.#SI + this.i2c(beginY) + this.i2c(i) + ATMScreen.#SO + imageName;
	 	 						imageIndex = i;
	 	 						
	 	 						break;
	 	 					}
	 	 				}
		 
                          let line = ATMScreen.#SI + this.i2c(beginY) + this.i2c(beginX) + str.trim();
		 
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
	 	 	// break;
	 	 }
	 	 if (typeof(activePosition) != "undefined")
	 	 {
	 	     let position = parseInt(activePosition);
	 	     let y = Math.floor( position / ATMScreen.#cols);
	 	     let x = position % ATMScreen.#cols;
	 	     cmds += ATMScreen.#SI + this.i2c(y) + this.i2c(x);
	 	 }
		 
	 	 cmds = cmds.replaceAll(ATMScreen.#FF,  ATMScreen.#FF_GLITCH);  //0c
	 	 cmds = cmds.replaceAll(ATMScreen.#SI,  ATMScreen.#SI_GLITCH);  //0f
	 	 cmds = cmds.replaceAll(ATMScreen.#SO,  ATMScreen.#SO_GLITCH);  //0e
	 	 cmds = cmds.replaceAll(ATMScreen.#ESC, ATMScreen.#ESC_GLITCH); //1b
		 
	 	 return cmds;
	 }

     keyupHandler(e)
	 {
		let screen = e.data.screen;  // para poder usarlo en otro contexto (Toda La Funcion)
	 	screen.mode = 'interacting';
	 	
	 	// need to figure out orientation up front, before we attempt to highlight an entry
	 	switch(e.which) 
	 	{
	 	    case 39:
	 		case 37:
	 			screen.currOri = 'across';
	 			break;
	 		case 38:
	 		case 40:
	 			screen.currOri = 'down';
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
	 			screen.currOri === 'across' ? screen.nav.nextPrevNav(e, 37) : screen.nav.nextPrevNav(e, 38); 
	 		} else {
	 			screen.nav.nextPrevNav(e);
	 		}
	 		
	 		e.preventDefault();
	 		return false;
	 	} 
	 	else 
	 	{
	 		// console.log('Line 787 input keyup: ');
	 		screen.checkAnswer(e);
	 	}
	 
	 	e.preventDefault();
	 	return false;					
	 }
	 
     keydownHandler(e)
	 {
		let screen = e.data.screen;  // para poder usarlo en otro contexto (Toda La Funcion)
	 	if ( e.keyCode === 9) {
	 		
	 		mode = "setting ui";
	 		// if (solvedToggle) solvedToggle = false;
	 
	 		screen.checkAnswer(e)
	 		screen.nav.updateByEntry(e);
	 		
	 	} 
		else 
		{
	 		return true;
	 	}
	 							
	 	e.preventDefault();
	 }

     clickHandler(e)
	 {
		let screen = e.data.screen;  // para poder usarlo en otro contexto (Toda La Funcion)
	 	this.mode = "setting ui";

	 	screen.nav.updateByEntry(e);
	 	e.preventDefault();
	 }

     mostrarHandler(e)
	 {
		let screen = e.data.screen;  // para poder usarlo en otro contexto (Toda La Funcion)
	 	let txt = $("#cmdTxtArea").val();
	    screen.putScreen(txt);
     }

     capturarHandler(e)
	 {
		let screen = e.data.screen;  // para poder usarlo en otro contexto (Toda La Funcion)
	 	$("#cmdTxtArea").val( screen.getScreen() );
     }

     showImg()
	 {
         if (this.checked) 
	 		$('table.screen td').css({"z-index":"1"});
	 	else
	 		$('table.screen td').css({"z-index":"3"});
     }

     LoadConfigIdSelect(configId) 
	 {
         $.ajax(
         {
	     	 url: this.baseURL + '/atmconfigs/unique',
             method: "GET",
             headers: {
               Authorization: 'Bearer ' + this.jwt
             },
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

     loadScreensList(configId) 
	 {
		 screen = this;  // para poder usarlo en otro contexto (ajax)
		 this.configId = configId;

         $.ajax(
         {
	     	 url: screen.baseURL + '/screens/' + this.configId,
             method: "GET",
             headers: {
               Authorization: 'Bearer ' + screen.jwt
             },
             success: function(data)
	         {
                 screen.screens = JSON.parse( JSON.stringify(data) );
	 			 $(ATMScreen.#SCREEN_LIST_DIV_ID).empty();
                 $.each(screen.screens, function(i, val)
	             {
	     	        val['scr_data'] = val['scr_data'].replaceAll(ATMScreen.#FF,  ATMScreen.#FF_GLITCH);
	     	        val['scr_data'] = val['scr_data'].replaceAll(ATMScreen.#SI,  ATMScreen.#SI_GLITCH);
	     	        val['scr_data'] = val['scr_data'].replaceAll(ATMScreen.#SO,  ATMScreen.#SO_GLITCH);
	     	        val['scr_data'] = val['scr_data'].replaceAll(ATMScreen.#ESC, ATMScreen.#ESC_GLITCH);
	     
	             	let optionStr = '<option value="' + i + '">' + val['scr_number'] + ' | ' + val['scr_desc'] + '</option>'
                    $(ATMScreen.#SCREEN_LIST_DIV_ID).append(optionStr);
                 });
                 screen.clearScreen();
	             screen.putScreen($("#cmdTxtArea").val());
	 			 $(ATMScreen.#SCREEN_LIST_DIV_ID).prop('selectedIndex',-1);
	 	         $(ATMScreen.#SCREEN_LIST_DIV_ID).focus();
				 window.scrollTo(0, 0);
             }
         });
	 }

     screenListChange(event)
     {
		 screen = event.data.screen;  // para poder usarlo en otro contexto (Toda La Funcion)
		 let id = "#screenList :selected";
		 let index = parseInt( $(id).attr('value') );
		 let scr = screen.screens[ index ];
		 
		 if (scr != null)
		 {
	 	     $('#cmdTxtArea').val( scr['scr_data'] );
	 	     $('#scr_number').val( scr['scr_number'] );
	 	     $('#scr_desc').val( scr['scr_desc'] );
		     
			 
	 	     if (scr['scr_data'].length > 0)
	             screen.putScreen(scr['scr_data'] );

		 }
		 
	 	 $(ATMScreen.#SCREEN_LIST_DIV_ID).focus();
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
		let screen = e.data.screen;  // para poder usarlo en otro contexto (Toda La Funcion)
	 	let action = document.activeElement['value'];      // Value of the submit button clicked
	 	let formData = $(this).formToJson();
	 
	 	formData['scr_data'] = screen.getScreen();         // Actualizar scr_data a partir del dibujo que esta en la Pantalla
	 
        Object.keys(formData).forEach(key =>               // Eliminar los Campos que no son parte del Objeto Screen
	 	{
	 	    if (! key.startsWith("scr_"))
	 	        delete formData[key];
        });

        e.preventDefault();
	 
        $.ajax(
		{
	       url: screen.baseURL + '/screens/' + action,    // url where to submit the request
           type : "POST",                                 // type of action POST || GET
           headers: {
             Authorization: 'Bearer ' + screen.jwt
           },
	 	   contentType: 'application/json;charset=utf-8',
           dataType : 'json',                             // data type
           data : JSON.stringify( formData ),             // post data || get data
           success : function(resultObj) 
	 		{
	 		   $('#ResponseMsg').empty();
	 		   let msg = "Success : " + resultObj["msg"];
	 		   $('#ResponseMsg').append( screen.createDismissableMsg(msg, "success") );
			   screen.configId = formData["scr_config_id"];
	    	   screen.loadScreensList(formData["scr_config_id"]);
	 	       $('#cmdTxtArea').val('');
	 	       $('#scr_number').val('');
	 	       $('#scr_desc').val('');
           },
           error: function(xhr, resp, text) 
	 		{
	 			let resultObj = JSON.parse(xhr.responseText);
	 			$('#ResponseMsg').empty();
	 			let msg = "Error : " + resultObj["msg"];
	 			$('#ResponseMsg').append( screen.createDismissableMsg(msg, "danger") );
           }
        });
	 
     }
	 
	 configIdChange(e)
	 {
	    let screen   = e.data.screen;
	    let configId = this.value;

		if (configId.length > 0)
		{
	 	    screen.loadScreensList(configId);
		    Cookies.set('configId', configId)
		}
		else
	    {
	        configId = Cookies.get('configId');
			if ( typeof(configId) != 'undefined' )
	 	        screen.loadScreensList(configId);
		}			
     }

     init() 
	 {
	     console.log('jquery.atm.screen.js Line 995 ' + this.constructor.name );

		 this.buildTable();

	     $(ATMScreen.#ATM_SCREEN_DIV_ID).delegate('input', 'keyup', {screen: this}, this.keyupHandler );
	     $(ATMScreen.#ATM_SCREEN_DIV_ID).delegate('input', 'keydown', {screen: this}, this. keydownHandler );
	     $(ATMScreen.#ATM_SCREEN_DIV_ID).delegate('input', 'click', {screen: this}, this.clickHandler );
         $("#Mostrar").click( {screen: this}, this.mostrarHandler );
         $("#Capturar").click( {screen: this}, this.capturarHandler );
         $('input[type="checkbox"]').change( {screen: this}, this.showImg );
         $("#config_id").change( {screen: this}, this.configIdChange );

	     let configId = Cookies.get('configId');
		 if ( typeof(configId) == 'undefined' )
			 configId = "";

	     this.LoadConfigIdSelect( configId );

         $(ATMScreen.#SCREEN_LIST_DIV_ID).change( {screen: this}, this.screenListChange );
         $("#screensForm").submit( {screen: this}, this.handleSubmit );
	 }
	 /***************************************************************************************************
	  * ATMScreen - Ends
	  ***************************************************************************************************/
}
