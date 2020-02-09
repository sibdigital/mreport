function validateDate(objDate)
{	
	var objDate = $(objDate);
	var value = objDate.val();
	var d = value.length>=2 ? value.substring(0,2) : '';
	var m = value.length>=5 ? value.substring(3,5) : '';
	var y = value.length>=10 ? value.substring(6,10) : '';
	var mes = "";
	
	if (value.length>0){
		//if (!value.match(pattern))		
		//	mes = "формат даты: YYYY-MM-DD";
		//else 
		{
			if (parseInt(m)>12){
				mes = "максимально возможное значение для указания месяца - 12";
			}else	
			if (d=="02" && (parseInt(y) % 4)==0){
				if (parseInt(d)>29)
					mes = y+" - високосный год, максимально возможное значение для указания дня месяца - 29";		
			}
			else if (d=="02" && (parseInt(y) % 4)>0){
				if (parseInt(d)>28)
					mes = "максимально возможное значение для указания дня месяца - 28";		
			}
			else if (d=="04" || d=="06" || d=="09" || d=="11"){
				if (parseInt(d)>30)
					mes= "максимально возможное значение для указания дня месяца - 30";		
			}
			else if (parseInt(d)>31)
					mes = "максимально возможное значение для указания дня месяца - 31";
		}
	}
	
	{
		var messObj = $('#'+objDate.attr('id')+'message');
		
		if (messObj.length<=0)
		{
			messObj = $("<div id="+objDate.attr('id')+"message style=\"color:red; font-size: 70%\"></div>").insertAfter(objDate);
		}
		messObj.text(mes);
	}
}


function drawDateWithMaska(nameComponent, idGroup, value, createHidden){
	var part1 = value.length>=4 ? value.substring(0,4) : value;
	var part2 = value.length>=7 ? value.substring(5,7) : (value.length>5 ? value.substring(5) : '');
	var part3 = value.length>=8 ? value.substring(8) : '';
	
	var pattern = /\d{4}\-\d{2}\-\d{2}/;
	var mes = "";
		
	if (value.length>0){
		if (!value.match(pattern))		
			mes = "формат даты: YYYY-MM-DD";
		else {
			if (parseInt(part2)>12){
				mes = "максимально возможное значение для указания месяца - 12";							
			}else	
			if (part2=="02" && (parseInt(part1) % 4)==0){
				if (parseInt(part3)>29)
					mes = part1+" - високосный год, максимально возможное значение для указания дня месяца - 29";		
			}
			else if (part2=="02" && (parseInt(part1) % 4)>0){
				if (parseInt(part3)>28)
					mes = "максимально возможное значение для указания дня месяца - 28";		
			}
			else if (part2=="04" || part2=="06" || part2=="09" || part2=="11"){
				if (parseInt(part3)>30)
					mes= "максимально возможное значение для указания дня месяца - 30";		
			}
			else if (parseInt(part3)>31)
					mes = "максимально возможное значение для указания дня месяца - 31";
		}
	}
	if (createHidden)
			document.write(
				"<input type=\"hidden\" id=\"id("+nameComponent+")\" name=\"component("+nameComponent+")\" onchange=\"date"+nameComponent+".value=this.value; createHiddenElement(date"+nameComponent+",'"+nameComponent+"')\" "+
				"value=\""+value+"\"/>\n");
	else
		document.write(
			"<input type=\"hidden\" id=\"id("+nameComponent+")\" name=\"component("+nameComponent+")\" onchange=\"date"+nameComponent+".value=this.value\" "+
			"value=\""+value+"\"/>\n");
	
	document.write(
	"<TABLE id=\"maska\" style=\"border:1px solid #7F9DB9; margin:0px; border-collapse: collapse; groupId:"+idGroup+"\">\n"+
		"<TR>\n"+
			"<TD style=\"padding:0px; margin:0px; padding-left:1px; border:0\">\n"+
				"<input type=\"text\" title=\"год\" style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; margin-right:-3px; width=31; border:0px solid\" id=\"d1("+nameComponent+")\" maxlength=\"4\" value=\""+part1+"\" onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" onkeyup=\"return autoTDate(this, null, 'd2("+nameComponent+")', 4, '"+nameComponent+"')\"/>"+
			"&minus;</td>\n"+
			"<TD style=\"padding:0px; margin:0px; padding-left:1px; border:0\">\n"+
				"<input type=\"text\" title=\"месяц\" style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; margin-right:-3px; width=16; border:0px solid\" id=\"d2("+nameComponent+")\" maxlength=\"2\" value=\""+part2+"\" onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" onkeyup=\"return autoTDate(this, 'd1("+nameComponent+")', 'd3("+nameComponent+")', 2, '"+nameComponent+"')\"/>"+
			"&minus;</td>\n"+
			"<td style=\"padding:0px; margin:0px; padding-left:1px; padding-right:1px; border:0\">\n"+
				"<input type=\"text\" title=\"день\" style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; width=16; border:0px solid\" id=\"d3("+nameComponent+")\" maxlength=\"2\" value=\""+part3+"\" onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" onkeyup=\"return autoTDate(this, 'd2("+nameComponent+")', null, 2, '"+nameComponent+"')\"/>"+
			"</td>\n"+
			"<td style=\"padding:0px; margin:0px; padding-left:3px; padding-right:3px; border:1px solid\">\n"+
				"<a style=\"cursor: pointer\" onclick=\"openCalendar(date"+nameComponent+")\"><img src=\"resources/calendarActive.jpg\" id=\"btn("+nameComponent+")\" align=\"center\" border=\"0\" alt=\"дата\"></a>\n"+
			"</td>\n"+	
		"</TR>\n"+
	"</TABLE>\n"+
	"<label id=\"message("+nameComponent+")\" style=\"font-size:70%; color:red\">"+mes+"</label>\n");

	if (createHidden)
		document.write("<input type=\"hidden\" id=\"date"+nameComponent+"\" name=\"date"+nameComponent+"\" onchange=\"setElDate('"+nameComponent+"',this);createHiddenElement(this,'"+nameComponent+"')\" value=\""+value+"\" />\n");		
	else
		document.write("<input type=\"hidden\" id=\"date"+nameComponent+"\" name=\"date"+nameComponent+"\" onchange=\"setElDate('"+nameComponent+"',this)\" value=\""+value+"\" />\n");				
}

