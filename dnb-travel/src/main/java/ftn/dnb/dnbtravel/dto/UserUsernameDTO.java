package ftn.dnb.dnbtravel.dto;

public class UserUsernameDTO {

    private String username;

    public UserUsernameDTO(){

    }

    public UserUsernameDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
