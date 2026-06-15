package service;

import model.User;
import org.mindrot.jbcrypt.BCrypt;
import repository.UserRepository;

import javax.servlet.http.HttpSession;

public class AuthenticationService {

    private static final int BCRYPT_LOG_ROUNDS = 12;

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userId, String password) {
        User user = userRepository.findByUserId(userId);

        if (user == null || !verifyPassword(password, user.getPasswordHash())) {
            return null;
        }

        return user;
    }

    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    public String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_LOG_ROUNDS));
    }

    public boolean verifyPassword(String password, String passwordHash) {
        if (password == null || passwordHash == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(password, passwordHash);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    public void register(User user, String password) {
        if (userRepository.exists(user.getUserId())) {
            throw new IllegalArgumentException("User ID already exists");
        }

        user.setPasswordHash(hashPassword(password));
        userRepository.save(user);
    }
}
