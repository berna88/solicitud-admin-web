package mx.com.cuervo.solicitud.admin.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import mx.com.cuervo.rutas.transporte.model.Ruta;
import mx.com.cuervo.rutas.transporte.model.Solicitud;
import mx.com.cuervo.rutas.transporte.service.RutaLocalService;
import mx.com.cuervo.rutas.transporte.service.SolicitudLocalService;
import mx.com.cuervo.solicitud.admin.constants.SolicitudAdminPortletKeys;

/**
 * @author Jonathan Cruz Sanchez
 */
@Component(
	    immediate = true,
	    property = {
	            "com.liferay.portlet.display-category=category.hidden",
	            "com.liferay.portlet.scopeable=true",
	            "javax.portlet.display-name=Solicitudes",
	            "javax.portlet.expiration-cache=0",
	            "javax.portlet.init-param.portlet-title-based-navigation=true",
	            "javax.portlet.init-param.template-path=/",
	            "javax.portlet.init-param.view-template=/view.jsp",
	            "javax.portlet.name=" + SolicitudAdminPortletKeys.SOLICITUDADMIN,
	            "javax.portlet.resource-bundle=content.Language",
	            "javax.portlet.security-role-ref=administrator",
	            "javax.portlet.supports.mime-type=text/html",
	            "com.liferay.portlet.add-default-resource=true"
	    },
	    service = Portlet.class
	)
public class SolicitudAdminPortlet extends MVCPortlet {
	 
	public void aprobarSolicitud(ActionRequest request, ActionResponse response)
		    throws PortalException {
		  ServiceContext serviceContext = ServiceContextFactory.getInstance(
			        Solicitud.class.getName(), request);

			    long solicitudId = ParamUtil.getLong(request, "solicitudId");

			    try {
			       Solicitud solicitud = _solicitudLocalService.aprobarSolicitud(serviceContext.getUserId(), 
			        		solicitudId, true, serviceContext);
			       
			      Ruta ruta = _rutaLocalService.fetchRuta(solicitud.getRutaId());
			      _rutaLocalService.updateRuta(serviceContext.getUserId(), 
			    		  ruta.getRutaId(), ruta.getNombreRuta(), 
			    		  ruta.getCapacidad(), 
			    		  ruta.getDisponibilidad() - 1, serviceContext);
			      
			    }
			    catch (PortalException pe) {

			        Logger.getLogger(SolicitudAdminPortlet.class.getName()).log(
			            Level.SEVERE, null, pe);
			    }
	}
	
	public void rechazarSolicitud(ActionRequest request, ActionResponse response)
		    throws PortalException {
		  ServiceContext serviceContext = ServiceContextFactory.getInstance(
			        Solicitud.class.getName(), request);

			    long solicitudId = ParamUtil.getLong(request, "solicitudId");

			    try {
			    	Solicitud solicitud = _solicitudLocalService.aprobarSolicitud(serviceContext.getUserId(), 
			        		solicitudId, false, serviceContext);
			        Ruta ruta = _rutaLocalService.fetchRuta(solicitud.getRutaId());
				      _rutaLocalService.updateRuta(serviceContext.getUserId(), 
				    		  ruta.getRutaId(), ruta.getNombreRuta(), 
				    		  ruta.getCapacidad(), 
				    		  ruta.getDisponibilidad() + 1, serviceContext);
				  
			    }
			    catch (PortalException pe) {

			        Logger.getLogger(SolicitudAdminPortlet.class.getName()).log(
			            Level.SEVERE, null, pe);
			    }
	}
	public void deleteSolicitud(ActionRequest request, ActionResponse response)
		    throws PortalException {

		    ServiceContext serviceContext = ServiceContextFactory.getInstance(
		        Solicitud.class.getName(), request);

		    long solicitudId = ParamUtil.getLong(request, "solicitudId");

		    try {
		        _solicitudLocalService.deleteSolicitud(solicitudId, serviceContext);
		    }
		    catch (PortalException pe) {

		        Logger.getLogger(SolicitudAdminPortlet.class.getName()).log(
		            Level.SEVERE, null, pe);
		    }
		}

	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	        throws IOException, PortletException {

	        try {
	            ServiceContext serviceContext = ServiceContextFactory.getInstance(
	                Ruta.class.getName(), renderRequest);

	            long groupId = serviceContext.getScopeGroupId();

	            long rutaId = ParamUtil.getLong(renderRequest, "rutaId");

	         
	            List<Ruta> rutas = _rutaLocalService.getRutas(
	                groupId);

	            if (rutas.isEmpty()) {
	                Ruta ruta = 
	                		_rutaLocalService.addRuta(serviceContext.getUserId(), 
	                				"Main", 0, 0, serviceContext);
	                
	                rutaId = ruta.getRutaId();
	            }

	            if (rutaId == 0) {
	            	rutaId = rutas.get(0).getRutaId();
	            }

	            renderRequest.setAttribute("rutaId", rutaId);
	        }
	        catch (Exception e) {
	           System.out.println("Exception");
	        }

	        super.render(renderRequest, renderResponse);
	}
	
	@Reference(unbind = "-")
	private RutaLocalService _rutaLocalService;
	
	@Reference(unbind = "-")
	private SolicitudLocalService _solicitudLocalService;
	
}