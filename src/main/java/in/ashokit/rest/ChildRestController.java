package in.ashokit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.binding.Child;
import in.ashokit.binding.ChildRequest;
import in.ashokit.binding.DcSummary;
import in.ashokit.service.DcService;

@RestController
public class ChildRestController {

	@Autowired
	private DcService service;
	
	@PostMapping("/childrens")
	public ResponseEntity<DcSummary> saveChild(@RequestBody ChildRequest chReq){
		
		Long caseNum = service.saveChildrens(chReq);
		
		DcSummary summary = service.getSummary(caseNum);
		
	    return new ResponseEntity<>(summary , HttpStatus.OK);
	}
}
