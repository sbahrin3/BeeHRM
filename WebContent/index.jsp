<%@page import="lebah.util.UIDGenerator"%>
<%
String randomNo = UIDGenerator.getUID();
response.sendRedirect("c/?rndId=" + randomNo); 
%>