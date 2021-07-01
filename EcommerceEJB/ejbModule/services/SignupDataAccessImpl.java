package services;

import javax.ejb.Stateless;
import dto.UtenteDTO;
import ejbInterfaces.SignupDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.SignupMapper;

@Stateless
public class SignupDataAccessImpl extends BaseService implements SignupDataAccess {
	
	@Override
	public int signup(UtenteDTO utenteDTO) throws EcommerceException {
		
		int result = 0;
		
		try {
			SignupMapper signupMapper = (SignupMapper) getSession().getMapper(SignupMapper.class);
			result = signupMapper.insertUtente(utenteDTO);
		} catch(Exception e) {
			System.out.print("Prova");
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}

}
