package uni.pe.Eureka.Dto;

public class TransferenciaDto {

	private String cuentaOrigen;
	private String cuentaDestino;
	private String empleado;
	private double importe; // MONTO A TRANSFERIR
	private double saldoOrigen;
	private double saldoDestino;

	public TransferenciaDto() {
		// TODO Auto-generated constructor stub
	}

	public TransferenciaDto(String cuentaOrigen, String cuentaDestino, String empleado, double importe,
			double saldoOrigen, double saldoDestino) {
		super();
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
		this.empleado = empleado;
		this.importe = importe;
		this.saldoOrigen = saldoOrigen;
		this.saldoDestino = saldoDestino;
	}

	public String getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public String getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getSaldoOrigen() {
		return saldoOrigen;
	}

	public void setSaldoOrigen(double saldoOrigen) {
		this.saldoOrigen = saldoOrigen;
	}

	public double getSaldoDestino() {
		return saldoDestino;
	}

	public void setSaldoDestino(double saldoDestino) {
		this.saldoDestino = saldoDestino;
	}
	
	
}