package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dto.ProdottoDTO;
import utils.Paginator;

@ManagedBean
@SessionScoped
public class VenditeBean {
	
	private List<ProdottoDTO> prodottiAttivi;
	private boolean tabellaProdottiAbilitata;
	private Paginator<ProdottoDTO> paginatorProdotti;
	
	
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

}
