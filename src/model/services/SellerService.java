package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	
	private SellerDao dao = DaoFactory.createSellerDao(); //aqui esta injetando a dependencia usando o padrão factory
	
	
	public List<Seller> findAll(){
		return dao.findAll();		
	}
	
	public void saveOrUpdate(Seller obj) {
		if(obj.getId() == null) {//aqui esta sendo inserido ou atulizando os dados
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void romeve(Seller obj) {//aqui esta removendo um Seller do banco de dados
		dao.deleteById(obj.getId());
	}
	
}
