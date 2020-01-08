<%@include file="init.jsp"%>

<%
    String mvcPath = ParamUtil.getString(request, "mvcPath");

    ResultRow row = (ResultRow) request
                    .getAttribute("SEARCH_CONTAINER_RESULT_ROW");

    Solicitud solicitud = (Solicitud) row.getObject();
%>

<liferay-ui:icon-menu>

    
      <portlet:actionURL name="aprobarSolicitud" var="aprobarURL">
            <portlet:param name="solicitudId"
                value="<%= String.valueOf(solicitud.getSolicitudId()) %>" />
    </portlet:actionURL>
     
      <portlet:actionURL name="rechazarSolicitud" var="rechazarURL">
            <portlet:param name="solicitudId"
                value="<%= String.valueOf(solicitud.getSolicitudId()) %>" />
    </portlet:actionURL>
     
     <portlet:actionURL name="deleteSolicitud" var="deleteURL">
            <portlet:param name="solicitudId"
                value="<%= String.valueOf(solicitud.getSolicitudId()) %>" />
    </portlet:actionURL>
    

<% if(solicitud.getAprobado()){ %>
<liferay-ui:icon image="close" message="Rechazar"
            url="<%=rechazarURL.toString() %>" />

<% }else{ %>
<liferay-ui:icon image="check" message="Aprobar"
            url="<%=aprobarURL.toString() %>" />

<%} %>
    <liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />




</liferay-ui:icon-menu>