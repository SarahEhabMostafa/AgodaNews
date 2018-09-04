package news.agoda.com.sample;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("bins/nl6jh")
    Call<ResponseBody> getNews();
}
