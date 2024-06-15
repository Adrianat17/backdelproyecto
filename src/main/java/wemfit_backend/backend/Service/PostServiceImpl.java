package wemfit_backend.backend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wemfit_backend.backend.Exceptions.PostNotFoundException;
import wemfit_backend.backend.Model.Post;
import wemfit_backend.backend.Repository.PostRepository;
import wemfit_backend.backend.dto.PostRequest;
import wemfit_backend.backend.dto.PostResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<PostResponse> getAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : posts) {
            postResponses.add(toPostResponse(post));
        }

        return postResponses;
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return this.toPostResponse(post);
    }

    public PostResponse createPost(PostRequest postRequest) {
        return this.toPostResponse(postRepository.save(this.toPost(postRequest)));
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest postRequest) {
        Post existingPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        // Actualizamos los campos del post existente con los nuevos valores
        existingPost.setTitulo(postRequest.getTitulo());
        existingPost.setDescripcion(postRequest.getDescripcion());
        existingPost.setFecha(LocalDate.now()); // Opcional: puedes o no actualizar la fecha

        // Guardamos el post actualizado en la base de datos
        return this.toPostResponse(postRepository.save(existingPost));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private Post toPost(PostRequest postRequest) {
        Post post = new Post();

        post.setTitulo(postRequest.getTitulo());
        post.setDescripcion(postRequest.getDescripcion());
        post.setFecha(LocalDate.now());
        post.setCategoria(postRequest.getCategoria());

        return post;
    }


    private PostResponse toPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitulo(post.getTitulo());
        postResponse.setDescripcion(post.getDescripcion());
        postResponse.setFecha(post.getFecha());
        postResponse.setCategoria(post.getCategoria());

        return postResponse;

    }
}
