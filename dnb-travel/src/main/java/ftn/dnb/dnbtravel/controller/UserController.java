package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User userToInsert) {
        User savedUser = userService.addUser(userToInsert);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id).orElse(null);
    }

    @PutMapping(path = "/{id}")
    public int updateUserById(@PathVariable("id") UUID id, @RequestBody User userToEdit) {
        return userService.updateUserById(id, userToEdit);
    }
}
