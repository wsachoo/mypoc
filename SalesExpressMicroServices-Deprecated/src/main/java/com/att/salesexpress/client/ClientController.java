package com.att.salesexpress.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientController {
	
	@Autowired
	protected ClientTestService webSalesExpressService;
	
	@RequestMapping("/home")
	@ResponseBody
	public String home() {
		return webSalesExpressService.test();
	}
}
