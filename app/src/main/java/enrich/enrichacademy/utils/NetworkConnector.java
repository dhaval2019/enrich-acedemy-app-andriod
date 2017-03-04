package enrich.enrichacademy.utils;

import com.google.gson.JsonElement;

import java.util.HashMap;

import enrich.enrichacademy.model.CategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.ResponseDS;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Admin on 02-Mar-17.
 */

public interface NetworkConnector {

    @POST("RegisterMobile")
    Call<ResponseDS<String>> sendOtp(@Body HashMap<String, String> otpModel);

    @POST("RegisterWithOTP")
    Call<ResponseDS<String>> verifyOtp(@Body HashMap<String, String> otpModel);

    @POST("ResendOTP")
    Call<ResponseDS<Boolean>> resendOtp(@Body JsonElement jsonElement);

    @GET()
    Call<CourseModel[]> getCourses(@Url String url);

    @GET()
    Call<CategoryModel[]> getCategories(@Url String url);

    @GET()
    Call<ServicesModel[]> getServices(@Url String url);

    @POST("api/Customer")
    Call<UserModel> saveUserData(@Body UserModel userModel);
}
