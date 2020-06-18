package cs425.mediaStream.channel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cs425.mediaStream.channel.DTO.StatisticsDto;
import cs425.mediaStream.channel.model.Channel;
import cs425.mediaStream.channel.model.Subscriber;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long>{

	@Query(value = "select s from Channel c join c.subscribedUsers s where c.id = :channelId AND s.subscriberId = :subscriberId")
	public Subscriber getSubscriber(long channelId, long subscriberId);
	
	public Channel findByChannelOwner(int onwerId);
	
	@Query(value = "select count(*) from Channel c join c.subscribedUsers s where c.id = :channelId")
	public long getNumOfSubscrbiers(long channelId);
	
	@Query(value = ""
	+ "select new cs425.mediaStream.channel.DTO.StatisticsDto (DATE_FORMAT(sub.creationDate,'%Y-%m') as date,count(sub.id) as number)"
	+ " from Channel c join c.subscribedUsers sub "
	+ " where c.id= :channelId"
	+ " group by month(sub.creationDate)"
	+ " ORDER BY date DESC")
	public List<StatisticsDto> getSubscrbierStatistics(long channelId);
	
	@Query(value = "select c from Channel c join c.subscribedUsers s where s.subscriberId = :userId")
	public List<Channel> getSubscrbiedChannel(long userId);
	
	@Query(value = "select c from Channel c join c.subscribedUsers s where s.subscriberId = :userId and c.id = :channelId")
	public Channel checkSubscrbiedChannel(long userId,long channelId);
	
	
}
