package ftn.dnb.dnbtravel.service;

import ftn.dnb.dnbtravel.dto.UserDTO;
import ftn.dnb.dnbtravel.model.*;
import ftn.dnb.dnbtravel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private RentACarCompanyRepository rentACarCompanyRepository;


    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        users.stream().forEach(user -> dtos.add(new UserDTO(user)));
        return dtos;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findOneById(id);

        if (user == null)
            return null;

        return new UserDTO(user);
    }

    public List<UserDTO> findAllHotelAdmins() {
        ArrayList<UserDTO> userList = new ArrayList<>();
        ArrayList<UserDTO> userList1 = new ArrayList<>();
        for (User user : userRepository.findAllHotelAdmins()) {
            userList.add(new UserDTO(user));
        }

        for (UserDTO user : userList) {
            userList1.add(user);
            for (Hotel hotel : hotelRepository.findAll()) {
                if (hotel.getAdministrator().getUsername().equals(user.getUsername())) {
                    userList1.remove(user);
                    break;
                }
            }
        }
        return userList1;
    }

    public List<UserDTO> findAllRACAdmins() {
        ArrayList<UserDTO> userList = new ArrayList<>();
        ArrayList<UserDTO> userList1 = new ArrayList<>();
        for (User user : userRepository.findAllRACAdmins()) {
            userList.add(new UserDTO(user));
        }

        for (UserDTO user : userList) {
            userList1.add(user);
            for (RentACarCompany rac : rentACarCompanyRepository.findAll()) {
                if (rac.getAdministrator().getUsername().equals(user.getUsername())) {
                    userList1.remove(user);
                    break;
                }
            }
        }
        return userList1;
    }

    public List<UserDTO> findAllAirlineAdmins() {
        ArrayList<UserDTO> userList = new ArrayList<>();
        ArrayList<UserDTO> userList1 = new ArrayList<>();
        for (User user : userRepository.findAllAirlineAdmins()) {
            userList.add(new UserDTO(user));
        }

        for (UserDTO user : userList) {
            userList1.add(user);
            for (Airline airline : airlineRepository.findAll()) {
                if (airline.getAdministrator().getUsername().equals(user.getUsername())) {
                    userList1.remove(user);
                    break;
                }
            }
        }
        return userList1;
    }


    public UserDTO addUser(UserDTO user) {
        User userProba = new User(user);
        userProba.setAuthorityList(new ArrayList<Authority>());
        userProba.getAuthorityList().add(authorityRepository.findOneById(user.convertRoleToLong()));
        User savedUser = userRepository.save(userProba);

        try {
            emailService.sendNotificationAsync(savedUser);
        } catch (Exception e) {
            System.out.println("E-Mail failed to send. " + e.getMessage());
        }

        //List<Authority> lista = authorityRepository.findAll();

        if (savedUser == null)
            return null;

        return new UserDTO(savedUser);
    }

    public UserDTO activateUser(Long id) {
        User user = userRepository.findOneById(id);
        user.setEnabled(true);
        userRepository.save(user);
        return new UserDTO(user);
    }

    public UserDTO updateUser(UserDTO user) {
        User userToUpdate = userRepository.findOneById(user.getId());

        if (userToUpdate == null)
            return null;

        if (!user.getPassword().equals(user.getRepeatPassword()))
            return null;

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));

        userToUpdate = userRepository.save(userToUpdate);

        return new UserDTO(userToUpdate);
    }

    public  UserDTO getUserByUsername (UserDTO user){
        User userR = userRepository.findByUsername(user.getUsername());

        if (userR == null)
            return null;

        return new UserDTO(userR);
    }

    public UserDTO getUserByUsername(String user){
        User userR = userRepository.findByUsername(user);

        if (userR == null)
            return null;

        return new UserDTO(userR);
    }


    public ResponseEntity<?> checkSameData(String username, String email) {
        // same username in database
        User userSameName = userRepository.findByUsername(username);
        if (userSameName != null) {
            return new ResponseEntity<>("That username is already in use", HttpStatus.CONFLICT);
        }

        // same email in database
        User userSameMail = userRepository.findByEmail(email);
        if (userSameMail != null) {
            return new ResponseEntity<>("That email address is already in use", HttpStatus.CONFLICT);
        }
        return null;
    }

    public List<UserDTO> findUsersByName(String firstLastName) {
        String name = firstLastName.toLowerCase().replaceAll("\\s+", "");
        List<UserDTO> users = getAllUsers();
        List<UserDTO> result = users.stream().filter(u -> {
            String userName = u.getFirstName() + u.getLastName();
            userName = userName.toLowerCase();
            return userName.toLowerCase().equals(name) && u.convertRoleToLong() == 1;
        }).collect(Collectors.toList());

        return result;
    }

    public boolean addFriend(String myUsername, String friendsUsername) {
        User me = userRepository.findOneByUsername(myUsername);
        User friend = userRepository.findOneByUsername(friendsUsername);

        if (me == null || friend == null)
            return false;

        if (me.equals(friend))
            return false;

        boolean friendshipExists1 = me.getFriendships().stream()
                .anyMatch(f -> f.getFriend().equals(friend) && (f.getStatus().equals(FriendshipStatus.ACCEPTED) || f.getStatus().equals(FriendshipStatus.REQUEST)));

        boolean friendshipExists2 = friend.getFriendships().stream()
                .anyMatch(f -> f.getFriend().equals(me) && (f.getStatus().equals(FriendshipStatus.ACCEPTED) || f.getStatus().equals(FriendshipStatus.REQUEST)));

        if (friendshipExists1 || friendshipExists2)
            return false;

        me.getFriendships().add(new Friendship(friend, FriendshipStatus.PENDING));
        friend.getFriendships().add(new Friendship(me, FriendshipStatus.REQUEST));

        userRepository.save(me);
        userRepository.save(friend);

        return true;
    }

    public boolean removeFriend(String myUsername, String friendsUsername) {
        User me = userRepository.findOneByUsername(myUsername);
        User friend = userRepository.findByUsername(friendsUsername);

        if (me == null || friend == null)
            return false;

        Friendship fr1 = me.getFriendships().stream().filter(f -> f.getFriend().equals(friend)).findFirst().orElse(null);
        Friendship fr2 = friend.getFriendships().stream().filter(f -> f.getFriend().equals(me)).findFirst().orElse(null);

        if (fr1 == null || fr2 == null)
            return false;

        me.getFriendships().remove(fr1);
        friend.getFriendships().remove(fr2);

        userRepository.save(me);
        userRepository.save(friend);

        return true;
    }

    public boolean acceptFriend(String myUsername, String friendsUsername) {
        User me = userRepository.findOneByUsername(myUsername);
        User friend = userRepository.findByUsername(friendsUsername);

        if (me == null || friend == null)
            return false;

        Friendship fr1 = me.getFriendships().stream().filter(f -> f.getFriend().equals(friend)).findFirst().orElse(null);
        Friendship fr2 = friend.getFriendships().stream().filter(f -> f.getFriend().equals(me)).findFirst().orElse(null);

        if (fr1 == null || fr2 == null)
            return false;

        fr1.setStatus(FriendshipStatus.ACCEPTED);
        fr2.setStatus(FriendshipStatus.ACCEPTED);

        userRepository.save(me);
        userRepository.save(friend);


        return true;
    }

    public boolean declineFriend(String myUsername, String friendsUsername) {
        User me = userRepository.findOneByUsername(myUsername);
        User friend = userRepository.findByUsername(friendsUsername);

        if (me == null || friend == null)
            return false;

        Friendship fr1 = me.getFriendships().stream().filter(f -> f.getFriend().equals(friend)).findFirst().orElse(null);
        Friendship fr2 = friend.getFriendships().stream().filter(f -> f.getFriend().equals(me)).findFirst().orElse(null);

        if (fr1 == null || fr2 == null)
            return false;

        me.getFriendships().remove(fr1);
        friend.getFriendships().remove(fr2);

        userRepository.save(me);
        userRepository.save(friend);

        return true;
    }

    public UserDTO changePasswordAdmin(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAdmin_password(false);

        user = userRepository.save(user);
        return new UserDTO(user);

    }

}
