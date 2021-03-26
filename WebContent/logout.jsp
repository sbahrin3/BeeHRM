<%@page import="lebah.util.UIDGenerator"%>
<%
String refKey = (String) session.getAttribute("_ref_key");
if ( refKey == null ) {
	
	refKey = "default";
	
}

session.invalidate();
String randomNo = UIDGenerator.getUID();
response.sendRedirect("c/" + refKey + "?rndId=" + randomNo); 
%>

Logging out... please wait...
