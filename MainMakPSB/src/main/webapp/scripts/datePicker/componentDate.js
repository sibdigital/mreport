function initDatePicker(compInputId, labelErrorId) {
    var input = $("#" + compInputId);
    var onCompleted = function () {
        var elem = null;
        if (labelErrorId != null)
            elem = document.getElementById(labelErrorId);
        else
            labelErrorId = compInputId + 'ErrorBox';

        if (elem == null) {
            elem = $('<small style="color: green" id=' + labelErrorId + '></small>');
            elem.insertAfter(input);
        } else
            elem = $(elem);

        if (isDate(compInputId, labelErrorId)) {
            //elem.html("���� ������� �����");
            //elem.css("color", "green");
        } else
            elem.css("color", "red");

    };

    //input.bind("keydown", keydownEvent);
    input.mask("99.99.9999", {"completed": onCompleted, "fill0": true});
    input.datepicker({changeYear: true, changeMonth: true, yearRange: "1900:2100", onSelect: selectDatePicker});

    //input.bind("keydown", keydownEvent);
}

function initDateMMYYPicker(compInputId, labelErrorId)
{
    var input = $("#" + compInputId);
    input.mask("99.9999");
    input.datepicker({changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        dateFormat: 'mm.yy',
        onClose: function (dateText, inst) {
            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            $(this).datepicker('setDate', new Date(year, month, 1));
        }
    });

    input.focus(function () {
        $(".ui-datepicker-calendar").hide();
        $("#ui-datepicker-div").position({
            my: "center top",
            at: "center bottom",
            of: $(this)
        });
    });
}

function selectDatePicker(dateText, input) {
    input = $(input);
    var id = input.attr('id');
    var txt = document.getElementById(id);
    //if (txt.onchange != null)
    //	txt.onchange();

    var labelErrorId = input.attr('id') + 'ErrorBox';
    var elem = document.getElementById(labelErrorId);
    if (elem != null) {
        if (isDate(input.attr('id'), labelErrorId)) {
            //elem.innerHTML = "���� ������� �����";
            //elem.style.color = "green";
        } else
            elem.style.color = "red";
    }
    if (dateText != null){
        var base = document.URL.substring(0, document.URL.lastIndexOf('/') + 1);
        $.post(base + "available_periods.do", {scheduled_date: dateText})
                        .done(function (data) {
                            var hours = data.availableHours;
                            var minutes = data.availableMinutes;
                            
//                            var h = $("#hour");
//                            var m = $("#min");
                            
                            var s = "";
                            for (var i = 0; i < hours.length; i++) {
                                
                                s += "<option value=\"" + hours[i] + "\">" + hours[i] + "</option>";
                                
//                                var opt = document.createElement('option');
//                                opt.value = opt.text = hours[i];                               
//                                h.add(opt);
                            }
                            $("#hour").html(s);
                            
                            s = "";
                            for (var i = 0; i < minutes.length; i++) {
                                
                                s += "<option value=\"" + minutes[i] + "\">" + minutes[i] + "</option>";
                                
//                                var opt = document.createElement('option');
//                                opt.value = opt.text = hours[i];                              
//                                m.add(opt);
                            }
                            $("#min").html(s);
                        });
    }
}

function keydownEvent(e) {
    var pos = $(this).caret();
    var k = e.keyCode;
    var mask = "99.99.9999";
    var defs = {
        '9': "[0-9]",
        'a': "[A-Za-z]",
        '*': "[A-Za-z0-9]"
    };

    console.log($(this).val());
    //var buffer = $.map($(this).val().split(""), function(c, i) { 
    //	if (c != '?') return defs[c] ? '_' : c });
    var buffer = $.map($(this).val().split(""), function (c, i) {
        return c
    });
    console.log(buffer);
    //var buffer = $.map(mask.split(""), function(c, i) { 
    //	if (c != '?') return defs[c] ? '_' : c });
    //checkVal($(this), buffer);

    console.log($(this).data("buffer", buffer));
    if (k == 32 || k == 110 || k == 109 || k == 188 || k == 189 || k == 190 || k == 191 || k == 111) {
        if (pos.begin == mask.length)
            return;
        var indexend = 0;
        for (var i = pos.begin; i < mask.length; i++) {
            var c = mask.charAt(i);
            if (c == '.' || c == '-' || c == ',' || c == '/' || c == ' ')
            {
                break;
            }
            indexend = i;
        }
        var str = "";
        var indexstart = 0;
        for (var j = pos.begin - 1; j >= 0; j--) {
            var c = mask.charAt(j);
            if (c == '.' || c == '-' || c == ',' || c == '/' || c == ' ') {
                indexstart = j + 1;
                break;
            }
            str += buffer[j];
        }

        if (str.length == 0)
            return;
        var lennol = indexend - indexstart - str.length + 1;
        for (var i = 0; i < lennol; i++) {
            buffer[indexstart + i] = "0";
            writeBuffer($(this), buffer);
        }
        indexstart += lennol;
        for (var j = str.length - 1; j >= 0; j--) {
            buffer[indexstart++] = str.charAt(j);
            writeBuffer($(this), buffer);
        }
        console.log('write buffer ' + buffer);
        $(this).caret(indexend + 2);
        $(this).data("buffer", buffer);
    }
}

function checkVal(input, buffer, allow) {
    var test = input.val(),
            lastMatch = -1,
            i,
            c;
    var mask = "99.99.9999";
    var len = mask.length;

    var defs = {
        '9': "[0-9]",
        'a': "[A-Za-z]",
        '*': "[A-Za-z0-9]"
    };

    tests = [];
    var partialPosition = len = mask.length;
    var firstNonMaskPos = null;

    $.each(mask.split(""), function (i, c) {
        if (c == '?') {
            len--;
            partialPosition = i;
        } else if (defs[c]) {
            tests.push(new RegExp(defs[c]));
            if (firstNonMaskPos === null) {
                firstNonMaskPos = tests.length - 1;
            }
        } else {
            tests.push(null);
        }
    }
    );

    for (i = 0, pos = 0; i < len; i++) {
        if (tests[i]) {
                buffer[i] = '_';
                while (pos++ < test.length) {
                        c = test.charAt(pos - 1);
                if (tests[i].test(c)) {
                                            buffer[i] = c;
                                            lastMatch = i;
                                            break;
}
}
if (pos > test.length) {
                                            break;
}
} else if (buffer[i] === test.charAt(pos) && i !== partialPosition) {
                                            pos++;
                                            lastMatch = i;
}
}
if (allow) {
                                            writeBuffer();
} else if (lastMatch + 1 < partialPosition) {
                                                    input.val("");
                                            clearBuffer(buffer, 0, len);
} else {
                                                    writeBuffer(input, buffer);
                                            input.val(input.val().substring(0, lastMatch + 1));
}
return (partialPosition ? i : firstNonMaskPos);
}

function clearBuffer(buffer, start, end) {
                                            var i;
                                            for (i = start; i < end && i < 10; i++) {
                                                if (tests[i]) {
                                                        buffer[i] = '_';
}
}
}

function writeBuffer(input, buffer) {
                                                        input.val(buffer.join(''));
}