function drawPersnumWithMaska(nameComponent, idGroup, value, createHidden, isAdmin, isVisibleChecksum){
	var part1 = value.length>=3 ? value.substring(0,3) : value;
	var part2 = value.length>=6 ? value.substring(3,6) : (value.length>3 ? value.substring(3) : '');
	var part3 = value.length>=9 ? value.substring(6,9) : (value.length>6 ? value.substring(6) : '');
	var part4 = value.length>9 ? value.substring(9) : '';
	
	var pattern = /\d{11}/;
	var mes = "";
		
	if (value.length>0){
		if (!value.match(pattern))		
			mes = "Неверное значение номера. Формат номера: XXX-XXX-XXX XX";
		else if (part4.length==2){			
			var contr = createKontrChislo(part1 + part2 + part3)
			if (contr!=part4){
				mes = "Неверное контрольное число!";
			if (isVisibleChecksum || isAdmin)
				mes = mes + " Должно быть: "+contr;
			}
		}
	}
	
	document.write(
	"<TABLE id=\"maska\" style=\"border:1px solid #7F9DB9; margin:0px; border-collapse: collapse; groupId:"+idGroup+"\">"+
		"<TR>"+
			"<TD style=\"padding:0px; margin:0px; padding-left:1px\">"+
				"<input type=\"text\" "+
					"style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; margin-right:-3px; width=24; border:0px solid\" "+
					"id=\""+nameComponent+"1\" maxlength=\"3\" value=\""+part1+"\" "+
					"onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" "+
					"onkeyup=\"return autoTPersnum(this, null, "+nameComponent+"2, 3, '"+nameComponent+"', "+isVisibleChecksum+", "+isAdmin+")\"/>"+
			"&minus;</td>"+
			"<TD style=\"padding:0px; margin:0px; padding-left:1px\">"+
				"<input type=\"text\" "+
				"style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; margin-right:-3px; width=24; border:0px solid\" "+
				"id=\""+nameComponent+"2\" maxlength=\"3\" value=\""+part2+"\" "+
				"onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" "+
				"onkeyup=\"return autoTPersnum(this, "+nameComponent+"1, "+nameComponent+"3, 3, '"+nameComponent+"', "+isVisibleChecksum+", "+isAdmin+")\"/>"+
			"&minus;</td>"+
			"<TD style=\"padding:0px; margin:0px; padding-left:1px\">"+
				"<input type=\"text\" "+
				"style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; margin-right:-3px; width=24; border:0px solid\" "+
				"id=\""+nameComponent+"3\" maxlength=\"3\" value=\""+part3+"\" "+
				"onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" "+
				"onkeyup=\"return autoTPersnum(this, "+nameComponent+"2, "+nameComponent+"4, 3, '"+nameComponent+"', "+isVisibleChecksum+", "+isAdmin+")\"/>"+
			"&nbsp;</td>"+
			"<TD style=\"padding:0px; margin:0px; padding-left:1px; padding-right:1px\">"+
				"<input type=\"text\" "+
				"style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; width=16; border:0px solid\" "+
				"id=\""+nameComponent+"4\" maxlength=\"2\" value=\""+part4+"\" "+
				"onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" "+
				"onkeyup=\"return autoTPersnum(this, "+nameComponent+"3, null, 2, '"+nameComponent+"', "+isVisibleChecksum+", "+isAdmin+")\"/>"+
			"</td>"+
		"</TR>"+
	"</TABLE>");
	document.write("<label id=\"message("+nameComponent+")\" style=\"font-size:10px; color:red\">"+mes+"</label>");
	if (createHidden)
		document.write("<input type=\"hidden\" id=\"id("+nameComponent+")\" name=\"component("+nameComponent+")\" onchange=\"createHiddenElement(this,'"+nameComponent+"'); initValue('"+nameComponent+"', this.value)\" ");
	else
		document.write("<input type=\"hidden\" id=\"id("+nameComponent+")\" name=\"component("+nameComponent+")\" onchange=\"initValue('"+nameComponent+"', this.value)\" ");	
	document.write("value=\""+value+"\"/>\n ");		
	//alert(document.getElementById("maska").innerHTML)
}

