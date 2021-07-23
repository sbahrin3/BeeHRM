function getPage(url) {
	if ( url != "" ) {
		doAjaxRequest('portal_main_view', '../renderer/' + url);
	}
}
function sendCommand(arg0, arg1, arg2) {
	return sendAjax(arg0, arg1, arg2);
}
function saveData(arg0, arg1, arg2) {
	return sendAjaxData(arg0, arg1, arg2);
}
function sendAjaxData(arg0, arg1, arg2) {
	
	var theForm = document.__main_form;
	var count = theForm.elements.length;
	var is_validated = true;
	var messages = [];
	for ( var i=0; i < count; i++) {
		var e = theForm.elements[i];
		if ( e.type != "button" ) {
			if ( e.getAttribute("data-required") == "true") {
				if ( e.value == "" ) {
					is_validated = false;
					e.style.border = "2px solid #ff0000";
					if ( e.getAttribute("data-msg")) {
						messages.push(e.getAttribute("data-msg"));
					}
				} else {
					e.style.border = "1px solid #cccccc";
					if ( e.getAttribute("data-pattern") ) {
						var result = new RegExp(e.getAttribute("data-pattern")).test(e.value);
						if ( result == false ) {
							is_validated = false;
							e.style.border = "2px solid #ff0000";
							if ( e.getAttribute("data-msg")) {
								messages.push(e.getAttribute("data-msg"));
							}
						}
					}
					
				}
				
			}
		}
	}
	
	if ( is_validated ) {
		sendAjax(arg0, arg1, arg2);
	} else {
		messages.forEach(function(message) {
			showWarningMessage(message);
		});
	}
	return is_validated;
}

function sendAjax(arg0, arg1, arg2) {

	console.log('sendAjax:' + arg0);
	
	actionName = arg0;
	if ( arg1 === undefined ) arg1 = '';
	if ( arg2 === undefined ) arg2 = '';
	
	if ( arg1.includes("=") ) {
		qs = arg1;
		target = arg2;
	}
	else if ( !arg1.includes("=") ) {
		target = arg1;
		qs = arg2;
	}
	
	if ( target == '' ) target = 'portal_main_view';
			
	var el = document.getElementById(target); 
	
	el.style.display='block'; 

	
    var d1 = document.createElement('div');
    d1.style.zIndex="9998";
    d1.setAttribute('id', 'div_wait_background');
    d1.setAttribute('style', 'position:absolute;top:0;width:100%;height:100%;opacity: 0.5;');
    d1.innerHTML = "";
    el.appendChild(d1);
   
   
    var d = document.createElement('div');
    d.style.zIndex="9999";
    d.setAttribute('id', 'div_wait_indicator');
    //d.setAttribute('style', 'opacity: 0.5;position:absolute;top:50px;text-align:center;color:#000; ');
    d.setAttribute('style', 'opacity: 0.5;position:absolute;top:60%;left:40%;text-align:center;color:#000; ');
    d.innerHTML = "<i class='fa fa-spinner fa-spin fa-5x fa-fw'></i>";
    el.appendChild(d);

	
	var module_name = document.getElementById('__module_name').value;
   	doAjaxUpdater(document.__main_form, '../div/' + module_name, target, actionName, qs);
	   	
	
   	
}

function sendAjaxDiv(arg0, arg1, arg2) {

	console.log('sendAjax:' + arg0);
	
	actionName = arg0;
	el = arg1; 
	qs = arg2;
	
	el.style.display='block'; 

	
    var d1 = document.createElement('div');
    d1.style.zIndex="9998";
    d1.setAttribute('id', 'div_wait_background');
    d1.setAttribute('style', 'position:absolute;top:0;width:100%;height:100%;opacity: 0.5;');
    d1.innerHTML = "";
    el.appendChild(d1);
   
   
    var d = document.createElement('div');
    d.style.zIndex="9999";
    d.setAttribute('id', 'div_wait_indicator');
    //d.setAttribute('style', 'opacity: 0.5;position:absolute;top:50px;text-align:center;color:#000; ');
    d.setAttribute('style', 'opacity: 0.5;position:absolute;top:60%;left:40%;text-align:center;color:#000; ');
    d.innerHTML = "<i class='fa fa-spinner fa-spin fa-5x fa-fw'></i>";
    el.appendChild(d);

	
	var module_name = document.getElementById('__module_name').value;
   	doAjaxUpdater(document.__main_form, '../div/' + module_name, el, actionName, qs);
	   	
	
   	
}


