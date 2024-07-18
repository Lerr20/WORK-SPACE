---obtener el resumen de ventas por tipo
SELECT 
    T.idtipo as IDTIPO,
    T.descripcion as DESCRIPCION,
    SUM(V.cantidad) AS CANTIDADTOTAL,
    SUM(V.total) AS VENTASTOTAL
FROM   VENTA V
JOIN PUBLICACION P ON V.idpublicacion = P.idpublicacion
JOIN TIPO T ON P.idtipo = T.idtipo
GROUP BY  T.idtipo,  T.descripcion;
 go


--obtener presumen de ventas por cada tipo de publicacion
SELECT 
    P.idpublicacion as IDTIPO,
    P.titulo as DESCRIPCION,
    SUM(V.cantidad) AS CANTIDADTOTAL,
    SUM(V.total) AS VENTASTOTAL
FROM   VENTA V
JOIN PUBLICACION P ON V.idpublicacion = P.idpublicacion
GROUP BY  P.idpublicacion,  P.titulo;
 go
 --VALIdar el idpublicacion
 select count(1) from publicacion where idpublicacion='LIB00005';
 go
 --obtener el porcentaje de descuento 
 SELECT porcentaje FROM PROMOCION WHERE 3 BETWEEN cantmin AND cantmax;
 go
 --calcular descuento
SELECT porcentaje FROM PROMOCION WHERE 3 >= cantmin AND 3 <= cantmax;
 go
 --obtener el igv
 select valor from control where parametro='IGV';
 go
 --obtener el precio de una id publicacion
select precio from PUBLICACION where idpublicacion='LIB00005';
 --obtener el stock
 select stock from PUBLICACION where idpublicacion='LIB00005';
 go
 --actualizar el stock
 update publicacion set stock=stock-1 where idpublicacion=?;
 go
 --obtenemos el numero de venta 
 select valor from control where parametro='VENTA';
 go
 --actualizar el numero de ventas en la tabla control
 update control set valor=valor+1 where parametro='VENTA';
 go
 --registrar una venta
 insert into venta (idventa,cliente,fecha,idempleado,idpublicacion,cantidad,precio,dcto,subtotal,impuesto,total)
  values(?,?,getdate(),?,?,?,?,?,?,?,?);
  go
  --obtener la fecha de una venta
  select fecha from venta where idventa=1;