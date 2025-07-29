package com.example.test.controller;

import com.example.test.entity.FaultLines;
import com.example.test.mapper.FaultLinesMapper;
import com.example.test.service.FaultLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 地震带
 */
@RestController
@RequestMapping("/fault_line")
public class FaultLinesController {

	@Autowired
	private FaultLinesService faultLinesService;

	@Autowired
	private FaultLinesMapper faultLinesMapper;

	@GetMapping
	public List<FaultLines> index(){

		return faultLinesMapper.getList();
	}

	@GetMapping("/id/{id}")
	public FaultLines getFaultLineById(@PathVariable("id") Integer id){
		return faultLinesMapper.getFaultLinesById(id);
	}
}
