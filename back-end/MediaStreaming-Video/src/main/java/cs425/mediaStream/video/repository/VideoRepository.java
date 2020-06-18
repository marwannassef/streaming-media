	package cs425.mediaStream.video.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cs425.mediaStream.video.domain.Like;
import cs425.mediaStream.video.domain.Video;
import cs425.mediaStream.video.service.dto.StatisticsDto;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{
	
	public List<Video> getVideosByIsActiveTrueAndIsbannedFalse();
	
	//@Query(name = "from Video v where v.channel.id = :id")
//	@Query(value = "select * from `media-stream`.video v where v.channel_id = :id",nativeQuery = true)
	public List<Video> getVideosByChannelNumberAndIsActiveTrue(long id);
	 
	@Query(value = "from Video v where v.id != :id and v.isActive = true and v.isbanned = false")
	public List<Video> getRecommendedVideos(long id);
	
	@Query(value = "select l from Video v join v.likes l where v.id = :videoId AND l.userId = :userId")
	public Like getLike(long videoId, long userId);
	
	@Query(value = "select count(*) from Video v join v.likes l where v.id = :videoId")
	public long getNumOfLikes(long videoId);
	
	@Query(value = "select count(*) from Video v join v.views s where v.id = :videoId")
	public long getNumOfViews(long videoId);
	
	@Query(value = ""
	+ "select new cs425.mediaStream.video.service.dto.StatisticsDto (DATE_FORMAT(vs.viewDate,'%Y-%m') as date,count(vs.id) as number)"
	+ " from Video v join v.views vs "
	+ " where v.id= :videoId"
	+ " group by month(vs.viewDate)"
	+ " ORDER BY date")
	public List<StatisticsDto> getViewStatistics(long videoId);
	
	@Query(value = ""
	+ "select new cs425.mediaStream.video.service.dto.StatisticsDto (v.videoTitle ,count(vs.id) as number)"
	+ " from Video v join v.views vs "
	+ " where v.channelNumber = :channelId"
	+ " group by v.id"
	+ " ORDER BY number desc")
	public List<StatisticsDto> getTopVideos(long channelId);
	
	@Query(value = "select v from Video v where concat(v.description, ' '" + ", v.videoTitle,' ') like concat('%',:search ,'%')")
	public List<Video> searchVideo(String search, Pageable pageableg);
	
	@Query(value = "select count(*) as size from Video")
	public int getNumberOfVideos();
	
}
