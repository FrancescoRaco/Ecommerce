package mapperInterfaces;

import java.util.List;
import dto.OrdineDTO;

public interface BuyerMapper {
	
	public int getMaxProgrOrdine() throws Exception;
	public int insertOrdine(OrdineDTO ordineDTO) throws Exception;
	public List<OrdineDTO> getOrdiniBy(OrdineDTO ordineDTO) throws Exception;
}
