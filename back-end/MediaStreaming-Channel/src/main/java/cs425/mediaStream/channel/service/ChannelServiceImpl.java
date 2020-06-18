package cs425.mediaStream.channel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.bouncycastle.LICENSE;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cs425.mediaStream.channel.DTO.ChannelDTO;
import cs425.mediaStream.channel.DTO.StatisticsDto;
import cs425.mediaStream.channel.clientService.videoClientService;
import cs425.mediaStream.channel.filter.JwtUserDetails;
import cs425.mediaStream.channel.model.Channel;
import cs425.mediaStream.channel.model.Subscriber;
import cs425.mediaStream.channel.repository.ChannelRepository;
import cs425.mediaStream.channel.repository.SubscriberRepository;



@Service
@Transactional
public class ChannelServiceImpl implements ChannelService{
	@Autowired
	private ChannelRepository channelRepo;
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private videoClientService videoClientService;
	
	@Autowired
	private SubscriberRepository subscriberRepo;

	@Override
	public List<ChannelDTO> findAll() {
		List<Channel> channels = channelRepo.findAll();
		return channels.stream().map(this::convert).collect(Collectors.toList());
	}

	@Override
	public ChannelDTO findById(long id) {
		Optional<Channel> channel = channelRepo.findById(id);
		if(!channel.isPresent()) 
			return null;
		ChannelDTO channelDto =  convert(channel.get());
		channelDto.setVideos(videoClientService.getVideos(id));
		channelDto.setNumOfSubscrbiers(channelRepo.getNumOfSubscrbiers(channelDto.getId()));
		List<ChannelDTO> SubscribedChannels= getSubscribedChannels(channel.get().getChannelOwner());
		channelDto.setIsSubscribed(checkIsSubscrbied(channelDto.getId()));
		channelDto.setSubscribedChannels(SubscribedChannels);
		return channelDto;
	}
	private List<ChannelDTO> getSubscribedChannels(int channelOwner){
		return channelRepo.getSubscrbiedChannel(channelOwner).stream().map(this::convert).collect(Collectors.toList());
	}
	private boolean checkIsSubscrbied(long channelId) {
		try {
			JwtUserDetails userDetails =getPrincipal();
			System.out.println(userDetails.getId()+"  mossad mossad "+channelId);
			if(channelRepo.checkSubscrbiedChannel(userDetails.getId(), channelId)==null) {
				System.out.println(userDetails.getId()+"  mossad In In "+channelId);
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
			return false;
		}

	}
	
	public ChannelDTO findByUserId() {
		Optional<Channel> channel = channelRepo.findById(findChannelByUserId());
		if(!channel.isPresent()) 
			return null;
		ChannelDTO channelDto =  convert(channel.get());
		channelDto.setVideos(videoClientService.getVideos(findChannelByUserId()));
		channelDto.setNumOfSubscrbiers(channelRepo.getNumOfSubscrbiers(channelDto.getId()));
		channelDto.setSubscribedChannels(getSubscribedChannels(channelDto.getChannelOwner()));
		return channelDto;
	}
	
	@Override
	public void subscribeToChannel(long channelId) {
		// TODO Auto-generated method stub
		Optional<Channel> channel = channelRepo.findById(channelId);
		//System.out.println(channel.get().getSubscribedUsers().size());
		JwtUserDetails userDetails =getPrincipal();
		if(!channel.isPresent() || channelRepo.getSubscriber(channelId, userDetails.getId())!=null )
			throw new ResponseStatusException(HttpStatus.CONFLICT, "you already subscribed");
		channel.get().addSubscribers(new Subscriber(userDetails.getId()));
	}
	
	@Override
	public void unSubscribe(long channelId) {
		// TODO Auto-generated method stub
		JwtUserDetails userDetails =getPrincipal();
		Subscriber subscriber = channelRepo.getSubscriber(channelId, userDetails.getId());
		if(subscriber ==null)
			throw new ResponseStatusException(HttpStatus.CONFLICT, "your should subscribe first");
		subscriberRepo.delete(subscriber);
	}
	
	@Override
	public ChannelDTO deleteById(long id) {
		Optional<Channel> channel = channelRepo.findById(id);
		channelRepo.deleteById(id);
		if(channel.isPresent()) 
			return convert(channel.get());
		return null;
	}

	@Override
	public ChannelDTO save(ChannelDTO channeldto) {
		Channel channel = mapper.map(channeldto, Channel.class);
		channelRepo.save(channel);
		return channeldto;
	}

	public ChannelDTO convert(Channel channel) {
        return mapper.map(channel, ChannelDTO.class);
    }
	private JwtUserDetails getPrincipal() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return (JwtUserDetails) principal;
		
	}

	@Override
	public long findChannelByUserId() {
		// TODO Auto-generated method stub
		JwtUserDetails userDetails =getPrincipal();
		Long id = new Long(userDetails.getId());
		return channelRepo.findByChannelOwner(id.intValue()).getId();
	}
	
	@Override
	public List<StatisticsDto> getChannelStat(){
		long userChannelId = findChannelByUserId();
		List<StatisticsDto> subscrbiers= new ArrayList<StatisticsDto>();
		subscrbiers.addAll(channelRepo.getSubscrbierStatistics(userChannelId)); 
		return subscrbiers;
		
	}
	


}
