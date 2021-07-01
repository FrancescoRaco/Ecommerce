package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import beans.OrdineBean;
import beans.OrdiniEffettuatiBean;
import beans.SessionBean;
import beans.UtenteBean;
import dto.OrdineDTO;
import ejbInterfaces.BuyerDataAccess;
import utils.Paginator;

@ManagedBean
@RequestScoped
public class OrdiniEffettuatiController extends BaseController {
	
	@ManagedProperty(value="#{ordiniEffettuatiBean}")
	private OrdiniEffettuatiBean ordiniEffettuatiBean;
	
	@ManagedProperty(value="#{ordineBean}")
	private OrdineBean ordineBean;
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;
	
	@ManagedProperty(value="#{utenteBean}")
	private UtenteBean utenteBean;
	
	@EJB
	private static BuyerDataAccess buyerDataAccess;
	
	private boolean fromInit;
	
	@PostConstruct
	public void init() {
		try {
			if (ordiniEffettuatiBean.getOrdini() == null) {
				caricaOrdiniEffettuati();
			}
		} catch(Exception e) {
			
		}
	}
	
	private void caricaOrdiniEffettuati() throws Exception {
		ordiniEffettuatiBean.setOrdini(buyerDataAccess.getOrdiniEffettuati(utenteBean.getCodiceFiscale()));
		if (ordiniEffettuatiBean.getOrdini() != null && !ordiniEffettuatiBean.getOrdini().isEmpty()) {
			ordiniEffettuatiBean.setPaginatorOrdini(new Paginator<OrdineDTO>());
			ordiniEffettuatiBean.getPaginatorOrdini().setResultset(ordiniEffettuatiBean.getOrdini());
			ordiniEffettuatiBean.setTabellaOrdiniAbilitata(true);
			fromInit = true;
		} else {
			ordiniEffettuatiBean.setPaginatorOrdini(null);
			ordiniEffettuatiBean.setTabellaOrdiniAbilitata(false);
		}
	}
	
	public void effettuaOperazioniIniziali(ComponentSystemEvent event) {
		if (sessionBean != null) {
			sessionBean.setActivePage("mieAttivita");
		}
		if (!FacesContext.getCurrentInstance().isPostback() && !fromInit && !ordineBean.isIndietro()) {
			ripulisci();
		}
	}
	
	private void ripulisci() {
		try {
			ordiniEffettuatiBean.setOrdini(null);
			ordiniEffettuatiBean.setTabellaOrdiniAbilitata(false);
			ordiniEffettuatiBean.setPaginatorOrdini(null);
			caricaOrdiniEffettuati();
		} catch(Exception e) {
			
		}
	}
	
	public String visualizzaOrdine() {
		
		ordineBean.setProvenienza("ordiniEffettuati.xhtml");
		
		return "ordine";
	}

	public OrdiniEffettuatiBean getOrdiniEffettuatiBean() {
		return ordiniEffettuatiBean;
	}

	public void setOrdiniEffettuatiBean(OrdiniEffettuatiBean ordiniEffettuatiBean) {
		this.ordiniEffettuatiBean = ordiniEffettuatiBean;
	}

	public OrdineBean getOrdineBean() {
		return ordineBean;
	}

	public void setOrdineBean(OrdineBean ordineBean) {
		this.ordineBean = ordineBean;
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

}