function doAjaxHTTPRequester(url, target) { 
	new Ajax.Updater(target, url, { 
		method: 'get',  
		evalScripts: true,
		evalJS: true,
		onComplete: function(t) { 
			t.responseText; 
		}, 
		on404: function(t) { 
			alert('error 404'); 
		}, 
		onFailure: function(t) { 
			alert('failure get response'); 
		} 
	}); 
}
function doAjaxRequest(divName, url) { 
	var el = document.getElementById(divName); 
	
	el.innerHTML = ""; 
	el.style.display='block'; 
	
    var d1 = document.createElement('div');
    d1.style.zIndex="9998";
    d1.setAttribute('id', 'div_wait_background');
    d1.setAttribute('style', 'position:absolute;top:0;width:100%;height:100%;opacity: 0.5;');
    d1.innerHTML = "";
    el.appendChild(d1);
   
   
    var d = document.createElement('div');
    d.style.zIndex="9999";
    d.setAttribute('id', 'div_wait_indicator');
    d.setAttribute('style', 'opacity: 0.5;position:absolute;top:50px;text-align:center;color:#000; ');
    d.innerHTML = "<i class='fa fa-spinner fa-spin fa-5x fa-fw'></i>";
    el.appendChild(d);	
	
	
	
	var target = divName; 
	doAjaxHTTPRequester(url, target);
}
function doAjaxPostRequest(divName, url) {
	var el = document.getElementById(divName);el.innerHTML = "";
	el.style.display='block';
	
    var d1 = document.createElement('div');
    d1.style.zIndex="9998";
    d1.setAttribute('id', 'div_wait_background');
    d1.setAttribute('style', 'position:absolute;top:0;width:100%;height:100%;opacity: 0.5;');
    d1.innerHTML = "";
    el.appendChild(d1);
   
   
    var d = document.createElement('div');
    d.style.zIndex="9999";
    d.setAttribute('id', 'div_wait_indicator');
    d.setAttribute('style', 'opacity: 0.5;position:absolute;top:50px;text-align:center;color:#000; ');
    d.innerHTML = "<i class='fa fa-spinner fa-spin fa-5x fa-fw'></i>";
    el.appendChild(d);
		
	
	var target = divName;
	new Ajax.Updater(target, url, {
		method: 'post', 
		evalScripts: true,
		onComplete: function(t) {
			t.responseText;
		},
		on404: function(t) {
			alert('error 404');
		},
		onFailure: function(t) { 
			alert('failure get response');
		}
	});
}


function doAjaxUpdater(objForm, url, target, actionName, qs) {
    var theForm = objForm;
	var pars = "_d=_d";
	var count = theForm.elements.length;
	theForm.command.value = actionName;
	var readparam = false;
	
	for ( var i=0; i < count; i++) {
	   readparam = false;
	   if ( theForm.elements[i].type == "radio" || theForm.elements[i].type == "checkbox") {
		  if ( theForm.elements[i].checked ) {
		     readparam = true;
		  } 
	   } else {
	   	  readparam = true;
	   }
	   
	   if ( readparam ) {
	  	   var id = theForm.elements[i].id;
		   var name = theForm.elements[i].name;
		   if ( name == "" ) theForm.elements[i].name = id;
		   if ( id == "" ) theForm.elements[i].id = name;
	       name = theForm.elements[i].name;
		   var value = theForm.elements[i].value;
	       pars = pars + '&' + name + '=' + encodeURIComponent(value);
       }
	}
	pars = pars + '&' + qs;
	
    var myAjax = new Ajax.Updater(target, url, {
				method: 'post', 
				parameters: pars,
				evalScripts: true, 
  				onComplete: function(t) {
  				  	result = t.responseText;
  				  	
				},
	            on404: function(t) {
					alert('error 404');
	            },
	            onFailure: function(t) {
					alert('failure get response');
	            }     											
		});	
    
	

}


