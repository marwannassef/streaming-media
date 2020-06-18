package cs425.mediaStream.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cs425.mediaStream.channel.model.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

}
