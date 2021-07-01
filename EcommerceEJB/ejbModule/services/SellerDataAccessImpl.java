package services;

import javax.ejb.Stateless;
import dto.OrdineDTO;
import dto.ProdottoDTO;
import ejbInterfaces.SellerDataAccess;
import exceptions.EcommerceException;
import mapperInterfaces.SellerMapper;

@Stateless
public class SellerDataAccessImpl extends BaseService implements SellerDataAccess {
	
	@Override
	public int insertProdotto(ProdottoDTO prodottoDTO) throws EcommerceException {
		
		int id = 0;
		
		try {
			SellerMapper sellerMapper = (SellerMapper) getSession().getMapper(SellerMapper.class);
			id = sellerMapper.getMaxIdProdotto() + 1;
			prodottoDTO.setId(id);
			sellerMapper.insertProdotto(prodottoDTO);
		} catch(Exception e) {
			System.out.print("Prova");
			throw new EcommerceException(e.getMessage(), e);
		}
		return id;
	}
	
	@Override
	public int getDisponibilitaProdotto(Integer idProdotto) throws EcommerceException {
		
		int id = 0;
		
		try {
			SellerMapper sellerMapper = (SellerMapper) getSession().getMapper(SellerMapper.class);
			id = sellerMapper.getDisponibilita(idProdotto);
		} catch(Exception e) {
			System.out.print("Prova");
			throw new EcommerceException(e.getMessage(), e);
		}
		return id;
	}
	
	@Override
	public int accettaOrdine(OrdineDTO ordineDTO) throws EcommerceException {
		
		int result = 0;
		
		try {
			SellerMapper sellerMapper = (SellerMapper) getSession().getMapper(SellerMapper.class);
			Integer disponibilita = sellerMapper.getDisponibilita(ordineDTO.getIdProdotto());
			disponibilita -= ordineDTO.getQuantita();
			result = sellerMapper.accettaOrdine(ordineDTO);
			ProdottoDTO prodottoDTO = new ProdottoDTO();
			prodottoDTO.setId(ordineDTO.getIdProdotto());
			prodottoDTO.setDisponibilita(disponibilita);
			result = sellerMapper.aggiornaDisponibilitaProdotto(prodottoDTO);
		} catch(Exception e) {
			System.out.print("Prova");
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public int rifiutaOrdine(OrdineDTO ordineDTO) throws EcommerceException {
		
		int result = 0;
		
		try {
			SellerMapper sellerMapper = (SellerMapper) getSession().getMapper(SellerMapper.class);
			result = sellerMapper.rifiutaOrdine(ordineDTO);
		} catch(Exception e) {
			System.out.print("Prova");
			throw new EcommerceException(e.getMessage(), e);
		}
		return result;
	}

}