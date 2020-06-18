package cs425.mediaStream.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cs425.mediaStream.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByEmailAndIsBannedFalse(String email);
	
	@Query(value = "select count(*) as size from User")
	public int getNumberOfUsers();
}
