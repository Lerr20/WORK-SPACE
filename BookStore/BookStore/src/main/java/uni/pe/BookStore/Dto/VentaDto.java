package uni.pe.BookStore.Dto;

public class VentaDto {
 
	private String idpublicacion;//
	private String cliente;//
	private String empleado;//
	private String fecha;
	private int cantidad;//
	private double precio;
	private double descuento;
	private double subtotal;
	private double impuesto;
	private double total;
	
	public VentaDto() {
		// TODO Auto-generated constructor stub
	}

	public VentaDto(String idpublicacion, String cliente, String empleado, String fecha, int cantidad, double precio,
			double descuento, double subtotal, double impuesto, double total) {
		super();
		this.idpublicacion = idpublicacion;
		this.cliente = cliente;
		this.empleado = empleado;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.precio = precio;
		this.descuento = descuento;
		this.subtotal = subtotal;
		this.impuesto = impuesto;
		this.total = total;
	}

	public String getIdpublicacion() {
		return idpublicacion;
	}

	public void setIdpublicacion(String idpublicacion) {
		this.idpublicacion = idpublicacion;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(double impuesto) {
		this.impuesto = impuesto;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
}
