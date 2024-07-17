package uni.pe.edutec.Dto;
 
public class PagoTotalDto {
	private String profesor;
	private String ciclo;
	private int secciones;
	private double pagototal;

	public PagoTotalDto() {
		// TODO Auto-generated constructor stub
	}

	public PagoTotalDto(String profesor, String ciclo, int secciones, double pagototal) {
		super();
		this.profesor = profesor;
		this.ciclo = ciclo;
		this.secciones = secciones;
		this.pagototal = pagototal;
	}

	public String getProfesor() {
		return profesor;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public int getSecciones() {
		return secciones;
	}

	public void setSecciones(int secciones) {
		this.secciones = secciones;
	}

	public double getPagototal() {
		return pagototal;
	}

	public void setPagototal(double pagototal) {
		this.pagototal = pagototal;
	}

	
	
}
