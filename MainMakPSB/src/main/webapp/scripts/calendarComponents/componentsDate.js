function setCalDate(nameElement, value ){
	var part1 = value.length>=4 ? value.substring(0,4) : value;
	var part2 = value.length>=7 ? value.substring(5,7) : (value.length>5 ? value.substring(5) : '');
	var part3 = value.length>=8 ? value.substring(8) : '';
	var mes = "";
	
	var pattern = /\d{4}\-\d{2}\-\d{2}/;
	if (value != null && value.length>0){
		if (!value.match(pattern)){
			return mes = "не соответствует формату : YYYY-MM-DD";
		}else {
			if (parseInt(part2)>12){mes = "максимально возможное значение для указания месяца - 12";							
			}else if (part2=="02" && (parseInt(part1) % 4)==0){
				if (parseInt(part3)>29)	mes = part1+" - високосный год, максимально возможное значение для указания дня месяца - 29";		
			}else if (part2=="02" && (parseInt(part1) % 4)>0){
				if (parseInt(part3)>28)mes = "максимально возможное значение для указания дня месяца - 28";		
			}else if (part2=="04" || part2=="06" || part2=="09" || part2=="11"){
				if (parseInt(part3)>30)mes= "максимально возможное значение для указания дня месяца - 30";		
			}else if (parseInt(part3)>31)mes = "максимально возможное значение для указания дня месяца - 31";
			if(mes.length==0){//если не возникло ошибок валидации, устанавливаем значение
				document.getElementById("d1("+nameElement+")").value = part1;
				document.getElementById("d2("+nameElement+")").value = part2;
				document.getElementById("d3("+nameElement+")").value = part3;					
				document.getElementById("date"+nameElement+"").value = value;					
				document.getElementById("id("+nameElement+")").value = value;					
			}
		}
	}
	return mes;
}
function setElDate(nameElDate, hiddenElDate){
			var elD = document.getElementById("id("+nameElDate+")");
			elD.value = hiddenElDate.value;
			var part1 = document.getElementById("d1("+nameElDate+")");
			var part2 = document.getElementById("d2("+nameElDate+")");
			var part3 = document.getElementById("d3("+nameElDate+")");						
			part1.value=elD.value.substring(0,4);
			part2.value=elD.value.substring(5,7);
			part3.value=elD.value.substring(8);	
			
			var mes = document.getElementById("message("+nameElDate+")");
			mes.innerHTML = "";	
		}		
		
function openCalendar(hiddenElement){ 
	var valuesObject = new Object();
	valuesObject.dateObject = hiddenElement;
	var result = window.showModalDialog("scripts/calendarComponents/cal.jsp", valuesObject, 'dialogWidth=235px; dialogHeight=243px; center=yes; help=no; status=no; scroll=no; unadorned=yes');
	if (result){
		if (result.indexOf("change")==0){
			var values = result.split("|");
			hiddenElement.value = values[1];			
			hiddenElement.onchange();
		}			
	}
}		