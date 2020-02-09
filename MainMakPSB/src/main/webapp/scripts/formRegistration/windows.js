// windows.js

	function openFindData() {
        newWin = open("","","resizable=yes,status=yes,location=no,toolbar=no,menubar=no,scrollbars=yes,width=1200,height=770,top=50,left=50");
        newWin.location.href = 'copyOfRegistrationSearch.jsp';
    }

	function openMSKData() {
		var persnum = document.getElementById("idpersnum").value;
		if (persnum==null || persnum=="")
			alert("Введите СНИЛС заявителя");
		else {
			var source = window.showModalDialog("modalDialogs/msk/dialogImportSource.jsp", null, 'dialogWidth=200px; dialogHeight=190px; center=1; help=no; status=no; unadorned=yes; caption=no');
			if (source){
				newWin = open("","","resizable=yes,status=yes,location=no,toolbar=no,menubar=no,scrollbars=yes,width=1200,height=700,top=50,left=50");
				newWin.document.write(
						"<html>" +
							"<head>" +
								"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=Cp1251\"/>" +
								"<style type=\"text/css\">@import url( \"resources/stylesheet.css\" ); </style>"+
								"<title>Данные застрахованного лица, полученные из ПК МСК</title>" +
							"</head>" +
						"<body>" +
							"<div class=\"header\">" +
								"<br/>" +
							"</div>" +
							"<div id=\"header-menu\">" +
								"<br/>" +
								"<div id=\"header-search\">" +
									"<br/>" +
								"</div>" +
							"</div>" +
							"<div id=\"main\">" +
								"<br/>подождите, идёт загрузка данных..." +
							"</div>" + 
						"</body>" +
						"</html>");
				var typeData = document.getElementById("typeData");
				if (typeData == null || typeData == ""){
					newWin.location.href = 'mskImport.do?action=genImportOfForm&persnum=' + persnum + "&source=" + source;
				}
				else{
					newWin.location.href = 'mskImport.do?action=genImportOfForm&persnum=' + persnum + '&typeData=' + typeData.value + "&source=" + source;
				}
			}
		}
    }
	
	function openMSKSource() {
		var source = window.showModalDialog("modalDialogs/msk/dialogImportSource.jsp", null, 'dialogWidth=200px; dialogHeight=190px; center=1; help=no; status=no; unadorned=yes; caption=no');
		if (source){
			document.getElementById("source").value = source;
		}
    }
	
	function openCatalogueData(name, parentText, parentHiddenText) {
		var objParent = null;
		var parentValue = '';		
		if (parentHiddenText.length==0){
			objParent = getElementByName(parentText);
			//alert("value="+objParent.value+"\nparentText="+parentText)
			}
		else{
			objParent = getElementByName(parentHiddenText); 
			//alert("objParent="+objParent.outerHTML+"\nparentHiddenText="+parentHiddenText)
			}
		if (objParent!=null)
			parentValue = objParent.value; 		
		if (name=='cityUrText|cityUrButton'){
				if (parentValue=='|-1'){
					var objParentParent = getElementByName('regionUr');
					if (objParentParent!=null)
						parentValue = "|"+objParentParent.value+"000";
				}
			}
			
        var newWin = open("","","resizable=yes,status=yes,location=no,toolbar=no,menubar=no,scrollbars=yes,width=650,height=500,top=50,left=50");
        newWin.location.href = 'kladrCatalogue.jsp?name='+name+'&param='+parentValue;
    }

	function openAddressRD(){
		window.focus();
		var str = "resizable=yes,status=yes,location=no,toolbar=no,menubar=no,scrollbars=yes,width=540,height=250,top="+(tempY+30)+",left="+(tempX-540);
		var winARD = open("dialogAddressReasonConcret.jsp?addressReason="+registrationForm.addressReason.value+"&addressReasonDescription="+registrationForm.addressReasonDescription.value,"",str);
	}
	
	function openAddressReasonDescription(addressReason, addressReasonDescription, btn){
		window.focus();
		var dialogName = "modalDialogs/dialogAddressReasonDescription.jsp?addressReasonDescription="+addressReasonDescription+"&addressReason="+addressReason;
		if (document.location.toString().indexOf("modalDialogs")>=0)
			dialogName = "dialogAddressReasonDescription.jsp?addressReasonDescription="+addressReasonDescription+"&addressReason="+addressReason;

		var objs = document.registrationForm.elements;
		var objsStr = "";
		for (var i=0; i<objs.length; i++)
			objsStr = objsStr+objs[i].outerHTML+"\n";
		
		var valuesObject = new Object();
		valuesObject.objsStr = objsStr;
		valuesObject.objs = objs;
		
		var result = window.showModalDialog(dialogName, valuesObject, 'dialogWidth=540px; dialogHeight=250px; center=1; help=no; status=no; unadorned=yes; caption=no');
		if (result)
			if (result.indexOf("changeARD")==0){
				var values = result.split("|");
				document.registrationForm.addressReasonDescriptionText.value = values[1];
				document.registrationForm.addressReasonDescription.value = values[2];
				document.registrationForm.addressReasonDescriptionShortText.value = values[3];
				document.getElementById("component(addressReasonDescriptionCode)").value = values[4];
				document.getElementById("actionForm").value = "changeAddressReasonDescription";
				btn.focus();
				document.registrationForm.submit();
			}
	}
	
	function openAddressReasonConcret(addressReasonDescription, addressReasonConcret, btn){
		window.focus();
		var dialogName = "modalDialogs/dialogAddressReasonConcret.jsp?addressReasonConcret="+addressReasonConcret+"&addressReasonDescription="+addressReasonDescription;
		if (document.location.toString().indexOf("modalDialogs")>=0)
			dialogName = "dialogAddressReasonConcret.jsp?addressReasonConcret="+addressReasonConcret+"&addressReasonDescription="+addressReasonDescription;

		var objs = document.registrationForm.elements;
		var objsStr = "";
		for (var i=0; i<objs.length; i++)
			objsStr = objsStr+objs[i].outerHTML+"\n";
		
		var valuesObject = new Object();
		valuesObject.objsStr = objsStr;
		valuesObject.objs = objs;
		
		var result = window.showModalDialog(dialogName, valuesObject, 'dialogWidth=540px; dialogHeight=250px; center=1; help=no; status=no; unadorned=yes; caption=no');
		if (result)
			if (result.indexOf("changeARD")==0){
				var values = result.split("|");
				document.registrationForm.addressReasonConcretText.value = values[1];
				document.registrationForm.addressReasonConcret.value = values[2];
				document.registrationForm.addressReasonConcretShortText.value = values[3];
				document.getElementById("component(addressReasonConcretCode)").value = values[4];
				document.getElementById("actionForm").value = "changeAddressReasonConcret";
				btn.focus();
				document.registrationForm.submit();
			}
	}
	
	function openSources(btn){
		window.focus();
		var dialogName = "modalDialogs/sources/dialogSources.jsp";
		if (document.location.toString().indexOf("modalDialogs")>=0)
			dialogName = "dialogSources.jsp";
		
		var valuesObject = new Object();
		
		var result = window.showModalDialog(dialogName, valuesObject, 'dialogWidth=400px; dialogHeight=230px; center=1; help=no; status=no; unadorned=yes; caption=no');
		if (result){
			if (result.indexOf("selectSource")==0){
				var values = result.split("|");
				var idSource = values[1];
				document.registrationForm.idSource.value = idSource;
				document.getElementById("actionForm").value = "changeSource";
				btn.focus();
				getValuesFromExternalSource("changeSource", document.getElementById("externalSource").value, idSource);
				//document.registrationForm.submit();
			}
		}
	}	
