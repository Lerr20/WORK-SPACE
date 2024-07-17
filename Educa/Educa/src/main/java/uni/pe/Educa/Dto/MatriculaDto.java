package uni.pe.Educa.Dto;

public class MatriculaDto {
  
	private String curso;//
	private String alumno;//
	private String empleado;//
	private String tipo;//
	private String fecha;
	private double precio;
	private int  cuotas;//
	private String nota;
	
	public MatriculaDto() {
		// TODO Auto-generated constructor stub
	}

	public MatriculaDto(String curso, String alumno, String empleado, String tipo, String fecha, double precio,
			int cuotas, String nota) {
		super();
		this.curso = curso;
		this.alumno = alumno;
		this.empleado = empleado;
		this.tipo = tipo;
		this.fecha = fecha;
		this.precio = precio;
		this.cuotas = cuotas;
		this.nota = nota;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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
