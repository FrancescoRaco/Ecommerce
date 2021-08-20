package services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dto.CategoriaDTO;
import dto.OrdineDTO;
import dto.ProdottoDTO;
import dto.UtenteDTO;
import ejbInterfaces.CommonDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.BuyerMapper;
import mapperInterfaces.CommonMapper;

@Stateless
public class CommonDataAccessImpl extends BaseService implements CommonDataAccess {
	
	private final static Logger logger = LogManager.getLogger(CommonDataAccessImpl.class);
	
	@Resource
	private SessionContext context;
	
	@Override
	public List<CategoriaDTO> getCategorie() throws EcommerceException {
		List<CategoriaDTO> categorie = null;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			categorie = commonMapper.getCategorie();
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return categorie;
	}
	
	@Override
	public ProdottoDTO getProdottoAttivo(Integer id) throws EcommerceException {
		ProdottoDTO prodotto = null;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			prodotto = commonMapper.getProdottoAttivo(id);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return prodotto;
	}
	
	@Override
	public List<ProdottoDTO> getProdottiAttivi(ProdottoDTO prodottoDTO) throws EcommerceException {
		List<ProdottoDTO> prodotti = null;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			prodotti = commonMapper.getProdottiAttivi(prodottoDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return prodotti;
	}
	
	@Override
	public int modificaPassword(UtenteDTO utenteDTO) throws EcommerceException {
		int result = 0;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			result = commonMapper.modificaPassword(utenteDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public int modificaProfilo(UtenteDTO utenteDTO) throws EcommerceException {
		int result = 0;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			result = commonMapper.modificaProfilo(utenteDTO);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}

}
