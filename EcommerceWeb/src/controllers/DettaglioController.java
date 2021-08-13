package controllers;

import java.util.ArrayList;
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
import beans.DettaglioBean;
import beans.MessagesBean;
import beans.OrdineBean;
import beans.RicercaProdottiBean;
import beans.UtenteBean;
import beans.VenditeBean;
import dto.OrdineDTO;
import ejbInterfaces.BuyerDataAccess;
import ejbInterfaces.CommonDataAccess;
import ejbInterfaces.SellerDataAccess;
import geneticAi.KnapSackGA;
import utils.CommonUtils;
import utils.Constants;
import utils.Paginator;

@ManagedBean
@RequestScoped
public class DettaglioController {
	
	private final static Logger logger = LogManager.getLogger(DettaglioController.class);
	
	@ManagedProperty(value="#{dettaglioBean}")
	private DettaglioBean dettaglioBean;
	
	@ManagedProperty(value="#{ricercaProdottiBean}")
	private RicercaProdottiBean ricercaProdottiBean;
	
	@ManagedProperty(value="#{venditeBean}")
	private VenditeBean venditeBean;
	
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
	
	@EJB
	private static SellerDataAccess sellerDataAccess;
	
	private boolean fromInit;
	
	@PostConstruct
	public void init() {
		try {
			if (dettaglioBean.getOrdini() == null && utenteBean.getCodiceFiscale().equals(dettaglioBean.getProdottoDTO().getCfVenditore())) {
				caricaOrdiniRicevuti();
				aiutaVenditore();
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void caricaOrdiniRicevuti() throws Exception {
		dettaglioBean.setOrdini(buyerDataAccess.getOrdiniRicevuti(dettaglioBean.getProdottoDTO().getId()));
		if (dettaglioBean.getOrdini() != null && !dettaglioBean.getOrdini().isEmpty()) {
			if (dettaglioBean.getPaginatorOrdini() == null) {
				dettaglioBean.setPaginatorOrdini(new Paginator<OrdineDTO>());
			}
			dettaglioBean.getPaginatorOrdini().setResultset(dettaglioBean.getOrdini());
			dettaglioBean.setTabellaOrdiniAbilitata(true);
			fromInit = true;
		} else {
			dettaglioBean.setPaginatorOrdini(null);
			dettaglioBean.setTabellaOrdiniAbilitata(false);
		}
	}
	
	private void aiutaVenditore() throws Exception {
		List<OrdineDTO> ordiniAttivi = getOrdiniInLavorazione();
		if (ordiniAttivi != null && !ordiniAttivi.isEmpty()) {
			int numeroOrdini = ordiniAttivi.size();
			double[] values = new double[numeroOrdini];
			double[] weights = new double[numeroOrdini];
			for (int i = 0; i < ordiniAttivi.size(); i++) {
				values[i] = ordiniAttivi.get(i).getOfferta();
				weights[i] = ordiniAttivi.get(i).getQuantita();
			}
			double knapsackSize = dettaglioBean.getProdottoDTO().getDisponibilita();
			int populationSize = 50;
			int maxGenerations = 100;
			double crossProb = 0.6;
			double mutatProb = 0.015;
			KnapSackGA geneticAi = new KnapSackGA(numeroOrdini, values, weights, knapsackSize, populationSize, maxGenerations, crossProb, mutatProb);
			String bestSolution = geneticAi.execute();
			if (bestSolution != null && !bestSolution.isEmpty()) {
				for (int i = 0; i < bestSolution.length(); i++) {
					char flagOrdine = bestSolution.charAt(i);
					if (flagOrdine == '1') {
						ordiniAttivi.get(i).setSuggerito(true);
					} else {
						ordiniAttivi.get(i).setSuggerito(false);
					}
				}
			}
		}
	}
	
	private List<OrdineDTO> getOrdiniInLavorazione() {
		List<OrdineDTO> ordiniAttivi = new ArrayList<OrdineDTO>();
		if (dettaglioBean.getOrdini() != null && !dettaglioBean.getOrdini().isEmpty()) {
			for (OrdineDTO ordineDTO : dettaglioBean.getOrdini()) {
				if (ordineDTO != null && ordineDTO.getFlagAccettazione() == 0) {
					ordiniAttivi.add(ordineDTO);
				}
			}
		}
		return ordiniAttivi;
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
			logger.error(e.getMessage(), e);
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
				caricaOrdiniRicevuti();
				aiutaVenditore();
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
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
			logger.error(e.getMessage(), e);
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
		if (ordineBean.getOrdineDTO() != null) {
			ordineBean.getOrdineDTO().setTitoloProdotto(dettaglioBean.getProdottoDTO() != null ? dettaglioBean.getProdottoDTO().getTitolo() : null);
		}
		
		return "ordine";
	}
	
	public String goToModificaProdotto() {
		return "modificaProdotto";
	}
	
	public void apriAnnullaProdotto() {
		dettaglioBean.setAnnullaProdotto(true);
	}
	
	public void chiudiAnnullaProdotto() {
		dettaglioBean.setAnnullaProdotto(false);
	}
	
	public String backToDettaglio() {
		return "dettaglioProdotto";
	}
	
	public void confermaAnnullaProdotto() {
		try {
			if (dettaglioBean.getProdottoDTO() != null && dettaglioBean.getProdottoDTO().getId() != null) {
				String titolo = dettaglioBean.getProdottoDTO().getTitolo();
				sellerDataAccess.annullaProdotto(dettaglioBean.getProdottoDTO().getId());
				chiudiAnnullaProdotto();
				messagesBean.getSuccesses().add("Annullato con successo il prodotto: " + (titolo != null && !titolo.isEmpty() ? titolo : ""));
				
				//Aggiornamento pagine dopo modifica
				dettaglioBean.setProdottoDTO(null);
				if (Constants.PAGINA_RICERCA_PRODOTTI.equals(dettaglioBean.getProvenienza())) {
					ricercaProdottiBean.setProdottiAttivi(null);
				} else if (Constants.PAGINA_VENDITE.equals(dettaglioBean.getProvenienza())) {
					venditeBean.setProdottiAttivi(null);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getMessaggiModale().getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
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

	public RicercaProdottiBean getRicercaProdottiBean() {
		return ricercaProdottiBean;
	}

	public void setRicercaProdottiBean(RicercaProdottiBean ricercaProdottiBean) {
		this.ricercaProdottiBean = ricercaProdottiBean;
	}

	public VenditeBean getVenditeBean() {
		return venditeBean;
	}

	public void setVenditeBean(VenditeBean venditeBean) {
		this.venditeBean = venditeBean;
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
