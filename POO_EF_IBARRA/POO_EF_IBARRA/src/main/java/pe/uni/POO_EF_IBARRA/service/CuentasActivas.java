package pe.uni.POO_EF_IBARRA.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CuentasActivas {

	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public List<Map<String,Object>> resumenActivas() {
		String sql = "select Cuenta.chr_sucucodigo as IDSUCURSAL, ";
				sql += "(select vch_sucunombre from Sucursal where chr_sucucodigo = Cuenta.chr_sucucodigo) as NOMBRE, ";
				sql += "sum(case when (Cuenta.chr_monecodigo = 1  and cuenta.vch_cuenestado='ACTIVO' )then 1 else 0 end) as SOLES, ";
				sql += "sum(case when (Cuenta.chr_monecodigo = 2  and cuenta.vch_cuenestado='ACTIVO')then 1 else 0 end) as DOLARES ";
				sql +=  "from cuenta ";
				sql += "group by chr_sucucodigo";
				
				
		return jdbctemplate.queryForList(sql);
	}
}
