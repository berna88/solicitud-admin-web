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
    

<%if(solicitud.getAprobado() == true){ %>
<liferay-ui:icon image="close" message="Rechazar"
            url="<%=rechazarURL.toString() %>" />
<%} else{%>
<liferay-ui:icon image="close" message="Rechazar" url="#"></liferay-ui:icon>
<%} %>

<liferay-ui:icon image="check" message="Aprobar"
            url="<%=aprobarURL.toString() %>" />
<% if(solicitud.getAprobado() == true) { %>
<liferay-ui:icon-delete id="delete" url="#" />
<% }else { %>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
<% } %>
 



</liferay-ui:icon-menu>
<script>
$('#_mx_com_cuervo_solicitud_admin_SolicitudPortlet_delete').click(function(){
	  //do something
	alert('Tienes que rechazar antes de eliminar');
	});

</script>