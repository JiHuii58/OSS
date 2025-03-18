package com.example.webapphkh.services;

import com.example.webapphkh.models.User;
import com.example.webapphkh.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 🔥 Import thêm để dùng @Transactional

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public ResponseEntity<String> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email đã tồn tại!");
        }
       
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

    @Transactional // 🔥 Đảm bảo Hibernate session mở khi load progress
    public ResponseEntity<?> loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Email không tồn tại!");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Mật khẩu không chính xác!");
        }

        // 🔥 Load progress trước khi trả về User
        user.getProgress().size(); // Kích hoạt lazy loading

        return ResponseEntity.ok(user); // Trả về user kèm progress
    }

    @Transactional // 🔥 Đảm bảo Hibernate session mở khi cập nhật user
    public ResponseEntity<String> updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại!");
        }

        User existingUser = existingUserOptional.get();
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        // 🔥 Load progress nếu cần
        existingUser.getProgress().size();

        userRepository.save(existingUser);
        return ResponseEntity.ok("Cập nhật thành công!");
    }

    public ResponseEntity<String> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại!");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thành công!");
    }
}
