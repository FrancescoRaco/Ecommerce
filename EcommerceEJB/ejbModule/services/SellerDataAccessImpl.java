package services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dto.OrdineDTO;
import dto.ProdottoDTO;
import ejbInterfaces.SellerDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.CommonMapper;
import mapperInterfaces.SellerMapper;

@Stateless
public class SellerDataAccessImpl extends BaseService implements SellerDataAccess {
	
	private final static Logger logger = LogManager.getLogger(SellerDataAccessImpl.class);
	
	@Resource
	private SessionContext context;
	
	@Override
	public int insertProdotto(ProdottoDTO prodottoDTO) throws EcommerceException {
		
		int id = 0;
		
		try (SqlSession session = getSession()) {
			SellerMapper sellerMapper = (SellerMapper) session.getMapper(SellerMapper.class);
			id = sellerMapper.getMaxIdProdotto() + 1;
			prodottoDTO.setId(id);
			sellerMapper.insertProdotto(prodottoDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return id;
	}
	
	@Override
	public int getDisponibilitaProdotto(Integer idProdotto) throws EcommerceException {
		
		int id = 0;
		
		try (SqlSession session = getSession()) {
			SellerMapper sellerMapper = (SellerMapper) session.getMapper(SellerMapper.class);
			id = sellerMapper.getDisponibilita(idProdotto);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return id;
	}
	
	@Override
	public List<OrdineDTO> getOrdiniRicevuti(Integer idProdotto) throws EcommerceException {
		List<OrdineDTO> ordiniRicevuti = null;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			OrdineDTO ordineDTO = new OrdineDTO();
			ordineDTO.setIdProdotto(idProdotto);
			ordiniRicevuti = commonMapper.getOrdiniBy(ordineDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return ordiniRicevuti;
	}
	
	@Override
	public int accettaOrdine(OrdineDTO ordineDTO) throws EcommerceException {
		
		int result = 0;
		
		try (SqlSession session = getSession()) {
			SellerMapper sellerMapper = (SellerMapper) session.getMapper(SellerMapper.class);
			Integer disponibilita = sellerMapper.getDisponibilita(ordineDTO.getIdProdotto());
			disponibilita -= ordineDTO.getQuantita();
			result = sellerMapper.accettaOrdine(ordineDTO);
			ProdottoDTO prodottoDTO = new ProdottoDTO();
			prodottoDTO.setId(ordineDTO.getIdProdotto());
			prodottoDTO.setDisponibilita(disponibilita);
			result = sellerMapper.aggiornaDisponibilitaProdotto(prodottoDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public int rifiutaOrdine(OrdineDTO ordineDTO) throws EcommerceException {
		
		int result = 0;
		
		try (SqlSession session = getSession()) {
			SellerMapper sellerMapper = (SellerMapper) session.getMapper(SellerMapper.class);
			result = sellerMapper.rifiutaOrdine(ordineDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public int modificaProdotto(ProdottoDTO prodottoDTO) throws EcommerceException {
		
		int result = 0;
		
		try (SqlSession session = getSession()) {
			SellerMapper sellerMapper = (SellerMapper) session.getMapper(SellerMapper.class);
			result = sellerMapper.modificaProdotto(prodottoDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public int annullaProdotto(Integer id) throws EcommerceException {
		
		int result = 0;
		
		try (SqlSession session = getSession()) {
			SellerMapper sellerMapper = (SellerMapper) session.getMapper(SellerMapper.class);
			result = sellerMapper.annullaProdotto(id);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public OrdineDTO getOrdine(OrdineDTO ordineDTO) throws EcommerceException {
		OrdineDTO ordineNuovo = null;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			List<OrdineDTO> ordini = commonMapper.getOrdiniBy(ordineDTO);
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