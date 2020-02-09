// kladr.js
	function changeKladrCode(obj, objCodeId)
	{
		var objCode = document.getElementById(objCodeId);
		var code = obj.value;
		if (code == null) return;
		
		switch (code.length){
			case 2: objCode.value = ""; break;
			case 5: objCode.value = code.substring(3); break;
			case 8: objCode.value = code.substring(5); break;
			case 11: objCode.value = code.substring(8); break;
			case 14: objCode.value = code.substring(11); break;
			case 18: objCode.value = code.substring(14); break;
		}
	}
	

	function changeKladrEditableCode(obj, objCodeId)
	{
		var objCode = document.getElementById(objCodeId);
		if (obj.value == null)
			return;
		var values = obj.value.split("|");
		var code;
		if (values.length>1)
		{
			code = values[1];
			switch (code.length){
				case 2: objCode.value = ""; break;
				case 5: objCode.value = code.substring(3); break;
				case 8: objCode.value = code.substring(5); break;
				case 11: objCode.value = code.substring(8); break;
				case 14: objCode.value = code.substring(11); break;
				case 18: objCode.value = code.substring(14); break;
				}
			var regex = new RegExp(/^0*$/);
			if (objCode.value != null && regex.test(objCode.value))
				objCode.value = '';
		}
	}

	function changeSelectKladr(parent, parentCode, childEl, childArray){
		//onChangeFindField(parent);
		//if (childEl.indexOf('Ur')>=0)
			//clearChildText('Ur');
		//else
			//clearChildText('Real');
		var selEl = getElementByName(childEl);		
		var prevSelectedIndex = selEl.selectedIndex;
		var objCode = document.getElementById(parentCode);
		if(selEl!=null){
			if (selEl.tagName=='SELECT'){
				var parentVal = parent.options[parent.selectedIndex].value;		
				switch (parentVal.length){
					case 2: objCode.value = ""; break; //valueCode="-1"
					//case 3: objCode.value = parseInt(parentVal); break;
					//case 5: objCode.value = parseInt(parentVal.substring(3)); break;
					//case 8: objCode.value = parseInt(parentVal.substring(5)); break;
					//case 11: objCode.value = parseInt(parentVal.substring(8)); break;
					//case 14: objCode.value = parseInt(parentVal.substring(11)); break;
					//case 18: objCode.value = parseInt(parentVal.substring(14)); break;
					case 5: objCode.value = parentVal.substring(3); break;
					case 8: objCode.value = parentVal.substring(5); break;
					case 11: objCode.value = parentVal.substring(8); break;
					case 14: objCode.value = parentVal.substring(11); break;
					case 18: objCode.value = parentVal.substring(14); break;
				}
				clearSel(selEl);
//				selEl.onchange();
				if (parentVal!=-1){
					for (var i=0; i<childArray.length; i++) 
						if (childArray[i][1].indexOf(parentVal)==0)
							selEl.options.add(new Option(childArray[i][0], childArray[i][1]));
					if (selEl.length>1){							
						var lenEnd = selEl.options[1].value.length - parentVal.length;
						selEl.options[0].value = parentVal+"00000".substring(0, lenEnd);
						if (prevSelectedIndex)
							selEl.selectedIndex=prevSelectedIndex;
						}
					
				}
				selEl.onchange();
			}
			if (selEl.tagName=='INPUT'){
				var parentVal = null;
				if (parent.tagName=='SELECT')
					parentVal = parent.selectedIndex<0 ? null : parent.options[parent.selectedIndex].value;
				else parentVal=selEl.value;
				if (parentVal!=null)
					selEl.value='|'+parentVal+'000';
				switch (parentVal.length){
					case 2: objCode.value = ""; break;
					case 5: objCode.value = parentVal.substring(3); break;
					case 8: objCode.value = parentVal.substring(5); break;
					case 11: objCode.value = parentVal.substring(8); break;
					case 14: objCode.value = parentVal.substring(11); break;
					case 18: objCode.value = parentVal.substring(14); break;
				}				
				//var selElText =  getElementByName(childEl.substring(0,childEl.indexOf('|'))+')');
				//if (selElText!=null){
				//	selElText.value='';					
				//	selElText.onkeyup();						
				//}
			}
		}		
		
		return true;
	}
	
	function onChangeFindField(sel){
		var findField = document.getElementById("findField_"+sel.name);
		if (findField!=null)
			findField.value = sel.options[sel.selectedIndex].text;
	}

	function changeTextKladr(nameText, hiddenCode, hiddenText, childText, childHiddenText) {
		var objName = document.getElementById("id("+nameText+")");
		var objChildNameHidden = document.getElementById("id("+childHiddenText+")");
		var objChildName = document.getElementById("id("+childText+")");
		var objNameHidden = document.getElementById("id("+hiddenText+")");
		var objCodeHidden = document.getElementById("id("+hiddenCode+")");
		
		var valueName = '';
		
		if (objName!=null)
			valueName = objName.value; 
				
		if (objNameHidden!=null){
			if (objNameHidden.value.indexOf('|')>=0)
				objNameHidden.value=valueName+objNameHidden.value.substring(objNameHidden.value.indexOf('|'));
			else if (objNameHidden.value.indexOf('|')<0)
				objNameHidden.value=valueName+'|-1';  
		}	
		
		if (objNameHidden!=null){
			if (objNameHidden.value.indexOf('|')>=0){
				var valueCode = objNameHidden.value.substring(objNameHidden.value.indexOf('|')+1);				
				//if (nameText.indexOf("punkt")>=0)
					//alert(nameText+"="+valueCode)					
				switch (valueCode.length){
					case 2: valueCode = "-1"; break; //valueCode="-1"
					//case 3: valueCode = parseInt(trim0(valueCode)); break;
					//case 5: valueCode = parseInt(trim0(valueCode.substring(3))); break;
					//case 8: valueCode = parseInt(trim0(valueCode.substring(5))); break;
					//case 11: valueCode = parseInt(trim0(valueCode.substring(8))); break;
					//case 14: valueCode = parseInt(trim0(valueCode.substring(11))); break;
					//case 18: valueCode = parseInt(trim0(valueCode.substring(14))); break;
					case 5: valueCode = valueCode.substring(3); break;
					case 8: valueCode = valueCode.substring(5); break;
					case 11: valueCode = valueCode.substring(8); break;
					case 14: valueCode = valueCode.substring(11); break;
					case 18: valueCode = valueCode.substring(14); break;
				}
				objCodeHidden.value=valueName.length==0 ? "" : valueCode;
			}
			else
				objCodeHidden.value="-1";  
		}	
		//alert(objCodeHidden.value)

		if (objChildName!=null){
			objChildName.value='';
			//objChildName.onchange();
			}
			
		if (objChildNameHidden!=null){
			objChildNameHidden.value='|-1';	
			}	
		//alert("value="+valueName+"\nhidden="+objNameHidden.value+"\nchild="+objChildName.value+"\nchildh="+objChildNameHidden.value)				
    }
    
    
    function clearChildText(typeAddress){
    	var	cu = getElementByName('component(area'+typeAddress+'Text)');
		if (cu!=null)
			cu.value="";
		
		cu = getElementByName('component(city'+typeAddress+'Text)');
		if (cu!=null)
			cu.value="";

		cu = getElementByName('component(punkt'+typeAddress+'Text)');
		if (cu!=null)
			cu.value="";
		
		cu = getElementByName('component(street'+typeAddress+'Text)');
		if (cu!=null)
			cu.value="";

		cu = getElementByName('component(home'+typeAddress+'Text)');
		if (cu!=null)
			cu.value="";
		
		cu = getElementByName('component(area'+typeAddress+'Text|area'+typeAddress+'Button)');
		if (cu!=null)
			cu.value="";
			
		cu = getElementByName('component(city'+typeAddress+'Text|city'+typeAddress+'Button)');
		if (cu!=null)
			cu.value="";
		
		cu = getElementByName('component(punkt'+typeAddress+'Text|punkt'+typeAddress+'Button)');
		if (cu!=null)
			cu.value="";
		
		cu = getElementByName('component(street'+typeAddress+'Text|street'+typeAddress+'Button)');
		if (cu!=null)
			cu.value="";

		cu = getElementByName('component(home'+typeAddress+'Text|home'+typeAddress+'Button)');
		if (cu!=null)
			cu.value="";

		cu = getElementByName('component(corp'+typeAddress+')');
		if (cu!=null)
			cu.value="";

		cu = getElementByName('component(flat'+typeAddress+')');
		if (cu!=null)
			cu.value="";    	
    }
    
    function copyAddress(){
		var cu = getElementByName('component(indexUr)');
		var cr = getElementByName('component(indexReal)');
		if (cu!=null){
			cr.value=cu.value;
			//createHiddenElement(cr,'indexReal')
		}
		
		var cu = getElementByName('component(indexUrText)');
		var cr = getElementByName('component(indexRealText)');
		if (cu!=null){
			cr.value=cu.value;
			//createHiddenElement(cr,'indexReal')
		}

		cu = getElementByName('component(countryUr)');
		cr = getElementByName('component(countryReal)');
		if (cu!=null){
			cr.value=cu.value;
        	cr.onchange();
		}
		cu = getElementByName('component(countryUrText)');
		cr = getElementByName('component(countryRealText)');
		if (cu!=null){
			cr.value=cu.value;
		}
		cu = getElementByName('component(regionUr)');
		cr = getElementByName('component(regionReal)');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
			//onChangeFindField(cr);
        	//cr.onchange();
		}
		cu = getElementByName('component(regionUrText)');
		cr = getElementByName('component(regionRealText)');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
		}

		cu = getElementByName('component(areaUrText)');
		cr = getElementByName('component(areaRealText)');
		if (cu!=null){
			cr.value=cu.value;
	       	//cr.onchange();
		}
		cu = getElementByName('component(cityUrText)');
		cr = getElementByName('component(cityRealText)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
			}

		cu = getElementByName('component(punktUrText)');
		cr = getElementByName('component(punktRealText)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
		}
		
		cu = getElementByName('component(streetUrText)');
		cr = getElementByName('component(streetRealText)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
		}

		cu = getElementByName('component(homeUrText)');
		cr = getElementByName('component(homeRealText)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
		}
		
		cu = getElementByName('component(areaUrText|areaUrButton)');
		cr = getElementByName('component(areaRealText|areaRealButton)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
			//createHiddenElement(cr,'areaRealText|areaRealButton');
		}
			
		cu = getElementByName('component(cityUrText|cityUrButton)');
		cr = getElementByName('component(cityRealText|cityRealButton)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
			//createHiddenElement(cr,'cityRealText|cityRealButton');
		}
		
		cu = getElementByName('component(punktUrText|punktUrButton)');
		cr = getElementByName('component(punktRealText|punktRealButton)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
			//createHiddenElement(cr,'punktRealText|punktRealButton');
		}
		
		cu = getElementByName('component(streetUrText|streetUrButton)');
		cr = getElementByName('component(streetRealText|streetRealButton)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
			//createHiddenElement(cr,'streetRealText|streetRealButton');
		}

		cu = getElementByName('component(homeUrText|homeUrButton)');
		cr = getElementByName('component(homeRealText|homeRealButton)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
			//createHiddenElement(cr,'homeRealText|homeRealButton');
		}
		
		cu = getElementByName('component(areaUrTextCode)');
		cr = getElementByName('component(areaRealTextCode)');
		if (cu!=null)
			cr.value=cu.value;

		cu = getElementByName('component(cityUrTextCode)');
		cr = getElementByName('component(cityRealTextCode)');
		if (cu!=null)
			cr.value=cu.value;

		cu = getElementByName('component(punktUrTextCode)');
		cr = getElementByName('component(punktRealTextCode)');
		if (cu!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component(streetUrTextCode)');
		cr = getElementByName('component(streetRealTextCode)');
		if (cu!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component(corpUr)');
		cr = getElementByName('component(corpReal)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
			//createHiddenElement(cr,'corpReal');
		}

		cu = getElementByName('component(flatUr)');
		cr = getElementByName('component(flatReal)');
		if (cu!=null){
			cr.value=cu.value;
			//cr.onchange();
			//createHiddenElement(cr,'flatReal');
		}
	}
	
    function copyAddressRealToUr(){
		var cu = getElementByName('component(indexUr)');
		var cr = getElementByName('component(indexReal)');
		if (cr!=null){
			cu.value=cr.value;
			//createHiddenElement(cr,'indexReal')
		}
		
		var cu = getElementByName('component(indexUrText)');
		var cr = getElementByName('component(indexRealText)');
		if (cr!=null){
			cu.value=cr.value;
			//createHiddenElement(cr,'indexReal')
		}

		cu = getElementByName('component(countryUr)');
		cr = getElementByName('component(countryReal)');
		if (cr!=null){
			cu.value=cr.value;
        	cu.onchange();
		}
		
		cu = getElementByName('component(countryUrText)');
		cr = getElementByName('component(countryRealText)');
		if (cr!=null && cu != null){
			cu.value=cr.value;
		}
		
		cu = getElementByName('component(regionUr)');
		cr = getElementByName('component(regionReal)');
		if (cr!=null){
			cu.value=cr.value;
		}
		
		cu = getElementByName('component(regionUrText)');
		cr = getElementByName('component(regionRealText)');
		if (cr!=null && cu != null){
			cu.value=cr.value;
		}

		cu = getElementByName('component(areaUrText)');
		cr = getElementByName('component(areaRealText)');
		if (cr!=null){
			cu.value=cr.value;
	       	//cr.onchange();
		}
		cu = getElementByName('component(cityUrText)');
		cr = getElementByName('component(cityRealText)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
			}

		cu = getElementByName('component(punktUrText)');
		cr = getElementByName('component(punktRealText)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
		}
		
		cu = getElementByName('component(streetUrText)');
		cr = getElementByName('component(streetRealText)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
		}

		cu = getElementByName('component(homeUrText)');
		cr = getElementByName('component(homeRealText)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
		}
		
		cu = getElementByName('component(areaUrText|areaUrButton)');
		cr = getElementByName('component(areaRealText|areaRealButton)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
			//createHiddenElement(cr,'areaRealText|areaRealButton');
		}
			
		cu = getElementByName('component(cityUrText|cityUrButton)');
		cr = getElementByName('component(cityRealText|cityRealButton)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
			//createHiddenElement(cr,'cityRealText|cityRealButton');
		}
		
		cu = getElementByName('component(punktUrText|punktUrButton)');
		cr = getElementByName('component(punktRealText|punktRealButton)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
			//createHiddenElement(cr,'punktRealText|punktRealButton');
		}
		
		cu = getElementByName('component(streetUrText|streetUrButton)');
		cr = getElementByName('component(streetRealText|streetRealButton)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
			//createHiddenElement(cr,'streetRealText|streetRealButton');
		}

		cu = getElementByName('component(homeUrText|homeUrButton)');
		cr = getElementByName('component(homeRealText|homeRealButton)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
			//createHiddenElement(cr,'homeRealText|homeRealButton');
		}
		
		cu = getElementByName('component(areaUrTextCode)');
		cr = getElementByName('component(areaRealTextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component(cityUrTextCode)');
		cr = getElementByName('component(cityRealTextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component(punktUrTextCode)');
		cr = getElementByName('component(punktRealTextCode)');
		if (cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component(streetUrTextCode)');
		cr = getElementByName('component(streetRealTextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component(corpUr)');
		cr = getElementByName('component(corpReal)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
			//createHiddenElement(cr,'corpReal');
		}

		cu = getElementByName('component(flatUr)');
		cr = getElementByName('component(flatReal)');
		if (cr!=null){
			cu.value=cr.value;
			//cr.onchange();
			//createHiddenElement(cr,'flatReal');
		}
	}	
	
function findMenuEnter(findField){
 var sel = document.getElementsByName(findField.id.substring(findField.id.indexOf("_")+1));
 var selectMain = (sel!=null && sel.length>0) ? sel[0] : null;
//circle rollup by arrow buttons
 if (event.keyCode==38){//up arrow
  selectMain.selectedIndex--;
  if (selectMain.selectedIndex == -1)
  	selectMain.selectedIndex = selectMain.length-1;
  findField.value = selectMain.options[selectMain.selectedIndex].text;
 }
 else if (event.keyCode==40){//down arrow
  selectMain.selectedIndex++;
  if (selectMain.selectedIndex == -1 || selectMain.selectedIndex >selectMain.length)
  	selectMain.selectedIndex = 0;  
  findField.value = selectMain.options[selectMain.selectedIndex].text;  	
 }
 else{
  for (var i=0; i< selectMain.length; i++){
    	if (selectMain.options[i].text.toLowerCase().indexOf(findField.value.toLowerCase())==0){
    		selectMain.selectedIndex = i; 		   		  	
 	    	break;
 	    	}
     }
 }

 if (event.keyCode!=46 && event.keyCode!=8 && event.keyCode!=37 && event.keyCode!=39 && event.keyCode!=16){
	 var textInput = findField.value;
	 var textInputLen = textInput.length;
	 var selectValue = selectMain.options[selectMain.selectedIndex].value;
	 if (selectMain.selectedIndex>0){
		 findField.value = selectMain.options[selectMain.selectedIndex].text;
		 selectMain.onchange();
		 }
	 
	 findField.focus();
	 if (event.keyCode!=46 && event.keyCode!=8)
		 if (selectMain.selectedIndex>0 && textInputLen<findField.value.length){
		   var range = findField.createTextRange();
		   range.collapse(true);
		   range.findText(findField.value.substring(textInputLen));
		   range.select();
		 }
 }

 findField.focus();

 if (event.keyCode==13)
  findField.value = selectMain.options[selectMain.selectedIndex].text;   
 
 if (event.keyCode==27)
  findField.value = ''; 
}	

function copyAddressS(suff, n){
		if (suff == null)
   		 suff = '';
		
		if (n == null)
			n = '';
		var cu = getElementByName('component('+suff+'IndexUr'+n+')');
		var cr = getElementByName('component('+suff+'IndexReal'+n+')');
		if (cu!=null && cr!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'CountryUr'+n+')');
		cr = getElementByName('component('+suff+'CountryReal'+n+')');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
		}
		
		cu = getElementByName('component('+suff+'CountryUr'+n+'Text)');
		cr = getElementByName('component('+suff+'CountryReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
		}
		
		cu = getElementByName('component('+suff+'RegionUr'+n+')');
		cr = getElementByName('component('+suff+'RegionReal'+n+')');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
		}
		
		cu = getElementByName('component('+suff+'RegionUr'+n+'Text)');
		cr = getElementByName('component('+suff+'RegionReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
		}

		cu = getElementByName('component('+suff+'AreaUr'+n+'Text)');
		cr = getElementByName('component('+suff+'AreaReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
			//cr.onchange();
		}
			
		cu = getElementByName('component('+suff+'CityUr'+n+'Text)');
		cr = getElementByName('component('+suff+'CityReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'PunktUr'+n+'Text)');
		cr = getElementByName('component('+suff+'PunktReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'StreetUr'+n+'Text)');
		cr = getElementByName('component('+suff+'StreetReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'HomeUr'+n+'Text)');
		cr = getElementByName('component('+suff+'HomeReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'AreaUr'+n+'Text|'+suff+'AreaUr'+n+'Button)');
		cr = getElementByName('component('+suff+'AreaReal'+n+'Text|'+suff+'AreaReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'CityUr'+n+'Text|'+suff+'CityUr'+n+'Button)');
		cr = getElementByName('component('+suff+'CityReal'+n+'Text|'+suff+'CityReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'PunktUr'+n+'Text|'+suff+'PunktUr'+n+'Button)');
		cr = getElementByName('component('+suff+'PunktReal'+n+'Text|'+suff+'PunktReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'StreetUr'+n+'Text|'+suff+'StreetUr'+n+'Button)');
		cr = getElementByName('component('+suff+'StreetReal'+n+'Text|'+suff+'StreetReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'HomeUr'+n+'Text|'+suff+'HomeUr'+n+'Button)');
		cr = getElementByName('component('+suff+'HomeReal'+n+'Text|'+suff+'HomeReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'CorpUr'+n+')');
		cr = getElementByName('component('+suff+'CorpReal'+n+')');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'CountryUr'+n+'Code)');
		cr = getElementByName('component('+suff+'CountryReal'+n+'Code)');
		if (cu!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'RegionUr'+n+'Code)');
		cr = getElementByName('component('+suff+'RegionReal'+n+'Code)');
		if (cu!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'AreaUr'+n+'TextCode)');
		cr = getElementByName('component('+suff+'AreaReal'+n+'TextCode)');
		if (cu!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'cityUr'+n+'TextCode)');
		cr = getElementByName('component('+suff+'cityReal'+n+'TextCode)');
		if (cu!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'punktUr'+n+'TextCode)');
		cr = getElementByName('component('+suff+'punktReal'+n+'TextCode)');
		if (cu!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'streetUr'+n+'TextCode)');
		cr = getElementByName('component('+suff+'streetReal'+n+'TextCode)');
		if (cu!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'FlatUr'+n+')');
		cr = getElementByName('component('+suff+'FlatReal'+n+')');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
}

   function copyAddressRealToUrS(suff, n){
	   if (suff == null)
	   		suff = '';
	   
   		if (n == null)
   			n = '';
   		
		var cu = getElementByName('component('+suff+'IndexUr'+n+')');
		var cr = getElementByName('component('+suff+'IndexReal'+n+')');
		if (cu!=null && cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'CountryUr'+n+')');
		cr = getElementByName('component('+suff+'CountryReal'+n+')');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
        	//cu.onchange();
		}
		
		cu = getElementByName('component('+suff+'CountryUr'+n+'Text)');
		cr = getElementByName('component('+suff+'CountryReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
		}
		
		cu = getElementByName('component('+suff+'RegionUr'+n+')');
		cr = getElementByName('component('+suff+'RegionReal'+n+')');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
		}
		
		cu = getElementByName('component('+suff+'RegionUr'+n+'Text)');
		cr = getElementByName('component('+suff+'RegionReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
		}

		cu = getElementByName('component('+suff+'AreaUr'+n+'Text)');
		cr = getElementByName('component('+suff+'AreaReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
			//cu.onchange();
		}
			
		cu = getElementByName('component('+suff+'CityUr'+n+'Text)');
		cr = getElementByName('component('+suff+'CityReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'PunktUr'+n+'Text)');
		cr = getElementByName('component('+suff+'PunktReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'StreetUr'+n+'Text)');
		cr = getElementByName('component('+suff+'StreetReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'HomeUr'+n+'Text)');
		cr = getElementByName('component('+suff+'HomeReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'AreaUr'+n+'Text|'+suff+'AreaUr'+n+'Button)');
		cr = getElementByName('component('+suff+'AreaReal'+n+'Text|'+suff+'AreaReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'CityUr'+n+'Text|'+suff+'CityUr'+n+'Button)');
		cr = getElementByName('component('+suff+'CityReal'+n+'Text|'+suff+'CityReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'PunktUr'+n+'Text|'+suff+'PunktUr'+n+'Button)');
		cr = getElementByName('component('+suff+'PunktReal'+n+'Text|'+suff+'PunktReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'StreetUr'+n+'Text|'+suff+'StreetUr'+n+'Button)');
		cr = getElementByName('component('+suff+'StreetReal'+n+'Text|'+suff+'StreetReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'HomeUr'+n+'Text|'+suff+'HomeUr'+n+'Button)');
		cr = getElementByName('component('+suff+'HomeReal'+n+'Text|'+suff+'HomeReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'CountryUr'+n+'Code)');
		cr = getElementByName('component('+suff+'CountryReal'+n+'Code)');
		if (cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'RegionUr'+n+'Code)');
		cr = getElementByName('component('+suff+'RegionReal'+n+'Code)');
		if (cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'AreaUr'+n+'TextCode)');
		cr = getElementByName('component('+suff+'AreaReal'+n+'TextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'cityUr'+n+'TextCode)');
		cr = getElementByName('component('+suff+'cityReal'+n+'TextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'punktUr'+n+'TextCode)');
		cr = getElementByName('component('+suff+'punktReal'+n+'TextCode)');
		if (cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'streetUr'+n+'TextCode)');
		cr = getElementByName('component('+suff+'streetReal'+n+'TextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'CorpUr'+n+')');
		cr = getElementByName('component('+suff+'CorpReal'+n+')');
		if (cu!=null && cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'FlatUr'+n+')');
		cr = getElementByName('component('+suff+'FlatReal'+n+')');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
	}
   
   function copyAddressOldToOldReal(suff, n){
	   	if (suff == null)
	   		suff = '';
	   	
		if (n == null)
			n = '';
		var cu = getElementByName('component('+suff+'indexOld'+n+')');
		var cr = getElementByName('component('+suff+'indexOldReal'+n+')');
		if (cu!=null && cr!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'countryOld'+n+')');
		cr = getElementByName('component('+suff+'countryOldReal'+n+')');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
		}
		
		cu = getElementByName('component('+suff+'countryOld'+n+'Text)');
		cr = getElementByName('component('+suff+'countryOldReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
		}
		
		cu = getElementByName('component('+suff+'regionOld'+n+')');
		cr = getElementByName('component('+suff+'regionOldReal'+n+')');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
			//cr.onchange();
		}
		
		cu = getElementByName('component('+suff+'regionOld'+n+'Text)');
		cr = getElementByName('component('+suff+'regionOldReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
		}

		cu = getElementByName('component('+suff+'areaOld'+n+'Text)');
		cr = getElementByName('component('+suff+'areaOldReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cr.value=cu.value;
			//cr.onchange();
		}
			
		cu = getElementByName('component('+suff+'cityOld'+n+'Text)');
		cr = getElementByName('component('+suff+'cityOldReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'punktOld'+n+'Text)');
		cr = getElementByName('component('+suff+'punktOldReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'streetOld'+n+'Text)');
		cr = getElementByName('component('+suff+'streetOldReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'homeOld'+n+'Text)');
		cr = getElementByName('component('+suff+'homeOldReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'areaOld'+n+'Text|'+suff+'areaOld'+n+'Button)');
		cr = getElementByName('component('+suff+'areaOldReal'+n+'Text|'+suff+'areaOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'cityOld'+n+'Text|'+suff+'cityOld'+n+'Button)');
		cr = getElementByName('component('+suff+'cityOldReal'+n+'Text|'+suff+'cityOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'punktOld'+n+'Text|'+suff+'punktOld'+n+'Button)');
		cr = getElementByName('component('+suff+'punktOldReal'+n+'Text|'+suff+'punktOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'streetOld'+n+'Text|'+suff+'streetOld'+n+'Button)');
		cr = getElementByName('component('+suff+'streetOldReal'+n+'Text|'+suff+'streetOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'homeOld'+n+'Text|'+suff+'homeOld'+n+'Button)');
		cr = getElementByName('component('+suff+'homeOldReal'+n+'Text|'+suff+'homeOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'corpOld'+n+')');
		cr = getElementByName('component('+suff+'corpOldReal'+n+')');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'countryOld'+n+'Code)');
		cr = getElementByName('component('+suff+'countryOldReal'+n+'Code)');
		if (cu!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'regionOld'+n+'Code)');
		cr = getElementByName('component('+suff+'regionOldReal'+n+'Code)');
		if (cu!=null)
			cr.value=cu.value;
			
		cu = getElementByName('component('+suff+'areaOld'+n+'TextCode)');
		cr = getElementByName('component('+suff+'areaOldReal'+n+'TextCode)');
		if (cu!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'cityOld'+n+'TextCode)');
		cr = getElementByName('component('+suff+'cityOldReal'+n+'TextCode)');
		if (cu!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'punktOld'+n+'TextCode)');
		cr = getElementByName('component('+suff+'punktOldReal'+n+'TextCode)');
		if (cu!=null)
			cr.value=cu.value;
		
		cu = getElementByName('component('+suff+'streetOld'+n+'TextCode)');
		cr = getElementByName('component('+suff+'streetOldReal'+n+'TextCode)');
		if (cu!=null)
			cr.value=cu.value;

		cu = getElementByName('component('+suff+'flatOld'+n+')');
		cr = getElementByName('component('+suff+'flatOldReal'+n+')');
		if (cu!=null && cr!=null)
			cr.value=cu.value;
}

  function copyAddressOldRealToOld(suff, n){
	  	if (suff == null)
	   		suff = '';
	  
  		if (n == null)
  			n = '';
  		
		var cu = getElementByName('component('+suff+'indexOld'+n+')');
		var cr = getElementByName('component('+suff+'indexOldReal'+n+')');
		if (cu!=null && cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'countryOld'+n+')');
		cr = getElementByName('component('+suff+'countryOldReal'+n+')');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
		}
		
		cu = getElementByName('component('+suff+'countryOld'+n+'Text)');
		cr = getElementByName('component('+suff+'countryOldReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
		}
		
		cu = getElementByName('component('+suff+'regionOld'+n+')');
		cr = getElementByName('component('+suff+'regionOldReal'+n+')');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
		}
		
		cu = getElementByName('component('+suff+'regionOld'+n+'Text)');
		cr = getElementByName('component('+suff+'regionOldReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
		}

		cu = getElementByName('component('+suff+'areaOld'+n+'Text)');
		cr = getElementByName('component('+suff+'areaOldReal'+n+'Text)');
		if (cu!=null && cr!=null){
			cu.value=cr.value;
			//cu.onchange();
		}
			
		cu = getElementByName('component('+suff+'cityOld'+n+'Text)');
		cr = getElementByName('component('+suff+'cityOldReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'punktOld'+n+'Text)');
		cr = getElementByName('component('+suff+'punktOldReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'streetOld'+n+'Text)');
		cr = getElementByName('component('+suff+'streetOldReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'homeOld'+n+'Text)');
		cr = getElementByName('component('+suff+'homeOldReal'+n+'Text)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'areaOld'+n+'Text|'+suff+'areaOld'+n+'Button)');
		cr = getElementByName('component('+suff+'areaOldReal'+n+'Text|'+suff+'areaOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'cityOld'+n+'Text|'+suff+'cityOld'+n+'Button)');
		cr = getElementByName('component('+suff+'cityOldReal'+n+'Text|'+suff+'cityOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'punktOld'+n+'Text|'+suff+'punktOld'+n+'Button)');
		cr = getElementByName('component('+suff+'punktOldReal'+n+'Text|'+suff+'punktOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'streetOld'+n+'Text|'+suff+'streetOld'+n+'Button)');
		cr = getElementByName('component('+suff+'streetOldReal'+n+'Text|'+suff+'streetReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'homeOld'+n+'Text|'+suff+'homeOld'+n+'Button)');
		cr = getElementByName('component('+suff+'homeOldReal'+n+'Text|'+suff+'homeOldReal'+n+'Button)');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'countryOld'+n+'Code)');
		cr = getElementByName('component('+suff+'countryOldReal'+n+'Code)');
		if (cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'regionOld'+n+'Code)');
		cr = getElementByName('component('+suff+'regionOldReal'+n+'Code)');
		if (cr!=null)
			cu.value=cr.value;
			
		cu = getElementByName('component('+suff+'areaOld'+n+'TextCode)');
		cr = getElementByName('component('+suff+'areaOldReal'+n+'TextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'cityOld'+n+'TextCode)');
		cr = getElementByName('component('+suff+'cityOldReal'+n+'TextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'punktOld'+n+'TextCode)');
		cr = getElementByName('component('+suff+'punktOldReal'+n+'TextCode)');
		if (cr!=null)
			cu.value=cr.value;
		
		cu = getElementByName('component('+suff+'streetOld'+n+'TextCode)');
		cr = getElementByName('component('+suff+'streetOldReal'+n+'TextCode)');
		if (cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'corpOld'+n+')');
		cr = getElementByName('component('+suff+'corpOldReal'+n+')');
		if (cu!=null && cr!=null)
			cu.value=cr.value;

		cu = getElementByName('component('+suff+'flatOld'+n+')');
		cr = getElementByName('component('+suff+'flatOldReal'+n+')');
		if (cu!=null && cr!=null)
			cu.value=cr.value;
	}
	
	function copyValue(source, dest, change)
	{
		var s = getElementByName(source);
		var d = getElementByName(dest);
		if (s!=null && d!=null)
		{
			//window.console.log(source+' = '+s.value);
			d.value=s.value;
			//window.console.log(dest+' = '+d.value);
			if (change && d.onchange!=null)
				{
					d.onchange();
					//window.console.log('change');
				}
		}
		//else
		//	window.console.log(source+' = null');
	}
	
	function copyAddressUrS(suffSource, stuffDest){
		var s = '';
		var d = '';
		if (suffSource == '')
			s = 'component(indexUrText)';
			else
				s = 'component('+suffSource+'IndexUr)';
		d = 'component('+stuffDest+'IndexUr)';
		
		copyValue(s, d, false);

		s = 'component('+suffSource+'CountryUr)';
		d = 'component('+stuffDest+'CountryUr)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'CountryUrText)';
		d = 'component('+stuffDest+'CountryUrText)';
		if (s != null && d != null)
			copyValue(s, d, false);
		
		s = 'component('+suffSource+'RegionUr)';
		d = 'component('+stuffDest+'RegionUr)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'RegionUrText)';
		d = 'component('+stuffDest+'RegionUrText)';
		if (s != null && d != null)
			copyValue(s, d, false);

		s = 'component('+suffSource+'AreaUrText)';
		d = 'component('+stuffDest+'AreaUrText)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'CityUrText)';
		d = 'component('+stuffDest+'CityUrText)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'PunktUrText)';
		d = 'component('+stuffDest+'PunktUrText)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'StreetUrText)';
		d = 'component('+stuffDest+'StreetUrText)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'HomeUrText)';
		d = 'component('+stuffDest+'HomeUrText)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'AreaUrText|'+suffSource+'AreaUrButton)';
		d = 'component('+stuffDest+'AreaUrText|'+stuffDest+'AreaUrButton)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'CityUrText|'+suffSource+'CityUrButton)';
		d = 'component('+stuffDest+'CityUrText|'+stuffDest+'CityUrButton)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'PunktUrText|'+suffSource+'PunktUrButton)';
		d = 'component('+stuffDest+'PunktUrText|'+stuffDest+'PunktUrButton)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'StreetUrText|'+suffSource+'StreetUrButton)';
		d = 'component('+stuffDest+'StreetUrText|'+stuffDest+'StreetUrButton)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'HomeUrText|'+suffSource+'HomeUrButton)';
		d = 'component('+stuffDest+'HomeUrText|'+stuffDest+'HomeUrButton)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'CorpUr)';
		d = 'component('+stuffDest+'CorpUr)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'CountryUrCode)';
		d = 'component('+stuffDest+'CountryUrCode)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'RegionUrCode)';
		d = 'component('+stuffDest+'RegionUrCode)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'AreaUrTextCode)';
		d = 'component('+stuffDest+'AreaUrTextCode)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'cityUrTextCode)';
		d = 'component('+stuffDest+'cityUrTextCode)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'punktUrTextCode)';
		d = 'component('+stuffDest+'punktUrTextCode)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'streetUrTextCode)';
		d = 'component('+stuffDest+'streetUrTextCode)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'FlatUr)';
		d = 'component('+stuffDest+'FlatUr)';
		copyValue(s, d, false);
	}

	function copyAddressRealS(suffSource, stuffDest){
		var s = '';
		var d = '';
		if (suffSource == '')
			s = 'component(indexRealText)';
			else
				s = 'component('+suffSource+'IndexReal)';
		
		var d = 'component('+stuffDest+'IndexReal)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'CountryReal)';
		d = 'component('+stuffDest+'CountryReal)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'CountryRealText)';
		d = 'component('+stuffDest+'CountryRealText)';
		if (s != null && d != null)
			copyValue(s, d, false);
		
		s = 'component('+suffSource+'RegionReal)';
		d = 'component('+stuffDest+'RegionReal)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'RegionRealText)';
		d = 'component('+stuffDest+'RegionRealText)';
		if (s != null && d != null)
			copyValue(s, d, false);

		s = 'component('+suffSource+'AreaRealText)';
		d = 'component('+stuffDest+'AreaRealText)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'CityRealText)';
		d = 'component('+stuffDest+'CityRealText)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'PunktRealText)';
		d = 'component('+stuffDest+'PunktRealText)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'StreetRealText)';
		cr = 'component('+stuffDest+'StreetRealText)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'HomeRealText)';
		d = 'component('+stuffDest+'HomeRealText)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'AreaRealText|'+suffSource+'AreaRealButton)';
		d = 'component('+stuffDest+'AreaRealText|'+stuffDest+'AreaRealButton)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'CityRealText|'+suffSource+'CityRealButton)';
		d = 'component('+stuffDest+'CityRealText|'+stuffDest+'CityRealButton)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'PunktRealText|'+suffSource+'PunktRealButton)';
		d = 'component('+stuffDest+'PunktRealText|'+stuffDest+'PunktRealButton)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'StreetRealText|'+suffSource+'StreetRealButton)';
		d = 'component('+stuffDest+'StreetRealText|'+stuffDest+'StreetRealButton)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'HomeRealText|'+suffSource+'HomeRealButton)';
		d = 'component('+stuffDest+'HomeRealText|'+stuffDest+'HomeRealButton)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'CountryRealCode)';
		d = 'component('+stuffDest+'CountryRealCode)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'RegionRealCode)';
		d = 'component('+stuffDest+'RegionRealCode)';
		copyValue(s, d, false);
			
		s = 'component('+suffSource+'AreaRealTextCode)';
		d = 'component('+stuffDest+'AreaRealTextCode)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'cityRealTextCode)';
		d = 'component('+stuffDest+'cityRealTextCode)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'punktRealTextCode)';
		d = 'component('+stuffDest+'punktRealTextCode)';
		copyValue(s, d, false);
		
		s = 'component('+suffSource+'streetRealTextCode)';
		d = 'component('+stuffDest+'streetRealTextCode)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'CorpReal)';
		d = 'component('+stuffDest+'CorpReal)';
		copyValue(s, d, false);

		s = 'component('+suffSource+'FlatReal)';
		d = 'component('+stuffDest+'FlatReal)';
		copyValue(s, d, false);
	}