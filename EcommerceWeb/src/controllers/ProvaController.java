package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import beans.ProvaBean;
import ejbInterfaces.CommonDataAccess;

@ManagedBean
@RequestScoped
public class ProvaController {
	
//	private final static Logger logger = Logger.getLogger(ProvaController.class);
	
	@EJB
	private static CommonDataAccess commonDataAccess;
	
	@ManagedProperty(value="#{provaBean}")
	ProvaBean provaBean;
	
	public void ottieniNome() {
		try {
			provaBean.setStudente(commonDataAccess.test(provaBean.getMatricola()));
		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
			provaBean.setStudente("Errore");
		}
	}

	public ProvaBean getProvaBean() {
		return provaBean;
	}

	public void setProvaBean(ProvaBean provaBean) {
		this.provaBean = provaBean;
	}

}
