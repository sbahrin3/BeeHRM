function log(msg) {
   	setTimeout(function() {
       	throw new Error(msg);
   	}, 0);
}

function highlightPortalStyle(title) {
	document.getElementById("portalStyle1").className = "fontNotSelected";
	document.getElementById("portalStyle2").className = "fontNotSelected";
	document.getElementById("portalStyle3").className = "fontNotSelected";
	log("highlight css = " + title);
	document.getElementById(title).className = "fontSelected";
}


function setPortalStyleSheet(title) {
	var i, a, main;
	for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
		if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
			a.disabled = true;
			if(a.getAttribute("title") == title) a.disabled = false;
		}
	}
	var title = getPortalStyleSheet();
	createCSSCookie("portal_style", title, 365);
	highlightPortalStyle(title);
}

function getPortalStyleSheet() {
	var i, a;
	for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
		if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title") && !a.disabled) return a.getAttribute("title");
	}
	return null;
}

function getPortalPreferredStyleSheet() {
	var i, a;
	for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
		if(a.getAttribute("rel").indexOf("style") != -1
				&& a.getAttribute("rel").indexOf("alt") == -1
				&& a.getAttribute("title")
		) return a.getAttribute("title");
	}
	return null;
}

function createCSSCookie(name,value,days) {
	if (days) {
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}

function readCSSCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

window.onload = function(e) {
	var cookie = readCSSCookie("portal_style");
	var title = cookie ? cookie : getPortalPreferredStyleSheet();
	setPortalStyleSheet(title);
}

window.onunload = function(e) {
	var title = getPortalStyleSheet();
	createCSSCookie("portal_style", title, 365);
}

var cookie = readCSSCookie("portal_style");
var title = cookie ? cookie : getPortalPreferredStyleSheet();
setPortalStyleSheet(title);