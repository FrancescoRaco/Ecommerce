package mapperInterfaces;

import dto.UtenteDTO;

public interface LoginMapper {
	
	public UtenteDTO getUtente(UtenteDTO utenteDTO) throws Exception;

}
