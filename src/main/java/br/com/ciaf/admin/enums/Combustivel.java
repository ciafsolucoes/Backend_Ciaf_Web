package br.com.ciaf.admin.enums;

public enum Combustivel {
	SIM(0), NÃO(1);
    private int code;
	private Combustivel(int code) {
		this.code = code;
	}
	private int getCode(){
		return code;
	}

	public static Combustivel valueOf(int code){
		for(Combustivel value: Combustivel.values()){
			if(value.getCode() == code){
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid combustível Type");
	}
}