function doAjaxUpdaterByParamName(objForm, url, target, actionName, qs, paramName) {
    var theForm = objForm;
	var pars = "_d=_d";
	var count = theForm.elements.length;
	theForm.command.value = actionName;
	var readparam = false;
	
	for ( var i=0; i < count; i++) {
	   readparam = false;
	   if ( theForm.elements[i].type == "radio" || theForm.elements[i].type == "checkbox") {
		  if ( theForm.elements[i].checked ) {
		     readparam = true;
		  } 
	   } else {
	   	  readparam = true;
	   }
	  
	   if ( readparam ) {
	  	   var id = theForm.elements[i].id;
		   var name = theForm.elements[i].name;
		   if ( name == "" ) theForm.elements[i].name = id;
		   if ( id == "" ) theForm.elements[i].id = name;
	       name = theForm.elements[i].name;
		   var value = theForm.elements[i].value;
		   if ( name == "command")
			   pars = pars + '&' + name + '=' + encodeURIComponent(value);
		   if ( name == paramName )
			   pars = pars + '&' + name + '=' + encodeURIComponent(value);
	       
       }
	}
	pars = pars + '&' + qs;
    var myAjax = new Ajax.Updater(target, url, {
    											method: 'post', 
    											parameters: pars,
    											evalScripts: true, 
    							  				onComplete: function(t) {
    							  				  	result = t.responseText;
    							  				  	
            									},
									            on404: function(t) {
													alert('error 404');
									            },
									            onFailure: function(t) {
													alert('failure get response');
									            }     											
    											});	
    	 
}


function doAjaxUpdaterChain(objForm, url, target, actionName, qs, target2, actionName2, qs2) {
    var theForm = objForm;
	var pars = '_d=d';
	var count = theForm.elements.length;
	theForm.command.value = actionName;
	var readparam = false;
	
	for ( var i=0; i < count; i++) {
	   readparam = false;
	   if ( theForm.elements[i].type == "radio" || theForm.elements[i].type == "checkbox") {
		  if ( theForm.elements[i].checked ) {
		     readparam = true;
		  } 
	   } else {
	   	  readparam = true;
	   }
	  
	   if ( readparam ) {
	  	   var id = theForm.elements[i].id;
		   var name = theForm.elements[i].name;
		   if ( name == '' ) theForm.elements[i].name = id;
		   if ( id == '' ) theForm.elements[i].id = name;
	       name = theForm.elements[i].name;
		   var value = theForm.elements[i].value;
	       pars = pars + '&' + name + '=' + encodeURIComponent(value);
       }
	}
	pars = pars + '&' + qs;
    var myAjax = new Ajax.Updater(target, url, {
    											method: 'post', 
    											parameters: pars,
    											evalScripts: true,
    							  				onComplete: function(t) {
    							  				  	result = t.responseText;
    							  				  	doAjaxUpdater(objForm, url, target2, actionName2, qs2);
            									},
									            on404: function(t) {
													alert('error 404');
									            },
									            onFailure: function(t) {
													alert('failure get response');
									            }     											
    											});		 
}


function numeralsOnly(evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : 
        ((evt.which) ? evt.which : 0));
    if ( charCode == 46 || charCode== 45) return true;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
	
function get_page_size() {
    var xScroll, yScroll;

    if (window.innerHeight && window.scrollMaxY) {	
      xScroll = document.body.scrollWidth;
      yScroll = window.innerHeight + window.scrollMaxY;
    } else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
      xScroll = document.body.scrollWidth;
      yScroll = document.body.scrollHeight;
    } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
      xScroll = document.body.offsetWidth;
      yScroll = document.body.offsetHeight;
    }

    var windowWidth, windowHeight;
    if (self.innerHeight) {	// all except Explorer
      windowWidth = self.innerWidth;
      windowHeight = self.innerHeight;
    } else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
      windowWidth = document.documentElement.clientWidth;
      windowHeight = document.documentElement.clientHeight;
    } else if (document.body) { // other Explorers
      windowWidth = document.body.clientWidth;
      windowHeight = document.body.clientHeight;
    }	
    
    // for small pages with total height less then height of the viewport
    if(yScroll < windowHeight){
      pageHeight = windowHeight;
    } else { 
      pageHeight = yScroll;
    }

    // for small pages with total width less then width of the viewport
    if(xScroll < windowWidth){	
      pageWidth = windowWidth;
    } else {
      pageWidth = xScroll;
    }

    arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight);
    return arrayPageSize;
}
	