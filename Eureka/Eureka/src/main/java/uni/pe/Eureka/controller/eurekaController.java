package uni.pe.Eureka.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uni.pe.Eureka.Dto.CuentaDto;
import uni.pe.Eureka.Dto.TransferenciaDto;
import uni.pe.Eureka.Dto.idtipoDto;
import uni.pe.Eureka.Dto.movimientoDto;
import uni.pe.Eureka.Service.eurekaService;

@RestController
@RequestMapping("/eureka")
public class eurekaController {

	private final eurekaService eurekaservice;

	@Autowired
	public eurekaController(eurekaService eurekaService) {
		this.eurekaservice = eurekaService;
	}
    
	
	
	@GetMapping("/movimientos")
	public List<Map<String, Object>> listadoMovimientosSucursal() {
		return eurekaservice.resumenMovimientoSucursal();
	}
	

	@GetMapping("/movimiento/{id}")
	public ResponseEntity<?> listadoMovimientosId(@PathVariable String id) {
		List<movimientoDto> bean = eurekaservice.resumenMovimientosId(id);

		if (bean == null || bean.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID NO EXISTE");
		}

		return ResponseEntity.ok(bean);
	}

	
	@GetMapping("/cuentas")
	public List<Map<String, Object>> listadoCuentasActivas() {
		return eurekaservice.resumenCuentasActivas();
	}

	
	@GetMapping("/cuenta/{id}") // ENDPOINT PARA OBTENERS NUMERO DE CUENTAS EN DETERMINADA SUCURSAL
    public ResponseEntity<?> listadoCuentasActivasId(@PathVariable String id) {
        List<CuentaDto> bean = eurekaservice.cuentasActivasPorSucursal(id);

        if (bean == null || bean.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID no existe");
        }

        return ResponseEntity.ok(bean);
    }
	
	@GetMapping("/movaccion")
	public List<Map<String, Object>>listadoMovimientosAccion (){
		return eurekaservice.resumenMovimientosPorAccion();
	}
	
	@GetMapping("/accion/{id}") // ENDPOINT 
    public ResponseEntity<?> listadoResumenPorAccion(@PathVariable String id) {
        List<idtipoDto> bean = eurekaservice.resumenMovimientosPorAccionId(id);

        if (bean == null || bean.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID no existe");
        }

        return ResponseEntity.ok(bean);
    }

	
	@PostMapping("/transferencia")
	public ResponseEntity<?> transferir(@RequestBody TransferenciaDto dto){
		
		
		//return ResponseEntity.ok(dto);
		
		try {
			eurekaservice.realizarTransferencia(dto);
			return ResponseEntity.ok(dto); 
		} catch (Exception e) {
			ErrorResponse error;
			error = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}
	}
	
}