package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.UserDTO;
import ftn.dnb.dnbtravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/whoami")
    public ResponseEntity<String> getRoleFromUser(Principal user){
        UserDTO u = userService.getUserByUsername(user.getName());

        if(u == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(u.getRole(),HttpStatus.OK);

    @RequestMapping(value = "/sysadmin_add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<?> addSystemAdmin(@RequestBody UserDTO user) {
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

}
