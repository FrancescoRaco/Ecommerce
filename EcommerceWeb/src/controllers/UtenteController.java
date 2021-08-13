package controllers;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import beans.MessagesBean;
import beans.UtenteBean;
import dto.UtenteDTO;
import ejbInterfaces.CommonDataAccess;
import ejbInterfaces.LoginDataAccess;
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
	
	@EJB
	private static LoginDataAccess loginDataAccess;
	
	@PostConstruct
	public void init() {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			ripulisci();
		}
	}
	
	public void apriModificaPassword() {
		utenteBean.setModificaPassword(true);
	}
	
	public void confermaModificaPassword() {
		try {
			if (validaInputPassword()) {
				UtenteDTO utenteDTO = new UtenteDTO();
				utenteDTO.setCodiceFiscale(utenteBean.getCodiceFiscale());
				utenteDTO.setPassword(utenteBean.getNuovaPassword());
				commonDataAccess.modificaPassword(utenteDTO);
				chiudiModificaPassword();
				utenteBean.setPassword(utenteBean.getNuovaPassword());
				messagesBean.getSuccesses().add("Password modificata con successo");
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getMessaggiModale().getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	public void chiudiModificaPassword() {
		utenteBean.setModificaPassword(false);
	}
	
	private boolean validaInputPassword() throws Exception {
		
		if (utenteBean == null || utenteBean.getVecchiaPassword() == null || utenteBean.getVecchiaPassword().isEmpty()) {
			messagesBean.getMessaggiModale().addError("Inserire la password attuale", "vecchiaPasswordId");
		} else if (utenteBean != null && utenteBean.getVecchiaPassword() != null && !CommonUtils.validaPassword(utenteBean.getVecchiaPassword())) {
			messagesBean.getMessaggiModale().addError("Errore durante la validazione della password attuale", "vecchiaPasswordId");
		} else if (!utenteBean.getVecchiaPassword().equals(utenteBean.getPassword())) {
			messagesBean.getMessaggiModale().addError("La password immessa non corrisponde a quella attuale", "vecchiaPasswordId");
		}
		
		if (utenteBean == null || utenteBean.getNuovaPassword() == null || utenteBean.getNuovaPassword().isEmpty()) {
			messagesBean.getMessaggiModale().addError("Inserire la nuova password", "nuovaPasswordId");
		} else if (utenteBean != null && utenteBean.getNuovaPassword() != null && !CommonUtils.validaPassword(utenteBean.getNuovaPassword())) {
			messagesBean.getMessaggiModale().addError("Errore durante la validazione della nuova password", "nuovaPasswordId");
		}
		
		if (messagesBean.getMessaggiModale().getErrors() != null && messagesBean.getMessaggiModale().getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}
	
	public String goToModificaProfilo() {
		ripulisci();
		return "modificaProfilo";
	}
	
	public String goBack() {
		return "profilo";
	}
	
	private boolean validaInput() throws Exception {
		
		if (utenteBean.getUtenteInput() != null && utenteBean.getUtenteInput().getCodiceFiscale() != null && !utenteBean.getUtenteInput().getCodiceFiscale().isEmpty() && !CommonUtils.validaCodiceFiscale(utenteBean.getUtenteInput().getCodiceFiscale())) {
			messagesBean.addError("Errore durante la validazione del codice fiscale", "codFiscSignupId");
		}
		
		if (utenteBean.getUtenteInput() != null && utenteBean.getUtenteInput().getNome() != null && !utenteBean.getUtenteInput().getNome().isEmpty() && !CommonUtils.validaNome(utenteBean.getUtenteInput().getNome())) {
			messagesBean.addError("Errore durante la validazione del nome", "nomeId");
		}
		
		if (utenteBean.getUtenteInput() != null && utenteBean.getUtenteInput().getCognome() != null && !utenteBean.getUtenteInput().getCognome().isEmpty() && !CommonUtils.validaNome(utenteBean.getUtenteInput().getCognome())) {
			messagesBean.addError("Errore durante la validazione del cognome", "cognomeId");
		}
		
		if (utenteBean.getUtenteInput() != null && utenteBean.getUtenteInput().getDomicilio() != null && !utenteBean.getUtenteInput().getDomicilio().isEmpty() && !CommonUtils.validaStringa(utenteBean.getUtenteInput().getDomicilio())) {
			messagesBean.addError("Errore durante la validazione del domicilio", "domicilioId");
		}
		
		if (utenteBean.getUtenteInput() != null && utenteBean.getUtenteInput().getEmail() != null &&!utenteBean.getUtenteInput().getEmail().isEmpty() && !CommonUtils.validaEmail(utenteBean.getUtenteInput().getEmail())) {
			messagesBean.addError("Errore durante la validazione dell'email", "emailId");
		}
		
		if (utenteBean.getUtenteInput() != null && utenteBean.getUtenteInput().getTelefono() != null && !utenteBean.getUtenteInput().getTelefono().isEmpty() && !CommonUtils.validaNumero(utenteBean.getUtenteInput().getTelefono())) {
			messagesBean.addError("Errore durante la validazione del telefono", "telefonoId");
		}
		
		if (utenteBean.getUtenteInput() != null && utenteBean.getUtenteInput().getDataNascitaWrap() != null) {
			Date converted = CommonUtils.stringToDate(utenteBean.getUtenteInput().getDataNascitaWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione della data di nascita", "dataNascitaId");
			} else {
				utenteBean.getUtenteInput().setDataNascita(converted);
			}
		}
		
		if (messagesBean.getErrors() != null && messagesBean.getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}
	
	public void confermaModifiche() {
		try {
			if (validaInput()) {
				commonDataAccess.modificaProfilo(utenteBean.getUtenteInput());
				messagesBean.getSuccesses().add("Dati dell'utente modificati con successo");
				UtenteDTO utenteAggiornato = loginDataAccess.login(utenteBean.getUtenteInput());
				if (utenteAggiornato != null) {
					utenteBean.setCodiceFiscale(utenteAggiornato.getCodiceFiscale());
					utenteBean.setPassword(utenteAggiornato.getPassword());
					utenteBean.setNome(utenteAggiornato.getNome());
					utenteBean.setCognome(utenteAggiornato.getCognome());
					utenteBean.setDomicilio(utenteAggiornato.getDomicilio());
					utenteBean.setEmail(utenteAggiornato.getEmail());
					utenteBean.setTelefono(utenteAggiornato.getTelefono());
					utenteBean.setDataNascita(utenteAggiornato.getDataNascita());
					utenteBean.setSesso(utenteAggiornato.getSesso());
					utenteBean.setLoggedIn(true);
				} 
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	public void ripulisci() {
		try {
			UtenteDTO utenteInput = new UtenteDTO();
			//Travaso campi utente modificabili
			utenteInput.setCodiceFiscale(utenteBean.getCodiceFiscale());
			utenteInput.setPassword(utenteBean.getPassword());
			utenteInput.setNome(utenteBean.getNome());
			utenteInput.setCognome(utenteBean.getCognome());
			utenteInput.setDomicilio(utenteBean.getDomicilio());
			utenteInput.setEmail(utenteBean.getEmail());
			utenteInput.setTelefono(utenteBean.getTelefono());
			utenteInput.setDataNascitaWrap(CommonUtils.dateToString(utenteBean.getDataNascita()));
			utenteInput.setSesso(utenteBean.getSesso());
			utenteBean.setUtenteInput(utenteInput);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
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
