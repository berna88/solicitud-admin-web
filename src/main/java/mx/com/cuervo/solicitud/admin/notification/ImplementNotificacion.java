package mx.com.cuervo.solicitud.admin.notification;

import java.util.Date;

import javax.portlet.ActionRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

public class ImplementNotificacion {
	private static final Log log = LogFactoryUtil.getLog(ImplementNotificacion.class);
	private ActionRequest request;
	private long userId;
	private String titulo;
	private String cuerpoMensaje;
	private String idPortlet;
	
	public String getIdPortlet() {
		return idPortlet;
	}
	public void setIdPortlet(String idPortlet) {
		this.idPortlet = idPortlet;
	}
	public ActionRequest getRequest() {
		return request;
	}
	public void setRequest(ActionRequest request) {
		this.request = request;
	}
	public long getUser() {
		return userId;
	}
	public void setUser(long userId) {
		this.userId = userId;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCuerpoMensaje() {
		return cuerpoMensaje;
	}
	public void setCuerpoMensaje(String cuerpoMensaje) {
		this.cuerpoMensaje = cuerpoMensaje;
	}
	
	public ImplementNotificacion(ActionRequest request, long userId, String titulo, String cuerpoMensaje, String idPortlet) {
		super();
		this.request = request;
		this.userId = userId;
		this.titulo = titulo;
		this.cuerpoMensaje = cuerpoMensaje;
		this.idPortlet = idPortlet;
	}
	
	public void sendNotification() {
        try {
        	ThemeDisplay display = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        	ServiceContext serviceContext = new ServiceContext();
    		serviceContext.setAddGuestPermissions(true);
    		serviceContext.setScopeGroupId(display.getScopeGroupId());
    		serviceContext.setUserId(userId);
    		JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
    	 	payloadJSON.put("userId", userId);
            payloadJSON.put("notificationText", cuerpoMensaje);
            payloadJSON.put("title", titulo);
			UserNotificationEvent event = UserNotificationEventLocalServiceUtil.addUserNotificationEvent(userId, idPortlet, (new Date()).getTime(), UserNotificationDeliveryConstants.TYPE_WEBSITE, userId, payloadJSON.toString(), false, serviceContext);
			log.info("Notificacion: "+event.isDelivered());
        } catch (PortalException e) {
			// TODO Auto-generated catch block
        	log.error("sendNotification"+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
