package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dto.OrdineDTO;
import dto.ProdottoDTO;
import utils.Paginator;

@ManagedBean
@SessionScoped
public class DettaglioBean {
	
	private ProdottoDTO prodottoDTO;
	
	private Integer offerta;
	private Integer quantita;
	private String noteAcquirente;
	
	private List<OrdineDTO> ordini;
	private boolean tabellaOrdiniAbilitata;
	private Paginator<OrdineDTO> paginatorOrdini;
	
	//Campi Wrapper
	private String offertaWrap;
	private String quantitaWrap;
	
	private String provenienza;
	private boolean indietro;
	

	public ProdottoDTO getProdottoDTO() {
		return prodottoDTO;
	}

	public void setProdottoDTO(ProdottoDTO prodottoDTO) {
		this.prodottoDTO = prodottoDTO;
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

	public String getNoteAcquirente() {
		return noteAcquirente;
	}

	public void setNoteAcquirente(String noteAcquirente) {
		this.noteAcquirente = noteAcquirente;
	}

	public String getOffertaWrap() {
		return offertaWrap;
	}

	public void setOffertaWrap(String offertaWrap) {
		this.offertaWrap = offertaWrap;
	}

	public String getQuantitaWrap() {
		return quantitaWrap;
	}

	public void setQuantitaWrap(String quantitaWrap) {
		this.quantitaWrap = quantitaWrap;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public boolean isIndietro() {
		return indietro;
	}

	public void setIndietro(boolean indietro) {
		this.indietro = indietro;
	}

	public List<OrdineDTO> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<OrdineDTO> ordini) {
		this.ordini = ordini;
	}

	public boolean isTabellaOrdiniAbilitata() {
		return tabellaOrdiniAbilitata;
	}

	public void setTabellaOrdiniAbilitata(boolean tabellaOrdiniAbilitata) {
		this.tabellaOrdiniAbilitata = tabellaOrdiniAbilitata;
	}

	public Paginator<OrdineDTO> getPaginatorOrdini() {
		return paginatorOrdini;
	}

	public void setPaginatorOrdini(Paginator<OrdineDTO> paginatorOrdini) {
		this.paginatorOrdini = paginatorOrdini;
	}

}
