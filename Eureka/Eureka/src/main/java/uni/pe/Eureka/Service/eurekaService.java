package uni.pe.Eureka.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uni.pe.Eureka.Dto.CuentaDto;
import uni.pe.Eureka.Dto.TransferenciaDto;
import uni.pe.Eureka.Dto.idtipoDto;
import uni.pe.Eureka.Dto.movimientoDto;

@Service
public class eurekaService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// METODO SIN DTO PARA SOLO MOSTRAR
	public List<Map<String, Object>> resumenMovimientoSucursal() {

		String sql;
		List<Map<String, Object>> lista;
		sql = " SELECT ";
		sql += "    cuenta.chr_sucucodigo AS CODIGO,";
		sql += "    sucursal.vch_sucunombre AS NOMBRE, ";
		sql += "    moneda.vch_monedescripcion AS MONEDA, ";
		sql += "    SUM(CASE WHEN TipoMovimiento.vch_tipoaccion = 'INGRESO' THEN Movimiento.dec_moviimporte ELSE 0 END) AS INGRESO,  ";
		sql += "    SUM(CASE WHEN  TipoMovimiento.vch_tipoaccion = 'SALIDA' THEN Movimiento.dec_moviimporte ELSE 0 END) AS SALIDA  ";
		sql += "FROM  Movimiento ";
		sql += "     JOIN cuenta ON cuenta.chr_cuencodigo = Movimiento.chr_cuencodigo  ";
		sql += "    JOIN sucursal ON sucursal.chr_sucucodigo = cuenta.chr_sucucodigo ";
		sql += "     JOIN Moneda ON Moneda.chr_monecodigo = cuenta.chr_monecodigo ";
		sql += "     JOIN TipoMovimiento ON TipoMovimiento.chr_tipocodigo = Movimiento.chr_tipocodigo ";
		sql += "GROUP BY  ";
		sql += "    cuenta.chr_sucucodigo, sucursal.vch_sucunombre, moneda.vch_monedescripcion  ";
		sql += "order by  ";
		sql += "    cuenta.chr_sucucodigo, ";
		sql += "    moneda.vch_monedescripcion desc;";
		lista = jdbcTemplate.queryForList(sql);
		return lista;
	}

	// METODO CON DTO PARA MOSTRAR DE ACUERDO A UN CODIGO ESPECIAL

	public List<movimientoDto> resumenMovimientosId(String codigo) {

		List<movimientoDto> lista = null;
		String sql;
		sql = " SELECT ";
		sql += "    cuenta.chr_sucucodigo AS CODIGO,";
		sql += "    sucursal.vch_sucunombre AS NOMBRE, ";
		sql += "    moneda.vch_monedescripcion AS MONEDA, ";
		sql += "    SUM(CASE WHEN TipoMovimiento.vch_tipoaccion = 'INGRESO' THEN Movimiento.dec_moviimporte ELSE 0 END) AS INGRESO,  ";
		sql += "    SUM(CASE WHEN  TipoMovimiento.vch_tipoaccion = 'SALIDA' THEN Movimiento.dec_moviimporte ELSE 0 END) AS SALIDA  ";
		sql += "FROM  Movimiento ";
		sql += "     JOIN cuenta ON cuenta.chr_cuencodigo = Movimiento.chr_cuencodigo  ";
		sql += "    JOIN sucursal ON sucursal.chr_sucucodigo = cuenta.chr_sucucodigo ";
		sql += "     JOIN Moneda ON Moneda.chr_monecodigo = cuenta.chr_monecodigo ";
		sql += "     JOIN TipoMovimiento ON TipoMovimiento.chr_tipocodigo = Movimiento.chr_tipocodigo";
		sql += " where cuenta.chr_sucucodigo= ? ";
		sql += "GROUP BY  ";
		sql += "    cuenta.chr_sucucodigo, sucursal.vch_sucunombre, moneda.vch_monedescripcion  ";
		sql += "order by  ";
		sql += "    cuenta.chr_sucucodigo, ";
		sql += "    moneda.vch_monedescripcion desc;";

		try {
			lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(movimientoDto.class), codigo);
		} catch (Exception e) {
			// ManejoDeExcepciones
		}

		return lista;
	}

	// METODO SIN DTO PARA CUENTAS ACTIVAS

	public List<Map<String, Object>> resumenCuentasActivas() {

		List<Map<String, Object>> lista;
		String sql = "";
		sql = "SELECT ";
		sql += "    cuenta.chr_sucucodigo AS IDSUCURSAL, ";
		sql += "    sucursal.vch_sucunombre AS NOMBRE, ";
		sql += "    SUM(CASE WHEN cuenta.vch_cuenestado = 'ACTIVO' AND cuenta.chr_monecodigo = '01' THEN 1 ELSE 0 END) AS SOLES, ";
		sql += "    SUM(CASE WHEN cuenta.vch_cuenestado = 'ACTIVO' AND cuenta.chr_monecodigo = '02' THEN 1 ELSE 0 END) AS DOLARES ";
		sql += "FROM ";
		sql += "    cuenta ";
		sql += "JOIN ";
		sql += "    sucursal ON sucursal.chr_sucucodigo = cuenta.chr_sucucodigo ";
		sql += "GROUP BY ";
		sql += "    cuenta.chr_sucucodigo,";
		sql += "    sucursal.vch_sucunombre ";
		sql += "ORDER BY ";
		sql += "    cuenta.chr_sucucodigo;";

		lista = jdbcTemplate.queryForList(sql);
		return lista;
	}

	// METODO CON DTO PARA CUENTAS ACTIVAS POR DETERMINADA SUCURSAL

	public List<CuentaDto> cuentasActivasPorSucursal(String codigo) {

		List<CuentaDto> lista = null;
		String sql;
		sql = " SELECT ";
		sql += "    cuenta.chr_sucucodigo AS IDSUCURSAL, ";
		sql += "    sucursal.vch_sucunombre AS NOMBRE, ";
		sql += "    SUM(CASE WHEN cuenta.vch_cuenestado = 'ACTIVO' AND cuenta.chr_monecodigo = '01' THEN 1 ELSE 0 END) AS SOLES, ";
		sql += "    SUM(CASE WHEN cuenta.vch_cuenestado = 'ACTIVO' AND cuenta.chr_monecodigo = '02' THEN 1 ELSE 0 END) AS DOLARES  ";
		sql += "FROM  ";
		sql += "    cuenta ";
		sql += "JOIN  ";
		sql += "    sucursal ON sucursal.chr_sucucodigo = cuenta.chr_sucucodigo ";
		sql += "	where  cuenta.chr_sucucodigo=? ";
		sql += "GROUP BY  ";
		sql += "    cuenta.chr_sucucodigo, ";
		sql += "    sucursal.vch_sucunombre  ";
		sql += "ORDER BY  ";
		sql += "    cuenta.chr_sucucodigo;";

		try {
			lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CuentaDto.class), codigo);
		} catch (Exception e) {
			// ManejoDeExcepciones
		}

		return lista;
	}

	// RESUMEN DE MOVIMIENOS POR TIPO DE ACCION

	public List<Map<String, Object>> resumenMovimientosPorAccion() {

		String sql;
		List<Map<String, Object>> lista;
		sql = "SELECT ";
		sql += "    TipoMovimiento.chr_tipocodigo AS IDTIPO, ";
		sql += "	TipoMovimiento.vch_tipodescripcion AS DESCRIPCION, ";
		sql += "	TipoMovimiento.vch_tipoaccion AS ACCION, ";
		sql += "	SUM(CASE WHEN Cuenta.chr_monecodigo = '01' THEN Movimiento.dec_moviimporte ELSE 0 END) AS SOLES, ";
		sql += "	SUM(CASE WHEN Cuenta.chr_monecodigo = '02' THEN Movimiento.dec_moviimporte ELSE 0 END) AS DOLARES ";
		sql += "FROM  TipoMovimiento ";
		sql += "JOIN  Movimiento ON TipoMovimiento.chr_tipocodigo = Movimiento.chr_tipocodigo ";
		sql += "JOIN  Cuenta ON Cuenta.chr_cuencodigo = Movimiento.chr_cuencodigo ";
		sql += "GROUP BY TipoMovimiento.chr_tipocodigo, TipoMovimiento.vch_tipodescripcion, TipoMovimiento.vch_tipoaccion ";
		sql += "ORDER BY TipoMovimiento.chr_tipocodigo, TipoMovimiento.vch_tipodescripcion;";

		lista = jdbcTemplate.queryForList(sql);
		return lista;
	}

	// METODO CON DTO PARA RESUMEN POR IDTIPO DE MOVIMIENTO
	public List<idtipoDto> resumenMovimientosPorAccionId(String codigo) {

		List<idtipoDto> lista = null;
		String sql;
		sql = " SELECT  ";
		sql += "    TipoMovimiento.chr_tipocodigo AS IDTIPO, ";
		sql += "	TipoMovimiento.vch_tipodescripcion AS DESCRIPCION, ";
		sql += "	TipoMovimiento.vch_tipoaccion AS ACCION, ";
		sql += "	SUM(CASE WHEN Cuenta.chr_monecodigo = '01' THEN Movimiento.dec_moviimporte ELSE 0 END) AS SOLES, ";
		sql += "	SUM(CASE WHEN Cuenta.chr_monecodigo = '02' THEN Movimiento.dec_moviimporte ELSE 0 END) AS DOLARES ";
		sql += "FROM  TipoMovimiento ";
		sql += "JOIN  Movimiento ON TipoMovimiento.chr_tipocodigo = Movimiento.chr_tipocodigo ";
		sql += "JOIN  Cuenta ON Cuenta.chr_cuencodigo = Movimiento.chr_cuencodigo ";
		sql += "where TipoMovimiento.chr_tipocodigo=? ";
		sql += "GROUP BY TipoMovimiento.chr_tipocodigo, TipoMovimiento.vch_tipodescripcion, TipoMovimiento.vch_tipoaccion ";
		sql += "ORDER BY TipoMovimiento.chr_tipocodigo, TipoMovimiento.vch_tipodescripcion; ";

		try {
			lista = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(idtipoDto.class), codigo);
		} catch (Exception e) {
			// ManejoDeExcepciones
		}

		return lista;

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public TransferenciaDto realizarTransferencia(TransferenciaDto bean) {
		// VARIABLES
		String sql;
		int cont,numOrigen,numDestino;
		double saldoOrigen,saldoDestino;

		// VERIFICAR CUENTA ORIGEN
		sql = " select count(1)  from cuenta  where chr_cuencodigo=? ;";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCuentaOrigen());
		if (cont == 0) {
			throw new RuntimeException(" la cuenta origen no existe");
		}
		// VERIFICAR CUENTA DESTINO
		sql = " select count(1)  from cuenta  where chr_cuencodigo=? ;";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCuentaDestino());
		if (cont == 0) {
			throw new RuntimeException(" la cuenta destino no existe");
		}

		// VERIFICAR CUENTA ORIGEN ESTE ACTIVA
		sql = " select count(1) from cuenta where  chr_cuencodigo=? and vch_cuenestado='ACTIVO'  ";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCuentaOrigen());
		if (cont == 0) {
			throw new RuntimeException(" la cuenta origen no esta activa");
		}

		// VERIFICAR CUENTA ORIGEN ESTE ACTIVA
		sql = " select count(1) from cuenta where  chr_cuencodigo=? and vch_cuenestado='ACTIVO'  ";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCuentaDestino());
		if (cont == 0) {
			throw new RuntimeException(" la cuenta destino no esta activa");
		}

		// VALIDAR QUE LAS CUENTAS SEAN DE LA MISMA MONEDA
		sql = "SELECT CASE ";
		sql += "    WHEN (SELECT chr_monecodigo FROM cuenta WHERE chr_cuencodigo = ?) = ";
		sql += "          (SELECT chr_monecodigo FROM cuenta WHERE chr_cuencodigo = ?) ";
		sql += "           THEN 1 ELSE 0 END ; ";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getCuentaOrigen(), bean.getCuentaDestino());
		if (cont == 0) {
			throw new RuntimeException(" la cuentas no estan en la misma moneda");
		}
		// VERIFICAR EMPELADO
		verificarEmpleado(bean.getEmpleado());

		// VERIFICAR IMPORTE POSITIVO
		if (bean.getImporte() <= 0) {
			throw new RuntimeException(" el importe debe ser positivo");
		}
		
		//VERIFICAR QUE HAYA SALDO SUFICIENTE PARA LA TRANSFERENCIA
		
		sql=" select dec_cuensaldo from cuenta where chr_cuencodigo=?;";
		saldoOrigen=jdbcTemplate.queryForObject(sql,Double.class, bean.getCuentaOrigen());
		if (saldoOrigen< bean.getImporte()) {
			throw new RuntimeException(" no hay saldo suficiente en la cuenta ");
		}
		
		//TRANSFERENCIA 
		//ACTUALIZAR SALDO CUENTA ORIGEN Y SU NUMERO DE MOVIMIENTOS
		sql="update Cuenta set dec_cuensaldo=dec_cuensaldo-? ,int_cuencontmov=int_cuencontmov+1  where chr_cuencodigo= ?;";
		jdbcTemplate.update(sql, bean.getImporte(),bean.getCuentaOrigen());
		//ACTUALIZAR SALDO DE LA CUENTA DESTINO Y SU NUMERO DE MOVIMENTOS
		sql="update Cuenta set dec_cuensaldo=dec_cuensaldo+? ,int_cuencontmov=int_cuencontmov+1  where chr_cuencodigo= ?;";
		jdbcTemplate.update(sql, bean.getImporte(),bean.getCuentaDestino());
		
		//OBTENER EL NUMERO DE MOVIMIENTO DE CADA CUENTA
		//PARA LA CUENTA ORIGEN
		sql=" select int_cuencontmov from Cuenta where chr_cuencodigo= ? ;";
		numOrigen=jdbcTemplate.queryForObject(sql,Integer.class, bean.getCuentaOrigen());
		//PARA LA CUENTA DESTINO
		sql=" select int_cuencontmov from Cuenta where chr_cuencodigo= ? ;";
		numDestino=jdbcTemplate.queryForObject(sql,Integer.class, bean.getCuentaDestino());
		
		//REGISTRAR MOVIMIENTO 
		//PARA LA CUENTA ORIGEN
		sql="insert into Movimiento (chr_cuencodigo,int_movinumero,dtt_movifecha,chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia) ";
		sql+= "values (?,?,getdate(),?,'009',?,?);";
		Object []datos= {bean.getCuentaOrigen(),numOrigen,bean.getEmpleado(),bean.getImporte(),bean.getCuentaDestino()};
		jdbcTemplate.update(sql,datos);
		//PARA LA CUENTA DESTINO
		sql="insert into Movimiento (chr_cuencodigo,int_movinumero,dtt_movifecha,chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia) ";
		sql+= "values (?,?,getdate(),?,'008',?,?);";
		Object []datos1= {bean.getCuentaDestino(),numDestino,bean.getEmpleado(),bean.getImporte(),bean.getCuentaOrigen()};
		jdbcTemplate.update(sql,datos1);
		
		//OBTENER SALDOS ACTUALIZADOS
		//SALDO DE CUENTA ORIGEN 
		sql="select dec_cuensaldo from cuenta where chr_cuencodigo=?;";
		saldoOrigen=jdbcTemplate.queryForObject(sql, Double.class, bean.getCuentaOrigen());
		//SALDO DE CUENTA DESTNO 
		sql="select dec_cuensaldo from cuenta where chr_cuencodigo=?;";
		saldoDestino=jdbcTemplate.queryForObject(sql, Double.class, bean.getCuentaDestino());
		
		//REPORTE
		bean.setSaldoOrigen(saldoOrigen);
		bean.setSaldoDestino(saldoDestino);
		return bean;
	}

	@Transactional(propagation = Propagation.NESTED)
	private void verificarEmpleado(String empleado) {
		String sql = "select count(1) cont from Asignado ";
		sql += "where chr_emplcodigo=? and dtt_asigfechabaja is null";
		int conta = jdbcTemplate.queryForObject(sql, Integer.class, empleado);
		if (conta != 1) {
			throw new RuntimeException("Empleado no existe.");
		}
	}
}