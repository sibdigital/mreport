(function($) {
	$.fn.createScrollableTable = function(options) {

		var defaults = {
			width: '400px',
			height: '300px',
			border: 'solid 1px #888'
		};
		var options = $.extend(defaults, options);

		return this.each(function() {
			var table = $(this);
			prepareTable(table);
		});

		function prepareTable(table) {
			var tableId = table.attr('id');

			// wrap the current table (will end up being just body table)
			var bodyWrap = table.wrap('<div></div>')
									.parent()
									.attr('id', tableId + '_body_wrap')
									.attr('class', 'scrollPrint')
									.css({
										width: options.width,
										height: options.height,
									});

			// wrap the body
			var tableWrap = bodyWrap.wrap('<div></div>')
									.parent()
									.attr('id', tableId + '_table_wrap')
									.attr('class', 'hiddenPrint')
									.css({
										display: 'inline-block',
										//border: options.border
									});

			// clone the header
			var headWrap = $(document.createElement('div'))
									.attr('Id', tableId + '_head_wrap')
									.attr('class', 'hiddenPrint tableheader')
									.prependTo(tableWrap)
									.css({
										width: options.width,
									});

			var headTable = table.clone(true)
									.attr('Id', tableId + '_head')
									.appendTo(headWrap)
									.css({
										'table-layout': 'fixed'
									});
			

			// remove the extra html

			// size the header columns to match the body
			headTable.find('tbody').remove();
			table.find('thead').remove();
			
			var w = 0;
			var measure = [];
			
			var allBodyCols = table.find('tbody tr:first td');
			allBodyCols.size = 
			headTable.find('thead tr:first th').each(function(index) {
				var desiredWidth = getWidth($(allBodyCols[index]));
				measure.push(desiredWidth);
				w+=desiredWidth;
				$(this).css({ width: desiredWidth + 'px' });
			});
			
			table.find('tbody tr:first td').each(function(index) {
				if (index < measure.length-1)
				{
					var desiredWidth = measure[index];
					$(this).css({ width: desiredWidth + 'px' });
				}
				else
				{
					var desiredWidth = measure[index];
					$(this).css({ width: '100%' });
				}
			});
			
			
			
			var bufferCol = $(document.createElement('th'))
									.css({
										width: '100%'
									})
									.appendTo(headTable.find('thead tr'));
			headTable.css({width: '100%'});
			table.css({width: '100%'});
			
		}

		function getWidth(td) {
			if ($.browser.msie) { return $(td).outerWidth() - 1; }
			if ($.browser.mozilla) { return $(td).width(); }
			if ($.browser.safari) { return $(td).outerWidth(); }
			return $(td).outerWidth();
		};


	};

})(jQuery);