package jp.todolist.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, UUID>{
    Optional<UserModel> findByUsername(String username);
}