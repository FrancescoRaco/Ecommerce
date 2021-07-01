package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dto.CategoriaDTO;
import dto.ProdottoDTO;
import utils.Paginator;

@ManagedBean
@SessionScoped
public class RicercaProdottiBean {
	
	private List<ProdottoDTO> prodottiAttivi;
	private boolean tabellaProdottiAbilitata;
	private Paginator<ProdottoDTO> paginatorProdotti;
	
	private Integer id;
	private String titolo;
	private Integer codiceCategoria;
	private List<CategoriaDTO> categorie;
	private String cfVenditore;
	private Integer prezzoBase;
	
	//Campi Wrapper
	private String idWrap;
	private String prezzoBaseWrap;

	public List<ProdottoDTO> getProdottiAttivi() {
		return prodottiAttivi;
	}

	public void setProdottiAttivi(List<ProdottoDTO> prodottiAttivi) {
		this.prodottiAttivi = prodottiAttivi;
	}

	public boolean isTabellaProdottiAbilitata() {
		return tabellaProdottiAbilitata;
	}

	public void setTabellaProdottiAbilitata(boolean tabellaProdottiAbilitata) {
		this.tabellaProdottiAbilitata = tabellaProdottiAbilitata;
	}

	public Paginator<ProdottoDTO> getPaginatorProdotti() {
		return paginatorProdotti;
	}

	public void setPaginatorProdotti(Paginator<ProdottoDTO> paginatorProdotti) {
		this.paginatorProdotti = paginatorProdotti;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

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

	public String getCfVenditore() {
		return cfVenditore;
	}

	public void setCfVenditore(String cfVenditore) {
		this.cfVenditore = cfVenditore;
	}

	public Integer getPrezzoBase() {
		return prezzoBase;
	}

	public void setPrezzoBase(Integer prezzoBase) {
		this.prezzoBase = prezzoBase;
	}

	public String getIdWrap() {
		return idWrap;
	}

	public void setIdWrap(String idWrap) {
		this.idWrap = idWrap;
	}

	public String getPrezzoBaseWrap() {
		return prezzoBaseWrap;
	}

	public void setPrezzoBaseWrap(String prezzoBaseWrap) {
		this.prezzoBaseWrap = prezzoBaseWrap;
	}

}
