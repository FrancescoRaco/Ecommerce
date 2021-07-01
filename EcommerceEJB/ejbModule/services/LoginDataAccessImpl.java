package services;

import javax.ejb.Stateless;
import dto.UtenteDTO;
import ejbInterfaces.LoginDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.LoginMapper;

@Stateless
public class LoginDataAccessImpl extends BaseService implements LoginDataAccess {
	
	@Override
	public UtenteDTO login(UtenteDTO utenteDTO) throws EcommerceException {
		
		UtenteDTO utenteLoggato = null;
		
		try {
			LoginMapper loginMapper = (LoginMapper) getSession().getMapper(LoginMapper.class);
			utenteLoggato = loginMapper.getUtente(utenteDTO);
		} catch(Exception e) {
			System.out.print("Prova");
			throw new EcommerceException(e.getMessage(), e);
		}
		return utenteLoggato;
	}

}
