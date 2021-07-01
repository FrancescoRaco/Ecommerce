package controllers;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class BaseController {
	
	public void addMessage(Severity severity, String descrizione){
		FacesMessage facesMsg = new FacesMessage(severity, descrizione, "");
	    FacesContext.getCurrentInstance().addMessage("", facesMsg);		
	}

}
