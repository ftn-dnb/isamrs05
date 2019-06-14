package ftn.dnb.dnbtravel.controller;

import ftn.dnb.dnbtravel.dto.UserDTO;
import ftn.dnb.dnbtravel.messaging.Producer;
import ftn.dnb.dnbtravel.model.User;
import ftn.dnb.dnbtravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8000")
public class UserController {

    private UserService userService;

    private Producer producer;

    @Autowired
    public UserController(UserService userService, Producer producer) {
        this.userService = userService;
        this.producer = producer;
    }


    @GetMapping(path = "")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/hotelAdmins")
    public ResponseEntity<List<UserDTO>> getAllHotelAdmins() {
        List<UserDTO> hotelAdmins = userService.findAllHotelAdmins();
        return new ResponseEntity<>(hotelAdmins, HttpStatus.OK);
    }

    @GetMapping(path = "/racAdmins")
    public ResponseEntity<List<UserDTO>> getAllRacAdmins() {
        List<UserDTO> racAdmins = userService.findAllRACAdmins();
        return new ResponseEntity<>(racAdmins, HttpStatus.OK);
    }

    @GetMapping(path = "/airlineAdmins")
    public ResponseEntity<List<UserDTO>> getAllAirlineAdmins() {
        List<UserDTO> airlineAdmins = userService.findAllAirlineAdmins();
        return new ResponseEntity<>(airlineAdmins, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('AIRLINE_ADMIN') or hasRole('RAC_ADMIN') or hasRole('HOTEL_ADMIN') or hasRole('USER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, (user == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody UserDTO userToInsert) {
        ResponseEntity errResposne = userService.checkSameData(userToInsert.getUsername(),userToInsert.getEmail());
        if (errResposne != null){
            return errResposne;
        }
        UserDTO savedUser = userService.addUser(userToInsert);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }



    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('AIRLINE_ADMIN') or hasRole('RAC_ADMIN') or hasRole('HOTEL_ADMIN') or hasRole('USER')")
    public ResponseEntity<UserDTO> updateUserById(@RequestBody UserDTO userToEdit) {
        UserDTO savedUser = userService.updateUser(userToEdit);
        return new ResponseEntity<>(savedUser, (savedUser == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @GetMapping(path = "/whoami")
    public ResponseEntity<?> getRoleFromUser(Principal user) {
        UserDTO u = userService.getUserByUsername(user.getName());

        if (u == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(u.convertRoleToString(u.convertRoleToLong()), HttpStatus.OK);
    }

    @GetMapping(path = "/info/{username}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('AIRLINE_ADMIN') or hasRole('RAC_ADMIN') or hasRole('HOTEL_ADMIN') or hasRole('USER')")
    public ResponseEntity<UserDTO> getUserInfoByUsername(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, (user == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(value = "/sysadmin_add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<?> addSystemAdmin(@RequestBody UserDTO user) {
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @GetMapping(value = "/find/{firstLastName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDTO>> findUsersByName(@PathVariable String firstLastName) throws UnsupportedEncodingException {
        firstLastName = URLDecoder.decode(firstLastName, StandardCharsets.UTF_8.toString());
        return new ResponseEntity<>(userService.findUsersByName(firstLastName), HttpStatus.OK);
    }

    @PostMapping(value = "/friends/addFriend")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> requestFriendship(Principal user, @RequestBody String friendsUsername) {
        if (userService.addFriend(user.getName(), friendsUsername)) {
            producer.sendMessageTo(friendsUsername, user.getName());
            return new ResponseEntity<>(userService.getUserByUsername(user.getName()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/friends/removeFriend")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> removeFriend(Principal user, @RequestBody String friendsUsername) {
        if (userService.removeFriend(user.getName(), friendsUsername)) {
            return new ResponseEntity<>(userService.getUserByUsername(user.getName()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/friends/acceptFriend")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> acceptFriend(Principal user, @RequestBody String friendsUsername) {
        if (userService.acceptFriend(user.getName(), friendsUsername)) {
            return new ResponseEntity<>(userService.getUserByUsername(user.getName()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/friends/declineFriend")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> declineFriend(Principal user, @RequestBody String friendsUsername) {
        if (userService.declineFriend(user.getName(), friendsUsername)) {
            return new ResponseEntity<>(userService.getUserByUsername(user.getName()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/confirmMail/{id}")
    public ResponseEntity<UserDTO> confirmActivationLink(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.activateUser(id);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", Arrays.asList("http://localhost:8000/login"));
        return new ResponseEntity<>(userDTO, headers, HttpStatus.FOUND);
    }

    @PostMapping(value = "/passwordChange", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('AIRLINE_ADMIN') or hasRole('RAC_ADMIN') or hasRole('HOTEL_ADMIN')")
    public ResponseEntity<UserDTO> changePassword(@RequestBody UserDTO userDTO){

        HttpStatus status;
        UserDTO responseUser = userService.changePasswordAdmin(userDTO);
        if(responseUser != null){
            status = HttpStatus.OK;
        }

        else{
            status = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(responseUser,status);
    }

    @PostMapping(value = "/getUser")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('AIRLINE_ADMIN') or hasRole('RAC_ADMIN') or hasRole('HOTEL_ADMIN')")
    public ResponseEntity<UserDTO>getUserByUsername(@RequestBody UserDTO userDTO){


        UserDTO responseUser = userService.getUserByUsername(userDTO.getUsername());
        System.out.println("Poslao sam user-a");
        return  new ResponseEntity<>(responseUser,HttpStatus.OK);
    }
}
