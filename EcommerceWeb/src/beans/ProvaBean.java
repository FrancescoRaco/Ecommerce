package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ProvaBean {
	
	private int matricola;
	private String studente;
	
	
	public int getMatricola() {
		return matricola;
	}
	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}
	public String getStudente() {
		return studente;
	}
	public void setStudente(String studente) {
		this.studente = studente;
	}

}
