package cs425.mediaStream.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cs425.mediaStream.user.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
