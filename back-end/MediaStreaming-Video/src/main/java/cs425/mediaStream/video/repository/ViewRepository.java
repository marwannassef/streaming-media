package cs425.mediaStream.video.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import cs425.mediaStream.video.domain.View;


@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
	
	
//	@Query(value = ""
//			+ "select DATE_FORMAT(v.viewDate,'%Y-%m') as date,count(v.id) as number"
//			+ "from View v "
//			+ "where v.")
//	public List<StatisticsDto> getViewStatistics(long videoId);

}
