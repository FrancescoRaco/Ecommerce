package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import beans.DettaglioBean;
import beans.MessagesBean;
import beans.OrdineBean;
import beans.UtenteBean;
import dto.OrdineDTO;
import ejbInterfaces.BuyerDataAccess;
import ejbInterfaces.CommonDataAccess;
import utils.CommonUtils;
import utils.Paginator;

@ManagedBean
@RequestScoped
public class DettaglioController {
	
	@ManagedProperty(value="#{dettaglioBean}")
	private DettaglioBean dettaglioBean;
	
	@ManagedProperty(value="#{utenteBean}")
	private UtenteBean utenteBean;
	
	@ManagedProperty(value="#{ordineBean}")
	private OrdineBean ordineBean;
	
	@ManagedProperty(value="#{messagesBean}")
	private MessagesBean messagesBean;
	
	@EJB
	private static BuyerDataAccess buyerDataAccess;
	
	@EJB
	private static CommonDataAccess commonDataAccess;
	
	private boolean fromInit;
	
	@PostConstruct
	public void init() {
		try {
			if (dettaglioBean.getOrdini() == null && utenteBean.getCodiceFiscale().equals(dettaglioBean.getProdottoDTO().getCfVenditore())) {
				caricaOrdiniRicevuti();
			}
		} catch(Exception e) {
			
		}
	}
	
	private void caricaOrdiniRicevuti() throws Exception {
		dettaglioBean.setOrdini(buyerDataAccess.getOrdiniRicevuti(dettaglioBean.getProdottoDTO().getId()));
		if (dettaglioBean.getOrdini() != null && !dettaglioBean.getOrdini().isEmpty()) {
			dettaglioBean.setPaginatorOrdini(new Paginator<OrdineDTO>());
			dettaglioBean.getPaginatorOrdini().setResultset(dettaglioBean.getOrdini());
			dettaglioBean.setTabellaOrdiniAbilitata(true);
			fromInit = true;
		} else {
			dettaglioBean.setPaginatorOrdini(null);
			dettaglioBean.setTabellaOrdiniAbilitata(false);
		}
	}
	
	public void inviaOrdine() {
		try {
			if (validaInput()) {
				//TODO insert
				OrdineDTO ordineDTO = new OrdineDTO();
				ordineDTO.setIdProdotto(dettaglioBean.getProdottoDTO().getId());
				ordineDTO.setOfferta(dettaglioBean.getOfferta());
				ordineDTO.setQuantita(dettaglioBean.getQuantita());
				ordineDTO.setCfAcquirente(utenteBean.getCodiceFiscale());
				ordineDTO.setNoteAcquirente(dettaglioBean.getNoteAcquirente());
				int progressivo = buyerDataAccess.insertOrdine(ordineDTO);
				messagesBean.getSuccesses().add("Ordine " + progressivo + " inserito con successo");
			}
		} catch(Exception e) {
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	public void effettuaOperazioniIniziali(ComponentSystemEvent event) {
		try {
			dettaglioBean.setIndietro(false);
			if (!FacesContext.getCurrentInstance().isPostback() && !fromInit) {
				ripulisciOrdine();
			} else if (FacesContext.getCurrentInstance().isPostback() && dettaglioBean.getProdottoDTO() != null) {
				dettaglioBean.setProdottoDTO(commonDataAccess.getProdotto(dettaglioBean.getProdottoDTO()));
			}
		} catch(Exception e) {
			
		}
	}
	
	public void ripulisciOrdine() {
		try {
			dettaglioBean.setOfferta(null);
			dettaglioBean.setNoteAcquirente(null);
			dettaglioBean.setOffertaWrap(null);
			dettaglioBean.setOrdini(null);
			dettaglioBean.setTabellaOrdiniAbilitata(false);
			dettaglioBean.setPaginatorOrdini(null);
		} catch(Exception e) {
			
		}
	}
	
	private boolean validaInput() throws Exception {
		
		if (dettaglioBean == null || dettaglioBean.getOffertaWrap() == null || dettaglioBean.getOffertaWrap().isEmpty()) {
			messagesBean.addError("Inserire l'offerta", "offertaId");
		} else if (dettaglioBean != null && dettaglioBean.getOffertaWrap() != null && !dettaglioBean.getOffertaWrap().isEmpty()) {
			Integer converted = CommonUtils.stringToInteger(dettaglioBean.getOffertaWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione dell'offerta", "offertaId");
			} else {
				dettaglioBean.setOfferta(converted);
				if (dettaglioBean.getOfferta() < dettaglioBean.getProdottoDTO().getPrezzoBase()) {
					messagesBean.addError("L'offerta è troppo bassa! Offrire almeno " + dettaglioBean.getProdottoDTO().getPrezzoBase() + " €", "offertaId");
				}
			}
		}
		
		if (dettaglioBean == null || dettaglioBean.getQuantitaWrap() == null || dettaglioBean.getQuantitaWrap().isEmpty()) {
			messagesBean.addError("Inserire la quantità", "quantitaId");
		} else if (dettaglioBean != null && dettaglioBean.getQuantitaWrap() != null && !dettaglioBean.getQuantitaWrap().isEmpty()) {
			Integer converted = CommonUtils.stringToInteger(dettaglioBean.getQuantitaWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione della quantità", "quantitaId");
			} else {
				dettaglioBean.setQuantita(converted);
				if (dettaglioBean.getQuantita() > dettaglioBean.getProdottoDTO().getDisponibilita()) {
					messagesBean.addError("Non richiedere più unità di quelle disponibili", "quantitaId");
				} else if (dettaglioBean.getQuantita() <= 0) {
					messagesBean.addError("Inserire almeno 1 unità al campo quantità", "quantitaId");
				}
			}
		}
		
		if (dettaglioBean != null && dettaglioBean.getNoteAcquirente() != null && !dettaglioBean.getNoteAcquirente().isEmpty() && !CommonUtils.validaStringa(dettaglioBean.getNoteAcquirente())) {
			messagesBean.addError("Errore durante la validazione delle note dell'acquirente", "noteAcquirenteId");
		}
		
		if (messagesBean.getErrors() != null && messagesBean.getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}
	
	public String visualizzaOrdine() {
		
		ordineBean.setProvenienza("dettaglioProdotto.xhtml");
		
		return "ordine";
	}
	
	public String backToRicercaProdotti() {
		dettaglioBean.setIndietro(true);
		return "ricercaProdotti";
	}
	
	public String backToVendite() {
		dettaglioBean.setIndietro(true);
		return "vendite";
	}

	public DettaglioBean getDettaglioBean() {
		return dettaglioBean;
	}

	public void setDettaglioBean(DettaglioBean dettaglioBean) {
		this.dettaglioBean = dettaglioBean;
	}

	public UtenteBean getUtenteBean() {
		return utenteBean;
	}

	public void setUtenteBean(UtenteBean utenteBean) {
		this.utenteBean = utenteBean;
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
