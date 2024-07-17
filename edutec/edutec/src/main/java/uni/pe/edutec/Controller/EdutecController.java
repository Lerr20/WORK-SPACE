package uni.pe.edutec.Controller;

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

import uni.pe.edutec.Dto.MatriculaDto;
import uni.pe.edutec.Dto.PagoCursoDto;
import uni.pe.edutec.Dto.PagoTotalDto;
import uni.pe.edutec.Dto.ResumenCursosDto;
import uni.pe.edutec.Service.EdutecService;

@RestController
@RequestMapping("/edutec")
public class EdutecController {
  
	
	private final EdutecService edutecservice;
	
	@Autowired
	public EdutecController(EdutecService EdutecService) {
		this.edutecservice=EdutecService;
	}
	
	@GetMapping("/resumen/{ciclo}")
	public ResponseEntity<?> listadoCursosProgramados(@PathVariable String ciclo){
		List<ResumenCursosDto> bean = edutecservice.resumirCursosProgramados(ciclo);
		if (bean == null || bean.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CICLO NO EXISTE");
		}

		return ResponseEntity.ok(bean);
	}
	
	@GetMapping("/pagocurso/{ciclo}/{idprofesor}")
	public ResponseEntity<?> listadoPagosDeUnProfesor(@PathVariable String ciclo,@PathVariable String idprofesor){
		List<PagoCursoDto> bean = edutecservice.resumirPagosProfesor(ciclo,idprofesor);
		if (bean == null || bean.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CICLO NO EXISTE o PROFESOR NO EXISTE");
		}

		return ResponseEntity.ok(bean);
	}
	
	@GetMapping("/pago/{ciclo}/{idprofesor}")
	public ResponseEntity<?> PagoDeUnProfesor(@PathVariable String ciclo,@PathVariable String idprofesor){
		List<PagoTotalDto> bean = edutecservice.pagoProfesor(ciclo,idprofesor);
		if (bean == null || bean.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CICLO NO EXISTE o PROFESOR NO EXISTE");
		}

		return ResponseEntity.ok(bean);
	}
	
	@PostMapping("/matricular")
	public ResponseEntity<?> matricular(@RequestBody MatriculaDto dto){
		try {
			edutecservice.registrarMatricula(dto);
			return ResponseEntity.ok(dto); 
		} catch (Exception e) {
			ErrorResponse error;
			error = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}
	}
}
