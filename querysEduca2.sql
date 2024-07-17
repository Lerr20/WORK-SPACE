
--informacion de un alumno en determinado curso
SELECT 
    alumno.alu_nombre AS Alumno,
    curso.cur_nombre AS Curso,
    matricula.mat_tipo AS Tipo,
    matricula.mat_precio AS Precio,
    matricula.mat_cuotas AS Cuotas,
    matricula.mat_nota AS Nota
FROM 
    matricula
JOIN 
    alumno ON matricula.alu_id = alumno.alu_id
JOIN 
    curso ON matricula.cur_id = curso.cur_id
WHERE 
    alumno.alu_id = '3'AND curso.cur_id = '1';
go

--informacion de las cuotas de un alumno en determinado curso 
SELECT 
    pago.pag_cuota AS NCouta,
    pago.pag_fecha AS Fecha,
    pago.pag_importe AS Importe
FROM 
    matricula
JOIN 
    pago ON matricula.cur_id  = pago.cur_id and matricula.alu_id = pago.alu_id
WHERE 
    matricula.alu_id = '3' ;
go
--validar si un alumno esta matriculado en determinado curso

select count(1) from matricula where cur_id=1 and alu_id=3;
go

--OBTENER EL NUMERO DE cuotas totales
select mat_cuotas from matricula where cur_id=1 and alu_id=3;
go
--obtener el numero de la ultima cuota pagada
SELECT count(1) pag_cuota 
FROM pago 
WHERE cur_id = 1 AND alu_id = 3 
ORDER BY pag_cuota desc;
--obtener todo lo que pago hasta el momento
SELECT SUM(pag_importe) AS TotalPagado FROM pago WHERE cur_id = 2 AND alu_id = 3;
go
--obtener el costo total 
select mat_precio from matricula WHERE cur_id = 2 AND alu_id = 3;
--registrar un pago 
insert into pago (cur_id,alu_id,pag_cuota,emp_id,pag_fecha,pag_importe) 
    values(?,?,?,?getdate(),?);
	go
-- seleccionar la informacion de un alumno y poner en nota pendiente si aun no tiene nota 
SELECT 
    alumno.alu_nombre AS Alumno,
    curso.cur_nombre AS Curso,
	curso.cur_profesor AS Profesor,
	matricula.mat_fecha AS Fecha,
    matricula.mat_tipo AS Tipo,
    matricula.mat_precio AS Precio,
    matricula.mat_cuotas AS Cuotas,
   CASE WHEN matricula.mat_nota IS NULL THEN 'pendiente' ELSE CAST(matricula.mat_nota AS VARCHAR(50)) END AS Nota  
FROM 
    matricula
JOIN 
    alumno ON matricula.alu_id = alumno.alu_id
JOIN 
    curso ON matricula.cur_id = curso.cur_id
WHERE 
    alumno.alu_id = '3' and curso.cur_id = '1';
go

--validar q el curso exista
select count(1) from curso where cur_id=1;
go
--obtener numero de vacantes de un curso
select cur_vacantes from curso where cur_id=1;
go
--validar si el alumno existe
select count(1) from alumno where alu_id=1;
go
--obtener si el alumno ya esta matriculado en el curso que desea 
select count(1) from matricula where cur_id=1 and alu_id=3;
go
--obtenemos el precio de un curso
select cur_precio from curso where cur_id=3;
go
--registrar una nueva matricula en la tabla matricula
insert into matricula (cur_id,alu_id,emp_id,mat_tipo,mat_fecha,mat_precio,mat_cuotas,mat_nota)
 values(?,?,?,?,getdate(),?,?,null);
go
--actualizar vacantes y metriculados en el curso
UPDATE curso
SET cur_vacantes = cur_vacantes - 1,  cur_matriculados = cur_matriculados + 1 WHERE cur_id = 1;
go

--obtener la fecha de una matricula
select mat_fecha from matricula where cur_id=1 and alu_id=3;
go
--actualizar algun valor de la matricula
SET mat_nota = null-- o cualquier otro valor que desees asignar
WHERE alu_id = '7' AND cur_id = '2'
go

--borrar una matricula
delete from matricula where alu_id = '7' AND cur_id = '2';