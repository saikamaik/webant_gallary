package retrofit

import io.reactivex.Single
import model.APIResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object APIService {

    var BASE_URL: String = "http://gallery.dev.webant.ru/api/"

    fun getApiPhotos(type: String, page: Int): Single<APIResponse> {
        val client = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val Photos = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(GetData::class.java)
            .getData("http://gallery.dev.webant.ru/api/photos?$type=true&limit=10&page=$page")

        return Photos
    }

}