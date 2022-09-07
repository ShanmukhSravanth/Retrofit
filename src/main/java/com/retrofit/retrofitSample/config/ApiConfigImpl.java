package com.retrofit.retrofitSample.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Configuration
@EnableAsync
//@PropertySource("classpath:sebi.properties")
public class ApiConfigImpl {
	@Autowired
	Environment env;

	@Bean
	ApiConfigHttp getMessageApi() {
		final OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setReadTimeout(160, TimeUnit.SECONDS);
		okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
		okHttpClient.setConnectionPool(new ConnectionPool(5, 5000));
		Retrofit server = new Retrofit.Builder().baseUrl(env.getProperty("baseurl", "http://localhost:8089"))
				.addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
		return server.create(ApiConfigHttp.class);
	}
	
	@Bean
	ApiConfigHttps getMessageHttpsApi() {
		final OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setReadTimeout(160, TimeUnit.SECONDS);
		okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
		okHttpClient.setConnectionPool(new ConnectionPool(5, 5000));
		okHttpClient.setHostnameVerifier(SSLHelper2.getHostnameVerifier());
		okHttpClient.setSslSocketFactory(SSLHelper2.getSocketFactory());
		Retrofit server = new Retrofit.Builder().baseUrl(env.getProperty("baseurl", "https://localhost:8089"))
				.addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
		return server.create(ApiConfigHttps.class);
	}
}