//**//
	function openSourcesNVP(btn){
		window.focus();
		var dialogName = "modalDialogs/sources/dialogSourcesNVP.jsp";
		if (document.location.toString().indexOf("modalDialogs")>=0)
			dialogName = "dialogSources.jsp";

		//var objs = document.registrationForm.elements;
		//var objsStr = "";
		//for (var i=0; i<objs.length; i++)
		//	objsStr = objsStr+objs[i].outerHTML+"\n";
		
		var valuesObject = new Object();
		//valuesObject.objsStr = objsStr;
		//valuesObject.objs = objs;
		
		var result = window.showModalDialog(dialogName, valuesObject, 'dialogWidth=400px; dialogHeight=230px; center=1; help=no; status=no; unadorned=yes; caption=no');
		if (result){
			if (result.indexOf("selectSource")==0){
				var values = result.split("|");
				var idSource = values[1];
				document.registrationForm.idSource.value = idSource;
				document.getElementById("actionForm").value = "changeSourceNVP";
				btn.focus(); 
				getValuesFromExternalSource("changeSourceNVP", document.getElementById("externalSource").value, idSource);
				//document.registrationForm.submit();
			}
		}
	}

	function fillFromPtkNvp(){
		document.getElementById("actionForm").value = "fillFromPtkNVP";
		
		getValuesFromExternalSource("fillFromPtkNVP", document.getElementById("externalSource").value, null);
		//document.registrationForm.submit();
	}

	/**
	 * @param ur - URL ресурса
	 * @param w - ширина окна
	 * @param h - высота окна
	 */
	function openWinList(ur, w, h) {
		src = "resizable=yes,status=yes,location=no,toolbar=no,menubar=yes,scrollbars=yes,width="+w+",height="+h+",top=50,left=50";
        newWin = open("","",src);
        newWin.location.href = ur;
    }
	
	/**
	 * @param ur - URL ресурса
	 * @param w - ширина окна
	 * @param h - высота окна
	 */
	function openWinModal(ur, w, h) {
		src = "dialogWidth="+w+"px; dialogHeight="+h+"px; center=1; help=no; status=no; unadorned=yes; scroll=yes; resizable:yes";
        window.showModalDialog(ur, null, src);    	
    }	
    
    function closeForm(){
//    	if ()
    	if (!confirm('Вы действительно хотите выйти из режима редактирования формы?'+window.location)) 
    		return false;
    }
    
    function openTicket()
    {
    	//openWinList("ticket.do",800,700);
    	openWinList("table.do?tableName=ticket",800,700);
    }
    
    function openTransferFunds()
    {
    	openWinList("table.do?tableName=transferFunds",800,700);
    }
    
    function openReturnFunds()
    {
    	openWinList("table.do?tableName=returnFunds",800,700);
    }
    
    function openAdvisementServiceRecords()
    {
    	openWinList("table.do?tableName=advisementServiceRecords",800,700);
    }
    
    function openMSKPayee()
    {
    	openWinList("table.do?tableName=mskPayee",1100,800);
    }
    
    function openRouteTable()
    {
    	openWinList("table.do?tableName=route",800,700);
    }
    
    function openRelativeDeceasedTable()
    {
    	openWinList("table.do?tableName=relative_deceased",800,700);
    }
    
    function openRelativeHeroTable()
    {
    	openWinList("table.do?tableName=relatives_hero",800,700);
    }
    
    function openRelativeRKSTable()
    {
    	openWinList("table.do?tableName=relatives_rks",1000,700);
    }
    
    function openPayeeRKSTable()
    {
    	openWinList("table.do?tableName=payee_rks",1000,700);
    }
    
    function openRKSTicket()
    {
    	//openWinList("ticket.do",800,700);
    	openWinList("table.do?tableName=ticket_rks",800,700);
    }
    
    function openSucessorTable()
    {
    	openWinList("table.do?tableName=successor",800,700);
    }
    
    function openCataloquePayee()
    {
	    var name = getElementByName("component(mskBank)").value;
	    
	    var mskAddress = getElementByName("component(mskAddress)").value;
		var mskInn = getElementByName("component(mskInn)").value;
		var mskBik = getElementByName("component(mskBik)").value;
		var mskKpp = getElementByName("component(mskKpp)").value;
		var mskBank = getElementByName("component(mskBank)").value;
		var mskCount = getElementByName("component(mskCount)").value;
		var mskKorrCount = getElementByName("component(mskKorrCount)").value;
		var mskIndex = getElementByName("component(mskIndex)").value;
		var mskPayee = getElementByName("component(mskPayMoney)").value;
		
    	openWinList("viewBank.do?name="+mskBank+"&inn="+mskInn+
    	"&bik="+mskBik+"&kpp="+mskKpp+"&account="+mskCount+
    	"&korrAccount="+mskKorrCount+"&address="+mskAddress+
    	"&index="+mskIndex+"&payee="+mskPayee,1000,700);
    }
    
    function openCataloguePayee2()
    {
	    var mskAddress = getElementByName("field(address)").value;
		var mskInn = getElementByName("field(inn)").value;
		var mskBik = getElementByName("field(bik)").value;
		var mskKpp = getElementByName("field(kpp)").value;
		var mskBank = getElementByName("field(bank_name)").value;
		var mskCount = getElementByName("field(account_number)").value;
		var mskKorrCount = getElementByName("field(corr_account)").value;
		var mskIndex = getElementByName("field(post_index)").value;
		var mskPayee = getElementByName("field(name)").value;
		
    	openWinList("viewBank.do?name="+mskBank+"&inn="+mskInn+
    	"&bik="+mskBik+"&kpp="+mskKpp+"&account="+mskCount+
    	"&korrAccount="+mskKorrCount+"&address="+mskAddress+
    	"&index="+mskIndex+"&payee="+mskPayee,1000,700);
    }
    
