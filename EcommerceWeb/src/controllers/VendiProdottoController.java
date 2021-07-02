package controllers;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beans.MessagesBean;
import beans.SessionBean;
import beans.UtenteBean;
import beans.VendiProdottoBean;
import dto.CategoriaDTO;
import dto.ProdottoDTO;
import ejbInterfaces.CommonDataAccess;
import ejbInterfaces.SellerDataAccess;
import utils.CommonUtils;

@ManagedBean
@RequestScoped
public class VendiProdottoController extends BaseController {
	
	private final static Logger logger = LogManager.getLogger(VendiProdottoController.class);
	
	@ManagedProperty(value="#{vendiProdottoBean}")
	private VendiProdottoBean vendiProdottoBean;
	
	@ManagedProperty(value="#{messagesBean}")
	private MessagesBean messagesBean;
	
	@ManagedProperty(value="#{utenteBean}")
	private UtenteBean utenteBean;
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;
	
	@EJB
	private static CommonDataAccess commonDataAccess;
	
	@EJB
	private static SellerDataAccess sellerDataAccess;
	
	@PostConstruct
	public void init() {
		try {
			if (getCategorie() == null) {
				setCategorie(commonDataAccess.getCategorie());
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void effettuaOperazioniIniziali(ComponentSystemEvent event) {
		if (sessionBean != null) {
			sessionBean.setActivePage("mieAttivita");
		}
		if (!FacesContext.getCurrentInstance().isPostback()) {
			ripulisci();
		}
	}
	
	public void ripulisci() {
		vendiProdottoBean.setCodiceCategoria(null);
		vendiProdottoBean.setTitolo(null);
		vendiProdottoBean.setDescrizione(null);
		vendiProdottoBean.setDisponibilitaWrap(null);
		vendiProdottoBean.setPrezzoBaseWrap(null);
		vendiProdottoBean.setInfoAcquirenti(null);
	}
	
	public void inserisciProdotto() {
		try {
			if (validaInput()) {
				ProdottoDTO prodottoDTO = new ProdottoDTO();
				prodottoDTO.setCodiceCategoria(vendiProdottoBean.getCodiceCategoria());
				prodottoDTO.setDescrizione(vendiProdottoBean.getDescrizione());
				prodottoDTO.setTitolo(vendiProdottoBean.getTitolo());
				prodottoDTO.setCfVenditore(utenteBean.getCodiceFiscale());
				prodottoDTO.setDisponibilita(vendiProdottoBean.getDisponibilita());
				prodottoDTO.setPrezzoBase(vendiProdottoBean.getPrezzoBase());
				prodottoDTO.setInfoAcquirenti(vendiProdottoBean.getInfoAcquirenti());
				int prodottoId = sellerDataAccess.insertProdotto(prodottoDTO);
				messagesBean.getSuccesses().add("Prodotto " + prodottoId + " inserito con successo");
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	private boolean validaInput() throws Exception {
		
		if (vendiProdottoBean == null || vendiProdottoBean.getTitolo() == null || vendiProdottoBean.getTitolo().isEmpty()) {
			messagesBean.addError("Inserire il titolo", "titoloId");
		} else if (vendiProdottoBean != null && vendiProdottoBean.getTitolo() != null && !vendiProdottoBean.getTitolo().isEmpty() && !CommonUtils.validaStringa(vendiProdottoBean.getTitolo())) {
			messagesBean.addError("Errore durante la validazione del titolo", "titoloId");
		}
		
		if (vendiProdottoBean == null || vendiProdottoBean.getDescrizione() == null || vendiProdottoBean.getDescrizione().isEmpty()) {
			messagesBean.addError("Inserire la descrizione", "descrizioneId");
		} else if (vendiProdottoBean != null && vendiProdottoBean.getDescrizione() != null && !vendiProdottoBean.getDescrizione().isEmpty() && !CommonUtils.validaStringa(vendiProdottoBean.getDescrizione())) {
			messagesBean.addError("Errore durante la validazione della descrizione", "descrizioneId");
		}
		
		if (vendiProdottoBean == null || vendiProdottoBean.getDisponibilitaWrap() == null || vendiProdottoBean.getDisponibilitaWrap().isEmpty()) {
			messagesBean.addError("Inserire la disponibilità", "disponibilitaId");
		} else if (vendiProdottoBean != null && vendiProdottoBean.getDisponibilitaWrap() != null && !vendiProdottoBean.getDisponibilitaWrap().isEmpty()) {
			Integer converted = CommonUtils.stringToInteger(vendiProdottoBean.getDisponibilitaWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione della disponibilità", "disponibilitaId");
			} else {
				vendiProdottoBean.setDisponibilita(converted);
			}
		}
		
		if (vendiProdottoBean == null || vendiProdottoBean.getPrezzoBaseWrap() == null || vendiProdottoBean.getPrezzoBaseWrap().isEmpty()) {
			messagesBean.addError("Inserire il prezzo base", "prezzoBaseId");
		} else if (vendiProdottoBean != null && vendiProdottoBean.getPrezzoBaseWrap() != null && !vendiProdottoBean.getPrezzoBaseWrap().isEmpty()) {
			Integer converted = CommonUtils.stringToInteger(vendiProdottoBean.getPrezzoBaseWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione del prezzo base", "prezzoBaseId");
			} else {
				vendiProdottoBean.setPrezzoBase(converted);
			}
		}
		
		if (vendiProdottoBean != null && vendiProdottoBean.getInfoAcquirenti() != null && !vendiProdottoBean.getInfoAcquirenti().isEmpty() && !CommonUtils.validaStringa(vendiProdottoBean.getInfoAcquirenti())) {
			messagesBean.addError("Errore durante la validazione dele informazioni per gli acquirenti", "infoAcquirentiId");
		}
		
		if (messagesBean.getErrors() != null && messagesBean.getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}
	
	public VendiProdottoBean getVendiProdottoBean() {
		return vendiProdottoBean;
	}

	public void setVendiProdottoBean(VendiProdottoBean vendiProdottoBean) {
		this.vendiProdottoBean = vendiProdottoBean;
	}

	public MessagesBean getMessagesBean() {
		return messagesBean;
	}

	public void setMessagesBean(MessagesBean messagesBean) {
		this.messagesBean = messagesBean;
	}

	public UtenteBean getUtenteBean() {
		return utenteBean;
	}

	public void setUtenteBean(UtenteBean utenteBean) {
		this.utenteBean = utenteBean;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public List<CategoriaDTO> getCategorie() {
		return vendiProdottoBean.getCategorie();
	}
	
	public void setCategorie(List<CategoriaDTO> categorie) {
		vendiProdottoBean.setCategorie(categorie);
	}

}
