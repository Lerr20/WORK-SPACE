package uni.pe.Educa.Dto;

public class AlumnoDto {
	private String alumno;
	private String curso;
	private String tipo;
	private double precio;
	private int cuotas;
	private int nota;

	public AlumnoDto() {
		// TODO Auto-generated constructor stub
	}

	public AlumnoDto(String alumno, String curso, String tipo, double precio, int cuotas, int nota) {
		super();
		this.alumno = alumno;
		this.curso = curso;
		this.tipo = tipo;
		this.precio = precio;
		this.cuotas = cuotas;
		this.nota = nota;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getCuotas() {
		return cuotas;
	}

	public void setCuotas(int cuotas) {
		this.cuotas = cuotas;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}
	
	
}
