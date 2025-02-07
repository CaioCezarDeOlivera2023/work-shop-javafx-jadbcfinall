package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao(); //aqui esta injetando a dependencia usando o padrão factory
	
	
	public List<Department> findAll(){
		return dao.findAll();		
	}
	
	public void saveOrUpdate(Department obj) {
		if(obj.getId() == null) {//aqui esta sendo inserido ou atulizando os dados
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void romeve(Department obj) {//aqui esta removendo um departamento do banco de dados
		dao.deleteById(obj.getId());
	}
	
}
