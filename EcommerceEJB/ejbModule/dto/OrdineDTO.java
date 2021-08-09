package dto;

import java.util.Date;

public class OrdineDTO {
	
	private Integer idProdotto;
	private String titoloProdotto;
	private String cfVenditore;
	private String cfAcquirente;
	private Integer offerta;
	private Integer quantita;
	private Integer progressivo;
	private Integer flagAccettazione;
	private String noteAcquirente;
	private Date dataOrdine;
	
	private boolean suggerito;
	
	public Integer getIdProdotto() {
		return idProdotto;
	}
	public void setIdProdotto(Integer idProdotto) {
		this.idProdotto = idProdotto;
	}
	public String getTitoloProdotto() {
		return titoloProdotto;
	}
	public void setTitoloProdotto(String titoloProdotto) {
		this.titoloProdotto = titoloProdotto;
	}
	public String getCfVenditore() {
		return cfVenditore;
	}
	public void setCfVenditore(String cfVenditore) {
		this.cfVenditore = cfVenditore;
	}
	public String getCfAcquirente() {
		return cfAcquirente;
	}
	public void setCfAcquirente(String cfAcquirente) {
		this.cfAcquirente = cfAcquirente;
	}
	public Integer getOfferta() {
		return offerta;
	}
	public void setOfferta(Integer offerta) {
		this.offerta = offerta;
	}
	public Integer getQuantita() {
		return quantita;
	}
	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}
	public Integer getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}
	public Integer getFlagAccettazione() {
		return flagAccettazione;
	}
	public void setFlagAccettazione(Integer flagAccettazione) {
		this.flagAccettazione = flagAccettazione;
	}
	public String getNoteAcquirente() {
		return noteAcquirente;
	}
	public void setNoteAcquirente(String noteAcquirente) {
		this.noteAcquirente = noteAcquirente;
	}
	public Date getDataOrdine() {
		return dataOrdine;
	}
	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
	}
	public boolean isSuggerito() {
		return suggerito;
	}
	public void setSuggerito(boolean suggerito) {
		this.suggerito = suggerito;
	}

}
