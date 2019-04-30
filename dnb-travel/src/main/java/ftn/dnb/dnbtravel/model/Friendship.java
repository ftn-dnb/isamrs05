package ftn.dnb.dnbtravel.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User friend;

    @Column(name = "status")
    private FriendshipStatus status;

    private Friendship() {
    }

    public Friendship(Long id, User friend, FriendshipStatus status) {
        this.id = id;
        this.friend = friend;
        this.status = status;
    }

    public Friendship(User friend) {
        this.friend = friend;
        this.status = FriendshipStatus.PENDING;
    }

    public Friendship(User friend, FriendshipStatus status) {
        this.friend = friend;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", friend=" + friend +
                ", status=" + status +
                '}';
    }
}
