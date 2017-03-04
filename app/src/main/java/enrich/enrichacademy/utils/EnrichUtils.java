package enrich.enrichacademy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 21-Feb-17.
 */

public class EnrichUtils {

    static DialogDisplay showDialog = new EnrichUtils.DialogDisplay();
    static LoadingDialogBox pDialog;
    static Runnable cancelDialog = new Runnable() {
        @Override
        public void run() {
            try {
                if (pDialog != null)
                    pDialog.cancel();
                pDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    static class DialogDisplay implements Runnable {
        Activity activity;
        String message;

        public DialogDisplay updateActivity(Activity activity, String message) {
            this.activity = activity;
            this.message = message;
            return this;
        }


        @Override
        public void run() {
            try {
                if (pDialog != null) {
                    pDialog.cancel();
                }
                pDialog = new LoadingDialogBox(activity);
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.setCancelable(false);
                pDialog.setMessage(message);
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showProgressDialog(final Activity activity) {
        Log.e(Constants.LOG_TAG, "Showing dialog... :)");
        activity.runOnUiThread(showDialog.updateActivity(activity, null));
    }

    public static void cancelCurrentDialog(Activity activity) {
        Log.e(Constants.LOG_TAG, "Canceling dialog... :|");
        if (pDialog != null && activity != null) {
            new Handler().postDelayed(cancelDialog, 150);
        }
    }

    /**
     * Check for Not Null
     *
     * @param data To check
     * @return
     */
    public static boolean notNull(String data) {
        if (data == null || data.equals("") || data.isEmpty())
            return false;
        return true;
    }

    /**
     * Check For Blank String
     *
     * @param text To check
     * @return
     */
    public static boolean notBlank(String text) {
        if (text == null || text.trim().length() == 0)
            return false;
        return true;
    }

    /**
     * Log String data in Error
     *
     * @param logData
     */
    public static void log(String logData) {
        Log.e(Constants.LOG_TAG, logData + "");
    }

    /**
     * Check if the email address entered is Valid or Not.
     *
     * @param email
     * @return
     */
    public static boolean isValidEmailId(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Show Message in Toast
     *
     * @param context
     * @param message
     */
    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static NetworkConnector getRetrofit() {
        if (EnrichURLs.retrofitNetworkHandler == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder();

                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                }
            });

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new GsonDateDeSerializer());
            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(EnrichURLs.HOST)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .build();
            EnrichURLs.retrofitNetworkHandler = retrofit.create(NetworkConnector.class);
        }


        return EnrichURLs.retrofitNetworkHandler;
    }

    public static NetworkConnector getRetrofitForEnrich() {
        if (EnrichURLs.retrofitNetworkHandlerForEnrich == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder();

                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                }
            });

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new GsonDateDeSerializer());
            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(EnrichURLs.ENRICH_HOST)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .build();
            EnrichURLs.retrofitNetworkHandlerForEnrich = retrofit.create(NetworkConnector.class);
        }


        return EnrichURLs.retrofitNetworkHandlerForEnrich;
    }

    public static Gson newGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new GsonDateSerializer());
        return builder.create();
    }

    /**
     * Encode Bitmap to Base64 String
     *
     * @param image Bitmap Image
     * @return Base64 String
     */
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    /**
     * Decode Base64 String to Bitmap
     *
     * @param input String Base64
     * @return Bitmap
     */
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
