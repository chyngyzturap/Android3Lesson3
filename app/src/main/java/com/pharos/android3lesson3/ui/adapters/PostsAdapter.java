package com.pharos.android3lesson3.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pharos.android3lesson3.R;
import com.pharos.android3lesson3.data.interfaces.OnClickListener;
import com.pharos.android3lesson3.data.interfaces.OnLongClick;
import com.pharos.android3lesson3.data.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.UserVH> {

    private List<Post> posts = new ArrayList<>();
    private OnLongClick onLongClick;
    private OnClickListener onClickListener;

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        holder.onBind(posts.get(position));

    }

    public void setLongListener(OnLongClick onLongClick){
        this.onLongClick = onLongClick;
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setList(List<Post> post) {
        posts.addAll(post);
        notifyDataSetChanged();
    }

    public void deletePost(Post post) {
        notifyItemRemoved((posts.indexOf(post)));
        posts.remove(post);
    }

    public class UserVH extends RecyclerView.ViewHolder{
        private TextView rvTitle;
        private TextView rvContent;
        private TextView rvUser;
        private TextView rvGroup;

        public UserVH(@NonNull View itemView) {
            super(itemView);
            rvTitle = itemView.findViewById(R.id.rv_title);
            rvContent = itemView.findViewById(R.id.rv_content);
            rvUser = itemView.findViewById(R.id.rv_user);
            rvGroup = itemView.findViewById(R.id.rv_group);
        }
        public void onBind(Post post){
            rvTitle.setText(post.getTitle());
            rvContent.setText(post.getContent());
            rvUser.setText(post.getUser().toString());
            rvGroup.setText(post.getGroup().toString());
            listeners();
        }

        private void listeners() {
            itemView.setOnLongClickListener(v -> {
                onLongClick.onLongClick(posts.get(getAdapterPosition()));
                return true;
            });
            itemView.setOnClickListener(v -> {
                onClickListener.onClickListener(posts.get(getAdapterPosition()));
            });
        }
    }
}
