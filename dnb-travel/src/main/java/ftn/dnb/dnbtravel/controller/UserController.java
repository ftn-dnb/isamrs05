package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8000")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User userToInsert) {
        User savedUser = userService.addUser(userToInsert);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PutMapping(path = "/{id}")
    public User updateUserById(@PathVariable("id") Long id, @RequestBody User userToEdit) {
        userToEdit.setId(id);
        return userService.updateUser(userToEdit);
    }
}
