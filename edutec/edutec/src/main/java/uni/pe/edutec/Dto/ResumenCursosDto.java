package uni.pe.edutec.Dto;

public class ResumenCursosDto {

	
	   private String idcurso;
	    private String nombre;
	    private int secciones;
	    private int programados;
	    private int matriculados;
	    private double ingresos;
	    
	    public ResumenCursosDto() {
			// TODO Auto-generated constructor stub
		}

		public ResumenCursosDto(String idcurso, String nombre, int secciones, int programados, int matriculados,
				double ingresos) {
			super();
			this.idcurso = idcurso;
			this.nombre = nombre;
			this.secciones = secciones;
			this.programados = programados;
			this.matriculados = matriculados;
			this.ingresos = ingresos;
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

		public int getSecciones() {
			return secciones;
		}

		public void setSecciones(int secciones) {
			this.secciones = secciones;
		}

		public int getProgramados() {
			return programados;
		}

		public void setProgramados(int programados) {
			this.programados = programados;
		}

		public int getMatriculados() {
			return matriculados;
		}

		public void setMatriculados(int matriculados) {
			this.matriculados = matriculados;
		}

		public double getIngresos() {
			return ingresos;
		}

		public void setIngresos(double ingresos) {
			this.ingresos = ingresos;
		}
	    
		
}
