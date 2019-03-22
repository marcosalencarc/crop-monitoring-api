package com.example.CropMonitoringAPI.enums;

public enum UnityEnum {

	CELCIUS(1,"ºC"),
	PORCETAGEM(1,"%")
	;
	

	private int id;
	private String unidade;
	
	private UnityEnum(int id, String unidade){
		this.id = id;
		this.unidade = unidade;
	}
	
	
}
