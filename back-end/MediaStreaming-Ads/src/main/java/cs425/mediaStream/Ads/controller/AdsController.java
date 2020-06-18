package cs425.mediaStream.Ads.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cs425.mediaStream.Ads.service.AdsService;
import cs425.mediaStream.Ads.service.dto.AdDto;
import javassist.NotFoundException;

@RestController
@RequestMapping("/ad")
public class AdsController {
	
	@Autowired
	private AdsService adService;
	
	@GetMapping(value = "list")
	public List<AdDto> getAllAds(){
		return adService.getAllAds();
	}
	
	@GetMapping(value = "{id}")
	public AdDto getAd(@PathVariable long id) {
		return adService.getAd(id).
				orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ad not found"));
	}
	
	@PostMapping
	public AdDto addAd(@Valid @RequestBody AdDto ad) {
		System.out.println(ad);
		return adService.createAd(ad);
	}
	
	@PutMapping
	public AdDto updateAd(@Valid @RequestBody AdDto ad) {
		return adService.updateAd(ad);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteAd(@Valid @PathVariable long id) {
		adService.deleteAd(id);
	}

}
