package br.com.trier.springmatutino.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calc")
public class MathResource {

	@GetMapping("/somar")
	public Integer somar(@RequestParam(name = "n1")Integer n1,@RequestParam(name = "n2") Integer n2) {
		return n1+n2;
	}
	
	@GetMapping("/subtrair")
	public Integer subtrair(@RequestParam(name = "n1")Integer n1,@RequestParam(name = "n2") Integer n2) {
		return n1-n2;
	}
	
	@GetMapping("/multiplicar")
	public Integer multiplicar(@RequestParam(name = "n1")Integer n1,@RequestParam(name = "n2") Integer n2) {
		return n1*n2;
	}
	
	@GetMapping("/dividir")
	public Integer dividir(@RequestParam(name = "n1")Integer n1,@RequestParam(name = "n2") Integer n2) {
		return n1/n2;
	}
	
}
