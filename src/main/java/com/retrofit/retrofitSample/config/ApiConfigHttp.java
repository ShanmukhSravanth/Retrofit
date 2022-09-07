package com.retrofit.retrofitSample.config;

import retrofit.Call;
import retrofit.http.GET;

public interface ApiConfigHttp {
	
	@GET("/v1/home")
	Call<String> home();
}
