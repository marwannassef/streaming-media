package cs425.mediaStream.channel.service;

import java.util.List;
import java.util.Optional;

import cs425.mediaStream.channel.DTO.ChannelDTO;
import cs425.mediaStream.channel.DTO.StatisticsDto;
import cs425.mediaStream.channel.model.Channel;

public interface ChannelService {
	public ChannelDTO save(ChannelDTO channel);
	public List<ChannelDTO> findAll();
	public ChannelDTO findById(long id);
	public ChannelDTO deleteById(long id);
	public void subscribeToChannel(long channelId);
	public void unSubscribe(long channelId);
	public long findChannelByUserId();
	public List<StatisticsDto> getChannelStat();
	
}
