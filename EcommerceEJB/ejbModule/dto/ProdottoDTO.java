package dto;

import java.util.Date;

public class ProdottoDTO {
	
	private Integer id;
	private Integer codiceCategoria;
	private String descCategoria;
	private String cfVenditore;
	private String titolo;
	private String descrizione;
	private Integer flagAttivazione;
	private Integer disponibilita;
	private Integer prezzoBase;
	private Date dataInserimento;
	private Date dataAggiornamento;
	private String infoAcquirenti;
	
	//Parameter type
	private Date dataDa;
	private Date dataA;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCodiceCategoria() {
		return codiceCategoria;
	}
	public void setCodiceCategoria(Integer codiceCategoria) {
		this.codiceCategoria = codiceCategoria;
	}
	public String getDescCategoria() {
		return descCategoria;
	}
	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}
	public String getCfVenditore() {
		return cfVenditore;
	}
	public void setCfVenditore(String cfVenditore) {
		this.cfVenditore = cfVenditore;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getFlagAttivazione() {
		return flagAttivazione;
	}
	public void setFlagAttivazione(Integer flagAttivazione) {
		this.flagAttivazione = flagAttivazione;
	}
	public Integer getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(Integer disponibilita) {
		this.disponibilita = disponibilita;
	}
	public Integer getPrezzoBase() {
		return prezzoBase;
	}
	public void setPrezzoBase(Integer prezzoBase) {
		this.prezzoBase = prezzoBase;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public String getInfoAcquirenti() {
		return infoAcquirenti;
	}
	public void setInfoAcquirenti(String infoAcquirenti) {
		this.infoAcquirenti = infoAcquirenti;
	}
	public Date getDataDa() {
		return dataDa;
	}
	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}
	public Date getDataA() {
		return dataA;
	}
	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

}
