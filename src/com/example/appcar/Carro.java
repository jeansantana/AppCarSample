package com.example.appcar;

public class Carro {

	private Integer id;
	
	private String nome;

	private String modelo;
	
	private Integer ano;
	
	private String fabricante;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + " - " + nome;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return this.getId() == ((Carro) o).getId();
	}
	
}