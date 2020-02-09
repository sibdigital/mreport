var contentOne;

function getContent(){
	return contentOne;
}

function setContent(c){
	contentOne=c;
}

function sign(templateXML, idRegister, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension) {
	try {
		if (transDir == null) transDir = "";
		if (keyDir == null) keyDir = "";
		if (flag == null) flag = 0;
		if (serialNumbers == null) serialNumbers = '';
		if (serialNumbersCA == null) serialNumbersCA = '';
		if (keyUsage == null) keyUsage = '';
		if (extension == null) extension = '.p7s';
		
		var content = buildContent('sectionForSign','', templateXML, idRegister);
		
		setContent(content);
		if (!content){
			alert("Невозможно подписать; Нет содержимого для подписи");
			return "";
		}
		//console.log('подписываемый контент: '+content);
		var signResult = cs_initAndSignContent('clientService', content, transDir,keyDir,flag,serialNumbers,serialNumbersCA,keyUsage,extension);
		
		var res = cs_parseXMLResult(signResult);
		
		if (res.code != 0) {
			alert("Невозможно подписать; " + res.code_message);
			return "";
		}
		return res.data;

	} catch (e) {
		alert("Невозможно подписать. Возможно не запущен криптосервер");
		return "";
	}
}

function getTemplate(){
	return xmlStructure.xml;
}

function confirmStartProcess(nUserUpfr, compUpfrAttr)
{
	var bufWF = document.getElementsByName('component(workflow)');
	var wf = bufWF[0];
	if (wf!=null && (wf.value=="1" || wf.value=="2"))
	{	
		var elements = document.getElementsByName('component('+compUpfrAttr+')');
		if (elements.length>0)
		{
			var el = elements[0];
			var upfr = el.options[el.selectedIndex].value;
			if (upfr != null && upfr > 0 && upfr != nUserUpfr)
			{
				var upfrName = el.options[el.selectedIndex].text;
				if (!confirm("Процесс будет запущен в подразделение ПФР \"" + upfrName +
				"\". Вы действительно хотите запустить процесс?"))
				{
					wf.value="0";
					wf.onchange();
					return false;
				}
				else
					return true;
			}
			else 
				return true;
		}
	}
}

function regF(isRole, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension) {
		if (isRole){
	        var signat = sign("", -1, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension);
			var content = getTemplate();
	        registrationForm.txtSign.value=signat;
	        registrationForm.txtContent.value=content;
	        registrationForm.txtTemplate.value=getContent();
        }
    }

function regFX(isRole, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension) {
		if (isRole){
	        var bufWF = document.getElementsByName('component(workflow)');
			var wf = bufWF[0];
			if (wf!=null && (wf.value=="1" || wf.value=="2"))
				if (!confirm("Возможно процесс уже был запущен. Вы действительно хотите запустить процесс?")){
					wf.value="0";
					wf.onchange();
				}
			
			var bufNvp = document.getElementsByName('component(nvp)');
			var nvp = bufNvp[0];
			if (nvp!=null && nvp.value.length>0 && nvp.value!="-1" )
				if (!confirm("Вы действительно хотите отправить данные в БД НВП подразделения ПФР '"+nvp.options[nvp.selectedIndex].text+"'?")){
					nvp.value="-1";
					nvp.onchange();
				}
			var signat = sign("", -1, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension);
			var content = getTemplate();
	        registrationForm.txtSign.value=signat;
	        registrationForm.txtContent.value=content;   
	        registrationForm.txtTemplate.value=getContent();
		}
    }

function getProtocolFromContent(templ, sign, idRegister, isFull, boxId, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension)
{
		var box = document.getElementById(boxId);
		if (transDir == null) transDir = "";
		if (keyDir == null) keyDir = "";
		if (flag == null) flag = 0;
		if (serialNumbers == null) serialNumbers = '';
		if (serialNumbersCA == null) serialNumbersCA = '';
		if (keyUsage == null) keyUsage = '';
		if (extension == null) extension = '.p7s';
		
		var strTempl = "";
		var strSign = "";
		var messVerify = "";
		for (var i=0; i<templ.length; i++){
			strTempl = strTempl+String.fromCharCode(templ[i])
		}
		for (var i=0; i<sign.length; i++){
			strSign = strSign+String.fromCharCode(sign[i])
		}
		
		if (templ.length==0)
		{
			box.innerHTML = "Проверка подписи невозможна: нет содержимого для проверки подписи";
		}
		else
		{
			var content = buildContentForVerify('sectionForVerifySign','DocumentElementValue_',strTempl, idRegister);
			cs_initAndGetProtocolFromContent('clientService',content, strSign,isFull, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension, 
			function (data)
{
	//data = data.replace('<HTML>', '<![CDATA[<HTML>');
	//data = data.replace('</HTML>', '</HTML>]]>');
	var xmlDoc = $.parseXML(data);
	var result = $(xmlDoc).find("result");
	var data1 = $(result).find("data").text();
	var box = document.getElementById(boxId);
	box.innerHTML = data1;
},false);
	}
}

