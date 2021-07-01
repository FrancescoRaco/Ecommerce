package services;

import java.util.List;
import javax.ejb.Stateless;
import dto.OrdineDTO;
import ejbInterfaces.BuyerDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.BuyerMapper;

@Stateless
public class BuyerDataAccessImpl extends BaseService implements BuyerDataAccess {
	
	@Override
	public int insertOrdine(OrdineDTO ordineDTO) throws EcommerceException {
		int progressivo = 0;
		try {
			BuyerMapper buyerMapper = (BuyerMapper) getSession().getMapper(BuyerMapper.class);
			progressivo = buyerMapper.getMaxProgrOrdine() + 1;
			ordineDTO.setProgressivo(progressivo);
			buyerMapper.insertOrdine(ordineDTO);
		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return progressivo;
	}
	
	@Override
	public List<OrdineDTO> getOrdiniEffettuati(String cfAcquirente) throws EcommerceException {
		List<OrdineDTO> ordiniEffettuati = null;
		try {
			BuyerMapper buyerMapper = (BuyerMapper) getSession().getMapper(BuyerMapper.class);
			OrdineDTO ordineDTO = new OrdineDTO();
			ordineDTO.setCfAcquirente(cfAcquirente);
			ordiniEffettuati = buyerMapper.getOrdiniBy(ordineDTO);
		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return ordiniEffettuati;
	}
	
	@Override
	public List<OrdineDTO> getOrdiniRicevuti(Integer idProdotto) throws EcommerceException {
		List<OrdineDTO> ordiniRicevuti = null;
		try {
			BuyerMapper buyerMapper = (BuyerMapper) getSession().getMapper(BuyerMapper.class);
			OrdineDTO ordineDTO = new OrdineDTO();
			ordineDTO.setIdProdotto(idProdotto);
			ordiniRicevuti = buyerMapper.getOrdiniBy(ordineDTO);
		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return ordiniRicevuti;
	}

}
