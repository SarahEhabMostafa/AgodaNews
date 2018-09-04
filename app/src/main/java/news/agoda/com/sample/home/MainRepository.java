package news.agoda.com.sample.home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import news.agoda.com.sample.ApiInterface;
import news.agoda.com.sample.R;
import news.agoda.com.sample.RetrofitBuilder;
import news.agoda.com.sample.data.ParentEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private MainContract mainContract;

    public MainRepository(MainContract mainContract) {
        this.mainContract = mainContract;
    }

    /*Fetches the new data from the server*/
    public void getUpdatedNews() {
        ApiInterface apiInterface = RetrofitBuilder.getInstance().create(ApiInterface.class);
        final Call<ResponseBody> parentEntityCall = apiInterface.getNews();
        parentEntityCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        String data = response.body().string();
                        data = data.replaceAll("\"des_facet\":\"\"", "\"des_facet\":[]")
                                .replaceAll("\"org_facet\":\"\"", "\"org_facet\":[]")
                                .replaceAll("\"per_facet\":\"\"", "\"per_facet\":[]")
                                .replaceAll("\"geo_facet\":\"\"", "\"geo_facet\":[]")
                                .replaceAll("\"multimedia\":\"\"", "\"multimedia\":[]")
                                .replaceAll("\"abstract\":", "\"summary\":");

                        Gson gson = new Gson();
                        ParentEntity parentEntity = gson.fromJson(data, new TypeToken<ParentEntity>() {
                        }.getType());

                        mainContract.onSuccess(parentEntity);
                    } catch (IOException e) {
                        e.printStackTrace();
                        mainContract.onFailure(e.getMessage());
                    }
                } else {
                    mainContract.onFailure(R.string.error);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                mainContract.onFailure(t.getMessage());
            }
        });
    }
}
