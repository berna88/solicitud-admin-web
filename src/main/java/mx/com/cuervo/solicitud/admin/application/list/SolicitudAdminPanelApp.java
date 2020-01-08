package mx.com.cuervo.solicitud.admin.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import mx.com.cuervo.solicitud.admin.constants.SolicitudAdminPortletKeys;

@Component(
	    immediate = true,
	    property = {
	        "panel.app.order:Integer=300",
	        "panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT
	    },
	    service = PanelApp.class
	)
public class SolicitudAdminPanelApp extends BasePanelApp{

	@Override
    public String getPortletId() {
        return SolicitudAdminPortletKeys.SOLICITUDADMIN;
    }

    @Override
    @Reference(
        target = "(javax.portlet.name=" + SolicitudAdminPortletKeys.SOLICITUDADMIN + ")",
        unbind = "-"
    )
    public void setPortlet(Portlet portlet) {
        super.setPortlet(portlet);
    }

}
