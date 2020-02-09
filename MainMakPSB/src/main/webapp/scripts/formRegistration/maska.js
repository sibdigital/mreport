
var prevCur = 0;

function autoT(input, prevInput, nextInput, len){
var cur = doGetCaretPosition(input);
var number;
if (event.keyCode>=96 && event.keyCode<=105)
	number = parseInt(String.fromCharCode(event.keyCode-48));
else
	number = parseInt(String.fromCharCode(event.keyCode));
if (cur==len && nextInput!=null && event.keyCode!=8 && event.keyCode!=46){
	nextInput.focus();
	if (prevCur!=cur-1){		
		if (number>=0 && number<=9){
			nextInput.value = number;
			setCaretPosition(nextInput, 1);		
			}
		}
	else{
		if (event.keyCode==39)
			setCaretPosition(input, cur);
		else{
			setCaretPosition(nextInput, 0);	
			input.onkeydown=function(){event.returnValue = false;}
			}
		}
	}

if (prevInput!=null)
	prevInput.onkeydown=function(){initCur(this); event.returnValue = true;}	
	
if (cur==0 && prevCur==0 && (number>=0 && number<=9) ){
	input.value = number;
	event.returnValue = false;
}
else if (cur==0 && prevCur==0 && prevInput!=null && (event.keyCode==8 || event.keyCode==37)){
	prevInput.focus();
	setCaretPosition(prevInput, prevInput.value.length);
	}	
}

function autoTPersnum(input, prevInput, nextInput, len, component, isVisibleChecksum, isAdmin){
	autoT(input, prevInput, nextInput, len);
	setInsnmb(component, isVisibleChecksum, isAdmin);
}

function autoTDate(input, prevI, nextI, len, component){
	var prevInput = prevI==null ? null : document.getElementById(prevI);
	var nextInput = nextI==null ? null : document.getElementById(nextI);
	autoT(input, prevInput, nextInput, len);
	setDate(component);
}

function autoTRegnum(input, prevI, nextI, len, component){
	var prevInput = prevI==null ? null : document.getElementById(prevI);
	var nextInput = nextI==null ? null : document.getElementById(nextI);
	autoT(input, prevInput, nextInput, len);
	setRegnum(component);
}

function doGetCaretPosition (ctrl) {
	var CaretPos = 0;

	if (document.selection) {
		ctrl.focus ();
		var Sel = document.selection.createRange ();
		Sel.moveStart ('character', -ctrl.value.length);
		CaretPos = Sel.text.length;
	}

	return (CaretPos);
}

function setCaretPosition(ctrl, pos)
{
	if(ctrl.setSelectionRange)
	{
		ctrl.focus();
		ctrl.setSelectionRange(pos,pos);
	}
	else if (ctrl.createTextRange) {
		var range = ctrl.createTextRange();
		range.collapse(true);
		range.moveEnd('character', pos);
		range.moveStart('character', pos);
		range.select();
	}
}

function initCur(element){
	prevCur = doGetCaretPosition(element);
}

function validate(element){
	if (event.keyCode==13 || event.keyCode==37 || event.keyCode==39 || event.keyCode==8 || event.keyCode==46 || event.keyCode==9 || event.keyCode==16 || (event.keyCode>=48 && event.keyCode<=57) || (event.keyCode>=96 && event.keyCode<=105)){
		element.onkeydown=function(){initCur(this); return validate(this);};
		event.returnValue = true;
		return true;
		}
	else{
		element.onkeydown=function(){initCur(this); return validate(this);}
		event.returnValue = false;
		return false;
	}
}

function setInsnmb(component, isVisibleChecksum, isAdmin){
	var insnmb = document.getElementById("id("+component+")")
	var mes = document.getElementById("message("+component+")");
	if (insnmb!=null){
		var part1 = document.getElementById(component+"1");
		var part2 = document.getElementById(component+"2");
		var part3 = document.getElementById(component+"3");
		var part4 = document.getElementById(component+"4");
		
		if (part1.value+part2.value+part3.value+part4.value!=""){
	
			insnmb.value = part1.value+part2.value+part3.value+part4.value;			
			mes.innerHTML = "";
			var contr = "";
			
			if ((part1.value+part2.value+part3.value).length==9)
				contr = createKontrChislo(part1.value+part2.value+part3.value);
			if (part4.value.length==2){			
				if ((part1.value+part2.value+part3.value).length!=9)
					mes.innerHTML = "Заполнены не все поля. <br/>Формат номера: XXX-XXX-XXX XX";
				else {
					if (contr!=part4.value){
						mes.innerHTML = "Неверное контрольное число!";
						if (isVisibleChecksum || isAdmin)
							mes.innerHTML = mes.innerHTML + " Должно быть: "+contr;
						}
					}
			}else if ((part1.value+part2.value+part3.value).length==9 && part4.value.length<2){
				if (isVisibleChecksum || isAdmin)
					mes.innerHTML = "Заполните контрольное число ("+contr+"). Формат номера: XXX-XXX-XXX XX";
				else
					mes.innerHTML = "Заполните контрольное число. <br/>Формат номера: XXX-XXX-XXX XX";
			}
			else
				mes.innerHTML = "Заполнены не все поля. <br/>Формат номера: XXX-XXX-XXX XX";
		}
		else{
			insnmb.value = "";
			insnmb.onchange();
			mes.innerHTML = "";
		}
	}
}

