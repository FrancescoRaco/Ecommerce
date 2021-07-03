package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import beans.MessagesBean;
import beans.UtenteBean;
import dto.UtenteDTO;
import ejbInterfaces.CommonDataAccess;
import utils.CommonUtils;

@ManagedBean
@RequestScoped
public class UtenteController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger(UtenteController.class);
	
	@ManagedProperty(value="#{utenteBean}")
	private UtenteBean utenteBean;
	
	@ManagedProperty(value="#{messagesBean}")
	private MessagesBean messagesBean;
	
	@EJB
	private static CommonDataAccess commonDataAccess;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void apriModificaPassword() {
		utenteBean.setModificaPassword(true);
	}
	
	public void confermaModificaPassword() {
		try {
			if (validaInputPassword()) {
				//TODO query
				UtenteDTO utenteDTO = new UtenteDTO();
				utenteDTO.setCodiceFiscale(utenteBean.getCodiceFiscale());
				utenteDTO.setPassword(utenteBean.getNuovaPassword());
				commonDataAccess.modificaPassword(utenteDTO);
				utenteBean.setPassword(utenteBean.getNuovaPassword());
				messagesBean.getSuccesses().add("Password modificata con successo");
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	public void chiudiModificaPassword() {
		utenteBean.setModificaPassword(false);
	}
	
	private boolean validaInputPassword() throws Exception {
		
		if (utenteBean == null || utenteBean.getVecchiaPassword() == null || utenteBean.getVecchiaPassword().isEmpty()) {
			messagesBean.addError("Inserire la password attuale", "vecchiaPasswordId");
		} else if (utenteBean != null && utenteBean.getVecchiaPassword() != null && !CommonUtils.validaPassword(utenteBean.getVecchiaPassword())) {
			messagesBean.addError("Errore durante la validazione della password attuale", "vecchiaPasswordId");
		} else if (!utenteBean.getVecchiaPassword().equals(utenteBean.getPassword())) {
			messagesBean.addError("La password immessa non corrisponde a quella attuale", "vecchiaPasswordId");
		}
		
		if (utenteBean == null || utenteBean.getNuovaPassword() == null || utenteBean.getNuovaPassword().isEmpty()) {
			messagesBean.addError("Inserire la nuova password", "nuovaPasswordId");
		} else if (utenteBean != null && utenteBean.getNuovaPassword() != null && !CommonUtils.validaPassword(utenteBean.getNuovaPassword())) {
			messagesBean.addError("Errore durante la validazione della nuova password", "nuovaPasswordId");
		}
		
		if (messagesBean.getErrors() != null && messagesBean.getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}

	public UtenteBean getUtenteBean() {
		return utenteBean;
	}

	public void setUtenteBean(UtenteBean utenteBean) {
		this.utenteBean = utenteBean;
	}

	public MessagesBean getMessagesBean() {
		return messagesBean;
	}

	public void setMessagesBean(MessagesBean messagesBean) {
		this.messagesBean = messagesBean;
	}

}
