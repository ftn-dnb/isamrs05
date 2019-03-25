package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("FakeUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User userToInsert) {
        return userRepository.addUser(UUID.randomUUID(), userToInsert);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.getUserById(id);
    }

    public int updateUserById(UUID id, User userToEdit) {
        return userRepository.updateUserById(id, userToEdit);
    }
}
