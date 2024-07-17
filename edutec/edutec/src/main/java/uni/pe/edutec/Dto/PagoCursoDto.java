package uni.pe.edutec.Dto;

public class PagoCursoDto {
	private String idcurso;
	private String nombre;
	private int horas;
	private double pagoHoras;
	private double total;

	public PagoCursoDto() {
		// TODO Auto-generated constructor stub
	}

	public PagoCursoDto(String idcurso, String nombre, int horas, double pagoHoras, double total) {
		super();
		this.idcurso = idcurso;
		this.nombre = nombre;
		this.horas = horas;
		this.pagoHoras = pagoHoras;
		this.total = total;
	}

	public String getIdcurso() {
		return idcurso;
	}

	public void setIdcurso(String idcurso) {
		this.idcurso = idcurso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public double getPagoHoras() {
		return pagoHoras;
	}

	public void setPagoHoras(double pagoHoras) {
		this.pagoHoras = pagoHoras;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
		

}
