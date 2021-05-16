//package retrofit
//
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//
//fun apiService() {
//    val apiService = Retrofit.Builder()
//        .baseUrl("http://gallery.dev.webant.ru/api/photos/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .build().create(getData::class.java)
//}