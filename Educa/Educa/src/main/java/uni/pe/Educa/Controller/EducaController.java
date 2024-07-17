package uni.pe.Educa.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uni.pe.Educa.Dto.AlumnoDto;
import uni.pe.Educa.Dto.CuotasDto;
import uni.pe.Educa.Dto.EstadoDto;
import uni.pe.Educa.Dto.MatriculaDto;
import uni.pe.Educa.Dto.NuevoResumenDto;
import uni.pe.Educa.Dto.PagoDto;
import uni.pe.Educa.Dto.resumenDto;
import uni.pe.Educa.Service.EducaService;




@RestController
@RequestMapping("/educa")
public class EducaController {

	private final EducaService educaservice;
	
	@Autowired
	public EducaController(EducaService EducaService) {
		this.educaservice = EducaService;
	}
	
	@GetMapping("/resumen/{alumnoid}/{cursoid}")
	public ResponseEntity<?> informacionAlumno(@PathVariable String alumnoid,@PathVariable String cursoid) {
		List<AlumnoDto> bean = educaservice.estadoAlumno(alumnoid,cursoid);
		List<CuotasDto> dto = educaservice.cuotasAlumno(alumnoid, cursoid);

		if (bean == null || bean.isEmpty() || dto == null || dto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("alumno o curso no existe");
		}

		resumenDto resumen = new resumenDto(bean, dto);
	    return ResponseEntity.ok(resumen);
	}
	
	@GetMapping("nuevoresumen/{alumnoid}/{cursoid}")
	public ResponseEntity<?> nuevoestadoalumno(@PathVariable String alumnoid,@PathVariable String cursoid){
		
		List<EstadoDto> estado =educaservice.nuevoEstadoAlumno(alumnoid, cursoid);
		List<CuotasDto> cuota = educaservice.cuotasAlumno(alumnoid, cursoid);
		
		if (estado == null || estado.isEmpty() || cuota == null || cuota.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("alumno o curso no existe");
		}
		
		NuevoResumenDto resumen = new NuevoResumenDto(estado, cuota);
	    return ResponseEntity.ok(resumen);
	}
	
	@PostMapping("/pagar")
	public ResponseEntity<?> pagar(@RequestBody PagoDto dto){
		
		try {
			educaservice.realizarPago(dto);
			return ResponseEntity.ok(dto); 
		} catch (Exception e) {
			ErrorResponse error;
			error = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}
	}
	
	@PostMapping("/matricular")
	public ResponseEntity<?> matricular (@RequestBody MatriculaDto dto){
		
		try {
			educaservice.realizarMatricula(dto);
			return ResponseEntity.ok(dto); 
		}catch (Exception e) {
			ErrorResponse error;
			error = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}	
		
	}

}
