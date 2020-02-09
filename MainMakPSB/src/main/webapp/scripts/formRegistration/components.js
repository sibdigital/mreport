// components.js

	function onchangeSelect(parent, childs){        
	    clearSelect(childs.name);
        var isChilds = false;
		for (var j=0;j<childs.values.length;j++)
			if (parent.options[parent.selectedIndex].value == childs.values[j].idParent){
				isChilds = true;
				childs.name.options.add(new Option(childs.values[j].value, childs.values[j].id));
			}
        childs.name.onchange();
        if (isChilds) {
            childs.name.disabled = false;            
        //    childs.name.onchange();
        }
        else {
			childs.name.onchange();
            //childs.name.disabled = true;
        }
		}

	function onchangeSelect2(parent, childs){     
        childObject = getElementByName(childs.text);
	    clearSelect(childObject);
        var isChilds = false;
		for (var j=0;j<childs.values.length;j++)
			if (parent.options[parent.selectedIndex].value == childs.values[j].idParent){
				isChilds = true;
				childObject.options.add(new Option(childs.values[j].value, childs.values[j].id));
			}
        childObject.onchange();
        if (isChilds) {
            childObject.disabled = false;            
        //    childs.name.onchange();
        }
        else {
			childObject.onchange();      
            //childs.name.disabled = true;
        }
		}

	function clkSel(parent, sel, arr){
		selEl = getElementByName(sel);
		if(selEl!=null){
			if (selEl.tagName=='SELECT'){
				parentVal = parent.options[parent.selectedIndex].value;
				clearSel(selEl);
				if (parentVal!=-1){
					parentVal = parentVal.substring(5,10);
					objArr = eval(arr);
					for (var i=0; i<objArr.length; i++){
						area0 = objArr[i];
						objOption = eval("new Option"+area0);
						if (objOption.value.substring(0,5)==parentVal)
							selEl.options.add(objOption);
					}
				}
				if (selEl.length>1)
					selEl.selectedIndex=1;
				selEl.onchange();
			}if (selEl.tagName=='INPUT'){
				if (parent.tagName=='SELECT')
					parentVal = parent.options[parent.selectedIndex].value;
				else parentVal='-1';
				selEl.value='|'+parentVal;
				selElText =  getElementByName(sel.substring(0,sel.indexOf('|'))+')');
				if (selElText!=null){
					selElText.value='';
					selElText.onkeyup();
				}
			}
		}
	}
	
	function clkSelCatalogue(parent, comp){
		var parentId = parent.options[parent.selectedIndex].value;
		var sel = document.getElementById("select"+comp);
		var len = sel.length;
		var selValue = null;
		if (sel.selectedIndex!=-1)
			selValue = sel.options[sel.selectedIndex].value;
		for (var i=0; i<len; i++)
			sel.remove(0);
		$.ajax({
			type: "GET",
            url: "/clientServiceWeb/getChildCatalogue.do",
            data: {idParent: parentId, component:comp},
            contentType: "charset=utf-8",
            dataType: "script",
            cache: false,
            async: false,
            success: function(data)
            {
            	for (var i=0; i<arr.length; i++){
   		 			sel.options[i] = new Option(arr[i][1], arr[i][0]);
   		 			if (selValue==arr[i][0])
   		 				sel.selectedIndex = i;
            	}
            },
            error : function(data)
            {
            	alert("Ошибка: " + data.status + " " + data.statusText);
            },
            beforeSend : function () {
            	
			    },
			 complete : function () {
			        
			    }
		});
	}
	
	function clearSel(s){
		var len = s.options.length;
		for (var k=0; k<len; k++)
			s.options.remove(0);
		s.options.add(new Option('','-1'));
	}

	function clearSelect(s){
		var len = s.options.length;
		for (var k=0; k<len; k++)
			s.options.remove(0);
	}

    function OptionChild(id, value, idParent) {
        this.id = id;
        this.value = value;
		this.idParent = idParent;
    }		
	
	function Children(name,values){
		this.name = name;
		this.values = values;
	}

	function Children2(text,values){
		this.text = text;
		this.values = values;
	}
	
	function onGroup(idGroup){
		bodys = document.getElementsByTagName("table");
		for (var bodEl=0; bodEl<bodys.length; bodEl++){			
			if(bodys[bodEl].style.groupId==idGroup && bodys[bodEl].id!="maska"){
				if (bodys[bodEl].style.display=='none')
					bodys[bodEl].style.display='';
				else
					bodys[bodEl].style.display='none';
			}
		}
		bodysG = document.getElementsByTagName("a");
		for (var bodGEl=0; bodGEl<bodysG.length; bodGEl++){			
			if(bodysG[bodGEl].style.groupParentId==idGroup)
				if (bodysG[bodGEl].name.indexOf('groupMinus')==0 && bodysG[bodGEl].style.display==''){
					bodysG[bodGEl].onclick();
					}
		}
	}

	function unrollGroups(){
//		unrolls = document.getElementsByTagName("table");
//		for (var unrEl=0; unrEl<unrolls.length; unrEl++){			
//			if(unrolls[unrEl].style.groupId>=1)
//				if (unrolls[unrEl].style.display=='none')
//					unrolls[unrEl].style.display='';
//		}

		metka = document.getElementsByTagName("a");
		for (var mEl=0; mEl<metka.length; mEl++){			
			if(metka[mEl].name!='')
				if(metka[mEl].name.indexOf('groupPlus')==0)
					if (metka[mEl].style.display=='')
						metka[mEl].onclick();
		}
	}

	function rollGroups(){
//		rolls = document.getElementsByTagName("table");
//		for (var rEl=0; rEl<rolls.length; rEl++){			
//			if(rolls[rEl].style.groupId>=1)
//				if (rolls[rEl].style.display=='')
//					rolls[rEl].style.display='none';
//		}
		metka = document.getElementsByTagName("a");
		for (var mEl=0; mEl<metka.length; mEl++){			
			if(metka[mEl].name!='')
				if(metka[mEl].name.indexOf('groupMinus')==0)
					if (metka[mEl].style.display=='')
						metka[mEl].onclick();
		}
	}

	function getElementByName(elementName){
		if (elementName==null || elementName.length==0)
			return null;
		else{			
			var bufferElementArray = document.getElementsByName(elementName);
			if (bufferElementArray!=null && bufferElementArray.length>0)
				return bufferElementArray[0];			
			else
				return null;
		}
	}

	function setValueGroup(elGroup,displayValue){
		var objGroup = getElementByName(elGroup);
		objGroup.value=displayValue;
	}

	function drawingHiddenComponents(){
		for (var cmEl=0; cmEl<registrationForm.length; cmEl++){
			celObj = registrationForm.elements[cmEl];
			if (celObj.name.indexOf('component(')==0){
				celObj.value=celObj.value;
			}
		}
	}
	
	function trim (str){ 
        return str.replace(/(^\s+)|(\s+$)/g, "");
	}
	
	function createPersNum(persnumber){
		nSp="";
     	if (persnumber.length==11) {
       		nSp=persnumber.substring(0,3)+persnumber.substring(3,6)+
              persnumber.substring(6,9)+trim(persnumber.substring(9));
     	} else if (persnumber.length==9){
        	nSp=persnumber.substring(0,3)+persnumber.substring(3,6)+
            trim(persnumber.substring(6))+createKontrChislo(persnumber);
     	}
     	return  nSp;
	}
	
	function createKontrChislo(nomPen){	
         pr=0;
         sum=0;
         j=1;
         for (i=8; i>=0; i--){
         	pr=parseInt(nomPen.substring(i,i+1));
          	sum=sum+pr*j;
          	j++;
         }
         if (sum>101 && (sum % 101)<100) {
             if (sum % 101>=0 && sum % 101<=9){
               return  "0"+(sum % 101);
             }else{
               return  (sum % 101);
             }

         }else if (sum<100){
             return sum;
         } else {
         	return "00";
         }
	}
	
	function doChangeElements(all){
		var objValues = new Array();
		
		for (var i=1; i<document.registrationForm.length; i++){
			var obj = document.registrationForm.elements[i];
			if (obj.type=="select-one")
				objValues[i] = obj.selectedIndex;
		}
		
		
		for (var i=1; i<document.registrationForm.length; i++){
			var obj = document.registrationForm.elements[i];
			var objName = obj.name;
			var objValue = "";
			
				if (all == null || !all)
				{
					var idg = obj.style.groupId;
					if (idg){
						var nameg = getElementByName("groupPlus"+idg);
						if (nameg!=null && (nameg.innerHTML.indexOf("Адрес")>=0 || nameg.innerHTML.indexOf("Место рождения")>=0 || 
								nameg.innerHTML.indexOf("Место погребения")>=0))
							continue;
					}
				}
								
			if (objName.indexOf('documentP')==0) break;
			if (objName.indexOf('mskSubType')>0) continue;
					
			if (objName.indexOf('component')==0){
				objType = obj.type;
				if (objType!="button" && objType!="submit"){					
					if(obj.getAttribute("onchange")){
						obj.onchange();
						if (objType=="select-one")
							obj.selectedIndex = objValues[i];
					}						
				}
			}
		}
	}

	function createHiddenElement(visibleElement, nameElement){
		var createEl = true;
		if (nameElement=='indexUr')	createEl = false;
		if (nameElement=='countryUr')	createEl = false;
		if (nameElement=='regionUr')	createEl = false;
		if (nameElement=='areaUrText|areaUrButton')	createEl = false;
		if (nameElement=='cityUrText|cityUrButton')	createEl = false;
		if (nameElement=='punktUrText|punktUrButton')	createEl = false;
		if (nameElement=='streetUrText|streetUrButton')	createEl = false;
		if (nameElement=='homeUrText|homeUrButton')	createEl = false;
		if (nameElement=='corpUr')	createEl = false;
		if (nameElement=='flatUr')	createEl = false;	
		if (nameElement=='indexReal')	createEl = false;
		if (nameElement=='countryReal')	createEl = false;
		if (nameElement=='regionReal')	createEl = false;
		if (nameElement=='areaRealText|areaRealButton')	createEl = false;
		if (nameElement=='cityRealText|cityRealButton')	createEl = false;
		if (nameElement=='punktRealText|punktRealButton')	createEl = false;
		if (nameElement=='streetRealText|streetRealButton')	createEl = false;
		if (nameElement=='homeRealText|homeRealButton')	createEl = false;
		if (nameElement=='corpReal')	createEl = false;
		if (nameElement=='flatReal')	createEl = false;																				
		if (nameElement.indexOf('Country')>=0)	createEl = false;
		if (nameElement.indexOf('Region')>=0)	createEl = false;
		
		if (nameElement.indexOf('applicantCountryBP')>=0)	createEl = false;
		if (nameElement.indexOf('applicantRegionBP')>=0)	createEl = false;
		if (nameElement.indexOf('applicantAreaBPText|applicantAreaBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('applicantCityBPText|applicantCityBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('applicantPunktBPText|applicantPunktBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('applicantOtherBP')>=0)	createEl = false;
		
		if (nameElement.indexOf('lookingBirthplace')>=0)	createEl = false;
		if (nameElement.indexOf('lookingRegionBP')>=0)	createEl = false;
		if (nameElement.indexOf('lookingAreaBPText|lookingAreaBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('lookingCityBPText|lookingCityBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('lookingPunktBPText|lookingPunktBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('lookingOtherBP')>=0)	createEl = false;
		
		if (nameElement.indexOf('successorBirthplace')>=0)	createEl = false;
		if (nameElement.indexOf('successorRegionBP')>=0)	createEl = false;
		if (nameElement.indexOf('successorAreaBPText|successorAreaBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('successorCityBPText|successorCityBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('successorPunktBPText|successorPunktBPButton')>=0)	createEl = false;
		if (nameElement.indexOf('successorOtherBP')>=0)	createEl = false;
		
		if (nameElement.indexOf('birthplace')>=0) createEl = false;
		if (nameElement.indexOf('Region')>=0)	createEl = false;
		if (nameElement.indexOf('AreaText|AreaButton')>=0)	createEl = false;
		if (nameElement.indexOf('CityText|CityButton')>=0)	createEl = false;
		if (nameElement.indexOf('PunktText|PunktButton')>=0)	createEl = false;
		if (nameElement.indexOf('OtherPlaceBirth')>=0)	createEl = false;
		
		if (nameElement.indexOf('successorBirthplace')>=0)	createEl = false;
		if (nameElement.indexOf('successorRegionBP')>=0)	createEl = false;
		
		if (nameElement.indexOf('delegateAreaOrgText|delegateAreaOrgButton')>=0) createEl = false;
		if (nameElement.indexOf('delegateCityOrgText|delegateCityOrgButton')>=0)	createEl = false;
		if (nameElement.indexOf('delegatePunktOrgText|delegatePunktOrgButton')>=0)	createEl = false;
		if (nameElement.indexOf('delegateStreetOrgText|delegateStreetOrgButton')>=0)	createEl = false;
		if (nameElement.indexOf('delegateHomeOrgText|delegateHomeOrgButton')>=0)	createEl = false;
		
		
		if (createEl){
			var idElement = nameElement;
			var bufElement = document.getElementById(idElement);
			var divElement = document.getElementById('sectionForSign');		
	
			if (bufElement != null){
				//alert(bufElement.innerHTML)
				var par = bufElement.parentNode; 
				//alert(par.parentNode.outerHTML)
				par.removeChild(bufElement);  
				//alert(par.parentNode.outerHTML)
			}
	
			if (visibleElement.value!=null)
				if (visibleElement.value.length>0){
					val = visibleElement.value;
					if (idElement.indexOf('persnum')>=0){
						val = createPersNum(val);
						}
					var hiddenElement = document.createElement("INPUT");
					hiddenElement.setAttribute("type","HIDDEN");
					hiddenElement.setAttribute("value",val.toUpperCase());
					hiddenElement.setAttribute("id",idElement);
					divElement.insertBefore(hiddenElement, divElement.lastChild);
				}			
			//alert(divElement.outerHTML);
		}
	}
	
	function createSelectTextElement(){
		var selects = document.getElementsByTagName("select");
		if (selects != null && selects.length>0){
			for (var i=0; i< selects.length; i++){
				var selectText = "";
				var selectCode = "";
				if (selects[i].length==0){
					selectText = "";
					selectCode = "";
				}
				else{
					selectText = selects[i].selectedIndex==-1 ? "" : selects[i].options[selects[i].selectedIndex].text; 
					selectCode = selects[i].selectedIndex==-1 ? "" : selects[i].options[selects[i].selectedIndex].value; 
				}
				var selectName = selects[i].name; 
				var selectShort = document.getElementById(selectName+"_short");
				if (selectShort!=null)
					if (selectShort.length>0 && selects[i].selectedIndex!=-1 && selectShort.options[selects[i].selectedIndex]!=null 
						&& selectShort.options[selects[i].selectedIndex].text.length>0){
						selectText = selectShort.options[selects[i].selectedIndex].text;
						selectCode = selectShort.options[selects[i].selectedIndex].value;
						} 
				if (selectCode=="-1")
					selectCode = "";		
				if (selectName.length>9){
					var hiddenSelectElement = document.createElement("INPUT");
					hiddenSelectElement.setAttribute("type","HIDDEN");
					hiddenSelectElement.setAttribute("value",selectText.toUpperCase());
					hiddenSelectElement.setAttribute("name","component("+selectName.substring(10,selectName.length-1)+"Text)");
					document.forms[1].insertAdjacentElement("beforeEnd", hiddenSelectElement);
					
					hiddenSelectElement = document.createElement("INPUT");
					hiddenSelectElement.setAttribute("type","HIDDEN");
					hiddenSelectElement.setAttribute("value",selectCode);
					hiddenSelectElement.setAttribute("name","component("+selectName.substring(10,selectName.length-1)+"Code)");
					document.forms[1].insertAdjacentElement("beforeEnd", hiddenSelectElement);					
				}
			}
		}	
		var inputs = document.getElementsByTagName("input");
		if (inputs != null && inputs.length>0){
			for (var i=0; i< inputs.length; i++)
				if (inputs[i].type=="checkbox"){
					var objCheck = inputs[i];
					var checkText = "";
					if (objCheck)
						checkText = objCheck.value!=null ? (objCheck.value=="1" ? "Да" : (objCheck.value=="0" ? "Нет" : "")) : "";
					var checkName = objCheck.name;
					if (checkName.length>9){
						var hiddenSelectElement = document.createElement("INPUT");
						hiddenSelectElement.setAttribute("type","HIDDEN");
						hiddenSelectElement.setAttribute("value",checkText.toUpperCase());
						hiddenSelectElement.setAttribute("name","component("+checkName.substring(10,checkName.length-1)+"Text)");
						document.forms[1].insertAdjacentElement("beforeEnd", hiddenSelectElement);
						//alert(checkName+"="+checkText.toUpperCase())
					}
				}
		}		
		//alert(document.forms[1].outerHTML);
	}	
	
function checkboxClick(obj){
	if (obj.checked && (obj.value==null || obj.value=="")){
		obj.value="1";
		obj.indeterminate = false;
		document.getElementById("lab_"+obj.name).innerHTML=" Да"
		return true;
	}
	else if (!obj.checked && (obj.value=="1" || obj.value=="2")){
		obj.value="0";
		obj.checked = true
		obj.indeterminate = true;
		document.getElementById("lab_"+obj.name).innerHTML=" Нет"
		return true;
	}
	else if (obj.checked && obj.value=="0"){
		obj.value="";
		obj.checked = false
		document.getElementById("lab_"+obj.name).innerHTML=""
		return true;
	}	
}

function trimText(obj, maxlen)
{
    if (obj.value.length>maxlen)
    obj.value=obj.value.substring(0,maxlen);
}
function calcLenText(obj, counterID, maxlen)
{
    var objcounter = document.getElementById(counterID);
    objcounter.innerHTML=obj.value.length+'/'+ maxlen;
}	

function createHiddenAdvisementElements(){
		var objValues = new Array();
		
		for (var i=1; i<document.advisementForm.length; i++){
			var obj = document.advisementForm.elements[i];
			if (obj.type=="select-one")
				objValues[i] = obj.selectedIndex;
		}
		
		
		for (var i=1; i<document.advisementForm.length; i++){
			var obj = document.advisementForm.elements[i];
			var objName = obj.name;
			var objValue = "";
			
			var idg = obj.style.groupId;
			if (idg){
				var nameg = getElementByName("groupPlus"+idg);
				if (nameg!=null && (nameg.innerHTML.indexOf("Адрес")>=0 || nameg.innerHTML.indexOf("Место рождения")>=0 || 
				nameg.innerHTML.indexOf("Место погребения")>=0))
					continue;
			}
								
			if (objName.indexOf('documentP')==0) break;
					
			if (objName.indexOf('component')==0){
				objType = obj.type;
				if (objType!="button" && objType!="submit"){
					if(obj.getAttribute("onchange")){
						obj.onchange();
						if (objType=="select-one")
							obj.selectedIndex = objValues[i];
					}						
				}
			}
		}
	}

function resizeArea(txtarea, cols)
{
	var area = document.getElementById(txtarea); 
	var linecount = 0;
	var array = area.value.split("\n");
	for (var i=0; i<array.length; i++){
	       linecount += Math.floor( array[i].length / cols ) + 1;
	}	
	area.rows = linecount;
}

function hideAdvisementButton(){
	var gidAddressReason = document.getElementById("gidAddressReason");
	if (gidAddressReason!=null && gidAddressReason.value != 42)
		document.getElementById("northButton").style.display = "none";
	var gidAddressTheme = document.getElementById("gidAddressTheme");
	if (gidAddressTheme!=null && gidAddressTheme.value == 3){
		document.getElementById("armNvpButton").style.display = "none";
		document.getElementById("ptkNvpButton").style.display = "none";
		var hiddenElement = document.createElement("INPUT");
		hiddenElement.setAttribute("type","HIDDEN");
		hiddenElement.setAttribute("value","");
		hiddenElement.setAttribute("id","source");
		hiddenElement.setAttribute("name","source");
		var divAction = document.getElementById("action");
		divAction.insertBefore(hiddenElement, divAction.lastChild);
	}
	if (gidAddressTheme!=null && gidAddressTheme.value != 3){
		document.getElementById("mskButton").style.display = "none";
	}
}