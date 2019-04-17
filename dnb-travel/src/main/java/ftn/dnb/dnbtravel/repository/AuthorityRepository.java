package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findOneById(Long id);
    List<Authority> findAll();
}
