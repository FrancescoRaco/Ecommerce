package mapperInterfaces;

import dto.OrdineDTO;

public interface BuyerMapper {
	
	public int getMaxProgrOrdine() throws Exception;
	public int insertOrdine(OrdineDTO ordineDTO) throws Exception;
}
