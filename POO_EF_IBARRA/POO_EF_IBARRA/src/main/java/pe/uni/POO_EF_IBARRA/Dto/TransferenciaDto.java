package pe.uni.POO_EF_IBARRA.Dto;


public class TransferenciaDto {
		
		private String cuentaOrigen;
		private String cuentaDestino;
		private double importe;
		private String empleado;
		
		public TransferenciaDto() {
			// TODO Auto-generated constructor stub
		}

		public TransferenciaDto( String cuentaOrigen, String cuentaDestino,double importe, String empleado) {
			super();
			this.cuentaOrigen = cuentaOrigen;
			this.cuentaDestino = cuentaDestino;
			this.importe = importe;
			this.empleado = empleado;
		}



		public String getCuentaOrigen() {
			return cuentaOrigen;
		}

		public void setCuentaOrigen(String cuentaOrigen) {
			this.cuentaOrigen = cuentaOrigen;
		}

		public String getCuentaDestino() {
			return cuentaDestino;
		}

		public void setCuentaDestino(String cuentaDestino) {
			this.cuentaDestino = cuentaDestino;
		}

		public double getImporte() {
			return importe;
		}

		public void setImporte(double importe) {
			this.importe = importe;
		}

		public String getEmpleado() {
			return empleado;
		}

		public void setEmpleado(String empleado) {
			this.empleado = empleado;
		}

		
		
}
