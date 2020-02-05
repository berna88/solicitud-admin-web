package mx.com.cuervo.solicitud.admin.notification;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import mx.com.cuervo.solicitud.admin.constants.SolicitudAdminPortletKeys;

@Component(immediate = true, service = {UserNotificationHandler.class})
public class Notification extends BaseUserNotificationHandler{
	public static final String PORTLET_ID = SolicitudAdminPortletKeys.SOLICITUDADMIN;
	
	public Notification() {
        this.setPortletId(PORTLET_ID);
    }
	
	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
			throws Exception {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject((String) userNotificationEvent.getPayload());
		 String notificationText = jsonObject.getString("notificationText");
	        String titleNotification = jsonObject.getString("title");
	        String title = "<strong>" + titleNotification + "</strong>";
	        String body = StringUtil.replace((String)this.getBodyTemplate(), (String[])new String[]{"[$TITLE$]", "[$BODY_TEXT$]"}, (String[])new String[]{title, notificationText});
		return body;
	}
	
	@Override
	protected String getBodyTemplate() throws Exception {
		// TODO Auto-generated method stub
		StringBundler sb = new StringBundler();
        sb.append("<div class=\"title\">[$TITLE$]</div><div ");
        sb.append("class=\"body\">[$BODY_TEXT$]</div>");
		return sb.toString();
	}
}
