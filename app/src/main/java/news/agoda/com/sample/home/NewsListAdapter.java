package news.agoda.com.sample.home;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.R;
import news.agoda.com.sample.data.MediaEntity;
import news.agoda.com.sample.data.NewsEntity;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private ArrayList<NewsEntity> newsEntities;
    private ListFragment.OnItemClickListener onItemClickListener;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        DraweeView imageView;
        LinearLayout parent;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = (LinearLayout) itemView;
            newsTitle = itemView.findViewById(R.id.news_title);
            imageView = itemView.findViewById(R.id.news_item_image);
        }
    }

    public NewsListAdapter(ArrayList<NewsEntity> newsEntities,
                           @NonNull ListFragment.OnItemClickListener onItemClickListener) {
        this.newsEntities = newsEntities;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (newsEntities == null)
            return 0;

        return newsEntities.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewsEntity newsEntity = newsEntities.get(position);
        if (newsEntity == null)
            return;

        List<MediaEntity> mediaEntityList = newsEntity.getMultimedia();
        String thumbnailURL = "";
        if (!mediaEntityList.isEmpty()) {
            MediaEntity mediaEntity = mediaEntityList.get(0);
            thumbnailURL = mediaEntity.getUrl();
        }

        holder.newsTitle.setText(newsEntity.getTitle());
        DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                (Uri.parse(thumbnailURL))).setOldController(holder.imageView.getController()).build();
        holder.imageView.setController(draweeController);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(newsEntity);
            }
        });
    }
}
