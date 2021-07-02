package services;

import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dto.UtenteDTO;
import ejbInterfaces.LoginDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.LoginMapper;

@Stateless
public class LoginDataAccessImpl extends BaseService implements LoginDataAccess {
	
	private final static Logger logger = LogManager.getLogger(LoginDataAccessImpl.class);
	
	@Override
	public UtenteDTO login(UtenteDTO utenteDTO) throws EcommerceException {
		
		UtenteDTO utenteLoggato = null;
		
		try {
			LoginMapper loginMapper = (LoginMapper) getSession().getMapper(LoginMapper.class);
			utenteLoggato = loginMapper.getUtente(utenteDTO);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return utenteLoggato;
	}

}
