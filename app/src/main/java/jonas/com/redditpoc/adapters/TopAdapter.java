package jonas.com.redditpoc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.model.Children;
import jonas.com.redditpoc.model.Post;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.MyViewHolder> {

    Context context;
    List<Children> dataList;

    public TopAdapter(Context context) {
        this.context = context;
        dataList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new MyViewHolder(v);
    }

    public void setData(List<Children> children) {
        this.dataList.clear();
        this.dataList.addAll(children);
        notifyDataSetChanged();
    }

    private Post getItem(int position) {
        return dataList.get(position).getPost();
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItem(position) != null) {
            holder.bind(getItem(position));
        }

    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView title;
        private TextView detail;
        private TextView numberOfComments;

        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            detail = (TextView) itemView.findViewById(R.id.detail);
            numberOfComments = (TextView) itemView.findViewById(R.id.comments);

        }

        public void bind(Post post) {
            title.setText(post.getTitle().trim());

            detail.setText(String.format(context.getResources().getString(R.string.post_detail),
                    post.getCreated_utc(), post.getAuthor()));
            numberOfComments.setText(String.valueOf(post.getNum_comments()));
            if(post.getThumbnail() != null && !post.getThumbnail().isEmpty()){
                Picasso.with(context).load(post.getThumbnail()).into(thumbnail);
            }
        }
    }
}
