
(function () {

    jQuery.fn.combobox = function (hiddenElement, selectOptions, raionID, onchangeFunc,edit, favoriteList, onOpenListFunc, isSaveText, isShowCode) {
    
        return this.each(function () {
            var newCombobox = new Combobox(this,hiddenElement, selectOptions, raionID, onchangeFunc,edit, favoriteList, onOpenListFunc, isSaveText, isShowCode);
        });
    };


    var Combobox = function (textInputElement, hiddenElement, selectOptions, raionID, onchangeFunc, edit, favoriteList, onOpenListFunc, isSaveText, isShowCode) {
		this.edit = edit;
		this.isSaveText = isSaveText;
		this.isShowCode = isShowCode;
		this.favoriteList = [];
		this.setFavoriteList(favoriteList);
		
		if (isSaveText == null)
			this.isSaveText = false;
		
		this.onchangeFunc = onchangeFunc;
		this.onOpenListFunc = onOpenListFunc;
        this.textInputElement = jQuery(textInputElement);
		this.hiddenElement = jQuery('#'+hiddenElement);
		
        var container = this.textInputElement.wrap(
            '<span class="combobox" style="position:relative; '+
            'display:-moz-inline-box; display:inline-block;"/>'
        );
        this.selector = new ComboboxSelector(this);
		this.selector.setRaionID(raionID);
        this.setSelectOptions(selectOptions);
        this.loadedData = false;
        
		var thisSelector = this.selector;
		var thisCombobox = this;
		
		if (this.textInputElement.val() == '')
		{
			if (!edit)
			{
				var val = this.selector.getSelectedValueByCode(this.getCode());
				this.textInputElement.val(val);
			}
			else
				{
					var val = this.getValuePartOfCode();
					this.textInputElement.val(val);
				}
		}
		//else
		//	thisSelector.buildSelectOptionList(this.textInputElement.val());
			
        var inputHeight = 19;//this.textInputElement.outerHeight();
		var inputWidth = 17;
        var buttonLeftPosition = this.textInputElement.outerWidth() + 0;
        var showSelectorButton = jQuery(
            '<a href="#" class="combobox_button" '+
            'style="white-space: nowrap; position:relative; height:'+inputHeight+'px; width:'+
            inputWidth+'px; top:0; left:0px; display: inline-block;">&nbsp;</a>'
        ).insertAfter(this.textInputElement);
          	
        //this.textInputElement.css('margin', '0 '+showSelectorButton.outerWidth()+'px 0 0');
		this.showSelectorButton = showSelectorButton;
		if (raionID>0)
		{
		var showAllLeftPos = buttonLeftPosition + showSelectorButton.outerWidth()+10;
		var showAllButton = jQuery(
            '<a href="#"  '+
            'style="position:absolute; height:'+inputHeight+'px; width:'+
            inputWidth+'px; top:0; left:'+showAllLeftPos+'px;"><div>полный&nbsp;справочник</div></a>'
        ).insertAfter(showSelectorButton);
		
		
		showAllButton.click(function (e) {
			if (thisSelector.isShowAll())
			{
				showAllButton.find('div').get(0).innerHTML = 'полный&nbsp;справочник';
				thisSelector.setShowAll(false);
			}
			else
			{
				showAllButton.find('div').get(0).innerHTML = 'сокращенный&nbsp;справочник';
				thisSelector.setShowAll(true);
			}
			return false;
        });
		}
		else
		{
			thisSelector.setShowAll(true);
		}
		
		//var funcAreaTextAreaButton = function() {}
		
        showSelectorButton.click(function (e) {
        	if (!textInputElement.disabled)
        	{
        		if (onOpenListFunc != null)
        			onOpenListFunc(thisCombobox.hiddenElement.get(0), thisCombobox);
        		jQuery('html').trigger('click');
        		thisSelector.buildSelectOptionList();
        		thisSelector.show();
        		thisCombobox.focus();
        	}
            return false;
        });
        this.bindKeypress();
		this.bindBlur();
    };

    Combobox.prototype = {

        setSelectOptions : function (selectOptions) {
            this.selector.setSelectOptions(selectOptions);
        },
        
        setRaionID : function (value) {
        	this.selector.setRaionID(value);
		},
		
		buildSelectOptionList: function() {
			this.selector.buildSelectOptionList()
		},
		
		show : function () {
        	this.selector.show();
		},
		
		setShowAll: function(value) {
			this.selector.setShowAll(value);
		},
		
		hide : function () {
			this.selector.hide();
		},

        bindKeypress : function () {
            var thisCombobox = this;
            
            this.textInputElement.keyup(function (event) {
            	
            	if (!thisCombobox.loadedData)
                {
                	if (thisCombobox.onOpenListFunc != null)
                		thisCombobox.onOpenListFunc.call(window, thisCombobox.hiddenElement.get(0), thisCombobox);
                	thisCombobox.loadedData = true;
                }
            	
                if (event.keyCode == Combobox.keys.TAB
                    || event.keyCode == Combobox.keys.SHIFT) 
                {
                    return;
                }
                if (event.keyCode != Combobox.keys.DOWNARROW
                    && event.keyCode != Combobox.keys.UPARROW
                    && event.keyCode != Combobox.keys.ESCAPE
                    && event.keyCode != Combobox.keys.ENTER)
                {
					if (!thisCombobox.edit && !thisCombobox.isSaveText)
						{
							thisCombobox.textInputElement.css('color','red');
						}
						
		            
					thisCombobox.setCode(-1);
					thisCombobox.selector.buildSelectOptionList(thisCombobox.getValue());
				
                    //if (!thisCombobox.selector.buildSelectOptionList(thisCombobox.getValue()))
					//thisCombobox.setValue(thisCombobox.getValue().substring(thisCombobox.getValue(),thisCombobox.getValue().length-1));
					 
                }
                thisCombobox.selector.show();
            });
        },
		
		bindBlur : function () {
			var thisCombobox = this;
			{
				this.textInputElement.blur(function (event) {
				{
					if (!thisCombobox.edit && !thisCombobox.isSaveText)
					{
						thisCombobox.setValue(thisCombobox.selector.getSelectedValueByCode(thisCombobox.hiddenElement.val()));
						thisCombobox.textInputElement.css('color','black');
					}
				}
            });			
			}
		},
        
        setValue : function (value) {
            this.textInputElement.val(value);
        },

        getValue : function () {
            return this.textInputElement.val();
        },
        
        focus : function () {
            this.textInputElement.trigger('focus');
        },
		
		setCode: function (value) {
			var code = this.getCode();
			if (this.edit)
			{
				this.hiddenElement.val(this.textInputElement.val()+"|"+value);
				this.textInputElement.css('color','black');
			}
			else
			{
				this.hiddenElement.val(value);
				if (value != -1)
					this.textInputElement.css('color','black');
			}
				
			if (code != value)
				this.onchangeFunc.call(window,this.hiddenElement.get(0));
		},
		
		getCode: function () {
			if (this.edit)
			{
				var values = this.hiddenElement.val().split("|");
				return values[1];
			}
			else
				return this.hiddenElement.val();
		},
		
		getValuePartOfCode: function () {
			var values = this.hiddenElement.val().split("|");
			return values[0];
		},
		
		setFavoriteList: function(favoriteList) {
			this.favoriteList = favoriteList;
		},
		
		getFavoriteList: function() {
			return this.favoriteList;
		}
    };

    Combobox.keys = {
        UPARROW : 38,
        DOWNARROW : 40,
        ENTER : 13,
        ESCAPE : 27,
        TAB : 9,
        SHIFT : 16
    };



    var ComboboxSelector = function (combobox) {
        this.combobox = combobox;
        this.optionCount = 0;
        this.selectedIndex = -1;
		this.raionID = -1;
        this.allSelectOptions = [];
		this.showAll = false;
        var selectorTop = 19;//combobox.textInputElement.outerHeight();
        var selectorWidth = combobox.textInputElement.outerWidth() + 17;
        this.selectorElement = jQuery(
            '<div class="combobox_selector" '+
            'style="display:none; width:'+selectorWidth+
            'px; height: 150px; overflow: auto; position:absolute; left: 0; top: '+selectorTop+'px;"'+
            '></div>'
        ).insertAfter(this.combobox.textInputElement);
		
		this.frame = jQuery("<!--[if IE 6]> <iframe src=javascript:'&lt;html&gt;&lt;/html&gt;'; scrolling='no' width='"+
		selectorWidth+"px' height='152px' "+
    "frameborder='0' hspace='0' vspace='0' style='display: none; position: absolute; z-index:-1; left: 0; top: "+selectorTop+";'> </iframe> <![endif]-->").insertAfter(this.selectorElement);
	
        var thisSelector = this;
        jQuery('html').click(function () {
            thisSelector.hide();
        });
        this.keypressHandler = function (e) {
            if (e.keyCode == Combobox.keys.DOWNARROW) {
                thisSelector.selectNext();
            } else if (e.keyCode == Combobox.keys.UPARROW) {
                thisSelector.selectPrevious();
            } else if (e.keyCode == Combobox.keys.ESCAPE) {
                thisSelector.hide();
                thisSelector.combobox.focus();
            } else if (e.keyCode == Combobox.keys.ENTER) {
                thisSelector.combobox.setValue(thisSelector.getSelectedValue());
				thisSelector.combobox.setCode(thisSelector.getSelectedCode());
                thisSelector.combobox.focus();
                thisSelector.hide();
            }
            return false;
        }
        
    }


    ComboboxSelector.prototype = {

        setSelectOptions : function (selectOptions) {
            this.allSelectOptions = selectOptions;
        },
		
		setRaionID : function (value) {
			this.raionID = value;
		},

        buildSelectOptionList : function (startingLetters) {
            if (! startingLetters) {
                startingLetters = "";
            }
            else
            	this.combobox.setCode(-1);
            
            this.unselect();
            this.selectorElement.empty();
            var selectOptions = [];
			this.options = selectOptions;
            this.selectedIndex = -1;
			var result = false;
			
			this.optionCount = 0; //selectOptions.length;  // количество строк в списке
			this.number=-1;
			
			this.maxCount = 100;
			this.startingLetters = startingLetters;
			
            //this.optionCount = selectOptions.length;
            	
            var ulElement = jQuery('<ul></ul>').appendTo(this.selectorElement);
			this.ulElement1 = ulElement;
			
			this.buildList(startingLetters, this.maxCount);
			if (this.optionCount < this.allSelectOptions.length && this.optionCount >= this.maxCount)
			{
			var button = jQuery('<a href="#" name="b" class="combobox_selector">далее<a/>');
			button.appendTo(this.selectorElement);
			this.nohide = false;
			button.click(function (e) {
				thisSelector.nohide = true;
				thisSelector.buildList(this.startingLetters, thisSelector.maxCount);
				thisSelector.combobox.focus();			
				return false;
            });
			
			var buttonAll = jQuery('<a href="#" name="button_all" class="combobox_selector">показать все<a/>');
			buttonAll.click(function (e) {
				thisSelector.nohide = true;
				thisSelector.buildList(this.startingLetters, 100000);
				thisSelector.combobox.focus();			
				return false;
            });
			buttonAll.appendTo(this.selectorElement);
			}
			
            this.selectorElement.mouseover(function (e) {
                thisSelector.unselect();
            });
			return result;
        },
		
		buildList: function(startingLetters, count, n)
		{
			var maxCount = this.optionCount + count;
			var n = this.number;
			for (var i=this.number+1; i < this.allSelectOptions.length; i++) {
			var opt = this.allSelectOptions[i][0];
			var raionOpt = -1;
			if (!this.showAll)
			 raionOpt = this.allSelectOptions[i][2];
			 
                if ((this.raionID<0 || (this.raionID == raionOpt || raionOpt<0 || this.showAll))
				&& (startingLetters == null || ! startingLetters.length 
                    || opt.toLowerCase().indexOf(startingLetters.toLowerCase()) >= 0))
                {
                    this.options.push(this.allSelectOptions[i]);
					this.optionCount++;
					this.number = i;
					if (this.optionCount >= maxCount) break;
					
					if (startingLetters != null)
					{
						if (opt != null && opt != '' && opt.toLowerCase() == startingLetters.toLowerCase())
						{
							var code = this.allSelectOptions[i][1];
							this.combobox.setCode(code);
						}
					}
					
					result = true;
                }
            }
			
			var favoriteValue =  null;
			if (this.combobox.favoriteList != null)
				favoriteValue = new String(','+this.combobox.favoriteList.valueOf()+',');
			
			for (var i = n+1; i < this.options.length; i++) {
					var opt = this.options[i][0];
					var code = this.options[i][1];
					var text = opt;
					if (this.combobox.isShowCode && code != '-1')
						text += '  '+code;
					if (favoriteValue != null && favoriteValue.indexOf(new String(','+code+','))>=0){
						this.ulElement1.append('<li><div code="'+code+'" value="'+opt+'" class="combobox_favorite">'+text+'</div></li>');
					}
					else
						this.ulElement1.append('<li><div code="'+code+'" value="'+opt+'">'+text+'</div></li>');
			}
			
			this.selectorElement.find('li div').click(function (e) {
				var div = this;
				var code = this.getAttribute("code");
				var value = this.getAttribute("value");
				thisSelector.hide();
				thisSelector.combobox.setValue(value);
				thisSelector.combobox.setCode(code);
				thisSelector.combobox.focus();
            });
		},

        show : function () {
            if (this.selectorElement.find('li').length < 1
                || this.selectorElement.is(':visible'))
            {
                return false;
            }
            jQuery('html').keyup(this.keypressHandler);
            this.selectorElement.slideDown('fast');
			this.selectorElement.css('overflow', 'auto');
			this.frame.css('display', 'block');
            thisSelector = this;
            return true;
        },

        hide : function () {
			if (this.nohide) { this.nohide = false; return; }
            jQuery('html').unbind('keyup', this.keypressHandler);
            this.selectorElement.unbind('click');
            this.unselect();
            this.selectorElement.hide();
			this.frame.css('display', 'none');
        },

        selectNext : function () {
            var newSelectedIndex = this.selectedIndex + 1;
            if (newSelectedIndex > this.optionCount - 1) {
                newSelectedIndex = this.optionCount - 1;
            }
            this.select(newSelectedIndex);
        },

        selectPrevious : function () {
            var newSelectedIndex = this.selectedIndex - 1;
            if (newSelectedIndex < 0) {
                newSelectedIndex = 0;
            }
            this.select(newSelectedIndex);
        },
        
        select : function (index) {
            this.unselect();
        	this.selectorElement.find('li:eq('+index+')').addClass('selected');
        	this.selectedIndex = index;
        },

        unselect : function () {
        	this.selectorElement.find('li').removeClass('selected');
        	this.selectedIndex = -1;
        },
        
        getSelectedValue : function () {
        	var obj = this.selectorElement.find('li div').get(this.selectedIndex);
        	var value = obj.getAttribute("value");
        	return value;
        	//return this.selectorElement.find('li div').get(this.selectedIndex).innerHTML;
        },
		getSelectedValueByCode : function (code) {
        	for (var i=0; i < this.allSelectOptions.length; i++) {
        			var opt = this.allSelectOptions[i][0];
        			var c = this.allSelectOptions[i][1];
        			if (c == code)
        			{
        				opt = opt.replace( /&#39;/g, "'" );
        				opt = opt.replace( /&quot;/g, "\"" );
        				opt = opt.replace( /&rsquo;/g, "'" );
        				
        				return opt;
        			}
        	}
        },
		
		getSelectedCode : function () {
		return this.selectorElement.find('li div').get(this.selectedIndex).getAttribute("code");
        	//return this.selectorElement.find('li div').getAttribute("code");
        },
		
		setShowAll: function(value) {
			this.showAll = value;
		},
		
		isShowAll: function() {
			return this.showAll;
		}

    };


})();