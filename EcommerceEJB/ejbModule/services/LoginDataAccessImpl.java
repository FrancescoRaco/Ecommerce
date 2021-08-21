package services;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dto.UtenteDTO;
import ejbInterfaces.LoginDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.LoginMapper;

@Stateless
public class LoginDataAccessImpl extends BaseService implements LoginDataAccess {
	
	private final static Logger logger = LogManager.getLogger(LoginDataAccessImpl.class);
	
	@Resource
	private SessionContext context;
	
	@Override
	public UtenteDTO login(UtenteDTO utenteDTO) throws EcommerceException {
		
		UtenteDTO utenteLoggato = null;
		
		try (SqlSession session = getSession()) {
			LoginMapper loginMapper = (LoginMapper) session.getMapper(LoginMapper.class);
			utenteLoggato = loginMapper.getUtente(utenteDTO);
			if (utenteLoggato != null) {
				logger.info("Loggato l'utente " + utenteLoggato.getNome() + " " + utenteLoggato.getCognome());
			}
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return utenteLoggato;
	}

}
