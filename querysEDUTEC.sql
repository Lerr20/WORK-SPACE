--resumen de cursos programados en determinad o ciclo
SELECT
    c.IdCurso AS IDCURSO,
    c.NomCurso AS NOMBRE,
    COUNT(cp.IdCursoProg) AS SECCIONES,
    SUM(cp.IdCursoProg) AS PROGRAMADOS,
    SUM(cp.Matriculados) AS MATRICULADOS,
    SUM(cp.Matriculados * t.PrecioVenta) AS INGRESOS
FROM
    Curso c
JOIN
    CursoProgramado cp ON c.IdCurso = cp.IdCurso
JOIN
    Tarifa t ON c.IdTarifa = t.IdTarifa
WHERE
    cp.IdCiclo = '1987-02'
GROUP BY
    c.IdCurso, c.NomCurso;
	go
--determinar el pago de un profesor en determinado ciclo

SELECT
    c.IdCurso AS IDCURSO,
    c.NomCurso AS NOMBRE,
    t.Horas AS HORAS,
    t.PagoHora AS PAGOHORAS,
    (t.Horas * t.PagoHora) AS TOTAL
FROM
    Curso c
JOIN
    CursoProgramado cp ON c.IdCurso = cp.IdCurso
JOIN
    Tarifa t ON c.IdTarifa = t.IdTarifa
WHERE
    cp.IdCiclo = '2023-01' AND cp.IdProfesor = 'P041';
go
--obtener el pago total
SELECT
    p.NomProfesor + ' ' + p.ApeProfesor AS Profesor,
    cp.IdCiclo AS Ciclo,
    COUNT(cp.IdCursoProg) AS Secciones,
    SUM(t.Horas * t.PagoHora) AS PagoTotal
FROM Profesor p
JOIN CursoProgramado cp ON p.IdProfesor = cp.IdProfesor
JOIN Curso c ON cp.IdCurso = c.IdCurso
JOIN Tarifa t ON c.IdTarifa = t.IdTarifa
WHERE cp.IdCiclo = '2023-01' AND p.IdProfesor = 'P041'
GROUP BY p.IdProfesor, p.NomProfesor, p.ApeProfesor, cp.IdCiclo;
--VALIDAR QU un curso este activo
SELECT count(1) from CursoProgramado where IdCiclo='1985-01' and IdCursoProg='1' and Activo=1;
--validar que aun no este matriculado en ese curso programado
select count(1) from matricula where IdAlumno='A0005' and IdCursoProg='1';
go
--obtener el id del curso
select IdCurso from CursoProgramado where IdCursoProg='1';
go
--validar que no este matriculado en elguna seccion de ese curso en ese  ciclo
select count(1) 
from matricula join CursoProgramado on matricula.IdCursoProg=CursoProgramado.IdCursoProg
where IdAlumno='A0005' and IdCurso='C013' and IdCiclo='1985-01';
go

--validar que aun haya vacantes en el curso programado
select  vacantes from CursoProgramado where IdCursoProg=1;
GO
--obtener el ciclo actual
select idciclo from ciclo where FecInicio<=getdate() and FecTermino>=getdate();
GO
--verificar si el ciclo al que se quiere matricular esta disponible
select count(1) from ciclo where IdCiclo='2024-05' and IdCiclo>='2024-07';
go
--registrar la amtricula
insert into matricula (IdCursoProg,IdAlumno,FecMatricula,ExaParcial,ExaFinal,Promedio,Subsanacion,ExaSub)
   values (?,?,getdate(),null,null,null,0,null);
   go
 --actualizar vacantes y matriculados
 update CursoProgramado set Vacantes=Vacantes-1,Matriculados=Matriculados+1 where IdCursoProg=?;
 --validar que el alumno exista
 select count(1) from alumno where IdAlumno='A0007';
 --OBTENER LA FECHA DE LA MATRICULA 
 select fecmatricula from matricula where IdAlumno= 'A0005' and IdCursoProg=1;