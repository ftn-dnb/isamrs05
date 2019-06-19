package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RACBranchOfficeRepository extends JpaRepository<BranchOffice, Long> {

    BranchOffice findOneById(Long id);

}
