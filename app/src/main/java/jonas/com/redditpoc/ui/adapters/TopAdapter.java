package jonas.com.redditpoc.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.interfaces.OnItemClickListener;
import jonas.com.redditpoc.model.Post;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.MyViewHolder> {

    public static String THUMBNAIL_BASE_URL = "http://b.thumbs.redditmedia.com/";

    private Context context;
    private ArrayList<Post> dataList;
    private OnItemClickListener onItemClickListener;


    public TopAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.onItemClickListener = listener;
        dataList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new MyViewHolder(v,viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) == null ? R.layout.item_progress : R.layout.item_post;
    }

    public void setData(ArrayList<Post> post) {
        this.dataList.clear();
        this.dataList.addAll(post);
        notifyDataSetChanged();
    }

    private Post getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //if getItem(position) == null, means that we are showing the progress bar
        if (getItem(position) != null) {
            holder.bind(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public void showProgress() {
        dataList.add(null);
        notifyItemInserted(dataList.size() - 1);
    }

    public void hideProgress() {
        if (!dataList.isEmpty() && dataList.get(dataList.size() - 1) == null) {
            dataList.remove(dataList.size() - 1);
            notifyItemRemoved(dataList.size());
        }
    }

    public void addMoreData(ArrayList<Post> post) {
        dataList.addAll(post);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView title;
        private TextView detail;
        private TextView numberOfComments;

        MyViewHolder(View itemView, int viewType) {
            super(itemView);
            if(viewType == R.layout.item_post) {
                thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
                title = (TextView) itemView.findViewById(R.id.title);
                detail = (TextView) itemView.findViewById(R.id.detail);
                numberOfComments = (TextView) itemView.findViewById(R.id.comments);
                thumbnail.setOnClickListener(onImageClickListener);
            }
        }

        void bind(Post post) {
            title.setText(post.getTitle().trim());
            detail.setText(String.format(context.getResources().getString(R.string.post_detail),
                    post.getFormattedDate(), post.getAuthor()));
            numberOfComments.setText(String.format(context.getResources().getString(R.string.comments), post.getNum_comments()));

            if (post.getThumbnail() != null && !post.getThumbnail().isEmpty()
                    && post.getThumbnail().startsWith(THUMBNAIL_BASE_URL)) {
                Picasso.with(context).load(post.getThumbnail()).error(R.drawable.reddit).into(thumbnail);
            } else {
                thumbnail.setImageResource(R.drawable.reddit);
            }
        }

        View.OnClickListener onImageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        };
    }
}