function drawRegnumWithMaska(nameComponent, idGroup, value, createHidden){
	var part1 = value.length>=3 ? value.substring(0,3) : value;
	var part2 = value.length>=7 ? value.substring(4,7) : (value.length>4 ? value.substring(4) : '');
	var part3 = value.length>8 ? value.substring(8) : '';
	
	var pattern = /\d{3}\-\d{3}\-\d{6}/;
	var mes = "";
		
	if (value.length>0){
		if (!value.match(pattern))		
			mes = "Неверное значение номера. Формат номера: XXX-XXX-XXXXXX";
	}
	if (createHidden)
			document.write(
				"<input type=\"hidden\" id=\"id("+nameComponent+")\" name=\"component("+nameComponent+")\" onchange=\"regn"+nameComponent+".value=this.value; createHiddenElement(regn"+nameComponent+",'"+nameComponent+"')\" "+
				"value=\""+value+"\"/>\n");
	else
		document.write(
			"<input type=\"hidden\" id=\"id("+nameComponent+")\" name=\"component("+nameComponent+")\" onchange=\"regn"+nameComponent+".value=this.value\" "+
			"value=\""+value+"\"/>\n");
	
	document.write(
	"<TABLE id=\"maska\" style=\"border:1px solid #7F9DB9; margin:0px; border-collapse: collapse; groupId:"+idGroup+"\">\n"+
		"<TR>\n"+
			"<TD style=\"padding:0px; margin:0px; padding-left:1px\">\n"+
				"<input type=\"text\" title=\"\" style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; margin-right:-3px; width=24; border:0px solid\" id=\"r1("+nameComponent+")\" maxlength=\"3\" value=\""+part1+"\" onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" onkeyup=\"return autoTRegnum(this, null, 'r2("+nameComponent+")', 3, '"+nameComponent+"')\"/>"+
			"&minus;</td>\n"+
			"<TD style=\"padding:0px; margin:0px; padding-left:1px\">\n"+
				"<input type=\"text\" title=\"\" style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; margin-right:-3px; width=24; border:0px solid\" id=\"r2("+nameComponent+")\" maxlength=\"3\" value=\""+part2+"\" onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" onkeyup=\"return autoTRegnum(this, 'r1("+nameComponent+")', 'r3("+nameComponent+")', 3, '"+nameComponent+"')\"/>"+
			"&minus;</td>\n"+
			"<td style=\"padding:0px; margin:0px; padding-left:1px; padding-right:1px\">\n"+
				"<input type=\"text\" title=\"\" style=\"font-size:90%; padding-left:0px; padding-right:0px; margin:0px; width=40; border:0px solid\" id=\"r3("+nameComponent+")\" maxlength=\"6\" value=\""+part3+"\" onmousedown=\"initCur(this)\" onkeydown=\"initCur(this); return validate(this);\" onkeyup=\"return autoTRegnum(this, 'r2("+nameComponent+")', null, 6, '"+nameComponent+"')\"/>"+
			"</td>\n"+
		"</TR>\n"+
	"</TABLE>\n"+
	"<label id=\"message("+nameComponent+")\" style=\"font-size:70%; color:red\">"+mes+"</label>\n");

	if (createHidden)
		document.write("<input type=\"hidden\" id=\"regn"+nameComponent+"\" name=\"regn"+nameComponent+"\" onchange=\"setElRegnum('"+nameComponent+"',this);createHiddenElement(this,'"+nameComponent+"')\" value=\""+value+"\" />\n");		
	else
		document.write("<input type=\"hidden\" id=\"regn"+nameComponent+"\" name=\"regn"+nameComponent+"\" onchange=\"setElRegnum('"+nameComponent+"',this)\" value=\""+value+"\" />\n");				
}

