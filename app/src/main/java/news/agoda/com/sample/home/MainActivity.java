package news.agoda.com.sample.home;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

import news.agoda.com.sample.R;
import news.agoda.com.sample.data.MediaEntity;
import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.details.DetailViewActivity;
import news.agoda.com.sample.utils.Constants;

public class MainActivity extends ListActivity implements /*Callback,*/ MainPresenterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        mainPresenter = new MainPresenter(this);
        mainPresenter.loadNews();
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

    @Override
    public void displayNewsList(final List<NewsEntity> newsEntityList) {
        NewsListAdapter adapter = new NewsListAdapter(MainActivity.this, R.layout.list_item_news, newsEntityList);
        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsEntity newsEntity = newsEntityList.get(position);
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

    @Override
    public void hideList() {
        ListView listView = getListView();
        TextView textView = new TextView(this);
        textView.setText(R.string.empty_list);
        listView.setEmptyView(textView);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if(progressDialog.isShowing())
            progressDialog.hide();
    }

    @Override
    public void showError(String errorMessage) {
        ListView listView = getListView();
        TextView textView = new TextView(this);
        textView.setText(errorMessage);
        listView.setEmptyView(textView);
    }

    @Override
    public void showError(int errorResId) {
        showError(getString(errorResId));
    }
}
