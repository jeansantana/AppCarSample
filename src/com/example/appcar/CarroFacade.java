package com.example.appcar;

import java.util.List;

public interface CarroFacade {
	
	public List<Carro> getAll() throws Exception;
	public void delete(Carro c) throws Exception;
	public void insert(Carro c) throws Exception;
	public void update(Carro c) throws Exception;
	

}
