package pe.uni.POO_EF_IBARRA.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.uni.POO_EF_IBARRA.service.ResumenMovimientosService;

@RestController
@RequestMapping("/resumen")
public class ResumenMovimientosController {
    
    @Autowired
    private ResumenMovimientosService resumenMovsService;
    
    @GetMapping("/movimientos")
    public List<Map<String, Object>> resumensucursal(){
        return resumenMovsService.resumenSucursal();
    }
}
