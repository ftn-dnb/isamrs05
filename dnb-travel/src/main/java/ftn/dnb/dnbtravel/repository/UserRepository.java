package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    User findOneById(Long id);

    User findOneByUsername(String username);

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.authorityList a WHERE a.name LIKE 'ROLE_SYSTEM_ADMIN'")
    Collection<User> findAllHotelAdmins();

}
