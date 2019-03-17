package it.unisa.studenti.anzelmo2.c.qr4labs.utility;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import it.unisa.studenti.anzelmo2.c.qr4labs.network.Webservice;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

	private static ServiceLocator serviceLocator;
	private Webservice webservice;
	private Executor executor;

	private ServiceLocator() {
		Retrofit retrofit=new Retrofit.Builder()
				 .baseUrl("https://labqr.000webhostapp.com/")
				 .addConverterFactory(GsonConverterFactory.create())
				 .build();

		webservice=retrofit.create(Webservice.class);
		executor= Executors.newSingleThreadExecutor();
	}

	public static ServiceLocator getInstance() {
		if (serviceLocator==null)
			serviceLocator=new ServiceLocator();

		return serviceLocator;
	}

	public static Webservice getWebservice() {
		return serviceLocator.webservice;
	}

	public static Executor getExecutor() {
		return serviceLocator.executor;
	}
}
