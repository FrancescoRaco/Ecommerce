package services;

import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dto.UtenteDTO;
import ejbInterfaces.SignupDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.SignupMapper;

@Stateless
public class SignupDataAccessImpl extends BaseService implements SignupDataAccess {
	
	private final static Logger logger = LogManager.getLogger(SignupDataAccessImpl.class);
	
	@Override
	public int signup(UtenteDTO utenteDTO) throws EcommerceException {
		
		int result = 0;
		
		try {
			SignupMapper signupMapper = (SignupMapper) getSession().getMapper(SignupMapper.class);
			result = signupMapper.insertUtente(utenteDTO);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}

}
