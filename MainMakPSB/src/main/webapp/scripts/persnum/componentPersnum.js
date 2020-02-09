
	function initPersnum(compInputId, labelErrorId, type, hiddenComponentId) {
		var input = $("#"+compInputId);
		var onCompleted = function(){
			var elem = null;
			if (labelErrorId != null)
				elem = document.getElementById(labelErrorId);
			else
				labelErrorId = compInputId+'ErrorBox';
			
			if (elem == null) {
				elem = $('<small style="color: green" id='+labelErrorId+'></small>');
	 			elem.insertAfter(input);
			}
			else
				elem = $(elem);
			
			if(isPersnum(compInputId, labelErrorId, type)){
				} else elem.css("color", "red");

			 };
			 
		input.mask("999-999-999 99", {"completed": onCompleted, "fill0": true});
		if (hiddenComponentId != null)
		{
			var onChange = function() 
			{
				var hidden = $('#id'+hiddenComponentId);
				var persnum=this.value;
				if (persnum != null && persnum.length > 0)
    			{
    				var d1 = persnum.substring(0,3);
					var d2 = persnum.substring(4,7);
					var d3 = persnum.substring(8,11);
					var d4 = persnum.substring(12,14);
				
					hidden.val(d1+d2+d3+d4);
				}
				else
					hidden.val('');
				
				hidden.change();
			}
			
			input.bind("change", onChange);
		}
		
		input.bind("keyup", onCompleted);
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
function isPersnum(idtext, idlabel, type){
	var elemtext = document.getElementById(idtext);
	var elemlabel = document.getElementById(idlabel);
	elemlabel.innerHTML = "";
	var mas = elemtext.value.substr(0, 11).split("-");
	var number = mas[0] + mas[1] + mas[2];
	
	if (elemtext.value.indexOf('_') >= 12 || elemtext.value.indexOf('_') == -1)
	{
	var contrSum = createKontrChislo(number);
	if (elemtext.value.length<=14 && elemtext.value.indexOf('_') >= 12)
	{
		if (type==1)
			elemlabel.innerHTML = "Заполните контрольное число (" + contrSum.toString()+")";
		else
			elemlabel.innerHTML = "Заполните контрольное число";
	}
	else
	{
	switch(type){
	case 0:
		if(elemtext.value.length>14 || elemtext.value.indexOf('_') == -1)
			if(contrSum != elemtext.value.substr(12,2)){
				elemlabel.innerHTML = "Контрольное число не верно";
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
			if(elemtext.value.length>14 || elemtext.value.indexOf('_') == -1)
				if(contrSum != elemtext.value.substr(12,2)){
					elemlabel.innerHTML = "Контрольное число не верно. Должно быть: " + contrSum.toString();
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
	}
}

function formatPersnum(num) {
    	if (num != null && num.length>=11)
    	{
	    	var persnum = num.substring(0, 3) + "-" + num.substring(3, 6) + 
					"-" + num.substring(6, 9) + " " + num.substring(9, 11);
			return persnum;
        }
        else
	        return '';
}