package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.UserDTO;
import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        users.stream().forEach(user -> dtos.add(new UserDTO(user)));
        return dtos;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findOneById(id);

        if (user == null)
            return null;

        return new UserDTO(user);
    }

    public UserDTO addUser(UserDTO user) {
        User savedUser = userRepository.save(new User(user));

        if (savedUser == null)
            return null;

        return new UserDTO(savedUser);
    }

    public UserDTO updateUser(UserDTO user) {
        List<UserDTO> users = this.getAllUsers();
        boolean userExists = users.stream().anyMatch(userDTO -> userDTO.getId().equals(user.getId()));

        if (!userExists)
            return null;

        long numOfMatches = users.stream()
                            .filter(userDTO -> userDTO.getEmail().equals(user.getEmail()) && !userDTO.equals(user))
                            .count();

        // Email already exists in database
        if (numOfMatches > 0)
            return null;

        if (!user.getPassword().equals(user.getRepeatPassword()))
            return null;

        User savedUser = userRepository.save(new User(user));

        if (savedUser == null)
            return null;

        return new UserDTO(savedUser);
    }

    public  UserDTO getUserByUsername (UserDTO user){
        User userR = userRepository.findByUsername(user.getUsername());

        if (userR == null)
            return null;

        return new UserDTO(userR);
    }

}
