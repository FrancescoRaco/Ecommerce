package controllers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
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
import dto.ProdottoDTO;
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
	
	@PostConstruct
	public void init() {
		try {
			if (dettaglioBean.getOrdini() == null && utenteBean.getCodiceFiscale().equals(dettaglioBean.getProdottoDTO().getCfVenditore())) {
				caricaOrdiniRicevuti();
			}
			if (dettaglioBean.getCategorie() == null) {
				dettaglioBean.setCategorie(commonDataAccess.getCategorie());
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void caricaOrdiniRicevuti() throws Exception {
		dettaglioBean.setAssistenzaAttivata(false);
		dettaglioBean.setOrdini(sellerDataAccess.getOrdiniRicevuti(dettaglioBean.getProdottoDTO().getId()));
		if (dettaglioBean.getOrdini() != null && !dettaglioBean.getOrdini().isEmpty()) {
			if (dettaglioBean.getPaginatorOrdini() == null) {
				dettaglioBean.setPaginatorOrdini(new Paginator<OrdineDTO>());
			}
			dettaglioBean.getPaginatorOrdini().setResultset(dettaglioBean.getOrdini());
			dettaglioBean.setTabellaOrdiniAbilitata(true);
		} else {
			dettaglioBean.setPaginatorOrdini(null);
			dettaglioBean.setTabellaOrdiniAbilitata(false);
		}
	}
	
	public void aiutaVenditore(AjaxBehaviorEvent event) {
		try {
			List<OrdineDTO> ordiniAttivi = getOrdiniInLavorazione();
			if (ordiniAttivi != null && !ordiniAttivi.isEmpty()) {
				int numeroOrdini = ordiniAttivi.size();
				double[] values = new double[numeroOrdini];
				double[] weights = new double[numeroOrdini];
				for (int i = 0; i < ordiniAttivi.size(); i++) {
					values[i] = ordiniAttivi.get(i).getOfferta() * ordiniAttivi.get(i).getQuantita();
					weights[i] = ordiniAttivi.get(i).getQuantita();
				}
				double knapsackSize = dettaglioBean.getProdottoDTO().getDisponibilita();
				int populationSize = 2000;
				int maxGenerations = 2000;
				double crossProb = 0.4;
				double mutatProb = 0.01;
				KnapSackGA geneticAi = new KnapSackGA(numeroOrdini, values, weights, knapsackSize, populationSize, maxGenerations, crossProb, mutatProb);
				String bestSolution = geneticAi.execute();
				if (bestSolution != null && !bestSolution.isEmpty()) {
					StringBuilder successMessage = new StringBuilder("Operazione effettuata con successo. Ordini convenienti nel formato {progressivo, offerta X quantità}: ");
					int peso = 0;
					int valore = 0;
					for (int i = 0; i < bestSolution.length(); i++) {
						char flagOrdine = bestSolution.charAt(i);
						if (flagOrdine == '1') {
							OrdineDTO ordineAttuale = ordiniAttivi.get(i);
							ordineAttuale.setSuggerito(true);
							peso += ordineAttuale.getQuantita();
							valore += ordineAttuale.getOfferta() * ordineAttuale.getQuantita();
							successMessage.append("{").append(ordineAttuale.getProgressivo()).append(", ").append(ordineAttuale.getOfferta()).append(" X ").append(ordineAttuale.getQuantita()).append("}, ");
						} else {
							ordiniAttivi.get(i).setSuggerito(false);
						}
					}
					String successMessageString = successMessage.toString().trim();
					if (successMessageString != null && successMessageString.length() > 1) {
						successMessageString = successMessageString.substring(0, successMessageString.length() - 1);
					}
					messagesBean.getSuccesses().add(successMessageString + "; " + "Peso: " + peso + ", Valore: " + valore + ".");
				}
			}
			dettaglioBean.setAssistenzaAttivata(true);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	private List<OrdineDTO> getOrdiniInLavorazione() {
		List<OrdineDTO> ordiniAttivi = new ArrayList<OrdineDTO>();
		if (dettaglioBean.getOrdini() != null && !dettaglioBean.getOrdini().isEmpty()) {
			for (OrdineDTO ordineDTO : dettaglioBean.getOrdini()) {
				if (ordineDTO != null && ordineDTO.getFlagAccettazione() == 0 && ordineDTO.getOfferta() != null && dettaglioBean.getProdottoDTO() != null && ordineDTO.getOfferta() >= dettaglioBean.getProdottoDTO().getPrezzoBase()) {
					ordiniAttivi.add(ordineDTO);
				}
			}
		}
		return ordiniAttivi;
	}
	
	public void effettuaOperazioniIniziali(ComponentSystemEvent event) {
		try {
			dettaglioBean.setIndietro(false);
			if (dettaglioBean.isPulireInvioOrdine()) {
				ripulisciInvioOrdine();
				dettaglioBean.setPulireInvioOrdine(false);
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void inviaOrdine() {
		try {
			if (validaInput()) {
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
	
	public void ripulisciInvioOrdine() {
		try {
			dettaglioBean.setOfferta(null);
			dettaglioBean.setOffertaWrap(null);
			dettaglioBean.setQuantita(null);
			dettaglioBean.setQuantitaWrap(null);
			dettaglioBean.setNoteAcquirente(null);
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
		ripulisci();
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
				Integer id = dettaglioBean.getProdottoDTO().getId();
				sellerDataAccess.annullaProdotto(id);
				chiudiAnnullaProdotto();
				messagesBean.getSuccesses().add("Annullato con successo il prodotto: " + (id != null ? id : "/"));
				
				//Aggiornamento pagine dopo modifica
				dettaglioBean.setProdottoDTO(null);
				if (Constants.PAGINA_RICERCA_PRODOTTI.equals(dettaglioBean.getProvenienza()) && ricercaProdottiBean != null) {
					ricercaProdottiBean.setProdottiAttivi(null);
				} else if (Constants.PAGINA_VENDITE.equals(dettaglioBean.getProvenienza()) && venditeBean != null) {
					venditeBean.setProdottiAttivi(null);
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getMessaggiModale().getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	private boolean validaInputModifica() throws Exception {
		
		if (dettaglioBean.getProdottoInput() == null || dettaglioBean.getProdottoInput().getTitolo() == null || dettaglioBean.getProdottoInput().getTitolo().isEmpty()) {
			messagesBean.addError("Inserire il titolo", "titoloId");
		} else if (dettaglioBean.getProdottoInput() != null && dettaglioBean.getProdottoInput().getTitolo() != null && !dettaglioBean.getProdottoInput().getTitolo().isEmpty() && !CommonUtils.validaStringa(dettaglioBean.getProdottoInput().getTitolo())) {
			messagesBean.addError("Errore durante la validazione del titolo", "titoloId");
		}
		
		if (dettaglioBean.getProdottoInput() == null || dettaglioBean.getProdottoInput().getDescrizione() == null || dettaglioBean.getProdottoInput().getDescrizione().isEmpty()) {
			messagesBean.addError("Inserire la descrizione", "descrizioneId");
		} else if (dettaglioBean.getProdottoInput() != null && dettaglioBean.getProdottoInput().getDescrizione() != null && !dettaglioBean.getProdottoInput().getDescrizione().isEmpty() && !CommonUtils.validaStringa(dettaglioBean.getProdottoInput().getDescrizione())) {
			messagesBean.addError("Errore durante la validazione della descrizione", "descrizioneId");
		}
		
		if (dettaglioBean.getProdottoInput() == null || dettaglioBean.getProdottoInput().getDisponibilitaWrap() == null || dettaglioBean.getProdottoInput().getDisponibilitaWrap().isEmpty()) {
			messagesBean.addError("Inserire la disponibilità", "disponibilitaId");
		} else if (dettaglioBean.getProdottoInput() != null && dettaglioBean.getProdottoInput().getDisponibilitaWrap() != null && !dettaglioBean.getProdottoInput().getDisponibilitaWrap().isEmpty()) {
			Integer converted = CommonUtils.stringToInteger(dettaglioBean.getProdottoInput().getDisponibilitaWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione della disponibilità", "disponibilitaId");
			} else {
				dettaglioBean.getProdottoInput().setDisponibilita(converted);
			}
		}
		
		if (dettaglioBean.getProdottoInput() == null || dettaglioBean.getProdottoInput().getPrezzoBaseWrap() == null || dettaglioBean.getProdottoInput().getPrezzoBaseWrap().isEmpty()) {
			messagesBean.addError("Inserire il prezzo base", "prezzoBaseId");
		} else if (dettaglioBean.getProdottoInput() != null && dettaglioBean.getProdottoInput().getPrezzoBaseWrap() != null && !dettaglioBean.getProdottoInput().getPrezzoBaseWrap().isEmpty()) {
			Integer converted = CommonUtils.stringToInteger(dettaglioBean.getProdottoInput().getPrezzoBaseWrap());
			if (converted == null) {
				messagesBean.addError("Errore durante la validazione del prezzo base", "prezzoBaseId");
			} else {
				dettaglioBean.getProdottoInput().setPrezzoBase(converted);
			}
		}
		
		if (dettaglioBean.getProdottoInput() != null && dettaglioBean.getProdottoInput().getInfoAcquirenti() != null && !dettaglioBean.getProdottoInput().getInfoAcquirenti().isEmpty() && !CommonUtils.validaStringa(dettaglioBean.getProdottoInput().getInfoAcquirenti())) {
			messagesBean.addError("Errore durante la validazione delle informazioni per gli acquirenti", "infoAcquirentiId");
		}
		
		if (messagesBean.getErrors() != null && messagesBean.getErrors().size() > 0) {
			return false;
		}
		
		return true;
	}
	
	public void modifica() {
		try {
			if (validaInputModifica()) {
				Integer id = dettaglioBean.getProdottoDTO().getId();
				sellerDataAccess.modificaProdotto(dettaglioBean.getProdottoInput());
				messagesBean.getSuccesses().add("Modificato con successo il prodotto: " + (id != null ? id : "/"));
				//Aggiornamento pagina web dopo la modifica
				dettaglioBean.setProdottoDTO(commonDataAccess.getProdottoAttivo(id));
				dettaglioBean.setOrdini(null);
				ripulisci();
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			messagesBean.getErrors().add("Operazione fallita: contattare l'amministratore di sistema");
		}
	}
	
	public void ripulisci() {
		try {
			ProdottoDTO prodottoInput = new ProdottoDTO();
			//Travaso campi prodotto modificabili + id
			prodottoInput.setId(dettaglioBean.getProdottoDTO().getId());
			prodottoInput.setTitolo(dettaglioBean.getProdottoDTO().getTitolo());
			prodottoInput.setCodiceCategoria(dettaglioBean.getProdottoDTO().getCodiceCategoria());
			prodottoInput.setVenditoreDenom(dettaglioBean.getProdottoDTO().getVenditoreDenom());
			prodottoInput.setDisponibilitaWrap(dettaglioBean.getProdottoDTO().getDisponibilita() != null ? ""+dettaglioBean.getProdottoDTO().getDisponibilita() : null);
			prodottoInput.setPrezzoBaseWrap(dettaglioBean.getProdottoDTO().getPrezzoBase() != null ? ""+dettaglioBean.getProdottoDTO().getPrezzoBase() : null);
			prodottoInput.setDescrizione(dettaglioBean.getProdottoDTO().getDescrizione());
			prodottoInput.setInfoAcquirenti(dettaglioBean.getProdottoDTO().getInfoAcquirenti());
			dettaglioBean.setProdottoInput(prodottoInput);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
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
