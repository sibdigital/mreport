function getMask(txt)
{
	var _result = "";
	var re = /[d.^$]/;
	var reg = /[0-9]/
	for(var i = 0; i < txt.length; i++) {
		if(txt[i]=='\\') {
			if(re.test(txt[i+1])) {
				switch(txt[i+1]) {
					case 'd':
						if(txt[i+2]=='{') {
							var index = 1;
							i += 3;
							while(txt[i] != '}') {
								if(reg.test(txt[i]))
									if(txt[i] > index)
										index = txt[i];
								i++;
							}
							for(var j = 0; j < index; j++)
								_result += 'd';
						}
						else {
							_result += 'd';
							i += 2;
						}
						break;
					case '.':
						_result += '.';
						i++;
						break;
				}
			}
			else {
				_result += txt[i+1];
				i++;
			}
		}
		else{
			if(!re.test(txt[i]))
				_result += txt[i];
		}
	}
	return _result;
}

var dateFormat = "дд.мм.гггг";
var maxYear = 2100;
var minYear = 1900;

function isDate(idtext, idlabel){
	var textElem = document.getElementById(idtext);
	var labelElem = document.getElementById(idlabel);
	var message = 'Неверная дата. Формат должен быть вида: ' + dateFormat;    
	if(textElem == null || labelElem == null)
		return false;
	var dtStr = textElem.value;
	var arr = dtStr.split(".");
	var Day = arr[0]
	var Mn = arr[1] - 1
	var Yr = arr[2]	
	var dt = new Date(Yr, Mn, Day);

	if (dt=="Invalid Date") {
		labelElem.innerHTML = message;
		textElem.focus();	
		return false;	
	}
	
	if (Yr > maxYear || Yr < minYear) {
		labelElem.innerHTML ="Год не находится в допустимом диапазоне: " + minYear + "-" + maxYear;
		textElem.focus();	      	
		return false;
	}

//	if(dt.getDate()!=Day){
//	   	labelElem.innerHTML ="Некорректная дата";
//	   	textElem.focus();
//	    return false; 	     
//	}
//	else if(dt.getMonth()!=Mn){
//	   		labelElem.innerHTML ="Некорректная дата";
//	    	textElem.focus();   
//	   		return false;
//	       }
//	else if(dt.getFullYear()!=Yr){
//	   		labelElem.innerHTML ="Некорректная дата";
//	   		textElem.focus();	     
//	        return false;
//	}
	
	labelElem.innerHTML = "";
	return true;
}

//возращает тип заданной маски
function getTypeMask(mask){
	var result = "notType";
	if(mask == "dd.dd.dddd")
		result = "date";
	if(mask == "ddd-ddd-ddd dd")
		result = "regNum";
	return result;
}

//создает контрольное число для регистрационного номера
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

//type равен 0 если проводится проверка контрольной суммы, 1 если нужно добавить контрольную сумму
function isRegNumber(idtext, idlabel, type){
	var elemtext = document.getElementById(idtext);
	var elemlabel = document.getElementById(idlabel);
	var mas = elemtext.value.substr(0, 11).split("-");
	var number = mas[0] + mas[1] + mas[2];
	var contrSum = createKontrChislo(number);
	switch(type){
	case 0:
		if(elemtext.value.indexOf('_') == -1)
			if(contrSum != elemtext.value.substr(12)){
				elemlabel.innerHTML = "Регистрационный номер введен неверно. Контрольная сумма не совпадает.";
				elemtext.focus();
				return;
			}
			else{
				elemlabel.innerHTML = "";
				return;
			}
		else{
			elemlabel.innerHTML = "";
			return;
		}	
		break;
	case 1:
			if(elemtext.value.indexOf('_') == -1)
				if(contrSum != elemtext.value.substr(12)){
					elemlabel.innerHTML = "Регистрационный номер введен неверно. Контрольная сумма не совпадает. Контрольная сумма равна " + contrSum.toString();
					elemtext.focus();
					return;
				}
				else{
					elemlabel.innerHTML = "";
					return;
				}
			else{
				elemlabel.innerHTML = "";
				return;
			}
	}
}

function getArray(str){
	var arr = str.split(";");
	var result = [];
	for(var i = 0; i < arr.length; i++){
		var mas = arr[i].split("|");
		result.push(mas)
	}
	return result;
}
