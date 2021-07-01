package mapperInterfaces;

import java.util.List;
import dto.CategoriaDTO;
import dto.ProdottoDTO;

public interface CommonMapper {
	
	public String test(int num) throws Exception;
	public List<CategoriaDTO> getCategorie() throws Exception;
	public List<ProdottoDTO> getProdottiAttivi(ProdottoDTO prodottoDTO) throws Exception;

}
