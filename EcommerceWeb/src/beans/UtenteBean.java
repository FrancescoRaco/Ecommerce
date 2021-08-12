package beans;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import dto.UtenteDTO;

@ManagedBean
@SessionScoped
public class UtenteBean {
	
	private String codiceFiscale;
	private String password;
	private String nome;
	private String cognome;
	private String domicilio;
	private String email;
	private String telefono;
	private Date dataNascita;
	
	//Campi wrapper
	private String dataNascitaWrap;
	
	private boolean signupAbilitato;
	private boolean loggedIn;
	
	private boolean modificaPassword;
	private String vecchiaPassword;
	private String nuovaPassword;
	
	//Wrapper campi input modifica
	private UtenteDTO utenteInput;
	
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public boolean isSignupAbilitato() {
		return signupAbilitato;
	}
	public void setSignupAbilitato(boolean signupAbilitato) {
		this.signupAbilitato = signupAbilitato;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	public String getDataNascitaWrap() {
		return dataNascitaWrap;
	}
	public void setDataNascitaWrap(String dataNascitaWrap) {
		this.dataNascitaWrap = dataNascitaWrap;
	}
	public boolean isModificaPassword() {
		return modificaPassword;
	}
	public void setModificaPassword(boolean modificaPassword) {
		this.modificaPassword = modificaPassword;
	}
	public String getVecchiaPassword() {
		return vecchiaPassword;
	}
	public void setVecchiaPassword(String vecchiaPassword) {
		this.vecchiaPassword = vecchiaPassword;
	}
	public String getNuovaPassword() {
		return nuovaPassword;
	}
	public void setNuovaPassword(String nuovaPassword) {
		this.nuovaPassword = nuovaPassword;
	}
	public UtenteDTO getUtenteInput() {
		return utenteInput;
	}
	public void setUtenteInput(UtenteDTO utenteInput) {
		this.utenteInput = utenteInput;
	}

}
