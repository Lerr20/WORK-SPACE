package pe.uni.POO_EF_IBARRA.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.uni.POO_EF_IBARRA.service.CuentasActivas;

@RestController
@RequestMapping("/resumen")
public class CuentasActivasController {
    
	
	@Autowired
	private CuentasActivas resumenCuentasService;
	
	@GetMapping("/cuentas")
	public List<Map<String, Object>> resumenActivas(){
		return resumenCuentasService.resumenActivas();
	}
}
