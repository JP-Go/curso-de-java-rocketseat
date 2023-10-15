package jp.todolist.user;

import lombok.Data;
import jakarta.persistence.Entity;
import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;


@Data
@Entity(name = "tb_user")
public class UserModel {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private String name;
    private String username;
    private String password;
}