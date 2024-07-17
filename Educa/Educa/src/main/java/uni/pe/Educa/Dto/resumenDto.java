package uni.pe.Educa.Dto;

import java.util.List;

public class resumenDto {
	 private List<AlumnoDto> alumno;
	 private List<CuotasDto> cuotas;
	 
	 public resumenDto() {
		// TODO Auto-generated constructor stub
	}

	public resumenDto(List<AlumnoDto> alumno, List<CuotasDto> cuotas) {
		super();
		this.alumno = alumno;
		this.cuotas = cuotas;
	}

	public List<AlumnoDto> getAlumno() {
		return alumno;
	}

	public void setAlumno(List<AlumnoDto> alumno) {
		this.alumno = alumno;
	}

	public List<CuotasDto> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<CuotasDto> cuotas) {
		this.cuotas = cuotas;
	}

	 
}
