<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" %>

<HTML>

	<HEAD>
	    <META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<TITLE>
			Календарь
		</TITLE>
		<script language="javascript" src="calendar.js"></script>
		<script>
			var objectAdd = window.dialogArguments;
			var dateObject = objectAdd.dateObject;
			dateObjectValue = dateObject.value;
			returnValue = "close";
			
			var dCurDate = new Date();
			
		  	function scrollSelect(sel, indx){
				var selIndx = sel.selectedIndex;
				if (selIndx == 0 && indx<0)
					sel.selectedIndex = sel.length-1;
				else if (selIndx == sel.length-1 && indx>0)
					sel.selectedIndex = 0;
				else
					sel.selectedIndex = sel.selectedIndex + indx;
				sel.onchange();
			}
			var windowWidth = parseInt(window.dialogWidth);
			for (var i=0; i<windowWidth; i++)
				document.title = document.title + String.fromCharCode(160);
		</script> 
		<script language="JavaScript" for=window event=onload>
			if (dateObjectValue.length<10)
				dCurDate = new Date();
			else
				dCurDate = new Date(dateObjectValue.substring(0,4),dateObjectValue.substring(5,7)-1,dateObjectValue.substring(8));
			frmCalendarSample.tbSelMonth.options[dCurDate.getMonth()].selected = true;
			frmCalendarSample.tbSelYear.options[dCurDate.getFullYear()-1900].selected = true;
			fUpdateCal(frmCalendarSample.tbSelYear.value, frmCalendarSample.tbSelMonth.value, dCurDate);			
		</script> 
		<style>
			body {
			    margin: 0px;
			    padding: 0px;
			    background-color: white;
			    color: black;
			    font-family: verdana, arial, helvetica, sans-serif;
			    font-size: 12pt;
			}
			#scroll-link {
			    font-size:11px;		    
			    font-weight:bold;
			    cursor: pointer;
			    color:#707070;
			    height: 15px;
			}
			#calCell {
			    font-size:9px;
			    font-family: arial;		    
			    cursor: pointer;
			}
			.tabledata table {
			    border:1px solid #ccc;
			    border-collapse:collapse;
				margin:0px;		
			}		
			.tabledata td {
				font-size:10px;
			    border:1px solid #ccc;
				padding:3px;
				margin:0px;
			}		
			.tabledata th {
			    font-size:10px;
				font-weight:normal;
			    border:1px solid #ccc;
				padding:3px;
			    margin:0px;
				background-color: #e0e8f3;
			}		
			.tabledata select {
				font-size:10px;
			}		
			.tabledata br {
				font-size:5px;
			}
		</style>
	</HEAD>
	
	<BODY>
				
	<center>
		<form name="frmCalendarSample"> 
			<div class="tabledata">
				<br/>
				<table> 
					<tr> 
						<td style="border-right: 0px; border-bottom: 0px">
							<table width="100%">
								<tr>
									<th align="center" style="border-right: 0px">
										<label id="scroll-link" title="предыдущий месяц" onselectstart="return false"
											onclick="scrollSelect(tbSelMonth, -1)"
											onmouseover="this.style.color='#ccc'"
											onmouseout="this.style.color='#707070'">&laquo;</label>
										<select id="tbSelMonth" name="tbSelMonth" onchange='fUpdateCal(frmCalendarSample.tbSelYear.value, frmCalendarSample.tbSelMonth.value, dCurDate)'> 
											<option value="01">Январь</option> 
											<option value="02">Февраль</option> 
											<option value="03">Март</option> 
											<option value="04">Апрель</option> 
											<option value="05">Май</option> 
											<option value="06">Июнь</option> 
											<option value="07">Июль</option> 
											<option value="08">Август</option> 
											<option value="09">Сентябрь</option> 
											<option value="10">Октябрь</option> 
											<option value="11">Ноябрь</option> 
											<option value="12">Декабрь</option>
										</select> 
										<label id="scroll-link" title="следующий месяц" onselectstart="return false"
											onclick="scrollSelect(tbSelMonth, 1)"
											onmouseover="this.style.color='#ccc'"
											onmouseout="this.style.color='#707070'">&raquo;</label>
									</th>
								</tr> 
							</table>
						</td>
	      				<td style="border-left: 0px; border-bottom: 0px">
							<table width="100%">
								<tr align="center">
									<th style="border-left: 0px; border-right: 0px">
										<label id="scroll-link" title="предыдущий год" onselectstart="return false"
											onclick="scrollSelect(tbSelYear, -1)"
											onmouseover="this.style.color='#ccc'"
											onmouseout="this.style.color='#707070'">&laquo;</label>
										<script>
											document.writeln("<select id=\"tbSelYear\" name=\"tbSelYear\" onchange='fUpdateCal(frmCalendarSample.tbSelYear.value, frmCalendarSample.tbSelMonth.value, dCurDate)'>");
											fDrawYears(dCurDate.getFullYear())
											document.writeln("</select>")
										</script>
										<label id="scroll-link" title="следующий год" onselectstart="return false"
											onclick="scrollSelect(tbSelYear, 1)"
											onmouseover="this.style.color='#ccc'"
											onmouseout="this.style.color='#707070'">&raquo;</label>
									</th> 
								</tr>
							</table>
						</td>
					</tr>
					<tr> 
						<td colspan="2" style="border-top: 0px"> 
							<script language="JavaScript">
								fDrawCal(dCurDate.getFullYear(), dCurDate.getMonth()+1, 23, 23, 1);
							</script>
						</td> 
					</tr> 
				</table> 
			</div>
		</form> 
	</center>
	</BODY>

</HTML>
