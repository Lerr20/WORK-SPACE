package uni.pe.Educa.Dto;

public class CuotasDto {
	private int ncuota;
	private String fecha;
	private double importe;

	public CuotasDto() {
		// TODO Auto-generated constructor stub
	}

	public CuotasDto(int ncuota, String fecha, double importe) {
		super();
		this.ncuota = ncuota;
		this.fecha = fecha;
		this.importe = importe;
	}

	public int getNcuota() {
		return ncuota;
	}

	public void setNcuota(int ncuota) {
		this.ncuota = ncuota;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	
}
