package com.zasterapp.network

import android.content.Intent
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.zasterapp.BuildConfig
import com.zasterapp.ZasterApplication
import com.zasterapp.initial.InitialActivity
import com.zasterapp.network.model.UserModel
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

object RestClient {
    fun retrofitCallBack(): Retrofit {
        val logging = HttpLoggingInterceptor()
// set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder()
                .addHeader("x-api-key", "ZVNFgVs2QD6SZxyEyk07034bXggshnXq8qTyrYv0")
                .addHeader("Content-Type", "application/json")
//                    .addHeader("iUserId", Prefs(App.instance).getUserId()!!)
                    .build()

            val response = chain.proceed(request)
            if (response.code() == 401) {
//                Prefs(App.instance).setIsLogin(false)
                val i = Intent(ZasterApplication.instance, InitialActivity::class.java)
                i.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_NEW_TASK
                )
                ZasterApplication.instance.startActivity(i)
                System.exit(2)
                return@Interceptor response
            }
            response
        }).connectionSpecs(
            listOf(
                ConnectionSpec.MODERN_TLS,
                ConnectionSpec.COMPATIBLE_TLS,
                ConnectionSpec.CLEARTEXT
            )
        )
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .addInterceptor(logging).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    interface NetworkCall {
        @POST("signin")
        @FormUrlEncoded
        fun loginUser(@Field("email") email: String, @Field("password") password: String): Call<UserModel>
    }
}