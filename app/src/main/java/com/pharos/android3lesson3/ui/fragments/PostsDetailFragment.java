package com.pharos.android3lesson3.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pharos.android3lesson3.R;
import com.pharos.android3lesson3.data.model.Post;
import com.pharos.android3lesson3.data.remote.RetrofitBuilder;
import com.pharos.android3lesson3.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsDetailFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";
    private static final String ARG_USER = "user";
    private static final String ARG_GROUP = "group";
    private static final String ARG_ID = "id";

    private String mTitle;
    private String mContent;
    private Integer mUser;
    private Integer mGroup;
    private Integer mId;
    private boolean isUpdating;
    private EditText editTitle, editContent, editUser, editGroup;
    private Button mBtnUpload;

    public PostsDetailFragment(){}

    public static PostsDetailFragment newInstance(Post post) {
        PostsDetailFragment fragment = new PostsDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, post.getTitle());
        args.putString(ARG_CONTENT, post.getContent());
        args.putInt(ARG_USER, post.getUser());
        args.putInt(ARG_GROUP, post.getGroup());
        args.putInt(ARG_ID, post.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mContent = getArguments().getString(ARG_CONTENT);
            mUser = getArguments().getInt(ARG_USER);
            mGroup = getArguments().getInt(ARG_GROUP);
            mId = getArguments().getInt(ARG_ID);
        }
    }

    private void setEditFields() {
        editTitle.setText(mTitle);
        editContent.setText(mContent);
        editUser.setText(mUser.toString());
        editGroup.setText(mGroup.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        if (isUpdating) setEditFields();
        mBtnUpload.setOnClickListener(v -> uploadClick());
    }

    private void init() {
        editTitle = getView().findViewById(R.id.edit_title_upload);
        editContent = getView().findViewById(R.id.edit_desc_upload);
        editUser = getView().findViewById(R.id.edit_user_upload);
        editGroup = getView().findViewById(R.id.edit_group_upload);
        mBtnUpload = getView().findViewById(R.id.upload_btn);
    }

    private void uploadClick() {
        String title = editTitle.getText().toString();
        String desc = editContent.getText().toString();
        String user = editUser.getText().toString();
        String group = editGroup.getText().toString();

        Post post = new Post(title
                , desc
                , Integer.parseInt(user)
                , Integer.parseInt(group));
        if (isUpdating) upDatePost(post);
        else uploadPost(post);
        Navigation.findNavController(getView()).navigateUp();

    }
    private void upDatePost(Post post) {
        RetrofitBuilder
                .getInstance()
                .putPost(post)
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            getParentFragmentManager().popBackStackImmediate();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                    }
                });

    }
    private void uploadPost(Post post) {
        RetrofitBuilder
                .getInstance()
                .createPost(post)
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                    }
                });

    }
}