package it.unisa.studenti.anzelmo2.c.qr4labs.network;

import java.util.Date;
import java.util.List;

import it.unisa.studenti.anzelmo2.c.qr4labs.database.alert.Alert;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.project.Project;
import it.unisa.studenti.anzelmo2.c.qr4labs.database.user.User;
import it.unisa.studenti.anzelmo2.c.qr4labs.utility.WebResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Webservice {

	@GET("getUser.php")
	Call<User> getUser();

	@GET("getUsersOfLab.php")
	Call<List<User>> getUsersOfLab(@Query("lab") String lab);

	@GET("getAlertsOfLab.php")
	Call<List<Alert>> getAlertsOfLab(@Query("lab") String lab);

	@GET("getProjectsOfLab.php")
	Call<List<Project>> getProjectsOfLab(@Query("lab") String lab);

	@POST("insertAlert.php")
	Call<WebResponse> addAlert(@Body Alert alert);

	@POST("insertUser.php")
	Call<WebResponse> addUser(@Body User user);

	@POST("updateUser.php")
	Call<WebResponse> updateUser(@Body User user);

	@POST("updateAlert.php")
	Call<WebResponse> updateAlert(@Body Alert alert);

	@POST("deleteUser.php")
	Call<WebResponse> deleteUser(@Body User user);

	@POST("deleteAlert.php")
	Call<WebResponse> deleteAlert(@Body Alert alert);
}
