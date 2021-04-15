package com.pagination.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagination.entities.Tutorial;
import com.pagination.repository.TutorialRepository;

@RestController
public class TutorialController {

	@Autowired
	  TutorialRepository tutorialRepository;

	  @GetMapping("/tutorials")
	  public ResponseEntity<Map<String, Object>> getAllTutorials(
			/* Valores default para páginação. Inicia a paginação em 0 e lista 3 itens do banco e dados*/
	        @RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size
	      ) {

	    try {
	      List<Tutorial> tutorials = new ArrayList<Tutorial>();
	      /* Cria a paginação*/
	      Pageable paging = PageRequest.of(page, size);
	      
	      Page<Tutorial> pageTuts;
	      if (title == null)
	        pageTuts = tutorialRepository.findAll(paging);
	      else
	        pageTuts = tutorialRepository.findTitleCustom(title, paging);

	      tutorials = pageTuts.getContent();

	      Map<String, Object> response = new HashMap<>();
	      response.put("tutorials", tutorials);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());

	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  @GetMapping("/tutorials/published")
	  public ResponseEntity<Map<String, Object>> findByPublished(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size
	      ) {
	    try {      
	      List<Tutorial> tutorials = new ArrayList<Tutorial>();
	      Pageable paging = PageRequest.of(page, size);
	      
	      Page<Tutorial> pageTuts = tutorialRepository.findByPublished(true, paging);
	      tutorials = pageTuts.getContent();
	            
	      Map<String, Object> response = new HashMap<>();
	      response.put("tutorials", tutorials);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());
	      
	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}

//  API:
//	/api/tutorials?page=1&size=5
//	/api/tutorials?size=5: usando o valor padrão para a página
//	/api/tutorials?title=data&page=1&size=3: paginação e filtro por título contendo 'dados'
//	/api/tutorials/published?page=2: paginação e filtro por status 'publicado'

//	Metodos utilizados para páginação
//	getContent() para recuperar a Lista de itens na página.
//	getNumber() para a página atual.
//	getTotalElements() para o total de itens armazenados no banco de dados.
//	getTotalPages() para número de páginas totais.
