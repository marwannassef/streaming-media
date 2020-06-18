package cs425.mediaStream.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cs425.mediaStream.video.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
