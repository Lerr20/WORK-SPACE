package uni.pe.Eureka.Dto;

public class movimientoDto {
 
	 private String codigo;
	 private String nombre;
	 private String moneda;
	 private float  ingreso;
	 private float salida;
	 
	 public movimientoDto() {
		// TODO Auto-generated constructor stub
	}

	public movimientoDto(String codigo, String nombre, String moneda, float ingreso, float salida) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.moneda = moneda;
		this.ingreso = ingreso;
		this.salida = salida;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public float getIngreso() {
		return ingreso;
	}

	public void setIngreso(float ingreso) {
		this.ingreso = ingreso;
	}

	public float getSalida() {
		return salida;
	}

	public void setSalida(float salida) {
		this.salida = salida;
	}
	 
	 
}