function verifyContent(signResult, showOnlyWrong)
{
	var xmlDoc = $.parseXML(signResult);
		var result = $(xmlDoc).find("result");
		var code = $(result).find('code').text();
		var message = '';
		if (code != 0) {
			message = "<font color='red'>"+$(result).find('code_message').text()+"</font>";
		}
		else
		{
		var signatureStatus = $(result).find("SignatureStatus");
 		var certificateStatus = $(signatureStatus).find("CertificateStatus").text();
 		var verificationStatus = $(signatureStatus).find("VerificationStatus").text();
 		var validCertificate = true;
 		var validVerification = true;
		if (certificateStatus != '0')
         	validCertificate = false;
         	
        if (verificationStatus != '0')
            validVerification = false;
                                            
		if (validVerification && validCertificate && !showOnlyWrong)
          message = "<font color='green'>ЭП верна. Сертификат действителен.</font>";

	    if (validVerification && !validCertificate)
               message = "<font color='red'>ЭП верна. Сертификат недействителен.</font>";
               
        if (!validVerification)
               message = "<font color='red'>ЭП не верна. Целостность документа нарушена.</font>";

		}
		var verInfo = document.getElementById("verifyInfo")
		verInfo.innerHTML = message;
}

function buildContentForVerify(sectionName, prefixName, templateXML, idRegister){
	if (! (initStructure(sectionName, templateXML, idRegister))) return null
	var mdxml=new MDXML()
	var data=new Array
	mdxml.createNode('document',null,false)
	var nodes=xmlStructure.selectNodes("//ITEM[PROPERTIES[@ISSIGNED='true']]")
	var value;
	for (var i=0; i<nodes.length; i++){
		var item=nodes[i].selectSingleNode('NAME')		
		if(item){
			data['name']=item.text
			if (idRegister>=0 && document.getElementById(sectionName+'_'+idRegister))
				value = document.getElementById(sectionName+'_'+idRegister).all(prefixName+data['name'])
			else 
			{
				value = document.getElementById(prefixName+data['name']);
				if (value == null)
				{
					value = new Object();
					value.nodeName = 'INPUT';
					value.type = 'hidden';
					value.name = data['name'];
					value.value = '';
				}
			}
				
			//console.log(data['name'] + '=' + value);
			mdxml.nodeItem(value, data['name'])
		}
	}
	mdxml.closeNode('document')

	return mdxml.xml
}

    
function verify(templateXML, signature, idRegister, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension, showOnlyWrong) {
	try {
			if (transDir == null) transDir = "";
			if (keyDir == null) keyDir = "";
			if (flag == null) flag = 0;
			if (serialNumbers == null) serialNumbers = '';
			if (serialNumbersCA == null) serialNumbersCA = '';
			if (keyUsage == null) keyUsage = '';
			if (extension == null) extension = '.p7s';
		
			var verInfo = document.getElementById("verifyInfo")
			verInfo.innerHTML = "Проверка подписи..."; 
			
			var message = ""
			var content = buildContentForVerify('sectionForVerifySign', 'DocumentElementValue_', templateXML, idRegister);
			
			//console.log('шаблон :'+templateXML);
			//console.log('проверяемый контент : ' +content);
			
			if (!signature){
				message = "<font color='red'> ЭП отсутствует. Целостность документа не подтверждена.</font>";
				var verInfo = document.getElementById("verifyInfo")
				verInfo.innerHTML = message;
			}
			else
			{
				cs_initAndVerifySignContent('clientService', content, signature, 'false', transDir,keyDir,flag,serialNumbers,serialNumbersCA,keyUsage,extension, 
						function (data)
						{
							verifyContent(data, showOnlyWrong);
						}, 
						true);
			}
				
	} catch (e) {
		alert(e.message);
		return e.message;
	}
}

function viewSign(templ, sign, idRegister, transDir, keyDir, flag, serialNumbers, serialNumbersCA, keyUsage, extension) {
	try {
			if (transDir == null) transDir = "";
			if (keyDir == null) keyDir = "";
			if (flag == null) flag = 0;
			if (serialNumbers == null) serialNumbers = '';
			if (serialNumbersCA == null) serialNumbersCA = '';
			if (keyUsage == null) keyUsage = '';
			if (extension == null) extension = '.p7s';
			
			if (idRegister == null)
			idRegister = -1;
			
		var templateXML = "";
		var signature = "";
		var messVerify = "";
		for (var i=0; i<templ.length; i++){
			templateXML = templateXML+String.fromCharCode(templ[i])
		}
		for (var i=0; i<sign.length; i++){
			signature = signature+String.fromCharCode(sign[i])
		}
		
		var content = buildContentForVerify('sectionForVerifySign', 'DocumentElementValue_', templateXML, idRegister);
		
		cs_initAndVerifySignContent('clientService', content, signature, 'true', transDir,keyDir,flag,serialNumbers,serialNumbersCA,keyUsage,extension, 
		function (data) {}, true);
				
	} catch (e) {
		alert(e.message);
		return e.message;
	}
}