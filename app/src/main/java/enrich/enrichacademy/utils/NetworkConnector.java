package enrich.enrichacademy.utils;

import com.google.gson.JsonElement;

import enrich.enrichacademy.model.CancelRequestModel;
import enrich.enrichacademy.model.CityModel;
import enrich.enrichacademy.model.CourseApplicationModel;
import enrich.enrichacademy.model.CourseApplicationRequestModel;
import enrich.enrichacademy.model.CourseCategoryModel;
import enrich.enrichacademy.model.CourseModel;
import enrich.enrichacademy.model.DateTimeSlotModel;
import enrich.enrichacademy.model.ListResponseModel;
import enrich.enrichacademy.model.OrderModel;
import enrich.enrichacademy.model.OrderRequestModel;
import enrich.enrichacademy.model.OrderResponseModel;
import enrich.enrichacademy.model.ResponseDS;
import enrich.enrichacademy.model.SearchModel;
import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.model.SingleResponseModel;
import enrich.enrichacademy.model.StoreModel;
import enrich.enrichacademy.model.TimingModel;
import enrich.enrichacademy.model.TopologyModel;
import enrich.enrichacademy.model.UserModel;
import enrich.enrichacademy.model.UserRequestModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by Admin on 02-Mar-17.
 */

public interface NetworkConnector {

    @GET()
    Call<SingleResponseModel<String>> sendOtp(@Url String url);

    @GET()
    Call<SingleResponseModel<Boolean>> verifyOtp(@Url String url);

    @GET()
    Call<SingleResponseModel<UserModel>> verifyOtpForLogin(@Url String url);

    @POST("ResendOTP")
    Call<ResponseDS<Boolean>> resendOtp(@Body JsonElement jsonElement);

    @GET()
    Call<ListResponseModel<CourseModel>> getCourses(@Url String url);

    @GET()
    Call<ListResponseModel<CourseCategoryModel>> getCategories(@Url String url);

    @GET()
    Call<ListResponseModel<TopologyModel>> getTopology(@Url String url);

    @GET()
    Call<ListResponseModel<ServicesModel>> getServices(@Url String url);

    @POST("User/AddUser")
    Call<SingleResponseModel<UserModel>> saveUserData(@Body UserRequestModel userModel);

    @GET
    Call<TimingModel[]> getTimings(@Url String url);

    @GET
    Call<ListResponseModel<DateTimeSlotModel>> getDateTimeSlots(@Url String url);

    @POST("Order/CreateOrder")
    Call<SingleResponseModel<Integer>> createOrder(@Body OrderRequestModel orderModel);

    @POST("CourseApplication/AddApplication")
    Call<SingleResponseModel<Integer>> applyForCourse(@Body CourseApplicationRequestModel model);

    @GET
    Call<OrderModel[]> getOrders(@Url String url);

    @GET
    Call<SingleResponseModel<OrderResponseModel>> getOrderById(@Url String url);

    @GET
    Call<ListResponseModel<OrderResponseModel>> getCurrentAppointments(@Url String url);

    @POST("Order/CancelOrder")
    Call<Boolean> cancelOrder(@Body CancelRequestModel userModel);

    @Multipart
    @POST("User/UploadProfileImage")
    Call<SingleResponseModel<String>> uploadProfileImage(@Part("id") RequestBody description, @Part MultipartBody.Part file);

    @GET
    Call<SingleResponseModel<UserModel>> getUserById(@Url String url);

    @GET
    Call<SingleResponseModel<SearchModel>> searchByKeyword(@Url String url);

    @GET
    Call<ListResponseModel<CourseApplicationModel>> getAppliedCourses(@Url String url);

    @GET
    Call<ListResponseModel<CityModel>> getCities(@Url String url);

    @GET
    Call<ListResponseModel<StoreModel>> getStores(@Url String url);
}
