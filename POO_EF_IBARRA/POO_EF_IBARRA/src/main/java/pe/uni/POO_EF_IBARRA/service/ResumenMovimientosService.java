package pe.uni.POO_EF_IBARRA.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResumenMovimientosService {
    
    @Autowired
    private JdbcTemplate jdbctemplate;

    public List<Map<String, Object>> resumenSucursal() {
        String sql =  "select  cuenta.chr_sucucodigo as codigo, ";
        		sql+= "      (select vch_sucunombre from Sucursal where chr_sucucodigo=cuenta.chr_sucucodigo) as NOMBRE, ";
        		sql+= "      (select vch_monedescripcion from Moneda where chr_monecodigo= cuenta.chr_monecodigo) as MONEDA, ";
        		sql+= "      sum( case when( Movimiento.chr_cuencodigo =cuenta.chr_cuencodigo and tipoMovimiento.vch_tipoaccion = 'INGRESO') then dec_moviimporte else 0 end )as INGRESO, ";
        		sql+= "	  sum( case when(Movimiento.chr_cuencodigo =cuenta.chr_cuencodigo and tipoMovimiento.vch_tipoaccion = 'SALIDA') then dec_moviimporte else 0 end) as SALIDA ";
        		sql+= "FROM  ";
        		sql+= "    movimiento ";
        		sql+= "JOIN cuenta ON movimiento.chr_cuencodigo = cuenta.chr_cuencodigo ";
        		sql+= "JOIN tipoMovimiento ON movimiento.chr_tipocodigo = tipoMovimiento.chr_tipocodigo ";
        		sql+= "GROUP BY ";
        		sql+= "    cuenta.chr_sucucodigo, ";
        		sql+= "    cuenta.chr_monecodigo ";
        		sql+= "ORDER BY  ";
        		sql+= "    cuenta.chr_sucucodigo, ";
        		sql+= "    cuenta.chr_monecodigo;";

        return jdbctemplate.queryForList(sql);
    }

}
