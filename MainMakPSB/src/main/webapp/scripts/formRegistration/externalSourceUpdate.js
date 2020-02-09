function expandGroup(groupId)
{
	var groupName = "groupPlus"+groupId;
	$("[name='"+groupName+"']").each(
			function (n) {
					var parentGroupId = this.parentGroupId;
					if (parentGroupId != null)
						expandGroup(parentGroupId);
					if (this.onclick != null && this.style.display=='') 
					{
						this.onclick();
					}
				});
}

function getValuesFromExternalSource(action, nameComponentPersnum, idSource)
	{
		var obj = getElementByName('component('+nameComponentPersnum+')');
		var valuePersnum = obj.value;
		var arrayComps = [];
		var arrayPanel = [];
		
		$.ajax({
			type: "GET",
            url: "/clientServiceWeb/getExternalDataAction.do",
            data: {action: "getElements", nameComponentPersnum: nameComponentPersnum},
            contentType: "charset=utf-8",
            dataType: "json",
            cache: false,
            async: false,
            success: function(data)
            {
   		 		for (var i=0; i<data.length; i++)
   		 		{ 
   		 			var name = data[i];
   		 			var panelName = 'updateDataPanel'+name;
   		 			
   		 			var comp = null;
   		 			$("input[inputComponent='"+name+"']").each(
   		 					function (n) {arrayComps.push(this); this.disabled = true; comp = this;});
   		 			$("span[inputComponent='"+name+"']").each(
		 					function (n) {arrayComps.push(this); this.disabled = true; comp = this;});
   		 			
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
   		 				}
   		 				
   		 				arrayPanel.push(panel);
   		 				panel.css('display', 'inline-block');
   		 			}
   		 		}
   		 	
            },
            error : function(data)
            {
            	alert("Ошибка: " + data.status + " " + data.statusText);
            },
            beforeSend : function () {
            	
			    },
			 complete : function () {
			        
			    }
		});
		
		$.ajax({
            type: "GET",
            url: "/clientServiceWeb/getExternalDataAction.do",
            data: {action: action, nameComponentPersnum: nameComponentPersnum, idSource: idSource, valuePersnum: valuePersnum},
            contentType: "charset=utf-8",
            dataType: "json",
            cache: false,
            async: true,
            success: function(data)
            {
            	if (data != null)
            	{
   		 			for (var i=0; i<data.length; i++)
   		 			{
   		 				var name = data[i].name;
   		 				var value = data[i].value;
   		 				var comp = getElementByName('component('+name+')');
   		 				if (comp != null)
   		 				{
   		 					var groupId = comp.groupId;
   		 					
   		 					if (groupId != null)
   		 						expandGroup(groupId);
   		 				
   		 					comp.value = value;
   		 					if (comp.onchange != null)
   		 						comp.onchange();
   		 				}
   		 			}
            	}
   		 	
            },
            error : function(data)
            {
            	for (var i=0; i < arrayComps.length; i++) {
		    		arrayComps[i].disabled = false;
		    	}
		        
		    	for (var i=0; i < arrayPanel.length; i++) {
		    		arrayPanel[i].css('display', 'none');
		    	}
		    	alert("Ошибка: " + data.status + " " + data.statusText);
            },
            beforeSend : function () {
            	
			    },
			 complete : function () {
			    	
			    	for (var i=0; i < arrayComps.length; i++) {
			    		arrayComps[i].disabled = false;
			    	}
			        
			    	for (var i=0; i < arrayPanel.length; i++) {
			    		arrayPanel[i].css('display', 'none');
			    	}
			    }
          });
	}