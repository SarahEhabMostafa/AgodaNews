package news.agoda.com.sample.home;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.List;

import news.agoda.com.sample.data.MediaEntity;
import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.R;

public class NewsListAdapter extends ArrayAdapter {
    private static class ViewHolder {
        TextView newsTitle;
        DraweeView imageView;
    }

    NewsListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        NewsEntity newsEntity = (NewsEntity) getItem(position);
        if(newsEntity == null)
            return convertView;

        List<MediaEntity> mediaEntityList = newsEntity.getMultimedia();
        String thumbnailURL = "";
        if (!mediaEntityList.isEmpty()) {
            MediaEntity mediaEntity = mediaEntityList.get(0);
            thumbnailURL = mediaEntity.getUrl();
        }

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_news, parent, false);
            viewHolder.newsTitle = convertView.findViewById(R.id.news_title);
            viewHolder.imageView = convertView.findViewById(R.id.news_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.newsTitle.setText(newsEntity.getTitle());
        DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                (Uri.parse(thumbnailURL))).setOldController(viewHolder.imageView.getController()).build();
        viewHolder.imageView.setController(draweeController);
        return convertView;
    }
}