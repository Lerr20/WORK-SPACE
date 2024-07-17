package uni.pe.edutec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uni.pe.edutec.Dto.MatriculaDto;
import uni.pe.edutec.Dto.PagoCursoDto;
import uni.pe.edutec.Dto.PagoTotalDto;
import uni.pe.edutec.Dto.ResumenCursosDto;

@Service
public class EdutecService {
   
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	 public List<ResumenCursosDto> resumirCursosProgramados (String ciclo){
		 //VARIABLES
		 String sql;
		 List<ResumenCursosDto> lista = null;
		 sql="SELECT ";
		 sql+= "    c.IdCurso AS IDCURSO, ";
		 sql+= "    c.NomCurso AS NOMBRE, ";
		 sql+= "    COUNT(cp.IdCursoProg) AS SECCIONES, ";
		 sql+= "    SUM(cp.IdCursoProg) AS PROGRAMADOS, ";
		 sql+= "    SUM(cp.Matriculados) AS MATRICULADOS, ";
		 sql+= "    SUM(cp.Matriculados * t.PrecioVenta) AS INGRESOS ";
		 sql+= "FROM ";
		 sql+= "    Curso c ";
		 sql+= "JOIN ";
		 sql+= "    CursoProgramado cp ON c.IdCurso = cp.IdCurso ";
		 sql+= "JOIN ";
		 sql+= "    Tarifa t ON c.IdTarifa = t.IdTarifa ";
		 sql+= "WHERE ";
		 sql+= "    cp.IdCiclo = ? ";
		 sql+= "GROUP BY ";
		 sql+= "    c.IdCurso, c.NomCurso; ";
		 
		 try {
				lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ResumenCursosDto.class), ciclo);
			} catch (Exception e) {
				// ManejoDeExcepciones
			}
		 
		 return lista;
	 }
	 
	 //DETERMINAR EL PAGO DE UN PROFESOR
	 public List<PagoCursoDto> resumirPagosProfesor (String ciclo,String idprofesor){
		 //VARIABLES
		 String sql;
		 List<PagoCursoDto>lista = null;
		 //PROCESO
		 sql="SELECT ";
		 sql+= "    c.IdCurso AS IDCURSO, ";
		 sql+= "    c.NomCurso AS NOMBRE, ";
		 sql+= "    t.Horas AS HORAS, ";
		 sql+= "    t.PagoHora AS PAGOHORAS, ";
		 sql+= "    (t.Horas * t.PagoHora) AS TOTAL ";
		 sql+= "FROM ";
		 sql+= "    Curso c ";
		 sql+= "JOIN ";
		 sql+= "    CursoProgramado cp ON c.IdCurso = cp.IdCurso ";
		 sql+= "JOIN ";
		 sql+="    Tarifa t ON c.IdTarifa = t.IdTarifa ";
		 sql+= "WHERE ";
		 sql+= "    cp.IdCiclo = ? AND cp.IdProfesor = ? ; ";
		 
		 
		 try {
				lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PagoCursoDto.class), ciclo,idprofesor);
			} catch (Exception e) {
				// ManejoDeExcepciones
			}
		 
		 return lista;
	 }

	 //PAGO  TOTAL DE UN PROFESOR
	 public List<PagoTotalDto> pagoProfesor (String ciclo,String idprofesor){
		 //VARIABLES
		 String sql;
		 List<PagoTotalDto>lista = null;
		 //PROCESO
		 sql="SELECT ";
		 sql+= "    p.NomProfesor + ' ' + p.ApeProfesor AS Profesor, ";
		 sql+= "    cp.IdCiclo AS Ciclo, ";
		 sql+= "    COUNT(cp.IdCursoProg) AS Secciones, ";
		 sql+= "    SUM(t.Horas * t.PagoHora) AS PagoTotal ";
		 sql+= "FROM Profesor p ";
		 sql+= "JOIN CursoProgramado cp ON p.IdProfesor = cp.IdProfesor ";
		 sql+= "JOIN Curso c ON cp.IdCurso = c.IdCurso ";
		 sql+= "JOIN Tarifa t ON c.IdTarifa = t.IdTarifa ";
		 sql+= "WHERE cp.IdCiclo = ? AND p.IdProfesor = ? ";
		 sql+= "GROUP BY p.IdProfesor, p.NomProfesor, p.ApeProfesor, cp.IdCiclo; ";
		
		 
		 try {
				lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PagoTotalDto.class), ciclo,idprofesor);
			} catch (Exception e) {
				// ManejoDeExcepciones
			}
		 
		 return lista;
	 }
	 
	 //REGISTRAR UN MATRICULA
	 @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	 public MatriculaDto registrarMatricula(MatriculaDto bean) {
		 
		 //VARIABLES
		 String sql,idcurso,cicloactual,fecha;
		 int cont,vacantes;
		 
		 //PROCESO
		 //VALIDAR QUE EL ALUMNO EXISTA
		 sql="select count(1) from alumno where IdAlumno=? ;";
		 cont=jdbcTemplate.queryForObject(sql,Integer.class,bean.getAlumno() );
		 if (cont == 0) {
				throw new RuntimeException(" el alumno que desea matricular no existe en nuestra base de datos .");
			}
		 //VALIDAR QUE EL CURSO PROGRAMADO ESTE ACTIVO EN ESE CICLO
		 sql="SELECT count(1) from CursoProgramado where IdCiclo=? and IdCursoProg=? and Activo=1;";
		 cont=jdbcTemplate.queryForObject(sql, Integer.class, bean.getCiclo(),bean.getCursoprog());
		 if (cont == 0) {
				throw new RuntimeException(" el curso no esta programado en ese ciclo .");
			}
		 //OBTENER EL ID DEL CURSO
		 sql=" select IdCurso from CursoProgramado where IdCursoProg= ?;";
		 idcurso=jdbcTemplate.queryForObject(sql, String.class, bean.getCursoprog());
		 //VALIDAR QUE AUN NO ESTE MATRICULADO EN ESE CURSO
		 sql=" select count(1)  ";
		 sql+= "from matricula join CursoProgramado on matricula.IdCursoProg=CursoProgramado.IdCursoProg ";
		 sql+= "where IdAlumno=? and IdCurso= ? and IdCiclo=?; ";
		 cont=jdbcTemplate.queryForObject(sql, Integer.class, bean.getAlumno(),idcurso,bean.getCiclo());
		 
		 if (cont == 1) {
				throw new RuntimeException(" el alumno ya esta matriculado en alguna seccion de ese curso en ese ciclo .");
			}
		 //VALIDAR QUE AUN EXISTAN VACANTES EN ESE CURSO PROGRAMADO
		 sql="select  vacantes from CursoProgramado where IdCursoProg=?;";
		 vacantes=jdbcTemplate.queryForObject(sql, Integer.class, bean.getCursoprog());
		 if (vacantes == 0) {
				throw new RuntimeException(" no existen vacantes en ese curso programado .");
			}
		 
		 //OBTENIENDO EL CICLO ACUTUAL
		 sql="select idciclo from ciclo where FecInicio<=getdate() and FecTermino>=getdate();";
		 cicloactual=jdbcTemplate.queryForObject(sql,String.class);
		 //VALIDAR EL CICLO EN QUE SE QUIERE MATRICULAR(ASUMEINSO QUE SE PUEDA MATRICULAR EN EL CICLO ACTUAL Y CICLO SUPERIORES
		 //PERO NO INFERIORES)
		 sql="select count(1) from ciclo where IdCiclo=? and IdCiclo>=?;";
		 cont=jdbcTemplate.queryForObject(sql, Integer.class,bean.getCiclo(),cicloactual );
		 if (cont == 0) {
				throw new RuntimeException(" el ciclo al que lo quiere matricular ya paso .");
			}
		 
		 //REGISTRAR MATRICULA
		 sql="insert into matricula (IdCursoProg,IdAlumno,FecMatricula,ExaParcial,ExaFinal,Promedio,Subsanacion,ExaSub) ";
		 sql+= "   values (?,?,getdate(),null,null,null,0,null);";
		 Object []datos= {bean.getCursoprog(),bean.getAlumno()};
		 jdbcTemplate.update(sql,datos);
		 //ACTUALIZAMOS VACANTES Y MATRICULADOS
		 sql=" update CursoProgramado set Vacantes=Vacantes-1,Matriculados=Matriculados+1 where IdCursoProg=?;";
		 jdbcTemplate.update(sql,bean.getCursoprog());
		 //OBTENER LA FECHA DE LA MATRICULA
		 sql=" select fecmatricula from matricula where IdAlumno= ? and IdCursoProg=? ;";
		 fecha=jdbcTemplate.queryForObject(sql, String.class, bean.getAlumno(),bean.getCursoprog());
		 //REPORTE
		 bean.setFecha(fecha);
		 return bean;
	 }
	 
}
