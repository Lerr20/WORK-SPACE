package uni.pe.BookStore.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uni.pe.BookStore.Dto.VentaDto;
import uni.pe.BookStore.Service.bookstoreService;

@RestController
@RequestMapping("/bookstore")
public class bookstoreController {
	
   private final bookstoreService bookstoreservice;
   
   @Autowired
	public bookstoreController(bookstoreService bookstoreService) {
		this.bookstoreservice = bookstoreService;
	}
   
   @GetMapping("/ventastipo")
   public List<Map<String, Object>> listadoVentaPorTipo (){
	   return bookstoreservice.resumenPorTipo();
   }
   
   @GetMapping("/ventaspublicacion")
   public List<Map<String, Object>> listadoVentaPorPublicacion (){
	   return bookstoreservice.resumenPorTipoPublicacion();
   }
   
   
   @PostMapping("/venta")
	public ResponseEntity<?> vender(@RequestBody VentaDto dto){
		
		try {
			
			bookstoreservice.registrarVenta(dto);
			return ResponseEntity.ok(dto); 
		} catch (Exception e) {
			ErrorResponse error;
			error = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
           return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}
	}
}
