package cs425.mediaStream.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cs425.mediaStream.video.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long>{
	
//	@Query(value = "from video v join ")
//	public Like findByIdAndUserId(long id,long userId);

//	@Query(value = "select count(*) from Like l where l.  ")
//	public long getNumOfLikes();
}
