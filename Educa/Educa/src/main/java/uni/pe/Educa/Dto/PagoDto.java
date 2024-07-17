package uni.pe.Educa.Dto;

public class PagoDto {

	//FIJARSE EN LA TABLA PAGO PARA VER QUE DATOS HAY QUE INSERTAR
	private int alumnoid;
	private int cursoid;
	 private int numcuota;
	 private double importe;
	 private int empleado;
	 
	 public PagoDto() {
		// TODO Auto-generated constructor stub
	}

	public PagoDto(int alumnoid, int cursoid, int numcuota, double importe, int empleado) {
		super();
		this.alumnoid = alumnoid;
		this.cursoid = cursoid;
		this.numcuota = numcuota;
		this.importe = importe;
		this.empleado = empleado;
	}

	public int getAlumnoid() {
		return alumnoid;
	}

	public void setAlumnoid(int alumnoid) {
		this.alumnoid = alumnoid;
	}

	public int getCursoid() {
		return cursoid;
	}

	public void setCursoid(int cursoid) {
		this.cursoid = cursoid;
	}

	public int getNumcuota() {
		return numcuota;
	}

	public void setNumcuota(int numcuota) {
		this.numcuota = numcuota;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public int getEmpleado() {
		return empleado;
	}

	public void setEmpleado(int empleado) {
		this.empleado = empleado;
	}
	 
	 
	 
}
