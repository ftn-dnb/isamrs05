package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.UserDTO;
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


    @GetMapping(path = "")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, (user == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userToInsert) {
        UserDTO savedUser = userService.addUser(userToInsert);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUserById(@RequestBody UserDTO userToEdit) {
        UserDTO savedUser = userService.updateUser(userToEdit);
        return new ResponseEntity<>(savedUser, (savedUser == null) ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }
}
