package wemfit_backend.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wemfit_backend.backend.Model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
