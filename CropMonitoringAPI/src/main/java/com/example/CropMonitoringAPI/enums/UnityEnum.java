package com.example.CropMonitoringAPI.enums;

public enum UnityEnum {

	CELCIUS(1,"ºC"),
	PORCETAGEM(2,"%")
	;
	

	private int id;
	private String unidade;
	
	private UnityEnum(int id, String unidade){
		this.id = id;
		this.unidade = unidade;
	}
	
	@Override
	public String toString() {
		return this.unidade;
	}
	
	public static UnityEnum getById(int id) {
	    for(UnityEnum u : values()) {
	        if(u.id == id) return u;
	    }
	    return null;
	}

	
	
}
