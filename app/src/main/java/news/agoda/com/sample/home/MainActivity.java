package news.agoda.com.sample.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import news.agoda.com.sample.R;
import news.agoda.com.sample.data.MediaEntity;
import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.details.DetailFragment;
import news.agoda.com.sample.utils.Constants;

public class MainActivity extends FragmentActivity
        implements ListFragment.OnItemClickListener, DetailFragment.OnButtonClick {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            ListFragment listFragment = new ListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, listFragment).commit();
        }
    }

    /*@Override
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
    }*/

    @Override
    public void onItemClick(NewsEntity newsEntity) {
        String title = newsEntity.getTitle();
        String storyURL = newsEntity.getUrl();
        String summary = newsEntity.getSummary();
        String imageURL = "";
        if (!newsEntity.getMultimedia().isEmpty()) {
            MediaEntity mediaEntity = newsEntity.getMultimedia().get(0);
            imageURL = mediaEntity.getUrl();
        }
        Bundle args = new Bundle();
        args.putString(Constants.KEY_TITLE, title);
        args.putString(Constants.KEY_STORY_URL, storyURL);
        args.putString(Constants.KEY_SUMMARY, summary);
        args.putString(Constants.KEY_IMAGE_URL, imageURL);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(findViewById(R.id.fragment_container_detail) != null) {
            transaction.replace(R.id.fragment_container_detail, detailFragment);
        } else {
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onButtonClick(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
