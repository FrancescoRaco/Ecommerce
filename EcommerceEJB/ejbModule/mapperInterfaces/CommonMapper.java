package mapperInterfaces;

import java.util.List;
import dto.CategoriaDTO;
import dto.OrdineDTO;
import dto.ProdottoDTO;
import dto.UtenteDTO;

public interface CommonMapper {
	
	public List<CategoriaDTO> getCategorie() throws Exception;
	public List<ProdottoDTO> getProdottiAttivi(ProdottoDTO prodottoDTO) throws Exception;
	public ProdottoDTO getProdottoAttivo(Integer id) throws Exception;
	public int modificaPassword(UtenteDTO utenteDTO) throws Exception;
	public int modificaProfilo(UtenteDTO utenteDTO) throws Exception;
	public List<OrdineDTO> getOrdiniBy(OrdineDTO ordineDTO) throws Exception;
}
