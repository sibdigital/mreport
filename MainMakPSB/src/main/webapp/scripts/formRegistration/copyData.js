function copyAnketToDeliveryDest()
	{
		copyAddressUrS("","deliveryDest");
		copyAddressRealS("","deliveryDest");
		var s = 'component(persnum)';
		var d = 'component(deliveryDestPersnum)';
		copyValue(s, d, true);
		s = 'component(family)';
		d = 'component(deliveryDestFamily)';
		copyValue(s, d, true);
		s = 'component(name)';
		d = 'component(deliveryDestName)';
		copyValue(s, d, true);
		s = 'component(patronymic)';
		d = 'component(deliveryDestPatronymic)';
		copyValue(s, d, true);
		s = 'component(birthday)';
		d = 'component(deliveryDestBirthday)';
		copyValue(s, d, true);
		
		var obj = document.getElementById('datedeliveryDestBirthday');
		if (obj!=null)
			if (obj.onchange!=null)
				obj.onchange();
		
		s = 'component(sex)';
		d = 'component(deliveryDestSex)';
		copyValue(s, d, true);
		s = 'component(docPerson)';
		d = 'component(deliveryDestDoc)';
		copyValue(s, d, true);
		s = 'component(seriaDocPerson)';
		d = 'component(deliveryDestDocSeria)';
		copyValue(s, d, true);
		s = 'component(numberDocPerson)';
		d = 'component(deliveryDestDocNumber)';
		copyValue(s, d, true);
		s = 'component(dateDocPerson)';
		d = 'component(deliveryDestDocDate)';
		copyValue(s, d, true);
		
		obj = document.getElementById('datedeliveryDestDocDate');
		if (obj!=null)
			if (obj.onchange!=null)
				obj.onchange();
		
		s = 'component(passOrgDocPerson)';
		d = 'component(deliveryDestDocWhom)';
		copyValue(s, d, true);
		s = 'component(whomDocPerson)';
		d = 'component(deliveryDestDocWhomText)';
		copyValue(s, d, true);
	}
	
	function copyAnketDelegateToDeliveryDest()
	{
		copyAddressUrS("delegate","deliveryDest");
		copyAddressRealS("delegate","deliveryDest");
		var s = 'component(persnumDelegate)';
		var d = 'component(deliveryDestPersnum)';
		copyValue(s, d, true);
		s = 'component(familyDelegate)';
		d = 'component(deliveryDestFamily)';
		copyValue(s, d, true);
		s = 'component(nameDelegate)';
		d = 'component(deliveryDestName)';
		copyValue(s, d, true);
		s = 'component(patronymicDelegate)';
		d = 'component(deliveryDestPatronymic)';
		copyValue(s, d, true);
		s = 'component(birthdayDelegate)';
		d = 'component(deliveryDestBirthday)';
		copyValue(s, d, true);
		
		var obj = document.getElementById('datedeliveryDestBirthday');
		if (obj!=null)
			if (obj.onchange!=null)
				obj.onchange();
		
		d = 'component(deliveryDestSex)';
		var dest = getElementByName(d);
		if (dest != null)
			dest.value='';
		
		s = 'component(docDelegate)';
		d = 'component(deliveryDestDoc)';
		copyValue(s, d, true);
		s = 'component(seriaDelegate)';
		d = 'component(deliveryDestDocSeria)';
		copyValue(s, d, true);
		s = 'component(numberDelegate)';
		d = 'component(deliveryDestDocNumber)';
		copyValue(s, d, true);
		s = 'component(dateDelegate)';
		d = 'component(deliveryDestDocDate)';
		copyValue(s, d, true);
		
		obj = document.getElementById('datedeliveryDestDocDate');
		if (obj!=null)
			if (obj.onchange!=null)
				obj.onchange();
		
		s = 'component(passOrgDelegate)';
		d = 'component(deliveryDestDocWhom)';
		copyValue(s, d, true);
		s = 'component(whomDelegate)';
		d = 'component(deliveryDestDocWhomText)';
		copyValue(s, d, true);
	}