package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.creator.model.Creator;
import com.example.demo.creator.service.CreatorService;
import com.example.demo.enterprise.model.EnterpriseDTO;

@RestController
public class CreatorController extends BaseController{

	@Autowired
	private CreatorService creatorService;
	
	
	@PostMapping("/creator/create")
	public EnterpriseDTO creator(@RequestBody Creator creator) {
		return creatorService.create(creator, getCurrentUser());
	}
	
}
