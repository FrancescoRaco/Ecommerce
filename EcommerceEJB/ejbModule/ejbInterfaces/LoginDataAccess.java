package ejbInterfaces;

import javax.ejb.Local;
import dto.UtenteDTO;
import exceptions.EcommerceException;

@Local
public interface LoginDataAccess {
	
	public UtenteDTO login(UtenteDTO utenteDTO) throws EcommerceException;

}
