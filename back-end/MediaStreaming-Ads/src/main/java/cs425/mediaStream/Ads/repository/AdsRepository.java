package cs425.mediaStream.Ads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cs425.mediaStream.Ads.domain.Ads;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long>  {

}