function initValue(nameComponent, value){
	var part1 = value.length>=3 ? value.substring(0,3) : value;
	var part2 = value.length>=6 ? value.substring(3,6) : (value.length>3 ? value.substring(3) : '');
	var part3 = value.length>=9 ? value.substring(6,9) : (value.length>6 ? value.substring(6) : '');
	var part4 = value.length>9 ? value.substring(9) : '';
	
	document.getElementById(nameComponent+'1').value = part1;
	document.getElementById(nameComponent+'2').value = part2;
	document.getElementById(nameComponent+'3').value = part3;
	document.getElementById(nameComponent+'4').value = part4;
}

function deactivateDate(nameComponent){
	var dateEndHiddenComponent = document.getElementById("id("+nameComponent+")");
	dateEndHiddenComponent.value="";
	var dateEndHiddenCalendar = document.getElementById("date"+nameComponent);
	dateEndHiddenCalendar.value="";
	
	var dateBegin = document.getElementById("d1("+nameComponent+")");			
	dateBegin.value="";
	dateBegin.disabled = true;
	dateBegin = document.getElementById("d2("+nameComponent+")");
	dateBegin.disabled = true;
	dateBegin.value="";
	dateBegin = document.getElementById("d3("+nameComponent+")");
	dateBegin.disabled = true;
	dateBegin.value="";
				
	var btnB = document.getElementById("btn("+nameComponent+")");
	var onclicktB = btnB.onclick;
    btnB.onclick="";
	btnB.alt="";
	btnB.style.cursor="auto";
	btnB.src="resources/calendarNoActive.jpg"
	btnB.parentNode.parentNode.parentNode.parentNode.parentNode.style.borderColor = "#ccc";
	
	return onclicktB
}

function activateDate(nameComponent, action){
	var now = new Date();
	var NowYear = now.getYear();
	var NowMonth = now.getMonth()<9 ? "0"+(now.getMonth() + 1) : (now.getMonth() + 1);
	var NowDay = now.getDate()<10 ? "0"+now.getDate() : now.getDate();
	if (NowYear < 2000) NowYear += 1900; //for Netscape
	var date = NowYear+"-"+NowMonth+"-"+NowDay
       				
	var dateEnd = document.getElementById("d1("+nameComponent+")");
	dateEnd.disabled = false;
	dateEnd.value=NowYear;
	dateEnd = document.getElementById("d2("+nameComponent+")");
	dateEnd.disabled = false;
	dateEnd.value=NowMonth;
	dateEnd = document.getElementById("d3("+nameComponent+")");
	dateEnd.disabled = false;
	dateEnd.value=NowDay;
	var dateEndHiddenComponent = document.getElementById("id("+nameComponent+")");
	dateEndHiddenComponent.value=date;
	var dateEndHiddenCalendar = document.getElementById("date"+nameComponent);
	dateEndHiddenCalendar.value=date;

	var btnE = document.getElementById("btn("+nameComponent+")");		
 	btnE.onclick=action;
    btnE.style.cursor="pointer";
	btnE.alt="дата";
 	btnE.src="resources/calendarActive.jpg"
 	btnE.parentNode.parentNode.parentNode.parentNode.parentNode.style.borderColor = "#7F9DB9";
}

function setElRegnum(nameElRegnum, hiddenElRegnum){
			var elR = document.getElementById("id("+nameElRegnum+")");
			elR.value = hiddenElRegnum.value;
			var part1 = document.getElementById("r1("+nameElRegnum+")");
			var part2 = document.getElementById("r2("+nameElRegnum+")");
			var part3 = document.getElementById("r3("+nameElRegnum+")");						
			part1.value=elR.value.substring(0,3);
			part2.value=elR.value.substring(4,7);
			part3.value=elR.value.substring(8);	
			
			var mes = document.getElementById("message("+nameElRegnum+")");
			mes.innerHTML = "";	
		}