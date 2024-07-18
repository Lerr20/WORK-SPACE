package uni.pe.BookStore.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uni.pe.BookStore.Dto.VentaDto;

@Service
public class bookstoreService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// MOSTRAR VENTAS POR TIPO (LIB,REV,SEP)
	public List<Map<String, Object>> resumenPorTipo() {

		// VARIABLES
		String sql;
		List<Map<String, Object>> lista;

		// PROCESO
		sql = " SELECT  ";
		sql += "    T.idtipo as IDTIPO, ";
		sql += "    T.descripcion as DESCRIPCION, ";
		sql += "    SUM(V.cantidad) AS CANTIDADTOTAL, ";
		sql += "    SUM(V.total) AS VENTASTOTAL ";
		sql += "FROM   VENTA V ";
		sql += "JOIN PUBLICACION P ON V.idpublicacion = P.idpublicacion ";
		sql += "JOIN TIPO T ON P.idtipo = T.idtipo ";
		sql += "GROUP BY  T.idtipo,  T.descripcion; ";

		lista = jdbcTemplate.queryForList(sql);
		return lista;
	}

	// VENTAS POR TIPO DE PUBLICACION
	public List<Map<String, Object>> resumenPorTipoPublicacion() {

		// VARIABLES
		String sql;
		List<Map<String, Object>> lista;

		// PROCESO
		// SI DE ALGUN TIPO DE PUBLICACION AUN NO SE HA VENDIDO NADA NO SE MOSTRARA NADA
		sql = " SELECT  ";
		sql += "    P.idpublicacion as IDTIPO, ";
		sql += "    P.titulo as DESCRIPCION, ";
		sql += "    SUM(V.cantidad) AS CANTIDADTOTAL, ";
		sql += "    SUM(V.total) AS VENTASTOTAL ";
		sql += "FROM   VENTA V ";
		sql += "JOIN PUBLICACION P ON V.idpublicacion = P.idpublicacion ";
		sql += "GROUP BY  P.idpublicacion,  P.titulo;";

		lista = jdbcTemplate.queryForList(sql);
		return lista;
	}

	// REGISTRAR UNA VENTA
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public VentaDto registrarVenta(VentaDto bean) {

		// VARIABLES
		String sql, fecha,porcentaje;
		int cont, stock, venta;
		double descuento = 0.0, igv = 0.0, precio = 0.0, total, impuesto, subtotal, porcentajedesc;

		// VALIDAR EL IDPUBLICACION
		sql = " select count(1) from publicacion where idpublicacion=? ;";
		cont = jdbcTemplate.queryForObject(sql, Integer.class, bean.getIdpublicacion());
		if (cont == 0) {
			throw new RuntimeException(" la idpublicacion no existe .");
		}

		// CALCULAR DESCUENTO
		// OBTENER EL PRECIO DE UNA UNIDAD
		sql = " select precio from PUBLICACION where idpublicacion=?;";
		precio = jdbcTemplate.queryForObject(sql, Double.class, bean.getIdpublicacion());
		// OBTENEMOS EL PORCENTAJE DE DESCUENTO
		//vVERIFICAR LA CANTIDAD
		int cantidad = bean.getCantidad();
		if (cantidad <= 0) {
			throw new RuntimeException(" la cantidad debe ser mayor a 0.");
		}
		sql = "SELECT porcentaje FROM PROMOCION WHERE ? >= cantmin AND ? <= cantmax;";
		porcentaje= jdbcTemplate.queryForObject(sql, String.class,cantidad,cantidad);
		porcentajedesc=Double.parseDouble(porcentaje);
		descuento = porcentajedesc * precio;
		// CALCULAR VENTA
		// OBTENER EL IGV
		sql = "select valor from control where parametro='IGV';";
		igv = jdbcTemplate.queryForObject(sql, Double.class);
		// CALCULAR TOTAL
		total = (precio - descuento) * bean.getCantidad();
		// CALCULAR IMPUESTO
		impuesto = igv * total;
		// CALCULAR SUBTOTAL
		subtotal = (total - impuesto) ;
		// VALIDAR EL STOCK
		sql = "select stock from PUBLICACION where idpublicacion=? ;";
		stock = jdbcTemplate.queryForObject(sql, Integer.class, bean.getIdpublicacion());
		if (stock == 0) {
			throw new RuntimeException(" no hay ejemplares para vender.");
		}
		// REALIZAR LA VENTA

		// ACTUALIZAR EL STOCK
		sql = "update publicacion set stock=stock-1  where idpublicacion=? ;";
		jdbcTemplate.update(sql, bean.getIdpublicacion());

		// OBTENEMOS EL NUMERO DE VENTA DE LA TABLA CONTROL,ESTO NOS AYUDARA A GENERAR
		// EL IDVENTA CORRECTO
		// OBTENER Y ACTUALIZAR EL NÚMERO DE VENTA EN LA TABLA CONTROL
	    sql = "SELECT valor FROM control WHERE parametro = 'VENTA' ";
	    venta = jdbcTemplate.queryForObject(sql, Integer.class);
	    venta=venta+1;
	    jdbcTemplate.update("UPDATE control SET valor = valor + 1 WHERE parametro = 'VENTA'");

		// REGISTRAR LA VENTA
		sql = "insert into venta (idventa,cliente,fecha,idempleado,idpublicacion,cantidad,precio,dcto,subtotal,impuesto,total) ";
		sql += "  values(?,?,getdate(),?,?,?,?,?,?,?,?); ";
		Object[] datos = { venta, bean.getCliente(), bean.getEmpleado(), bean.getIdpublicacion(), bean.getCantidad(),
				precio, descuento, subtotal, impuesto, total };
		jdbcTemplate.update(sql, datos);

		// OBTENER LA FECHA DE LA VENTA
		sql = " select fecha from venta where idventa=? ;";
		fecha = jdbcTemplate.queryForObject(sql, String.class, venta);
		
		// REPORTE
		bean.setDescuento(descuento);
		bean.setImpuesto(impuesto);
		bean.setPrecio(precio);
		bean.setSubtotal(subtotal);
		bean.setTotal(subtotal);
		bean.setFecha(fecha);

		return bean;
	}

}
