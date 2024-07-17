package uni.pe.Educa.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uni.pe.Educa.Dto.AlumnoDto;
import uni.pe.Educa.Dto.CuotasDto;
import uni.pe.Educa.Dto.EstadoDto;
import uni.pe.Educa.Dto.MatriculaDto;
import uni.pe.Educa.Dto.PagoDto;

@Service
public class EducaService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<AlumnoDto> estadoAlumno(String alumnoid, String cursoid) {

		List<AlumnoDto> lista = null;
		String sql;
		sql = "SELECT ";
		sql += "    alumno.alu_nombre AS Alumno, ";
		sql += "    curso.cur_nombre AS Curso, ";
		sql += "    matricula.mat_tipo AS Tipo,  ";
		sql += "    matricula.mat_precio AS Precio, ";
		sql += "    matricula.mat_cuotas AS Cuotas, ";
		sql += "    matricula.mat_nota AS Nota ";
		sql += "FROM  ";
		sql += "    matricula ";
		sql += "JOIN  ";
		sql += "    alumno ON matricula.alu_id = alumno.alu_id ";
		sql += "JOIN  ";
		sql += "    curso ON matricula.cur_id = curso.cur_id ";
		sql += "WHERE alumno.alu_id = ? AND curso.cur_id = ?; ";

