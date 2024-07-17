package uni.pe.Educa.Dto;

public class EstadoDto {
	private String alumno;
	private String curso;
	private String profesor;
	private String fecha;
	private String tipo;
	private double precio;
	private int cuotas;
	private String nota;
	
	public EstadoDto() {
		// TODO Auto-generated constructor stub
	}

	public EstadoDto(String alumno, String curso, String profesor, String fecha, String tipo, double precio, int cuotas,
			String nota) {
		super();
		this.alumno = alumno;
		this.curso = curso;
		this.profesor = profesor;
		this.fecha = fecha;
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

	public String getProfesor() {
		return profesor;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	
}
