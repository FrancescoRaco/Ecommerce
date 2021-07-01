package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ComponentSystemEvent;
import beans.MessagesBean;
import beans.OrdineBean;
import ejbInterfaces.SellerDataAccess;

@ManagedBean
@RequestScoped
public class OrdineController extends BaseController {
	
	@ManagedProperty(value="#{ordineBean}")
	private OrdineBean ordineBean;
	
	@ManagedProperty(value="#{messagesBean}")
	private MessagesBean messagesBean;
	
	@EJB
	private static SellerDataAccess sellerDataAccess;
	
	private boolean fromInit;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void effettuaOperazioniIniziali(ComponentSystemEvent event) {
//		ordineBean.setIndietro(false);
	}
	
	public void accettaOrdine() {
		try {
			if (ordineBean.getOrdineDTO().getQuantita() <= sellerDataAccess.getDisponibilitaProdotto(ordineBean.getOrdineDTO().getIdProdotto())) {
				sellerDataAccess.accettaOrdine(ordineBean.getOrdineDTO());
				messagesBean.getSuccesses().add("Ordine accettato con successo");
			} else {
				messagesBean.getErrors().add("Non richiedere pi� unit� di quelle disponibili");
			}
			
		} catch(Exception e) {
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	public void rifiutaOrdine() {
		try {
			sellerDataAccess.rifiutaOrdine(ordineBean.getOrdineDTO());
			messagesBean.getSuccesses().add("Ordine rifiutato con successo");
		} catch(Exception e) {
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	public String backToDettaglioProdotto() {
		return "dettaglioProdotto";
	}
	
	public String backToOrdiniEffettuati() {
		return "ordiniEffettuati";
	}

	public OrdineBean getOrdineBean() {
		return ordineBean;
	}

	public void setOrdineBean(OrdineBean ordineBean) {
		this.ordineBean = ordineBean;
	}

	public MessagesBean getMessagesBean() {
		return messagesBean;
	}

	public void setMessagesBean(MessagesBean messagesBean) {
		this.messagesBean = messagesBean;
	}

}