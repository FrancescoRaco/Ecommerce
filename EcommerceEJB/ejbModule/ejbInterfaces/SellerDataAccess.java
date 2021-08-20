package ejbInterfaces;

import java.util.List;

import javax.ejb.Local;
import dto.OrdineDTO;
import dto.ProdottoDTO;
import exceptions.EcommerceException;

@Local
public interface SellerDataAccess {
	
	public int insertProdotto(ProdottoDTO prodottoDTO) throws EcommerceException;
	public int getDisponibilitaProdotto(Integer idProdotto) throws EcommerceException;
	public List<OrdineDTO> getOrdiniRicevuti(Integer idProdotto) throws EcommerceException;
	public int accettaOrdine(OrdineDTO ordineDTO) throws EcommerceException;
	public int rifiutaOrdine(OrdineDTO ordineDTO) throws EcommerceException;
	public int modificaProdotto(ProdottoDTO prodottoDTO) throws EcommerceException;
	public int annullaProdotto(Integer idProdotto) throws EcommerceException;
}
