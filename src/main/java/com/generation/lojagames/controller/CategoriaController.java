package com.generation.lojagames.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.lojagames.model.Categoria;
import com.generation.lojagames.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class CategoriaController {

	@Autowired // auto fiação
	private CategoriaRepository categoriaRepository;

	@RequestMapping
	public ResponseEntity<List<Categoria>> getAll() { // getall = pegar todas
		return ResponseEntity.ok(categoriaRepository.findAll()); // findall = encontrar todas

	}
  
	@RequestMapping("/{id}") // Método get para listar uma categoria pelo id
	public ResponseEntity<Categoria> getById(@PathVariable Long id) {
		return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping("/titulo/{titulo}") // Método get para listar categorias pelo gênero
	public ResponseEntity<List<Categoria>> getBytitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(categoriaRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping // Método post para criar uma categoria
	public ResponseEntity<Categoria> postCategoria(@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	}

	@PutMapping // Método put para atualizar uma categoria
	public ResponseEntity<Categoria> putCategoria(@Valid @RequestBody Categoria categoria) {
		if (categoriaRepository.existsById(categoria.getId())) {
			return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@DeleteMapping("/{id}") // Método delete para deletar uma categoria
	public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
		if (categoriaRepository.existsById(id)) {
			categoriaRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
