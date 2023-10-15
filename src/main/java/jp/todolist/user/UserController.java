package jp.todolist.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserModel userModel) {
        var maybeUser = userRepository.findByUsername(userModel.getUsername());
        if (maybeUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username taken");
        }
        String hashedPassword = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(hashedPassword);
        var user = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}