package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User addUser(UUID id, User userToInsert);

    List<User> getAllUsers();

    Optional<User> getUserById(UUID id);

    int updateUserById(UUID id, User userToEdit);
}
