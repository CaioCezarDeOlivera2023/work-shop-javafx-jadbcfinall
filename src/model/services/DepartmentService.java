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
	

}
