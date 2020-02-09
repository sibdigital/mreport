function IsNotEmpty(objNames)
{
	var v = false;
	if (objNames.length)
    {
        for (var i = 0; i < objNames.length; i++)
        {
			var objs = document.getElementsByName(objNames[i]);
			if (objs.length>0)
			{
				v = v || validateObj(objs[0]);
			}
        }
    }
	
    return v;
}

function validateObj(field) {
                var isValid = false;
                var i = 0;
                
                    if (field.type == 'text' ||
                        field.type == 'textarea' ||
                        field.type == 'file' ||
                        field.type == 'select-one' ||
                        field.type == 'radio' ||
                        field.type == 'password' ||
                        field.type == 'hidden') {
                        isValid = true;
                        
                        var value = '';
						// get field's value
						if (field.type == "select-one") {
							var si = field.selectedIndex;
							if (si >= 0) {
								value = field.options[si].value;
							}
							if (value == -1)
								isValid = false;
								
						} else {
							value = field.value;
						}

                        if (value == null || trim(value).length == 0) {
	                        isValid = false;
                        }
                    }
                    
                return isValid;
            }

function validateRequired(fieldId) {
   var field = document.getElementById(fieldId);
   return  validateObj(field);
}

            // Trim whitespace from left and right sides of s.
function trim(s) {
                return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
            }