package uni.pe.Eureka.Dto;

public class idtipoDto {
	private String idtipo;
	private String descripcion;
	private String accion;
	private float soles;
	private float dolares;
	
	public idtipoDto() {
		// TODO Auto-generated constructor stub
	}

	public idtipoDto(String idtipo, String descripcion, String accion, float soles, float dolares) {
		super();
		this.idtipo = idtipo;
		this.descripcion = descripcion;
		this.accion = accion;
		this.soles = soles;
		this.dolares = dolares;
	}

	public String getIdtipo() {
		return idtipo;
	}

	public void setIdtipo(String idtipo) {
		this.idtipo = idtipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public float getSoles() {
		return soles;
	}

	public void setSoles(float soles) {
		this.soles = soles;
	}

	public float getDolares() {
		return dolares;
	}

	public void setDolares(float dolares) {
		this.dolares = dolares;
	}
	
	

}
