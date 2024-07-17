--para resumir cuentas por sucursal
SELECT 
    cuenta.chr_sucucodigo AS CODIGO,
    sucursal.vch_sucunombre AS NOMBRE,
    moneda.vch_monedescripcion AS MONEDA,
    SUM(CASE WHEN TipoMovimiento.vch_tipoaccion = 'INGRESO' THEN Movimiento.dec_moviimporte ELSE 0 END) AS INGRESO,
    SUM(CASE WHEN  TipoMovimiento.vch_tipoaccion = 'SALIDA' THEN Movimiento.dec_moviimporte ELSE 0 END) AS SALIDA
FROM 
    Movimiento
     JOIN cuenta ON cuenta.chr_cuencodigo = Movimiento.chr_cuencodigo
    JOIN sucursal ON sucursal.chr_sucucodigo = cuenta.chr_sucucodigo
     JOIN Moneda ON Moneda.chr_monecodigo = cuenta.chr_monecodigo
     JOIN TipoMovimiento ON TipoMovimiento.chr_tipocodigo = Movimiento.chr_tipocodigo
	 --where cuenta.chr_sucucodigo=?
GROUP BY 
    cuenta.chr_sucucodigo, sucursal.vch_sucunombre, moneda.vch_monedescripcion
	
order by 
    cuenta.chr_sucucodigo,
    moneda.vch_monedescripcion desc;
go

--resumir cuentas activas
SELECT 
    cuenta.chr_sucucodigo AS IDSUCURSAL,
    sucursal.vch_sucunombre AS NOMBRE,
    SUM(CASE WHEN cuenta.vch_cuenestado = 'ACTIVO' AND cuenta.chr_monecodigo = '01' THEN 1 ELSE 0 END) AS SOLES,
    SUM(CASE WHEN cuenta.vch_cuenestado = 'ACTIVO' AND cuenta.chr_monecodigo = '02' THEN 1 ELSE 0 END) AS DOLARES
FROM 
    cuenta 
JOIN 
    sucursal ON sucursal.chr_sucucodigo = cuenta.chr_sucucodigo
	--where  cuenta.chr_sucucodigo=001
GROUP BY 
    cuenta.chr_sucucodigo,
    sucursal.vch_sucunombre
ORDER BY 
    cuenta.chr_sucucodigo;
go

--resumir movimientos por sucursal segun accion
SELECT 
    TipoMovimiento.chr_tipocodigo AS IDTIPO,
	TipoMovimiento.vch_tipodescripcion AS DESCRIPCION,
	TipoMovimiento.vch_tipoaccion AS ACCION,
	SUM(CASE WHEN Cuenta.chr_monecodigo = '01' THEN Movimiento.dec_moviimporte ELSE 0 END) AS SOLES,
	SUM(CASE WHEN Cuenta.chr_monecodigo = '02' THEN Movimiento.dec_moviimporte ELSE 0 END) AS DOLARES
FROM  TipoMovimiento
JOIN  Movimiento ON TipoMovimiento.chr_tipocodigo = Movimiento.chr_tipocodigo
JOIN  Cuenta ON Cuenta.chr_cuencodigo = Movimiento.chr_cuencodigo
--where TipoMovimiento.chr_tipocodigo=?
GROUP BY TipoMovimiento.chr_tipocodigo, TipoMovimiento.vch_tipodescripcion, TipoMovimiento.vch_tipoaccion
ORDER BY TipoMovimiento.chr_tipocodigo, TipoMovimiento.vch_tipodescripcion;
go
--resumir movimientos por sucursal segun accion v.2
select Movimiento.chr_tipocodigo as IDTIPO, 
	(select vch_tipodescripcion from TipoMovimiento where chr_tipocodigo = Movimiento.chr_tipocodigo) as DESCRIPCION, 
	(select vch_tipoaccion from TipoMovimiento where chr_tipocodigo = Movimiento.chr_tipocodigo) as ACCION, 
	sum(case when (Movimiento.chr_cuencodigo = Cuenta.chr_cuencodigo and Cuenta.chr_monecodigo = 1) then Movimiento.dec_moviimporte else 0 end) as SOLES, 
	sum(case when (Movimiento.chr_cuencodigo = Cuenta.chr_cuencodigo and Cuenta.chr_monecodigo = 2 ) then Movimiento.dec_moviimporte else 0 end) as DOLARES 
	from Movimiento, Cuenta 
	group by chr_tipocodigo;
go

--verificar empleado
select count(1) from Asignado where chr_emplcodigo ='0001' and dtt_asigfechabaja is NULL;
go
--verificar saldo de una cuenta
select dec_cuensaldo from cuenta  with (UPDLOCK) where chr_cuencodigo='00100001';
go
--validar q una cuenta exista 
select count(1)  from cuenta  where chr_cuencodigo='00100002' ;
go
--verificar cuenta este activa
select count(1) from cuenta where  chr_cuencodigo='00300001' and vch_cuenestado='ACTIVO';
--verificar q dos cuentas sean de la misma moneda
SELECT CASE
    WHEN (SELECT chr_monecodigo FROM cuenta WHERE chr_cuencodigo = '00100001') =
          (SELECT chr_monecodigo FROM cuenta WHERE chr_cuencodigo = '00100002')
           THEN 1 ELSE 0 END ;
go

--obtener el saldo de una cuenta
select dec_cuensaldo from cuenta where chr_cuencodigo='00100001';
go
--actualizar saldo de una cuenta y su numero de movimientos
  --quitando de el saldo
update Cuenta set dec_cuensaldo=dec_cuensaldo-? ,int_cuencontmov=int_cuencontmov+1  where chr_cuencodigo= ?;
go
 --aumentando  el saldo
update Cuenta set dec_cuensaldo=dec_cuensaldo+? ,int_cuencontmov=int_cuencontmov+1  where chr_cuencodigo= ?;
go
--insertar un nuevo movimiento
insert into Movimiento (chr_cuencodigo,int_movinumero,dtt_movifecha,chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia) 
		values (?,?,getdate(),?,'008',?,?);
go

--obtener la cantidad de movimientos de una cuenta 
select int_cuencontmov from Cuenta where chr_cuencodigo= ? ;
go

