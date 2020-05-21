package com.thinker.basethinker.utils;

public class Country {
	
	public Country(Continent continent,String name, String code) {
		super();
		this.name = name;
		this.code = code;
		this.continent = continent;
	}
	
	private String name;
	
	private String code;
	
	private Continent continent;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Continent getContinent() {
		return continent;
	}
	public void setContinent(Continent continent) {
		this.continent = continent;
	}
}
