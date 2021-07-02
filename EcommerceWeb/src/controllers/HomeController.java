package controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beans.SessionBean;

@ManagedBean
@RequestScoped
public class HomeController extends BaseController {
	
	private final static Logger logger = LogManager.getLogger(HomeController.class);
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void activatePage(ComponentSystemEvent event) {
		sessionBean.setActivePage("home");
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

}
