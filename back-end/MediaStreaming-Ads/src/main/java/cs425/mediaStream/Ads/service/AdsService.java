package cs425.mediaStream.Ads.service;

import java.util.List;
import java.util.Optional;

import cs425.mediaStream.Ads.domain.Ads;
import cs425.mediaStream.Ads.service.dto.AdDto;

public interface AdsService {
	
	public List<AdDto> getAllAds();
	
	public AdDto createAd(AdDto ad);
	
	public void deleteAd(long id);
	
	public AdDto updateAd(AdDto ad);
	
	public Optional<AdDto> getAd(long id);
	
	List<AdDto> convertEntityListToResponseList(List<Ads> entityList);

}
