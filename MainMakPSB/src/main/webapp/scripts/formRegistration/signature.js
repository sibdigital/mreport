// signature.js

	function regF(isRole) {
		if (isRole){
	        var signat = sign("", -1);
			var content = getTemplate();
	        registrationForm.txtSign.value=signat;
	        registrationForm.txtContent.value=content;
	        registrationForm.txtTemplate.value=getContent();
        }
    }
	
	function regFEmul() {
        registrationForm.txtSign.value=" ";
        alert("����������� �������� ������� ������������!");
    }

	function regFX(isRole) {
		if (isRole){
	        var signat = sign("", -1);
			var content = getTemplate(); 
			var bufWF = document.getElementsByName('component(workflow)');
			var wf = bufWF[0];
			var bufNvp = document.getElementsByName('component(nvp)');
			var nvp = bufNvp[0];
			if (wf!=null && (wf.value=="1" || wf.value=="2"))
				if (!confirm("�� ������������� ������ ��������� �������?")){
					wf.value="0";
					wf.onchange();
				}	
			if (nvp!=null && nvp.value.length>0 && nvp.value!="-1" )
				if (!confirm("�� ������������� ������ ��������� ������ � �� ��� ������������� ��� '"+nvp.options[nvp.selectedIndex].text+"'?")){
					nvp.value="-1";
					wf.onchange();
				}
	        registrationForm.txtSign.value=signat;
	        registrationForm.txtContent.value=content;
	        registrationForm.txtTemplate.value=getContent();
		}
    }
    
    function regFXEmul(isRole) {
		if (isRole){
	        registrationForm.txtSign.value=" ";
        	alert("����������� �������� ������� ������������!");
			var bufWF = document.getElementsByName('component(workflow)');
			var wf = bufWF[0];
			var bufNvp = document.getElementsByName('component(nvp)');
			var nvp = bufNvp[0];
			if (wf!=null && (wf.value=="1" || wf.value=="2"))
				if (!confirm("�� ������������� ������ ��������� �������?"))
					wf.value="0"
					
			if (nvp!=null && nvp.value.length>0 && nvp.value!="-1" )
				if (!confirm("�� ������������� ������ ��������� ������ � �� ��� ������������� ��� '"+nvp.options[nvp.selectedIndex].text+"'?"))
					nvp.value="-1";
		}
    }
