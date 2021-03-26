<%@page import="lebah.util.UIDGenerator"%>
<%
session.invalidate();
String randomNo = UIDGenerator.getUID();
response.sendRedirect("c/?rndId=" + randomNo); 
%>

Redirect... please wait...