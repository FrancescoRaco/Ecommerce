package mapperInterfaces;

import dto.OrdineDTO;
import dto.ProdottoDTO;

public interface SellerMapper {
	
	public int getMaxIdProdotto() throws Exception;
	public int getDisponibilita(Integer idProdotto) throws Exception;
	public int insertProdotto(ProdottoDTO prodottoDTO) throws Exception;
	public int accettaOrdine(OrdineDTO ordineDTO) throws Exception;
	public int rifiutaOrdine(OrdineDTO ordineDTO) throws Exception;
	public int aggiornaDisponibilitaProdotto(ProdottoDTO prodottoDTO) throws Exception;
	public int modificaProdotto(ProdottoDTO prodottoDTO) throws Exception;
	public int annullaProdotto(Integer id) throws Exception;
}
