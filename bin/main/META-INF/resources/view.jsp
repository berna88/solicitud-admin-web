<%@include file="init.jsp"%>

<%
long rutaId = Long.valueOf((Long) renderRequest
        .getAttribute("rutaId"));
%>


<aui:nav cssClass="nav-tabs">

    <%
        List<Ruta> rutas = RutaLocalServiceUtil.getRutas(scopeGroupId);

            for (int i = 0; i < rutas.size(); i++) {

                Ruta curRuta = rutas.get(i);
                String cssClass = StringPool.BLANK;

                if (curRuta.getRutaId() == rutaId) {
                    cssClass = "active";
                }

 
    %>

    <portlet:renderURL var="viewPageURL">
        <portlet:param name="mvcPath" value="/view.jsp" />
        <portlet:param name="rutaId"
            value="<%=String.valueOf(curRuta.getRutaId())%>" />
    </portlet:renderURL>


    <aui:nav-item cssClass="<%=cssClass%>" href="<%=viewPageURL%>"
        label="<%=HtmlUtil.escape(curRuta.getNombreRuta())%>" />

    <%  
                

            }
    %>

</aui:nav>


<aui:button-row cssClass="parada-buttons">

    <portlet:renderURL var="addSolicitudURL">
        <portlet:param name="mvcPath" value="/edit_solicitud.jsp" />
        <portlet:param name="rutaId"
            value="<%=String.valueOf(rutaId)%>" />
    </portlet:renderURL>

    <aui:button onClick="<%=addSolicitudURL.toString()%>" value="Agregar Solicitud"></aui:button>

</aui:button-row>

<liferay-ui:search-container total="<%=SolicitudLocalServiceUtil.getSolicitudesByRutaCount(scopeGroupId.longValue(),rutaId)%>">
<liferay-ui:search-container-results
    results="<%=SolicitudLocalServiceUtil.getSolicitudesByRuta(scopeGroupId.longValue(),
                    rutaId, searchContainer.getStart(),
                    searchContainer.getEnd())%>" />

<liferay-ui:search-container-row
    className="mx.com.cuervo.rutas.transporte.model.Solicitud" modelVar="solicitud">

    <liferay-ui:search-container-column-text property="nombreParada" />

    <liferay-ui:search-container-column-text property="empleadoId" />

	 <liferay-ui:search-container-column-text property="nombreUsuario" />

	<liferay-ui:search-container-column-text property="telefono" />
	
	<liferay-ui:search-container-column-text property="aprobado" />
	
	        <liferay-ui:search-container-column-jsp
            align="right" 
            path="/solicitud_actions.jsp" />
	
</liferay-ui:search-container-row>

<liferay-ui:search-iterator />

</liferay-ui:search-container>