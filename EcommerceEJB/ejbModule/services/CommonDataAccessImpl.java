package services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dto.CategoriaDTO;
import dto.ProdottoDTO;
import dto.UtenteDTO;
import ejbInterfaces.CommonDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.CommonMapper;

@Stateless
public class CommonDataAccessImpl extends BaseService implements CommonDataAccess {
	
	private final static Logger logger = LogManager.getLogger(CommonDataAccessImpl.class);
	
	@Resource
	private SessionContext context;
	
	@Override
	public String test(int num) throws Exception {
		String result = null;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			result = commonMapper.test(num);
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
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
	public ProdottoDTO getProdotto(ProdottoDTO prodottoDTO) throws EcommerceException {
		ProdottoDTO prodottoNuovo = null;
		try (SqlSession session = getSession()) {
			CommonMapper commonMapper = (CommonMapper) session.getMapper(CommonMapper.class);
			List<ProdottoDTO> prodotti = commonMapper.getProdottiAttivi(prodottoDTO);
			if (prodotti != null && !prodotti.isEmpty()) {
				prodottoNuovo = prodotti.get(0);
			}
		} catch(Exception e) {
			context.setRollbackOnly();
			logger.error(e.getMessage(), e);
			throw new EcommerceException(e.getMessage(), e);
		}
		return prodottoNuovo;
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

}
