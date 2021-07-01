package ejbInterfaces;

import javax.ejb.Local;
import dto.UtenteDTO;
import exceptions.EcommerceException;

@Local
public interface SignupDataAccess {
	
	public int signup(UtenteDTO utenteDTO) throws EcommerceException;

}
