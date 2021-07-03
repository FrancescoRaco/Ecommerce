package ejbInterfaces;

import java.util.List;
import javax.ejb.Local;
import dto.CategoriaDTO;
import dto.ProdottoDTO;
import dto.UtenteDTO;
import exceptions.EcommerceException;

@Local
public interface CommonDataAccess {
	
	public String test(int num) throws Exception;
	public List<CategoriaDTO> getCategorie() throws EcommerceException;
	public List<ProdottoDTO> getProdottiAttivi(ProdottoDTO prodottoDTO) throws EcommerceException;
	public ProdottoDTO getProdotto(ProdottoDTO prodottoDTO) throws EcommerceException;
	public int modificaPassword(UtenteDTO utenteDTO) throws EcommerceException;
}
