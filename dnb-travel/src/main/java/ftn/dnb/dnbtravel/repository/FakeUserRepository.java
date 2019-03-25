package ftn.dnb.dnbtravel.repository;

import ftn.dnb.dnbtravel.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("FakeUserRepository")
public class FakeUserRepository implements UserRepository {

    private static List<User> users = new ArrayList<User>();

    @Override
    public User addUser(UUID id, User userToInsert) {
        User userToSave = new User(id, userToInsert.getFirstName(), userToInsert.getLastName());
        users.add(userToSave);
        return userToSave;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public int updateUserById(UUID id, User newUser) {
        int result = getUserById(id).map(user -> {
            int indexOfUserToUpdate = users.indexOf(user);
            if (indexOfUserToUpdate >= 0) {
                users.set(indexOfUserToUpdate, new User(id, newUser.getFirstName(), newUser.getLastName()));
                return 1;
            }
            return 0;
        }).orElse(0);

        return result;
    }
}
