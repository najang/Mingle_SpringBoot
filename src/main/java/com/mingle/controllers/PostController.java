package com.mingle.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.mingle.dto.PostDTO;
import com.mingle.dto.UploadPostDTO;
import com.mingle.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService pServ;
	
	@GetMapping("/freeTop10")
	public ResponseEntity<List<Map<String,Object>>> getLastestFreePosts(){
		return ResponseEntity.ok(pServ.selectByNoticeFalseTop10());
	}
	
	@GetMapping("/test")
	public ResponseEntity<List<PostDTO>> getTest(){
		return ResponseEntity.ok(pServ.selectAll());
	}
	
	@GetMapping("/noticeTop10")
	public ResponseEntity<List<Map<String,Object>>> getLastestNoticePosts(){
		return ResponseEntity.ok(pServ.selectByNoticeTrueTop10());
	}
	
	@GetMapping("/free")
	public ResponseEntity<List<Map<String,Object>>> getFreePosts(){
		return ResponseEntity.ok(pServ.selectByNoticeFalse());
	}
	
	@GetMapping("/notice")
	public ResponseEntity<List<Map<String,Object>>> getNoticePosts(){
		return ResponseEntity.ok(pServ.selectByNoticeTrue());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostInfo(@PathVariable Long id) {
	    return ResponseEntity.ok(pServ.findPostById(id));
	}

	
	@PostMapping
	public ResponseEntity<Void> postInsert(@RequestBody UploadPostDTO dto) throws IllegalStateException, IOException{
		pServ.insert(dto);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> postUpdate(@RequestBody Long id, @RequestBody PostDTO dto) {
		pServ.updateById(id, dto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> postDelete(@RequestBody Long id, @RequestBody PostDTO dto) {
		pServ.deleteById(id, dto);
		return ResponseEntity.ok().build();
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e, WebRequest request) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	}

}
