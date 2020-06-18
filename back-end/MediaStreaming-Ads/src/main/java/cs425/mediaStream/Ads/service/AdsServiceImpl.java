package cs425.mediaStream.Ads.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs425.mediaStream.Ads.domain.Ads;
import cs425.mediaStream.Ads.repository.AdsRepository;
import cs425.mediaStream.Ads.service.dto.AdDto;

@Service
public class AdsServiceImpl implements AdsService {
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AdsRepository adRepository;

	@Override
	public List<AdDto> getAllAds() {
		// TODO Auto-generated method stub
		return convertEntityListToResponseList(adRepository.findAll());
	}

	@Override
	public AdDto createAd(AdDto ad) {
		// TODO Auto-generated method stub
		Ads newAd = mapper.map(ad, Ads.class);
		return mapper.map(adRepository.save(newAd), AdDto.class);
	}

	@Override
	public void deleteAd(long id) {
		// TODO Auto-generated method stub
		adRepository.deleteById(id);
	}

	@Override
	public AdDto updateAd(AdDto ad) {
		// TODO Auto-generated method stub
		Ads updatedAd = mapper.map(ad, Ads.class);
		return mapper.map(adRepository.save(updatedAd), AdDto.class);
	}

	@Override
	public Optional<AdDto> getAd(long id) {
		// TODO Auto-generated method stub
		Optional<Ads> ad =adRepository.findById(id);
		if(!ad.isPresent())
			return Optional.ofNullable(null);
		return Optional.ofNullable(mapper.map(ad.get(), AdDto.class));
	}

	@Override
	public List<AdDto> convertEntityListToResponseList(List<Ads> entityList) {
		// TODO Auto-generated method stub
		if(entityList == null)
			return null;
		return entityList.stream()
				.map(entity -> mapper.map(entity, AdDto.class))
				.collect(Collectors.toList());
	}

}
