--cuentas activas
select Cuenta.chr_sucucodigo as IDSUCURSAL, 
				(select vch_sucunombre from Sucursal where chr_sucucodigo = Cuenta.chr_sucucodigo) as NOMBRE, 
				sum(case when (Cuenta.chr_monecodigo = 1 and cuenta.vch_cuenestado='ACTIVO')  then 1 else 0 end) as SOLES, 
				sum(case when (Cuenta.chr_monecodigo = 2 and cuenta.vch_cuenestado='ACTIVO' )then 1 else 0 end) as DOLARES 
				from cuenta 
				group by chr_sucucodigo;
go

--resumen movimientos
SELECT cuenta.chr_sucucodigo AS CODIGO,
    (SELECT vch_sucunombre FROM Sucursal WHERE chr_sucucodigo = Cuenta.chr_sucucodigo) AS NOMBRE,
    Moneda.vch_monedescripcion AS MONEDA,
    SUM(CASE WHEN TipoMovimiento.vch_tipoaccion = 'INGRESO' THEN Movimiento.dec_moviimporte ELSE 0 END) AS INGRESO,
    SUM(CASE WHEN TipoMovimiento.vch_tipoaccion = 'SALIDA' THEN Movimiento.dec_moviimporte ELSE 0 END) AS SALIDA
FROM
    Movimiento
JOIN Cuenta ON Movimiento.chr_cuencodigo = Cuenta.chr_cuencodigo
JOIN Moneda ON Cuenta.chr_monecodigo = Moneda.chr_monecodigo
JOIN TipoMovimiento ON Movimiento.chr_tipocodigo = TipoMovimiento.chr_tipocodigo
GROUP BY
    Cuenta.chr_sucucodigo,
    Moneda.vch_monedescripcion
ORDER BY
    Cuenta.chr_sucucodigo,
    Moneda.vch_monedescripcion;
go
--resumen movimientos v.2
 select  cuenta.chr_sucucodigo as codigo,
      (select vch_sucunombre from Sucursal where chr_sucucodigo=cuenta.chr_sucucodigo) as NOMBRE,
      (select vch_monedescripcion from Moneda where chr_monecodigo= cuenta.chr_monecodigo) as MONEDA,
      sum( case when( Movimiento.chr_cuencodigo =cuenta.chr_cuencodigo and tipoMovimiento.vch_tipoaccion = 'INGRESO') then dec_moviimporte else 0 end )as INGRESO,
	  sum( case when(Movimiento.chr_cuencodigo =cuenta.chr_cuencodigo and tipoMovimiento.vch_tipoaccion = 'SALIDA') then dec_moviimporte else 0 end) as SALIDA
FROM
    movimiento
JOIN cuenta ON movimiento.chr_cuencodigo = cuenta.chr_cuencodigo
JOIN tipoMovimiento ON movimiento.chr_tipocodigo = tipoMovimiento.chr_tipocodigo
GROUP BY
    cuenta.chr_sucucodigo,
    cuenta.chr_monecodigo
ORDER BY
    cuenta.chr_sucucodigo,
    cuenta.chr_monecodigo;
go

