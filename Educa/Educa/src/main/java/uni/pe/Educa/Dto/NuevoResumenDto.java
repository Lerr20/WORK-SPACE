package uni.pe.Educa.Dto;

import java.util.List;

public class NuevoResumenDto {

	 private List<EstadoDto> alumno;
	 private List<CuotasDto> cuotas;
	 
	 public NuevoResumenDto() {
		// TODO Auto-generated constructor stub
	}

	public NuevoResumenDto(List<EstadoDto> alumno, List<CuotasDto> cuotas) {
		super();
		this.alumno = alumno;
		this.cuotas = cuotas;
	}

	public List<EstadoDto> getAlumno() {
		return alumno;
	}

	public void setAlumno(List<EstadoDto> alumno) {
		this.alumno = alumno;
	}

	public List<CuotasDto> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<CuotasDto> cuotas) {
		this.cuotas = cuotas;
	}
	 
	 
}
