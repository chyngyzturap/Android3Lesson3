package com.pharos.android3lesson3.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pharos.android3lesson3.R;
import com.pharos.android3lesson3.data.interfaces.OnClickListener;
import com.pharos.android3lesson3.data.interfaces.OnLongClick;
import com.pharos.android3lesson3.data.model.Post;
import com.pharos.android3lesson3.data.remote.RetrofitBuilder;
import com.pharos.android3lesson3.ui.adapters.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment implements OnLongClick, OnClickListener{
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private List<Post> list;
    private FloatingActionButton mBtnUpload;

    public PostsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        loadData();
    }

    private void loadData() {
        RetrofitBuilder
                .getInstance()
                .getPosts()
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            list = response.body();
                            postsAdapter.setList(list);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                    }
                });
    }

    private void initView() {
        list = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.recyclerView);
        postsAdapter = new PostsAdapter();
        recyclerView.setAdapter(postsAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext()
                        , DividerItemDecoration.VERTICAL));
        postsAdapter.setLongListener(this);
        postsAdapter.setOnClickListener(this);
        mBtnUpload = getView().findViewById(R.id.btn_fab);
        mBtnUpload.setOnClickListener(v ->
                fabClick()
        );
    }

    @Override
    public void onClickListener(Post post) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,PostsDetailFragment.newInstance(post))
                .addToBackStack(null)
                .commit();

    }


    private void fabClick() {
        Navigation.findNavController(getView()).navigate(R.id.postsDetailFragment);
    }


    @Override
    public void onLongClick(Post post) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setMessage("Wanna delete?")
                .setTitle("Delete post")
                .setIcon(R.drawable.ic_delete)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteById(post);
                    }
                });
        builder.create().show();
    }

    private void deleteById(Post post) {
        RetrofitBuilder
                .getInstance()
                .deletePost(post.getId())
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()) {
                            postsAdapter.deletePost(post);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                    }
                });
    }
}