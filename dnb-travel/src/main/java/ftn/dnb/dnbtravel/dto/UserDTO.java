package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {


    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;
    private String username;
    private Long role;
    private List<FriendshipDTO> friendships;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String password, String repeatPassword, String username, Long role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.username = username;
        this.role = role;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.role = new Long(convertRoleToInt(user.getAuthorityList().get(0).getAuthority()));

        this.friendships = new ArrayList<>();
        user.getFriendships().stream().forEach(f -> friendships.add(new FriendshipDTO(f)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }
  
    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRole() {
        return this.role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public List<FriendshipDTO> getFriendships() {
        return friendships;
    }

    public void setFriendships(List<FriendshipDTO> friendships) {
        this.friendships = friendships;
    }

    private int convertRoleToInt(String s){
        switch (s) {
            case "ROLE_USER":{
                return 1;
            }
            case "ROLE_SYSTEM_ADMIN":{
                return 2;
            }
            case "ROLE_AIRLINE_ADMIN":{
                return 3;
            }
            case "ROLE_HOTEL_ADMIN":{
                return 4;
            }
            case "ROLE_RAC_ADMIN":{
                return 5;
            }
        }
        return 0; // error
    }

    public String convertRoleToString(Long l){
        int a = (int)l.longValue();
        switch (a) {
            case 1:{
                return "ROLE_USER";
            }
            case 2:{
                return "ROLE_SYSTEM_ADMIN";
            }
            case 3:{
                return "ROLE_AIRLINE_ADMIN";
            }
            case 4:{
                return "ROLE_HOTEL_ADMIN";
            }
            case 5:{
                return "ROLE_RAC_ADMIN";
            }
        }
        return "err"; // error
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
