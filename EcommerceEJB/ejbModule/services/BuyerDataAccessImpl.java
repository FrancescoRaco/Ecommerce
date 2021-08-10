package services;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dto.OrdineDTO;
import ejbInterfaces.BuyerDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.BuyerMapper;

@Stateless
public class BuyerDataAccessImpl extends BaseService implements BuyerDataAccess {
	
	private final static Logger logger = LogManager.getLogger(BuyerDataAccessImpl.class);
	
	@Resource
	private SessionContext context;
	
	@Override
	public int insertOrdine(OrdineDTO ordineDTO) throws EcommerceException {
		int progressivo = 0;
		try (SqlSession session = getSession()) {
			BuyerMapper buyerMapper = (BuyerMapper) session.getMapper(BuyerMapper.class);
			progressivo = buyerMapper.getMaxProgrOrdine() + 1;
			ordineDTO.setProgressivo(progressivo);
			buyerMapper.insertOrdine(ordineDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return progressivo;
	}
	
	@Override
	public List<OrdineDTO> getOrdiniEffettuati(String cfAcquirente) throws EcommerceException {
		List<OrdineDTO> ordiniEffettuati = null;
		try (SqlSession session = getSession()) {
			BuyerMapper buyerMapper = (BuyerMapper) session.getMapper(BuyerMapper.class);
			OrdineDTO ordineDTO = new OrdineDTO();
			ordineDTO.setCfAcquirente(cfAcquirente);
			ordiniEffettuati = buyerMapper.getOrdiniBy(ordineDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return ordiniEffettuati;
	}
	
	@Override
	public List<OrdineDTO> getOrdiniRicevuti(Integer idProdotto) throws EcommerceException {
		List<OrdineDTO> ordiniRicevuti = null;
		try (SqlSession session = getSession()) {
			BuyerMapper buyerMapper = (BuyerMapper) session.getMapper(BuyerMapper.class);
			OrdineDTO ordineDTO = new OrdineDTO();
			ordineDTO.setIdProdotto(idProdotto);
			ordiniRicevuti = buyerMapper.getOrdiniBy(ordineDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return ordiniRicevuti;
	}
	
	@Override
	public OrdineDTO getOrdine(OrdineDTO ordineDTO) throws EcommerceException {
		OrdineDTO ordineNuovo = null;
		try (SqlSession session = getSession()) {
			BuyerMapper buyerMapper = (BuyerMapper) session.getMapper(BuyerMapper.class);
			List<OrdineDTO> ordini = buyerMapper.getOrdiniBy(ordineDTO);
			if (ordini != null && !ordini.isEmpty()) {
				ordineNuovo = ordini.get(0);
			}
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return ordineNuovo;
	}

}
