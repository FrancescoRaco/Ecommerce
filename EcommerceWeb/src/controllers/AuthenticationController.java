package controllers;


import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beans.LoginBean;
import beans.MessagesBean;
import beans.UtenteBean;
import dto.UtenteDTO;
import ejbInterfaces.LoginDataAccess;
import ejbInterfaces.SignupDataAccess;
import utils.CommonUtils;

@ManagedBean
@RequestScoped
public class AuthenticationController extends BaseController {
	
	private final static Logger logger = LogManager.getLogger(AuthenticationController.class);
	
	@ManagedProperty(value="#{utenteBean}")
	private UtenteBean utenteBean;
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginBean;
	
	@ManagedProperty(value="#{messagesBean}")
	private MessagesBean messagesBean;
	
	@EJB
	private static LoginDataAccess loginDataAccess;
	
	@EJB
	private static SignupDataAccess signupDataAccess;
	
	@PostConstruct
	public void init() {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			clearLoginBean();
		}
	}
	
	public String login() {
		
		try {
			if (validaInput()) {
				UtenteDTO utenteDTO = new UtenteDTO();
				utenteDTO.setCodiceFiscale(loginBean.getCodiceFiscale());
				utenteDTO.setPassword(loginBean.getPassword());
				UtenteDTO utenteLoggato = loginDataAccess.login(utenteDTO);
				if (utenteLoggato != null) {
					utenteBean.setCodiceFiscale(utenteLoggato.getCodiceFiscale());
					utenteBean.setPassword(utenteLoggato.getPassword());
					utenteBean.setNome(utenteLoggato.getNome());
					utenteBean.setCognome(utenteLoggato.getCognome());
					utenteBean.setDomicilio(utenteLoggato.getDomicilio());
					utenteBean.setEmail(utenteLoggato.getEmail());
					utenteBean.setTelefono(utenteLoggato.getTelefono());
					utenteBean.setDataNascita(utenteLoggato.getDataNascita());
					utenteBean.setLoggedIn(true);
					return "success";
				} else {
					messagesBean.addError("Codice fiscale e/o password sbagliati!", "codFiscId");
					messagesBean.getInvalidFields().add("passwordId");
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	
	public String fakeLogin() {
		try {
			UtenteDTO utenteDTO = new UtenteDTO();
			utenteDTO.setCodiceFiscale("RCAFNC93H26H501M");
			utenteDTO.setPassword("FrancescoRaco93!");
			UtenteDTO utenteLoggato = loginDataAccess.login(utenteDTO);
			if (utenteLoggato != null) {
				utenteBean.setCodiceFiscale(utenteLoggato.getCodiceFiscale());
				utenteBean.setPassword(utenteLoggato.getPassword());
				utenteBean.setNome(utenteLoggato.getNome());
				utenteBean.setCognome(utenteLoggato.getCognome());
				utenteBean.setDomicilio(utenteLoggato.getDomicilio());
				utenteBean.setEmail(utenteLoggato.getEmail());
				utenteBean.setTelefono(utenteLoggato.getTelefono());
				utenteBean.setDataNascita(utenteLoggato.getDataNascita());
				utenteBean.setLoggedIn(true);
				return "success";
			} else {
				messagesBean.addError("Codice fiscale e/o password sbagliati!", "codFiscId");
				messagesBean.getInvalidFields().add("passwordId");
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	
	public void signup() {
		try {
			clearUtenteBean();
			utenteBean.setSignupAbilitato(true);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void confermaSignup() {
		try {
			if (validaInputSignup(utenteBean.getPassword())) {
				//TODO insert
				UtenteDTO utenteDTO = new UtenteDTO();
				utenteDTO.setCodiceFiscale(utenteBean.getCodiceFiscale());
				utenteDTO.setPassword(utenteBean.getPassword());
				utenteDTO.setNome(utenteBean.getNome());
				utenteDTO.setCognome(utenteBean.getCognome());
				utenteDTO.setDomicilio(utenteBean.getDomicilio());
				utenteDTO.setEmail(utenteBean.getEmail());
				utenteDTO.setTelefono(utenteBean.getTelefono());
				utenteDTO.setDataNascita(utenteBean.getDataNascita());
				signupDataAccess.signup(utenteDTO);
				messagesBean.getSuccesses().add("Utente " + utenteBean.getNome() + " " + utenteBean.getCognome() + " creato con successo");
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	public void chiudiModalSignup() {
		utenteBean.setSignupAbilitato(false);
	}
	
	private boolean validaInputSignup(String password) throws Exception {
		
		if (utenteBean == null || utenteBean.getCodiceFiscale() == null || utenteBean.getCodiceFiscale().isEmpty()) {
			messagesBean.addError("Inserire il codice fiscale", "codFiscSignupId");
		} else if (utenteBean != null && utenteBean.getCodiceFiscale() != null && !utenteBean.getCodiceFiscale().isEmpty() && !CommonUtils.validaCodiceFiscale(utenteBean.getCodiceFiscale())) {
			messagesBean.addError("Errore durante la validazione del codice fiscale", "codFiscSignupId");
		}
		
		if (utenteBean == null || password == null || password.isEmpty()) {
			messagesBean.addError("Inserire la password", "passwordSignupId");
		} else if (utenteBean != null && password != null && !password.isEmpty() && !CommonUtils.validaPassword(password)) {
			messagesBean.addError("Errore durante la validazione della password", "passwordSignupId");
		}
		
		if (utenteBean == null || utenteBean.getNome() == null || utenteBean.getNome().isEmpty()) {
			messagesBean.addError("Inserire il nome", "nomeId");
		} else if (utenteBean != null && utenteBean.getNome() != null && !utenteBean.getNome().isEmpty() && !CommonUtils.validaNome(utenteBean.getNome())) {
			messagesBean.addError("Errore durante la validazione del nome", "nomeId");
		}
		
		if (utenteBean == null || utenteBean.getCognome() == null || utenteBean.getCognome().isEmpty()) {
			messagesBean.addError("Inserire il cognome", "cognomeId");
		} else if (utenteBean != null && utenteBean.getCognome() != null && !utenteBean.getCognome().isEmpty() && !CommonUtils.validaNome(utenteBean.getCognome())) {
			messagesBean.addError("Errore durante la validazione del cognome", "cognomeId");
		}
		
		if (utenteBean == null || utenteBean.getDomicilio() == null || utenteBean.getDomicilio().isEmpty()) {
			messagesBean.addError("Inserire il domicilio", "domicilioId");
		} else if (utenteBean != null && utenteBean.getDomicilio() != null && !utenteBean.getDomicilio().isEmpty() && !CommonUtils.validaStringa(utenteBean.getDomicilio())) {
			messagesBean.addError("Errore durante la validazione del domicilio", "domicilioId");
		}
		
		if (utenteBean == null || utenteBean.getEmail() == null || utenteBean.getEmail().isEmpty()) {
			messagesBean.addError("Inserire l'email", "emailId");
		} else if (utenteBean != null && utenteBean.getEmail() != null && !utenteBean.getEmail().isEmpty() && !CommonUtils.validaEmail(utenteBean.getEmail())) {
			messagesBean.addError("Errore durante la validazione dell'email", "emailId");
		}
		
		if (utenteBean == null || utenteBean.getTelefono() == null || utenteBean.getTelefono().isEmpty()) {
			messagesBean.addError("Inserire il telefono", "telefonoId");
		} else if (utenteBean != null && utenteBean.getTelefono() != null && !utenteBean.getTelefono().isEmpty() && !CommonUtils.validaNumero(utenteBean.getTelefono())) {
			messagesBean.addError("Errore durante la validazione del telefono", "telefonoId");
		}
		
		if (utenteBean == null || utenteBean.getDataNascitaWrap() == null || utenteBean.getDataNascitaWrap().isEmpty()) {
			messagesBean.addError("Inserire la data di nascita", "dataNascitaId");
		} else if (utenteBean != null && utenteBean.getDataNascitaWrap() != null) {
			Date converted = CommonUtils.stringToDate(utenteBean.getDataNascitaWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione della data di nascita", "dataNascitaId");
			} else {
				utenteBean.setDataNascita(converted);
			}
		}
		
		if (messagesBean.getErrors() != null && messagesBean.getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}
	
	private boolean validaInput() throws Exception {
		
		if (loginBean == null || loginBean.getCodiceFiscale() == null || loginBean.getCodiceFiscale().isEmpty()) {
			messagesBean.addError("Inserire il codice fiscale", "codFiscId");
		} else if (loginBean != null && loginBean.getCodiceFiscale() != null && !loginBean.getCodiceFiscale().isEmpty() && !CommonUtils.validaCodiceFiscale(loginBean.getCodiceFiscale())) {
			messagesBean.addError("Errore durante la validazione del codice fiscale", "codFiscId");
		}
		
		if (loginBean == null || loginBean.getPassword() == null || loginBean.getPassword().isEmpty()) {
			messagesBean.addError("Inserire la password", "passwordId");
		} else if (loginBean != null && loginBean.getPassword() != null && !loginBean.getPassword().isEmpty() && !CommonUtils.validaPassword(loginBean.getPassword())) {
			messagesBean.addError("Errore durante la validazione della password", "passwordId");
		}
		
		if (messagesBean.getErrors() != null && messagesBean.getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}
	
	public void logout() {
		try {
			clearUtenteBean();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/EcommerceWeb/faces/pages/home.xhtml");
			FacesContext.getCurrentInstance().responseComplete();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void clearUtenteBean() {
		if (utenteBean != null) {
			utenteBean.setCodiceFiscale(null);
			utenteBean.setPassword(null);
			utenteBean.setNome(null);
			utenteBean.setCognome(null);
			utenteBean.setDomicilio(null);
			utenteBean.setEmail(null);
			utenteBean.setTelefono(null);
			utenteBean.setDataNascita(null);
			utenteBean.setLoggedIn(false);
			utenteBean.setSignupAbilitato(false);
		}
	}
	
	private void clearLoginBean() {
		if (loginBean != null) {
			loginBean.setCodiceFiscale(null);
			loginBean.setPassword(null);
		}
	}

	public UtenteBean getUtenteBean() {
		return utenteBean;
	}

	public void setUtenteBean(UtenteBean utenteBean) {
		this.utenteBean = utenteBean;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public MessagesBean getMessagesBean() {
		return messagesBean;
	}

	public void setMessagesBean(MessagesBean messagesBean) {
		this.messagesBean = messagesBean;
	}

}