//    function openTransferTimes()
//    {
//    	openWinList("mskTransfers.do?idPayee=-1",800,700);
//    }
    
    function openTransferTimes()
    {
    	var mskIdPayee = document.getElementById("idRow").value;
    	openWinList("mskTransfers.do?idPayee="+mskIdPayee,800,700);
    }
    
    function openServiceRecords()
    {
    	openWinList("serviceRecords.do",800,700);
    }
    
    function openPensDoc()
    {
    	var num = document.getElementById("idpersnum").value;
    	if (num=="")
    	{
	    	alert("Для перехода в Пенсионные Документы введите СНИЛС");
	    	return;
	    }
    	if (num.length>=11)
    	{
	    	var persnum = num.substring(0, 3) + "-" + num.substring(3, 6) + 
					"-" + num.substring(6, 9) + " " + num.substring(9, 11);
	        newWin = open("","","");
    	    newWin.location.href = document.getElementById("pens_doc_url").value + "?snils="+persnum;
        }
        else
	        alert("Для перехода в Пенсионные Документы введите корректный СНИЛС");
    }
    
    
    function checkAddr(compName, boxId){
    	document.getElementById("actionValue").value = compName;
    	document.getElementById("actionForm").value = "checkIndex";
    	document.getElementById(boxId).innerHTML = "Подождите ...";
    	 $.ajax({
             type: "POST",
             url: "/clientServiceWeb/components.do",
             contentType: "application/x-www-form-urlencoded; charset=utf-8",
             data: $("#registrationForm").serialize(),
             success: function(data)
             {
                 document.getElementById(boxId).innerHTML = data;
             },
    	 error : function(data)
         {
             alert(data.status);
         }
           });
           
           document.getElementById("actionForm").value = "";
	}
	
	
	function getRegions(value){
			var values = value.split("|");
			var countryId = values[1];
			
    	 $.ajax({
             type: "GET",
             url: "/clientServiceWeb/kladr.jsp?countryId="+countryId,
             contentType: "charset=utf-8",
             dataType: "json",
             cache: false,
             success: function(data)
             {
             regionArray.splice(0, regionArray.length);
             for (var i=0; i<data.length; i++)
             {
	             regionArray.push(new Array(data[i].name, data[i].codekladr));
             }
             },
    	 error : function(data, m)
         {
             alert(data.status + " "+m);
         }
           });
	}
	
	function getKladr(arrayValue, typeKladr, value, parentId, inputID, hiddenID, preParentId, async){
		if (typeKladr == null || typeKladr == "")
			return;
		if (async == null)
			async = false;
		
		var kladrId = null;
		var values = value.split("|");
		if (values.length>1)
			kladrId = values[1];
		else
			kladrId = values[0];
		
			if (kladrId != null)
			{
				if (inputID != null)
				{
					var objInput = document.getElementById(inputID);
					if (objInput.disabled) return;
				}
					
			if (kladrId != '-1' && kladrId != '')
			{
				if (inputID != null)
				{
					var obj = document.getElementById(inputID);
					obj.value = 'Построение списка...';
					obj.disabled = true;
				}
				$.ajax({
					type: "GET",
					url: "/clientServiceWeb/kladr.jsp?kladrId="+kladrId+"&typeKladr="+typeKladr,
					contentType: "charset=utf-8",
					dataType: "json",
					cache: false,
					async: async,
					success: function(data)
					{
					if (inputID != null)
					{
						var obj = document.getElementById(inputID);
						obj.value = '';
						obj.disabled = false;
						if (hiddenID != null)
						{
							var hidden = document.getElementById(hiddenID);
							var values =hidden.value.split("|");
							if (values.length>1)
								obj.value = values[0];
						}
						
					}
    		 	
					arrayValue.splice(0, arrayValue.length);
					for (var i=0; i<data.length; i++)
					{
						if (data[i].name == null)
							data[i].name = "";
						arrayValue.push(new Array(data[i].name, data[i].codekladr));
					}
					},
					error : function(data, m)
					{
						//alert(data.status + " "+m);
						if (inputID != null)
						{
							var obj = document.getElementById(inputID);
							obj.value = '';
							obj.disabled = false;
						}
					}
				});
			}
			else if (parentId != null)
				{
					var obj = document.getElementById(parentId);
					if (obj != null)
						getKladr(arrayValue, typeKladr, obj.value, preParentId, inputID, hiddenID, null, async);
				}
			
           }
	}
	
	function showChildrensData(){
    	//document.getElementById("actionForm").value = "childrensData";
    	//document.registrationForm.submit();
    	openWinList("children.do?open=true", 1200, 800); 
	}
	
	function createWin(ur, w, h, windowName) {
		src = "resizable=yes,status=yes,location=no,toolbar=no,menubar=yes,scrollbars=yes,width="+w+",height="+h+",top=50,left=50";
        newWin = open("",windowName,src);
    }
	
	function showDocPresent(){
		var form = $("#form_form");
		if (form.length)
			{
				form.remove();
			}
		{
			var form = $("<form id=\"form_form\" method=\"post\" action=\"docPresent.do?open=true\" target=\"window_window\"></form>");
			$('body').append(form);
			var f = $('#registrationForm');
			$(':input[name^="component"]', f).each(function() {
				if ($(this).val() != null && $(this).val().length > 0)
				{
					var el = $('<input type="hidden" name='+$(this).attr('name')+'></input>');
					el.val($(this).val());
					el.appendTo(form);
				}
			});
		}
		
		
		window.open('', 'window_window');
		document.getElementById('form_form').submit();
	}
	
	function showDocIssued(){
    	openWinList("docIssued.do?open=true", 1200, 800);  
	}
	
	function setDisabledComp(compName, disabled)
	{
		var obj = getElementByName('component('+compName+'Text)');
		if (obj != null)
			obj.disabled = disabled;
	}
	
	function fillAddressFromIndex(compIndex, compCountry, compRegion, compRaion, compCity, compPunkt, compStreet){
		
		var indexPost = null;
		var obj = getElementByName('component('+compIndex+')');
		if (obj != null)
			indexPost = obj.value;
		
		setDisabledComp(compCountry, true);
		setDisabledComp(compRegion, true);
		setDisabledComp(compRaion, true);
		setDisabledComp(compCity, true);
		setDisabledComp(compPunkt, true);
		setDisabledComp(compStreet, true);
		
    	 $.ajax({
             type: "GET",
             url: "/clientServiceWeb/addressFromIndex.do?indexPost="+indexPost,
             contentType: "charset=utf-8",
             dataType: "json",
             cache: false,
             async: true,
             success: function(data)
             {
    		 	for (var i=0; i<data.length; i++)
    		 	{
    		 		var obj = null;
    		 		if (data[i].type == 'country')
    		 			obj = getElementByName('component('+compCountry+')');
    		 		if (data[i].type == 'region')
    		 			obj = getElementByName('component('+compRegion+')');
    		 		else if (data[i].type == 'raion')
    		 			obj = getElementByName('component('+compRaion+')');
    		 		else if (data[i].type == 'city')
    		 			obj = getElementByName('component('+compCity+')');
    		 		else if (data[i].type == 'punkt')
    		 			obj = getElementByName('component('+compPunkt+')');
    		 		else if (data[i].type == 'street')
    		 			obj = getElementByName('component('+compStreet+')');
    		 		
    		 		if (obj != null)
    		 		{
    		 			if (data[i].type == 'region' || data[i].type == 'country')
    		 			{
    		 				obj.value = data[i].code;
    		 				if (data[i].type == 'region')
    		 				{
    		 					var objInput = document.getElementById('txt'+compRegion);
    		 					objInput.value = data[i].name;
    		 				}
    		 			}
    		 			else
    		 				{
    		 				obj.value = data[i].name+'|'+data[i].code;
    		 				//alert(data[i].type+' '+ obj.value);
    		 				}
    		 			
    		 			if (obj.onchange != null)
    		 				obj.onchange();
    		 		}
    		 	}
    		 	
    		 	setDisabledComp(compCountry, false);
    			setDisabledComp(compRegion, false);
    			setDisabledComp(compRaion, false);
    			setDisabledComp(compCity, false);
    			setDisabledComp(compPunkt, false);
    			setDisabledComp(compStreet, false);
             },
             error : function(data)
             {
            	setDisabledComp(compCountry, false);
     			setDisabledComp(compRegion, false);
     			setDisabledComp(compRaion, false);
     			setDisabledComp(compCity, false);
     			setDisabledComp(compPunkt, false);
     			setDisabledComp(compStreet, false);
     			
            	 alert("Ошибка: " + data.status + " " + data.statusText);
             },
             beforeSend : function () {
         	 	var objInput = document.getElementById('txt'+compRegion);
					var c = $(objInput);
					var position = c.offset();
					var x = position.left + c.outerWidth()/2;
			        var y = position.top + c.outerHeight()/2;
			        $('#updateProgressDiv').css("left", x);
			        $('#updateProgressDiv').css("top", y);
			      $("#updateProgressDiv").show();
			    },
			 complete : function () {
			        $("#updateProgressDiv").hide();
			    }
           });
	}
 
	function selectNVPWpr(componentName, type)
	{
		var wnd = window.open('catalogueNVPWpr.jsp?componentName='+componentName+'&type='+type, 'nvpWprList', "top=100, left=100, width=800, height=610, scrollbars=yes, resizable=yes");
		wnd.focus();
		return false;
	}
	
	function createUpdatePanel(compId, panelName)
	{
		var comp = $('#'+compId);
		var panel;
		if (comp != null)
			{
				var groupId = comp.groupId;
				
				if (groupId != null)
					expandGroup(groupId);
				
				comp = $(comp);
				
				panel = document.getElementById(panelName);
				panel = $(panel);
				if (panel.length == 0)
				{
					var position = comp.offset();
					var x = position.left + comp.outerWidth()+10;
					var y = position.top + comp.outerHeight()/2;
					
					panel = jQuery('<div id='+panelName+' style="display:inline-block; position:absolute; height:'+'32px; width:'+
		 		            +'32px; top: 0; left:'+x+'px;">'+
		 		            '<img src="resources/loadData_small.gif" width="16" height="16"/></div>');
					
					panel.insertAfter(comp);
					panel.css('display', 'none');
				}
			}
		return panel;
	}
	
	function createComboboxWPR(componentTextId, type)
	{
		var userRaionId = $('#iupfr').val();
		var upfrComponentName = $('#numberUpfrRealComponent').val();
		var elements = document.getElementsByName('component('+upfrComponentName+')');
		var elUpfr;
		if (elements.length>0)
		{
			var elUpfr = elements[0];
		}
		var comp;
		var onchangeBank = function(a)
		{
			
		};
		
		var bankArray = new Array();
		var updatePanel = createUpdatePanel(componentTextId,'updateDataPanel'+componentTextId);
		var funcOpenBank = function(a, combo) {
			var raionId = userRaionId;
			combo.setShowAll(false);
			var upfr = elUpfr.value;
			if (upfr != null && upfr > 0 && upfr != raionId)
				raionId = upfr;
			combo.setRaionID(raionId);
			
			if (bankArray.length <= 0)
			{
			$.ajax({
	            type: "GET",
	            url: "/clientServiceWeb/getJSONListWpr.do",
	            data: {typeSV: type},
	            contentType: "charset=utf-8",
	            dataType: "json",
	            cache: false,
	            async: true,
	            success: function(data)
	            {
	            	if (data != null)
	            	{
	            		bankArray.splice(0, bankArray.length);
						for (var i=0; i<data.length; i++)
						{
							if (data[i].name == null)
								data[i].name = "";
							bankArray.push(new Array(data[i].name, data[i].code, data[i].raion));
						}
	            	}
	            	combo.buildSelectOptionList();
	            	combo.show();
	            },
	            error : function(data)
	            {
	            },
	            beforeSend : function () {
	            	updatePanel.css('display', 'inline-block');
				    },
				 complete : function () {
				    	updatePanel.css('display', 'none');
				    }
	          });
			}
		};
		
		$('#'+componentTextId).css('width', '378px');
		var comp = $('#'+componentTextId).combobox(componentTextId+'Code',bankArray,0,onchangeBank, false, null, funcOpenBank, true,false);
	}
	
	function createComboboxCatalogue(componentTextId, componentId, action, refreshOnOpen)
	{
		var obj = document.getElementById(componentTextId);
		obj.onchange = null;
		var value = $('#'+componentId).val();
		var valueText = $('#'+componentTextId).val();
		var valueArray = new Array();
		var updatePanel = createUpdatePanel(componentTextId,'updateDataPanel'+componentTextId);
		var funcOpen = function(a, combo) {
			if (combo != null)
				combo.setShowAll(false);
			
			if (valueArray.length <= 0 || refreshOnOpen)
			{
			$.ajax({
	            type: "GET",
	            url: action,
	            contentType: "charset=utf-8",
	            dataType: "json",
	            cache: false,
	            async: true,
	            success: function(data)
	            {
	            	if (data != null)
	            	{
	            		valueArray.splice(0, valueArray.length);
						for (var i=0; i<data.length; i++)
						{
							if (data[i].name == null)
								data[i].name = "";
							valueArray.push(new Array(data[i].name, data[i].code, 0));
							if (valueText == '' && data[i].code == value)
							{
								$('#'+componentTextId).val(data[i].name);
							}
						}
	            	}
	            	if (combo != null)
	            	{
	            		combo.buildSelectOptionList();
	            		combo.show();
	            	}
	            },
	            error : function(data)
	            {
	            },
	            beforeSend : function () {
	            	updatePanel.css('display', 'inline-block');
				    },
				 complete : function () {
				    	updatePanel.css('display', 'none');
				    }
	          });
			}
		};
		
		var onchange = function(a)
		{
			a.onchange();
			if (obj.onchange != null)
				obj.onchange();
		};
		
		$('#'+componentTextId).css('width', '378px');
		var comp = $('#'+componentTextId).combobox(componentId,valueArray,0,onchange, false, null, funcOpen, false,false);
		//funcOpen(null, null);
	}
	