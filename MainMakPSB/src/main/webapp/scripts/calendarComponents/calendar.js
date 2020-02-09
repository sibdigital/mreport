// calendar.js
var dDate = new Date();
var dCurMonth = dDate.getMonth();
var dCurDayOfMonth = dDate.getDate();
var dCurYear = dDate.getFullYear();
var objPrevElement = new Object();

function fSetSelectedDay(myElement){
	if (myElement.id == "calCell") {
		if (!isNaN(parseInt(myElement.children["calDateText"].innerText))) {
			myElement.bgColor = "#e0e8f3";//"#EBF0F8";
			objPrevElement.bgColor = "";
			tmp = "0"+myElement.children["calDateText"].innerText;
			objPrevElement = myElement;
			returnValue = "change|"+dCurYear+"-"+dCurMonth+"-"+tmp.substring(tmp.length-2);
			window.close();
		}
   }
}

function fGetDaysInMonth(iMonth, iYear) {
	var dPrevDate = new Date(iYear, iMonth, 0);
	return dPrevDate.getDate();
}

function fBuildCal(iYear, iMonth, iDayStyle) {
	var aMonth = new Array();
	aMonth[0] = new Array(7);
	aMonth[1] = new Array(7);
	aMonth[2] = new Array(7);
	aMonth[3] = new Array(7);
	aMonth[4] = new Array(7);
	aMonth[5] = new Array(7);
	aMonth[6] = new Array(7);
	var dCalDate = new Date(iYear, iMonth-1, 1);
	var iDayOfFirst = dCalDate.getDay();
	var iDaysInMonth = fGetDaysInMonth(iMonth, iYear);
	var iVarDate = 1;
	var i, d, w;
	if (iDayStyle == 2) {
		aMonth[0][0] = "Sunday";
		aMonth[0][1] = "Monday";
		aMonth[0][2] = "Tuesday";
		aMonth[0][3] = "Wednesday";
		aMonth[0][4] = "Thursday";
		aMonth[0][5] = "Friday";
		aMonth[0][6] = "Saturday";
	} else if (iDayStyle == 1) {
		aMonth[0][0] = "\u041f\u043d";
		aMonth[0][1] = "\u0412\u0442";
		aMonth[0][2] = "\u0421\u0440";
		aMonth[0][3] = "\u0427\u0442";
		aMonth[0][4] = "\u041f\u0442";
		aMonth[0][5] = "\u0421\u0431";
		aMonth[0][6] = "\u0412\u0441";
	}
	for (d = iDayOfFirst; d <= 7; d++) {
		if (d<1){
			aMonth[1][6] = iVarDate;		
			iVarDate++;
			break;
		}
		else
			aMonth[1][d-1] = iVarDate;
		iVarDate++;
	}
	for (w = 2; w < 7; w++) {
		for (d = 0; d < 7; d++) {
			if (iVarDate <= iDaysInMonth) {
				aMonth[w][d] = iVarDate;
				iVarDate++;
	      	}
	   	}
	}
	return aMonth;
}

function fDrawCal(iYear, iMonth, iCellWidth, iCellHeight, iDayStyle) {
	//var start1 = (new Date()).getTime();
	var myMonth;
	var drawStr = "";
	myMonth = fBuildCal(iYear, iMonth, iDayStyle);
	drawStr = drawStr + "<table>";
	drawStr = drawStr + "<tr>";
	drawStr = drawStr + "<th align='center'>" + myMonth[0][0] + "</th>";
	drawStr = drawStr + "<th align='center'>" + myMonth[0][1] + "</th>";
	drawStr = drawStr + "<th align='center'>" + myMonth[0][2] + "</th>";
	drawStr = drawStr + "<th align='center'>" + myMonth[0][3] + "</th>";
	drawStr = drawStr + "<th align='center'>" + myMonth[0][4] + "</th>";
	drawStr = drawStr + "<th align='center'>" + myMonth[0][5] + "</th>";
	drawStr = drawStr + "<th align='center'>" + myMonth[0][6] + "</th>";
	drawStr = drawStr + "</tr>";
	for (w = 1; w < 7; w++) {
		drawStr = drawStr + "<tr>"
		for (d = 0; d < 7; d++) {
			drawStr = drawStr + "<td align='left' valign='top' width='" + iCellWidth + "' height='" + iCellHeight + 
				"' id=calCell onclick=fSetSelectedDay(this)>";
			if (!isNaN(myMonth[w][d]))
				drawStr = drawStr + "<label id=calDateText onclick=fSetSelectedDay(this)>" + myMonth[w][d] + "</label>";
			else
				drawStr = drawStr + "<label id=calDateText onclick=fSetSelectedDay(this)> </label>";
			drawStr = drawStr + "</td>"
		}
		drawStr = drawStr + "</tr>";
	}
	drawStr = drawStr + "</table>"
	document.write(drawStr)
	//alert(start1 - (new Date()).getTime())
}

function fDrawYears(iYear){
	var drawStr = "";
	var max = iYear - 1900 + 10;
	for (var i=0;i<=max;i++)
		drawStr = drawStr + "<option value=\""+(1900+i)+"\">"+(1900+i)+"</option>\n";
	document.write(drawStr)
}

function fUpdateCal(iYear, iMonth, fDate) {
	this.dCurMonth = iMonth;
	this.dCurYear = iYear;
	var curDate = new Date();
	var curYear = curDate.getFullYear();
	var curMonth = curDate.getMonth()+1;
	var curD = curDate.getDate();
	var fYear = fDate.getFullYear();
	var fMonth = fDate.getMonth()+1;
	var fD = fDate.getDate();
	myMonth = fBuildCal(iYear, iMonth);
	objPrevElement.bgColor = "";
	var indx = 0;
	for (w = 1; w < 7; w++) {
		for (d = 0; d < 7; d++) {
			indx = ((7*w)+d)-7;
			if (!isNaN(myMonth[w][d])) {
				if (myMonth[w][d]==curD && iMonth==curMonth && iYear==curYear){
					calCell[indx].bgColor="#fecac4";
					calDateText[indx].color="#ff0000";
				}
				else if (myMonth[w][d]==fD && iMonth==fMonth && iYear==fYear){
					calCell[indx].bgColor = "#a8eca8";
					calDateText[indx].color="#00b600";
				}
				else{
			    	calCell[indx].bgColor = "#ffffff"
					calDateText[indx].color="#000000";
				}
				calDateText[indx].innerText = myMonth[w][d];
			} else {
				calCell[indx].bgColor = "#ffffff"
				calDateText[indx].color="#000000";
				calDateText[indx].innerText = " ";
			}
		}
	}
}