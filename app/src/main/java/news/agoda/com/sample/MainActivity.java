package news.agoda.com.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.utils.Constants;

public class MainActivity extends ListActivity implements Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<NewsEntity> newsItemList;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        newsItemList = new ArrayList<>();

        loadResource(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadResource(final Callback callback) {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    URL url = new URL("https://api.myjson.com/bins/nl6jh");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    String readStream = readStream(con.getInputStream());
                    callback.onResult(readStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override public void onResult(final String data) {
        handler.postDelayed(new Runnable() {
            @Override public void run() {

                String updatedData = data;
                updatedData = updatedData.replaceAll("\"des_facet\":\"\"", "\"des_facet\":[]")
                .replaceAll("\"org_facet\":\"\"", "\"org_facet\":[]")
                .replaceAll("\"per_facet\":\"\"", "\"per_facet\":[]")
                .replaceAll("\"geo_facet\":\"\"", "\"geo_facet\":[]")
                .replaceAll("\"multimedia\":\"\"", "\"multimedia\":[]")
                .replaceAll("\"abstract\":", "\"summary\":");

                Gson gson = new Gson();
                ParentEntity parentEntity = gson.fromJson(updatedData, new TypeToken<ParentEntity>(){}.getType());
//                JSONObject jsonObject;
//
//                try {
//                    jsonObject = new JSONObject(data);
//                    JSONArray resultArray = jsonObject.getJSONArray("results");
//                    for (int i = 0; i < resultArray.length(); i++) {
//                        JSONObject newsObject = resultArray.getJSONObject(i);
//                        NewsEntity newsEntity = new NewsEntity(newsObject);
//                        newsItemList.add(newsEntity);
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, "fail to parse json string");
//                }

                newsItemList = parentEntity.getResults();
                NewsListAdapter adapter = new NewsListAdapter(MainActivity.this, R.layout.list_item_news, newsItemList);
                setListAdapter(adapter);

                ListView listView = getListView();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NewsEntity newsEntity = newsItemList.get(position);
                        String title = newsEntity.getTitle();
                        String storyURL = newsEntity.getUrl();
                        String summary = newsEntity.getSummary();
                        String imageURL = "";
                        if (!newsEntity.getMultimedia().isEmpty()) {
                            MediaEntity mediaEntity = newsEntity.getMultimedia().get(0);
                            imageURL = mediaEntity.getUrl();
                        }
                        Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
                        intent.putExtra(Constants.KEY_TITLE, title);
                        intent.putExtra(Constants.KEY_STORY_URL, storyURL);
                        intent.putExtra(Constants.KEY_SUMMARY, summary);
                        intent.putExtra(Constants.KEY_IMAGE_URL, imageURL);
                        startActivity(intent);
                    }
                });
            }
        }, 0);
    }

}
