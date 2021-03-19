package com.pharos.android3lesson3.data.remote;

import com.pharos.android3lesson3.data.model.Post;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Android3Api {
    @GET(EndPoints.POSTS_END_POINTS)
    Call<List<Post>> getPosts();

    @Headers("Content-Type: application/json")
    @PUT(EndPoints.ENDPOINT_BY_ID)
    Call<Post> putPost(@Path("postId") @Body Post post);

    @DELETE(EndPoints.ENDPOINT_BY_ID)
    Call<Object> deletePost(@Path("postId")int postId);

    @POST(EndPoints.POSTS_END_POINTS)
    Call<Post> createPost(@Body Post post);
}
