package com.example.repofitutils.model

import android.app.Application
import android.text.TextUtils
import com.example.repofitutils.RepofitApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.repofitutils.utils.ACache
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManage{

    var mApi: ApiService? = null
    fun getApiService(): ApiService {
        if (mApi == null) {
            synchronized(this) {
                if (mApi == null) {
                    val builder = builderOkHttpClient()
                    mApi = buildRetrofit(builder.build()).create(ApiService::class.java)
                }
            }
        }
        return mApi!!
    }

    fun builderOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                val token = ACache.get(RepofitApp.context).getAsString("token")
                if (!TextUtils.isEmpty(token)) {
                    builder.addHeader("token", token)
                }
                val request = builder.build()
                chain.proceed(request)
            }
            .connectTimeout(TIMEOUT,TimeUnit.SECONDS)
            .readTimeout(TIMEOUT,TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT,TimeUnit.SECONDS)
            .addNetworkInterceptor(createLoggingInterceptor())

    }


    fun buildRetrofit(clients: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://124.70.98.255:8083")
            .addConverterFactory(CustomGsonConverterFactory.create())
            .client(clients!!)
            .build()
    }



//    companion object{
//        private val instance:RetrofitManage by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitManage() }
//        fun getRetrofitFactory():RetrofitManage{
//            return instance
//        }
//    }
    /**
     * create logging interceptor and set level
     */
    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply { level=HttpLoggingInterceptor.Level.BODY }
    }

    companion object{
        private val instance:RetrofitManage by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitManage() }
        /**
         * connect read write timeout number
         */
        val TIMEOUT=30L;

        fun getRetrofitFactory():RetrofitManage{
            return instance
        }
    }



}