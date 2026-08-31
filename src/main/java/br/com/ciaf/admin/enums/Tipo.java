package br.com.ciaf.admin.enums;



public enum Tipo {
	ENTRADA(0), SAÍDA(1);

	private int code;

	private Tipo(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public static Tipo valueOf(int code){
		for(Tipo value: Tipo.values()){
			if(value.getCode() == code){
				return value;
			}
		}
		throw new IllegalArgumentException("Invlalid Tipo Code");
	}
}
