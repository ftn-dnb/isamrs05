package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    User findOneById(Long id);

    User findByUsername(String username);

}
