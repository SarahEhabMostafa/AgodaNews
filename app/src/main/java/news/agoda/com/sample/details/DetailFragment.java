package news.agoda.com.sample.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import news.agoda.com.sample.R;
import news.agoda.com.sample.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnButtonClick} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private String storyURL, title, summary, imageURL;

    private OnButtonClick mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param storyURL Full URL of the story
     * @param title    Title of the story
     * @param summary  Summary of the story
     * @param imageURL ImageURL of the story
     * @return A new instance of fragment DetailFragment.
     */
    public static DetailFragment newInstance(String storyURL, String title,
                                             String summary, String imageURL) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_STORY_URL, storyURL);
        args.putString(Constants.KEY_TITLE, title);
        args.putString(Constants.KEY_SUMMARY, summary);
        args.putString(Constants.KEY_IMAGE_URL, imageURL);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storyURL = getArguments().getString(Constants.KEY_STORY_URL);
            title = getArguments().getString(Constants.KEY_TITLE);
            summary = getArguments().getString(Constants.KEY_SUMMARY);
            imageURL = getArguments().getString(Constants.KEY_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView titleView = rootView.findViewById(R.id.title);
        DraweeView imageView = rootView.findViewById(R.id.news_image);
        TextView summaryView = rootView.findViewById(R.id.summary_content);
        Button fullStoryButton = rootView.findViewById(R.id.full_story_link);

        titleView.setText(title);
        summaryView.setText(summary);

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(imageURL)))
                .setOldController(imageView.getController()).build();
        imageView.setController(draweeController);

        fullStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonClick(storyURL);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClick) {
            mListener = (OnButtonClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnButtonClick");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnButtonClick {
        void onButtonClick(String url);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.KEY_TITLE, title);
        outState.putString(Constants.KEY_STORY_URL, storyURL);
        outState.putString(Constants.KEY_SUMMARY, summary);
        outState.putString(Constants.KEY_IMAGE_URL, imageURL);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            storyURL = getArguments().getString(Constants.KEY_STORY_URL);
            title = getArguments().getString(Constants.KEY_TITLE);
            summary = getArguments().getString(Constants.KEY_SUMMARY);
            imageURL = getArguments().getString(Constants.KEY_IMAGE_URL);
        }
    }
}
