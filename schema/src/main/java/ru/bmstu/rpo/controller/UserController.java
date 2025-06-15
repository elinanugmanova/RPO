package ru.bmstu.rpo.controller;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rpo.entity.Museum;
import ru.bmstu.rpo.entity.Users;
import ru.bmstu.rpo.service.UserService;
import ru.bmstu.rpo.tools.DataValidationException;

import java.util.List;
import java.util.Set;

@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
@Data
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public Page<Users> getAllUsers(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return userService.getAllUsers(page, limit);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody Users users) {
        return userService.createUsers(users);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long usersId, @RequestBody Users usersDetails) {
        return userService.updateUsers(usersId, usersDetails);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long usersId) {
        return userService.deleteUsers(usersId);
    }

    @GetMapping("/{id}/paintings")
    public ResponseEntity<Set<Museum>> getUserPainting(@PathVariable(value = "id") Long usersId) {
        return userService.getUsersMuseum(usersId);
    }

    @PostMapping("/{id}/addmuseums")
    public ResponseEntity<Object> addMuseums(@PathVariable(value = "id") Long userId, @Valid @RequestBody Set<Museum> museums) {
        return ResponseEntity.ok(userService.addMuseums(userId, museums));
    }

    @PostMapping("/{id}/removemuseums")
    public ResponseEntity<Object> removeMuseums(@PathVariable(value = "id") Long userId, @Valid @RequestBody Set<Museum> museums) {
        return ResponseEntity.ok(userService.removeMuseums(userId, museums));
    }

    @GetMapping("/{id}")
    public HttpEntity<Users> ResponseEntityGetUsers(@PathVariable(value = "id") Long usersId) throws DataValidationException {
        Users users = userService.findById(usersId)
                .orElseThrow(()->new DataValidationException("Страна с таким индексом не найдена"));
        return ResponseEntity.ok(users);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createUsersRest(@Validated @RequestBody Users users) throws DataValidationException {
        return userService.createUsersRest(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUsersRest(@PathVariable(value = "id") Long usersId, @Validated @RequestBody Users usersDetails) throws DataValidationException {
        return userService.updateUsersRest(usersId, usersDetails);
    }

    @PostMapping("/deleteusers")
    public ResponseEntity deleteUsersRest(@Validated @RequestBody List<Users> users) {
        return userService.deleteUsersRest(users);
    }
}
