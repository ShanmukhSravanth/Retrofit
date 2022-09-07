package com.retrofit.retrofitSample.contorller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retrofit.retrofitSample.config.ApiConfigHttp;
import com.retrofit.retrofitSample.config.ApiConfigHttps;

@RestController
@RequestMapping("/v1")
public class HomeController {

	@Autowired
	ApiConfigHttp apiConfigHttp;
	
	@Autowired
	ApiConfigHttps apiConfigHttps;
	
	@GetMapping("/")
	public String home() throws IOException {
		System.out.println("IN API");
		return apiConfigHttp.home().execute().body();
	}
	
	
	
	@GetMapping("/secured/")
	public String securedHome() throws IOException {
		System.out.println("IN secured API");
		return apiConfigHttps.homes().execute().body();
	}
	
	@GetMapping("/home")
	public String home1() throws IOException {
		System.out.println("IN Home API");
		return "Welcome";
	}

}