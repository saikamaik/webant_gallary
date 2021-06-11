package retrofit

import io.reactivex.Single
import model.APIResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface GetData {

    @GET
    fun getData(@Url url: String): Single<APIResponse>
}