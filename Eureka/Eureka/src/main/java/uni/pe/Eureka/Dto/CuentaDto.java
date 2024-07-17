package uni.pe.Eureka.Dto;

public class CuentaDto {
	private String idsucursal;
	private String nombre;
	private int soles; //NUMERO DE CUNETAS ACTIVAS EN SOLES
	private int dolares; //NUMERO DE CUENTAS ACTIVAS EN DOLARES
	
	public CuentaDto() {
		// TODO Auto-generated constructor stub
	}

	public CuentaDto(String idsucursal, String nombre, int soles, int dolares) {
		super();
		this.idsucursal = idsucursal;
		this.nombre = nombre;
		this.soles = soles;
		this.dolares = dolares;
	}

	public String getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(String idsucursal) {
		this.idsucursal = idsucursal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getSoles() {
		return soles;
	}

	public void setSoles(int soles) {
		this.soles = soles;
	}

	public int getDolares() {
		return dolares;
	}

	public void setDolares(int dolares) {
		this.dolares = dolares;
	}
	

}
