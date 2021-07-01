package ejbInterfaces;

import java.util.List;
import javax.ejb.Local;
import dto.OrdineDTO;
import exceptions.EcommerceException;

@Local
public interface BuyerDataAccess {
	
	public int insertOrdine(OrdineDTO ordineDTO) throws EcommerceException;
	public List<OrdineDTO> getOrdiniEffettuati(String cfAcquirente) throws EcommerceException;
	public List<OrdineDTO> getOrdiniRicevuti(Integer idProdotto) throws EcommerceException;
	public OrdineDTO getOrdine(OrdineDTO ordineDTO) throws EcommerceException;
}
