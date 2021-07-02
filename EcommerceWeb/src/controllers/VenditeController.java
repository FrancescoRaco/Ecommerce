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
import beans.SessionBean;
import beans.UtenteBean;
import beans.VenditeBean;
import dto.ProdottoDTO;
import ejbInterfaces.CommonDataAccess;
import utils.Paginator;

@ManagedBean
@RequestScoped
public class VenditeController extends BaseController {
	
	private final static Logger logger = LogManager.getLogger(AuthenticationController.class);
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;
	
	@ManagedProperty(value="#{utenteBean}")
	private UtenteBean utenteBean;
	
	@ManagedProperty(value="#{venditeBean}")
	private VenditeBean venditeBean;
	
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
			if (venditeBean.getProdottiAttivi() == null) {
				caricaProdottiAttivi();
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void effettuaOperazioniIniziali(ComponentSystemEvent event) {
		if (sessionBean != null) {
			sessionBean.setActivePage("mieAttivita");
		}
		if (!FacesContext.getCurrentInstance().isPostback() && !fromInit && !dettaglioBean.isIndietro()) {
			ripulisci();
		}
	}
	
	private void ripulisci() {
		try {
			venditeBean.setProdottiAttivi(null);
			venditeBean.setTabellaProdottiAbilitata(false);
			venditeBean.setPaginatorProdotti(null);
			caricaProdottiAttivi();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void caricaProdottiAttivi() throws Exception {
		ProdottoDTO prodottoDTO = new ProdottoDTO();
		prodottoDTO.setCfVenditore(utenteBean.getCodiceFiscale());
		venditeBean.setProdottiAttivi(commonDataAccess.getProdottiAttivi(prodottoDTO));
		if (venditeBean.getProdottiAttivi() != null && !venditeBean.getProdottiAttivi().isEmpty()) {
			venditeBean.setPaginatorProdotti(new Paginator<ProdottoDTO>());
			venditeBean.getPaginatorProdotti().setResultset(venditeBean.getProdottiAttivi());
			venditeBean.setTabellaProdottiAbilitata(true);
			fromInit = true;
		} else {
			venditeBean.setPaginatorProdotti(null);
			venditeBean.setTabellaProdottiAbilitata(false);
		}
	}
	
	public String visualizzaDettaglio() {
		
		dettaglioBean.setProvenienza("vendite.xhtml");
		
		return "dettaglio";
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public UtenteBean getUtenteBean() {
		return utenteBean;
	}

	public void setUtenteBean(UtenteBean utenteBean) {
		this.utenteBean = utenteBean;
	}

	public VenditeBean getVenditeBean() {
		return venditeBean;
	}

	public void setVenditeBean(VenditeBean venditeBean) {
		this.venditeBean = venditeBean;
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
