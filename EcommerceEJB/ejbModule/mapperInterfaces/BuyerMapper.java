package mapperInterfaces;

import dto.OrdineDTO;

public interface BuyerMapper {
	
	public int getMaxProgrOrdine(int idProdotto) throws Exception;
	public int insertOrdine(OrdineDTO ordineDTO) throws Exception;
}