function setDate(component){
	var componentDate = document.getElementById("id("+component+")");
	if (componentDate!=null){
		var part1 = document.getElementById("d1("+component+")");
		var part2 = document.getElementById("d2("+component+")");
		var part3 = document.getElementById("d3("+component+")");
		var mes = document.getElementById("message("+component+")");
		
		if (part1.value+part2.value+part3.value!=""){
			componentDate.value = part1.value+"-"+part2.value+"-"+part3.value;
			componentDate.onchange();			
			mes.innerHTML = "";
			
			//if (document.activeElement.id != part1.id)
				//if (part1.value.length<4)
					//mes.innerHTML = "год должен состоять из 4 символов (формат даты: YYYY-MM-DD)";
			
			//if (document.activeElement.id != part2.id){
				//if (part2.value.length<2)
					//mes.innerHTML = "месяц должен состоять из 2 символов (формат даты: YYYY-MM-DD)";
			//}
			
			var pattern = /\d{4}\-\d{2}\-\d{2}/;
			
			if (!componentDate.value.match(pattern))		
				mes.innerHTML = "формат даты: YYYY-MM-DD";
			
			if (parseInt(part2.value)>12)
				mes.innerHTML = "максимально возможное значение - 12";							
			else if (document.activeElement.id == part3.id){
				//if (part3.value.length<2)
					//mes.innerHTML = "день должен состоять из 2 символов (формат даты: YYYY-MM-DD)";
				if (part2.value=="02" && (parseInt(part1.value) % 4)==0){
					if (parseInt(part3.value)>29)
						mes.innerHTML = part1.value+" - високосный год, максимально возможное значение - 29";		
				}
				else if (part2.value=="02" && (parseInt(part1.value) % 4)>0){
					if (parseInt(part3.value)>28)
						mes.innerHTML = "максимально возможное значение - 28";		
				}
				else if (part2.value=="04" || part2.value=="06" || part2.value=="09" || part2.value=="11"){
					if (parseInt(part3.value)>30)
						mes.innerHTML = "максимально возможное значение - 30";		
				}
				else if (parseInt(part3.value)>31)
						mes.innerHTML = "максимально возможное значение - 31";
			}
	
			//var pattern = /\d{4}\-\d{2}\-\d{2}/;
			
			//if (!componentDate.value.match(pattern))		
				//mes.innerHTML = "error: YYYY-MM-DD";
		}
		else{
			componentDate.value = "";
			componentDate.onchange();
			mes.innerHTML = "";
		}
	}
}

function setRegnum(component){
	var componentRegnum = document.getElementById("id("+component+")");
	if (componentRegnum!=null){
		var part1 = document.getElementById("r1("+component+")");
		var part2 = document.getElementById("r2("+component+")");
		var part3 = document.getElementById("r3("+component+")");
		var mes = document.getElementById("message("+component+")");
		
		if (part1.value+part2.value+part3.value!=""){
			componentRegnum.value = part1.value+"-"+part2.value+"-"+part3.value;
			componentRegnum.onchange();			
			mes.innerHTML = "";
			
			var pattern = /\d{3}\-\d{3}\-\d{6}/;
			
			if (!componentRegnum.value.match(pattern))		
				mes.innerHTML = "Неверное значение номера. Формат номера: XXX-XXX-XXXXXX";
		}
		else{
			componentRegnum.value = "";
			componentRegnum.onchange();
			mes.innerHTML = "";
		}
	}
}

function sdf_FTS(_number,_decimal,_separator)
	// _number - любое число
	// _decimal - число знаков после запятой
	// _separator - разделитель разрядов
	{
	// определяем, количество знаков после точки, по умолчанию выставляется 2 знака
	var decimal=(typeof(_decimal)!='undefined')?_decimal:2;
	
	// определяем, какой будет сепаратор [он же разделитель] между разрядами
	var separator=(typeof(_separator)!='undefined')?_separator:'';
	
	// преобразовываем входящий параметр к дробному числу, если вдруг
	// входящий параметр будет не корректным
	var r=parseFloat(_number)
	
	// так как в JavaScript нет функции для фиксации дробной части после точки
	// то выполняем своеобразный fix
	var exp10=Math.pow(10,decimal);// приводим к правильному множителю
	r=Math.round(r*exp10)/exp10;// округляем до необходимого числа знаков после запятой
	
	// преобразуем к строгому, фиксированному формату, так как в случае вывода целого числа
	// нули отбрасываются не корректно, то есть целое число должно
	// отображаться 1.00, а не 1
	rr=Number(r).toFixed(decimal).toString().split('.');
	
	// разделяем разряды в больших числах, если это необходимо
	// то есть, 1000 превращаем 1 000
	b=rr[0].replace(/(\d{1,3}(?=(\d{3})+(?:\.\d|\b)))/g,"\$1"+separator);
	
	// можно использовать r=b+'.'+rr[1], но при 0 после запятой выходит ошибка undefined,
	// поэтому применяем костыль
	r=(rr[1]?b+'.'+rr[1]:b);
	
	return r;// возвращаем результат
}

