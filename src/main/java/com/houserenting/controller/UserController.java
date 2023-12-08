package com.houserenting.controller;


import com.houserenting.dto.ChangePasswordInfo;
import com.houserenting.dto.ObjectWith2String;
import com.houserenting.model.JwtResponse;
import com.houserenting.model.Role;
import com.houserenting.model.User;
import com.houserenting.service.RoleService;
import com.houserenting.service.UserService;
import com.houserenting.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users") //Không dùng phương thức này. Sai phân quyền.
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Iterable<User>> showAllUserByAdmin() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<Optional<User>> FindUserDetail(@PathVariable Long id){
        Optional<User> user = userService.findById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user,HttpStatus.OK);
        } return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/admin/owners")
    public ResponseEntity<Iterable<User>> findAllOwner(){
        Iterable<User> listOwner = userService.findAllOwner();
        return new ResponseEntity<>(listOwner,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user, BindingResult bindingResult) {
        user.setAvatar("https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg?w=900");
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userService.findAll();
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(user.getUsername())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (!userService.isCorrectConfirmPassword(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user.getRoles() != null) {
            Role role = roleService.findByName("ROLE_OWNER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            Role role1 = roleService.findByName("ROLE_RENTER");
            Set<Role> roles1 = new HashSet<>();
            roles1.add(role1);
            user.setRoles(roles1);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getAvatar(), userDetails.getAuthorities()));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = this.userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Set<Role> mapRole = user.getRoles();
            for (Role m : mapRole) {
                if (m.getId() == 1) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            user.setId(userOptional.get().getId());
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }


    @PutMapping("/api/changePassword/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody ChangePasswordInfo info) {
        Optional<User> userWithOldPassword = this.userService.findById(id);
        if (!userWithOldPassword.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            User user = userWithOldPassword.get();
            if(passwordEncoder.matches(info.getOldPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(info.getNewPassword()));
                user.setConfirmPassword(passwordEncoder.encode(info.getConfirmNewPassword()));
                userService.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
    }

    @PutMapping("/user/updateFirstNameAndLastName")
    public ResponseEntity<User> updateFirstNameAndLastName( @RequestBody ObjectWith2String objectWith2String){
        Optional<User> user = userService.findById(objectWith2String.getId());
        if(user.isPresent()){
            user.get().setFirstname(objectWith2String.getString1());
            user.get().setLastname(objectWith2String.getString2());
            userService.save(user.get());
            return new ResponseEntity<>(user.get(),HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
