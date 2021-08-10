package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import beans.DettaglioBean;
import beans.MessagesBean;
import beans.RicercaProdottiBean;
import beans.SessionBean;
import dto.ProdottoDTO;
import ejbInterfaces.CommonDataAccess;
import utils.CommonUtils;
import utils.Paginator;

@ManagedBean
@RequestScoped
public class RicercaProdottiController extends BaseController {
	
	private final static Logger logger = LogManager.getLogger(RicercaProdottiController.class);
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;
	
	@ManagedProperty(value="#{ricercaProdottiBean}")
	private RicercaProdottiBean ricercaProdottiBean;
	
	@ManagedProperty(value="#{dettaglioBean}")
	private DettaglioBean dettaglioBean;
	
	@ManagedProperty(value="#{messagesBean}")
	private MessagesBean messagesBean;
	
	@EJB
	private static CommonDataAccess commonDataAccess;
	
	private boolean fromInit;
	
	@PostConstruct
	public void init() {
		try {
			if (ricercaProdottiBean.getProdottiAttivi() == null) {
				caricaProdottiAttivi();
			}
			if (ricercaProdottiBean.getCategorie() == null) {
				ricercaProdottiBean.setCategorie(commonDataAccess.getCategorie());
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}		
	}
	
	public void effettuaOperazioniIniziali(ComponentSystemEvent event) {
		if (sessionBean != null) {
			sessionBean.setActivePage("ricercaProdotti");
		}
		if (!FacesContext.getCurrentInstance().isPostback() && !fromInit && !dettaglioBean.isIndietro()) {
			ripulisci();
		}
	}
	
	public void ripulisci() {
		try {
			ricercaProdottiBean.setProdottiAttivi(null);
			ricercaProdottiBean.setPaginatorProdotti(null);
			ricercaProdottiBean.setTabellaProdottiAbilitata(false);
			ricercaProdottiBean.setCodiceCategoria(null);
			ricercaProdottiBean.setCfVenditore(null);
			ricercaProdottiBean.setId(null);
			ricercaProdottiBean.setPrezzoBase(null);
			ricercaProdottiBean.setPrezzoBaseWrap(null);
			ricercaProdottiBean.setIdWrap(null);
			caricaProdottiAttivi();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void caricaProdottiAttivi() throws Exception {
		if (validaInput()) {
			ProdottoDTO prodottoDTO = new ProdottoDTO();
			prodottoDTO.setId(ricercaProdottiBean.getId() == null ? 0 : ricercaProdottiBean.getId());
			prodottoDTO.setTitolo(ricercaProdottiBean.getTitolo());
			prodottoDTO.setCodiceCategoria(ricercaProdottiBean.getCodiceCategoria() == null ? 0 : ricercaProdottiBean.getCodiceCategoria());
			prodottoDTO.setCfVenditore(ricercaProdottiBean.getCfVenditore());
			prodottoDTO.setPrezzoBase(ricercaProdottiBean.getPrezzoBase() == null ? 0 : ricercaProdottiBean.getPrezzoBase());
			ricercaProdottiBean.setProdottiAttivi(commonDataAccess.getProdottiAttivi(prodottoDTO));
			if (ricercaProdottiBean.getProdottiAttivi() != null && !ricercaProdottiBean.getProdottiAttivi().isEmpty()) {
				ricercaProdottiBean.setPaginatorProdotti(new Paginator<ProdottoDTO>());
				ricercaProdottiBean.getPaginatorProdotti().setResultset(ricercaProdottiBean.getProdottiAttivi());
				ricercaProdottiBean.setTabellaProdottiAbilitata(true);
				fromInit = true;
			} else {
				ricercaProdottiBean.setPaginatorProdotti(null);
				ricercaProdottiBean.setTabellaProdottiAbilitata(false);
			}
		}
	}
	
	private boolean validaInput() throws Exception {
		
		if (ricercaProdottiBean != null && ricercaProdottiBean.getIdWrap() != null && !ricercaProdottiBean.getIdWrap().isEmpty()) {
			Integer converted = CommonUtils.stringToInteger(ricercaProdottiBean.getIdWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione dell'identificativo del prodotto", "prodottoId");
			} else {
				ricercaProdottiBean.setId(converted);
			}
		}
		
		if (ricercaProdottiBean != null && ricercaProdottiBean.getTitolo() != null && !ricercaProdottiBean.getTitolo().isEmpty() && !CommonUtils.validaStringa(ricercaProdottiBean.getTitolo())) {
			messagesBean.addError("Errore durante la validazione del titolo", "titoloId");
		}
		
		if (ricercaProdottiBean != null && ricercaProdottiBean.getCfVenditore() != null && !ricercaProdottiBean.getCfVenditore().isEmpty() && !CommonUtils.validaCodiceFiscale(ricercaProdottiBean.getCfVenditore())) {
			messagesBean.addError("Errore durante la validazione del codice fiscale del venditore", "cfVenditoreId");
		}
		
		if (ricercaProdottiBean != null && ricercaProdottiBean.getPrezzoBaseWrap() != null && !ricercaProdottiBean.getPrezzoBaseWrap().isEmpty()) {
			Integer converted = CommonUtils.stringToInteger(ricercaProdottiBean.getPrezzoBaseWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione del prezzo base", "prezzoBaseId");
			} else {
				ricercaProdottiBean.setPrezzoBase(converted);
			}
		}
		
		if (messagesBean.getErrors() != null && messagesBean.getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}
	
	public void ricercaProdottiAttivi() {
		try {
			caricaProdottiAttivi();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public String visualizzaDettaglio() {
		
		dettaglioBean.setProvenienza("ricercaProdotti.xhtml");
		
		return "dettaglio";
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public RicercaProdottiBean getRicercaProdottiBean() {
		return ricercaProdottiBean;
	}

	public void setRicercaProdottiBean(RicercaProdottiBean ricercaProdottiBean) {
		this.ricercaProdottiBean = ricercaProdottiBean;
	}

	public DettaglioBean getDettaglioBean() {
		return dettaglioBean;
	}

	public void setDettaglioBean(DettaglioBean dettaglioBean) {
		this.dettaglioBean = dettaglioBean;
	}

	public MessagesBean getMessagesBean() {
		return messagesBean;
	}

	public void setMessagesBean(MessagesBean messagesBean) {
		this.messagesBean = messagesBean;
	}

}
