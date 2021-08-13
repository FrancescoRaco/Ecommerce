package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class MessagesBean {
	
	private List<String> errors;
	private List<String> warnings;
	private List<String> successes;
	
	private Set<String> invalidFields;
	
	//Errori nella finestra modale
	private MessagesBean messaggiModale;
	
	@PostConstruct
	public void init() {
		initializeLists(this);
		messaggiModale = new MessagesBean();
		initializeLists(messaggiModale);
	}
	
	private void initializeLists(MessagesBean messagesBean) {
		messagesBean.errors = new ArrayList<String>();
		messagesBean.invalidFields = new TreeSet<String>();
		messagesBean.successes = new ArrayList<String>();
	}
	
	public void addError(String error, String field) {
		errors.add(error);
		invalidFields.add(field);
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public List<String> getSuccesses() {
		return successes;
	}

	public void setSuccesses(List<String> successes) {
		this.successes = successes;
	}

	public Set<String> getInvalidFields() {
		return invalidFields;
	}

	public void setInvalidFields(Set<String> invalidFields) {
		this.invalidFields = invalidFields;
	}

	public MessagesBean getMessaggiModale() {
		return messaggiModale;
	}

	public void setMessaggiModale(MessagesBean messaggiModale) {
		this.messaggiModale = messaggiModale;
	}

}
