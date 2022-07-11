package com.api.projetoFinal.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.projetoFinal.domain.Produto;
import com.api.projetoFinal.domain.dtos.ProdutoDTO;
import com.api.projetoFinal.services.ProdutoService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/service/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> findById(@PathVariable Integer id) {
		Produto obj = service.findById(id);
		return ResponseEntity.ok().body(new ProdutoDTO(obj));
	}

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> findAllProduto() {
		List<Produto> list = service.findAllProduto();
		List<ProdutoDTO> listDto = list.stream().map(prod -> new ProdutoDTO(prod)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@PostMapping
	public ResponseEntity<ProdutoDTO> createProduto(@Valid @RequestBody ProdutoDTO objDto) {
		Produto newObj = service.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable Integer id, @RequestBody ProdutoDTO objDto) {
		Produto obj = service.update(id, objDto);
		return ResponseEntity.ok().body(new ProdutoDTO(obj));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
