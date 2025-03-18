package com.example.webapphkh.controllers;

import com.example.webapphkh.models.User;
import com.example.webapphkh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        
        if (user == null || user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
            response.put("error", "Dữ liệu đăng ký không hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }

        ResponseEntity<String> registerResponse = userService.registerUser(user);
        response.put("message", registerResponse.getBody());
        return ResponseEntity.status(registerResponse.getStatusCode()).body(response);
    }

    @PostMapping("/login")
public ResponseEntity<Map<String, Object>> login(@RequestBody User loginRequest) {
    Map<String, Object> response = new HashMap<>();
    
    if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
        response.put("error", "Email hoặc mật khẩu không hợp lệ");
        return ResponseEntity.badRequest().body(response);
    }

    ResponseEntity<?> loginResponse = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

    if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
        response.put("error", "Sai tài khoản hoặc mật khẩu");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // ✅ Đảm bảo trả về đầy đủ thông tin user
    User user = userService.findByEmail(loginRequest.getEmail());
    response.put("message", "Đăng nhập thành công!");
    response.put("user", user);
    
    return ResponseEntity.ok(response);
}


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
