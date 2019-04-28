package ftn.dnb.dnbtravel.dto;

import ftn.dnb.dnbtravel.model.Friendship;
import ftn.dnb.dnbtravel.model.FriendshipStatus;

public class FriendshipDTO {

    private String friendUsername;
    private String friendFirstName;
    private String friendLastName;
    private FriendshipStatus status;

    public FriendshipDTO() {
    }

    public FriendshipDTO(Friendship friendship) {
        this.friendFirstName = friendship.getFriend().getFirstName();
        this.friendLastName = friendship.getFriend().getLastName();
        this.friendUsername = friendship.getFriend().getUsername();
        this.status = friendship.getStatus();
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getFriendFirstName() {
        return friendFirstName;
    }

    public void setFriendFirstName(String friendFirstName) {
        this.friendFirstName = friendFirstName;
    }

    public String getFriendLastName() {
        return friendLastName;
    }

    public void setFriendLastName(String friendLastName) {
        this.friendLastName = friendLastName;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
