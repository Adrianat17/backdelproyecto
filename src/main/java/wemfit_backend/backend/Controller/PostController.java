package wemfit_backend.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemfit_backend.backend.Service.PostService;
import wemfit_backend.backend.dto.PostRequest;
import wemfit_backend.backend.dto.PostResponse;

@RestController
@RequestMapping(value = "/post")
@RequiredArgsConstructor

public class PostController {


    private final PostService postService;


    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllPost() {

        try {
            // Devuelve una respuesta con todos los posts y estado OK si no hay errores.
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPost());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable(name = "id") Long id) {

        return postService.getPostById(id);
    }

    @PostMapping(value = "/crear_post")
    //poniendo ? podemos manejar excepciciones
    public ResponseEntity<String> createPost(@RequestBody PostRequest post) {

        try {
            postService.createPost(post);
            return ResponseEntity.status(HttpStatus.OK).body("Post creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable(value = "id") Long id) {
        postService.deletePost(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostRequest postRequest) {

        try {
            postService.updatePost(id, postRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Post actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

