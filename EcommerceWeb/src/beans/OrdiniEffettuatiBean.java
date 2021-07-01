package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import dto.OrdineDTO;
import utils.Paginator;

@ManagedBean
@SessionScoped
public class OrdiniEffettuatiBean {
	
	private List<OrdineDTO> ordini;
	private boolean tabellaOrdiniAbilitata;
	private Paginator<OrdineDTO> paginatorOrdini;
	
	
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
