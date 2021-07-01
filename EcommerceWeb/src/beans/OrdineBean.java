package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import dto.OrdineDTO;

@ManagedBean
@SessionScoped
public class OrdineBean {
	
	private OrdineDTO ordineDTO;
	
	private boolean indietro;
	private String provenienza;
	

	public OrdineDTO getOrdineDTO() {
		return ordineDTO;
	}

	public void setOrdineDTO(OrdineDTO ordineDTO) {
		this.ordineDTO = ordineDTO;
	}

	public boolean isIndietro() {
		return indietro;
	}

	public void setIndietro(boolean indietro) {
		this.indietro = indietro;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

}
