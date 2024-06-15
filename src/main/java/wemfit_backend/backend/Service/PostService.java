package wemfit_backend.backend.Service;

import wemfit_backend.backend.dto.PostRequest;
import wemfit_backend.backend.dto.PostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> getAllPost();

    PostResponse getPostById(Long id);

    PostResponse createPost(PostRequest post);

    PostResponse updatePost(Long id, PostRequest postRequest);

    void deletePost(Long id);


}