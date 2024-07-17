package uni.pe.edutec.Dto;

public class MatriculaDto {
	private String alumno;
	private String cursoprog;
	private String fecha;
	private String ciclo;

	public MatriculaDto() {
		// TODO Auto-generated constructor stub
	}

	public MatriculaDto(String alumno, String cursoprog, String fecha, String ciclo) {
		super();
		this.alumno = alumno;
		this.cursoprog = cursoprog;
		this.fecha = fecha;
		this.ciclo = ciclo;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public String getCursoprog() {
		return cursoprog;
	}

	public void setCurso(String cursoprog) {
		this.cursoprog = cursoprog;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	

	
}