		try {
			lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AlumnoDto.class), alumnoid, cursoid);
		} catch (Exception e) {
			// ManejoDeExcepciones
		}

		return lista;

	}

	public List<CuotasDto> cuotasAlumno(String alumnoid, String cursoid) {
		List<CuotasDto> lista = null;
		String sql;
		sql = "SELECT  ";
		sql += "    pago.pag_cuota AS NCuota, ";
		sql += "    pago.pag_fecha AS Fecha, ";
		sql += "    pago.pag_importe AS Importe ";
		sql += "FROM  ";
		sql += "    matricula ";
		sql += "JOIN  ";
		sql += "    pago ON matricula.cur_id  = pago.cur_id and matricula.alu_id = pago.alu_id ";
		sql += "WHERE ";
		sql += "    matricula.alu_id = ? AND matricula.cur_id = ?;";

		try {
			lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CuotasDto.class), alumnoid, cursoid);
		} catch (Exception e) {
			// ManejoDeExcepciones
		}

		return lista;
	}

	// ESTADO DE UN ALUMNO CON NOTA PENDIENTE SI AUN NO SALE

	public List<EstadoDto> nuevoEstadoAlumno(String alumnoid, String cursoid) {

		List<EstadoDto> lista = null;
		String sql;
		sql = " SELECT  ";
		sql += "    alumno.alu_nombre AS Alumno, ";
		sql += "    curso.cur_nombre AS Curso, ";
		sql += "	curso.cur_profesor AS Profesor, ";
		sql += "	matricula.mat_fecha AS Fecha, ";
		sql += "    matricula.mat_tipo AS Tipo, ";
		sql += "    matricula.mat_precio AS Precio, ";
		sql += "    matricula.mat_cuotas AS Cuotas, ";
		sql += "   CASE WHEN matricula.mat_nota IS NULL THEN 'pendiente' ELSE CAST(matricula.mat_nota AS VARCHAR(50)) END AS Nota  ";
		sql += "FROM ";
		sql += "    matricula ";
		sql += "JOIN ";
		sql += "    alumno ON matricula.alu_id = alumno.alu_id ";
		sql += "JOIN  ";
		sql += "    curso ON matricula.cur_id = curso.cur_id ";
		sql += "WHERE  ";
		sql += "    alumno.alu_id = ? and curso.cur_id = ? ; ";

		try {
			lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EstadoDto.class), alumnoid, cursoid);
		} catch (Exception e) {
			// ManejoDeExcepciones
		}

		return lista;

	}

	// PAGO DE UNA CUOTA

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public PagoDto realizarPago(PagoDto bean) {
		// VARIABLES

		String sql;
		int cont, cuota, totalcuotas;
		double pagado, costo, faltapagar;

		// PARA VALIDAR MATRICULA VALIDAMOS Q EL ALUMNO ESTE MATRICULADO EN EL CURSO
		sql = " select count(1) from matricula where cur_id=? and alu_id=?;";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCursoid(), bean.getAlumnoid());

		if (cont == 0) {
			throw new RuntimeException(" la matricula no existe ");
		}
		// PARA VALIDAR EL NUMERO DE CUOTA
		// OBTENER EL NUMERO DE CUOTAS TOTALES
		sql = " select mat_cuotas from matricula where cur_id=? and alu_id=?;";
		totalcuotas = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCursoid(), bean.getAlumnoid());
		// OBTENER EL NUMERO DE DE CUOTA DE LA ULTIMA CUOTA PAGADA
		sql = "SELECT count(1) pag_cuota FROM pago WHERE cur_id = ? AND alu_id = ? ORDER BY pag_cuota desc;";
		cuota = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCursoid(), bean.getAlumnoid());

		// VALIDAR QUE EL NUEMRO DE CUOTA QUE SE QUIERA PAGAR SEA POSIBLE
		if (bean.getNumcuota() > totalcuotas) {
			throw new RuntimeException(" el numero de cuota es incorecto porque es mayor al numero de cuotas acordado");
		}

		if (bean.getNumcuota() != cuota + 1) {
			throw new RuntimeException(
					" el numero de cuota es invalido porque esa cuota ya se pago o no es la que sigue");
		}
		// VALIDAR EL IMPORTE
		// OBTENER LO QUE YA PAGO
		sql = "SELECT SUM(pag_importe) AS TotalPagado FROM pago WHERE cur_id = ? AND alu_id = ? ;";
		pagado = jdbcTemplate.queryForObject(sql, Double.class, bean.getCursoid(), bean.getAlumnoid());
		// OBTENER EL COSTO TOTAL DE LA MATRICULA
		sql = "select mat_precio from matricula WHERE cur_id = ? AND alu_id = ? ;";
		costo = jdbcTemplate.queryForObject(sql, Double.class, bean.getCursoid(), bean.getAlumnoid());
		faltapagar = costo - pagado;
		// VALIDAR PARA MATRICULAS EN UNA CUOTA
		if ((totalcuotas == 1) && (bean.getImporte() != costo)) {
			throw new RuntimeException(" el importe es insuficiente ,debe ser igual al costo total ");
		}

		// VALIDAR PARA MATRICULAS EN DOS CUOTAS ,LA PRIMERA DEBE SER DEL 50% Y LA
		// SEGUNTA TAMBIEN
		if ((totalcuotas == 2) && (bean.getImporte() != 0.5 * costo)) {
			throw new RuntimeException(" el importe debe ser del 50% del costo de la matricula");
		}
		// VALIDAR PARA MATRICULAS DE TRES CUOTAS
		// PARA LA PRIMERA CUOTA
		if (totalcuotas == 3 && bean.getNumcuota() == 1 && bean.getImporte() != 0.4 * costo) {
			throw new RuntimeException(
					" el importe debe ser del 40% del costo de la matricula,ya que es la primera cuota");
		}
		// PARA LA SEGUNDA CUOTA
		if (totalcuotas == 3 && bean.getNumcuota() == 2 && bean.getImporte() != 0.3 * costo) {
			throw new RuntimeException(
					" el importe debe ser del 30% del costo de la matricula, ya que es la segunda cuota");
		}
		// PARA LA SEGUNDA CUOTA
		if (totalcuotas == 3 && bean.getNumcuota() == 3 && bean.getImporte() != 0.3 * costo) {
			throw new RuntimeException(
					" el importe debe ser del 30% del costo de la matricula, ya que es la tercera cuota");
		}

		if (bean.getImporte() > faltapagar) {

			throw new RuntimeException(" el importe exede la deuda");
		}

		// REGISTRAR PAGO
		sql = " insert into pago (cur_id,alu_id,pag_cuota,emp_id,pag_fecha,pag_importe) ";
		sql += "    values(?,?,?,?,getdate(),?); ";
		Object[] datos = { bean.getCursoid(), bean.getAlumnoid(), bean.getNumcuota(), bean.getEmpleado(),
				bean.getImporte() };
		jdbcTemplate.update(sql, datos);

		return bean;
	}

	// REALIZAR UNA MATRICULA
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public MatriculaDto realizarMatricula(MatriculaDto bean) {
		// VARIABLES
		String sql,fecha;
		int cont, vacantes;
		double  precioCurso=0.0,precioMatricula=0.0;

		// VALIDAR EXISTENCIA DEL CURSO
		sql = " select count(1) from curso where cur_id=? ;";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCurso());
		if (cont == 0) {
			throw new RuntimeException(" el curso en el que se quiere matricular no existe .");
		}
		// VALIDAR QUE EL CURSO TENGA VACANTE
		sql = "select cur_vacantes from curso where cur_id=? ; ";
		vacantes = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCurso());
		if (vacantes == 0) {
			throw new RuntimeException(" el curso no tiene vacantes disponibles .");
		}
		// VALIDAR QUE EL ALUMNO EXISTA
		sql = "select count(1) from alumno where alu_id=? ;";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getAlumno());
		if (cont == 0) {
			throw new RuntimeException(" el alumno  que se quiere matricular no existe en nuestra base de datos .");
		}
		// VALIDAR QUE EL ALUMNO AUN NO ESTE MATRICULADO EN EL CURSO QUE DESEA
		// MATRICULARSE
		sql = "select count(1) from matricula where cur_id=? and alu_id=? ;";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCurso(), bean.getAlumno());
		if (cont == 1) {
			throw new RuntimeException(" el alumno ya esta matriculado en ese curso .");
		}

		// VALIDAR TIPO DE MATRICULA
		if (!bean.getTipo().equalsIgnoreCase("regular") && !bean.getTipo().equalsIgnoreCase("mediabeca")
				&& !bean.getTipo().equalsIgnoreCase("beca")) {
			throw new RuntimeException("El tipo de matrícula es inválido.");
		}

		// VALIDAR EL NUMERO DE CUOTAS SEGUN EL TIPO DE MATRICULA
		// SI ES BECA O MEDIA BECA DEBE SER UNA CUOTA
		if (("beca".equalsIgnoreCase(bean.getTipo()) || "mediabeca".equalsIgnoreCase(bean.getTipo()))
				&& bean.getCuotas() != 1) {
			throw new RuntimeException("El número de cuotas debe ser 1.");
		}
        //OBTENER EL PRECIO QUE DEBE PAGAR SEGUN SU TIPO DE MATRICULA
		
		//OBTENEMOS EL PRECIO DEL CURSO NORMAL
		sql="select cur_precio from curso where cur_id=? ;";
		precioCurso=jdbcTemplate.queryForObject(sql, Double.class, bean.getCurso());
		//DETERMINAR CUANTO DEBE PAGAR SEGUN SU TIPO DE MATRICULA
		switch (bean.getTipo().toLowerCase()) {
	    case "beca":
	        precioMatricula = 0.1 * precioCurso;
	        break;
	    case "mediabeca":
	        precioMatricula = 0.5 * precioCurso;
	        break;
	    case "regular":
	        precioMatricula = precioCurso;
	        break;
	      }
		//REGISTRAR MATRICULA
		sql="insert into matricula (cur_id,alu_id,emp_id,mat_tipo,mat_fecha,mat_precio,mat_cuotas,mat_nota) ";
		sql+= " values(?,?,?,?,getdate(),?,?,null);";
		Object []datos = {bean.getCurso(),bean.getAlumno(),bean.getEmpleado(),bean.getTipo(),precioMatricula,bean.getCuotas()};
		jdbcTemplate.update(sql, datos);
		
		//ACTUALIZAR VACANTES  Y MATRICULADOS DEL CURSO
		sql="UPDATE curso ";
		sql+= "SET cur_vacantes = cur_vacantes - 1,  cur_matriculados = cur_matriculados + 1 WHERE cur_id = ?;";
		jdbcTemplate.update(sql,bean.getCurso());
		
		//OBTENER FECHA
		sql="select mat_fecha from matricula where cur_id=? and alu_id=? ;";
		fecha=jdbcTemplate.queryForObject(sql, String.class, bean.getCurso(),bean.getAlumno());
		//REPORTE
		bean.setPrecio(precioMatricula);
		bean.setFecha(fecha);
		bean.setNota(null);
		return bean;
	}

}
