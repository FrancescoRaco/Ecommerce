package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dto.CategoriaDTO;

@ManagedBean
@SessionScoped
public class VendiProdottoBean {
	
	private Integer codiceCategoria;
	private List<CategoriaDTO> categorie;
	private String titolo;
	private String descrizione;
	private int disponibilita;
	private int prezzoBase;
	private String infoAcquirenti;
	
	//Campi wrapper
	private String disponibilitaWrap;
	private String prezzoBaseWrap;
	
	public Integer getCodiceCategoria() {
		return codiceCategoria;
	}
	public void setCodiceCategoria(Integer codiceCategoria) {
		this.codiceCategoria = codiceCategoria;
	}
	public List<CategoriaDTO> getCategorie() {
		return categorie;
	}
	public void setCategorie(List<CategoriaDTO> categorie) {
		this.categorie = categorie;
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
	public int getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}
	public int getPrezzoBase() {
		return prezzoBase;
	}
	public void setPrezzoBase(int prezzoBase) {
		this.prezzoBase = prezzoBase;
	}
	public String getDisponibilitaWrap() {
		return disponibilitaWrap;
	}
	public void setDisponibilitaWrap(String disponibilitaWrap) {
		this.disponibilitaWrap = disponibilitaWrap;
	}
	public String getPrezzoBaseWrap() {
		return prezzoBaseWrap;
	}
	public void setPrezzoBaseWrap(String prezzoBaseWrap) {
		this.prezzoBaseWrap = prezzoBaseWrap;
	}
	public String getInfoAcquirenti() {
		return infoAcquirenti;
	}
	public void setInfoAcquirenti(String infoAcquirenti) {
		this.infoAcquirenti = infoAcquirenti;
	}

}
