package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAll();

}
