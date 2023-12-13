package com.houserenting.controller;


import com.houserenting.dto.*;
import com.houserenting.model.JwtResponse;
import com.houserenting.model.Role;
import com.houserenting.model.User;
import com.houserenting.service.RoleService;
import com.houserenting.service.UserService;
import com.houserenting.service.impl.JwtService;
import com.houserenting.utils.email.Email;
import com.houserenting.utils.email.authen.VerificationToken;
import com.houserenting.utils.email.authen.VerificationTokenService;
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

import java.util.*;

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
    private VerificationTokenService verificationTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Email email;

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

    @GetMapping("/admin/renters")
    public ResponseEntity<Iterable<User>> findAllRenter(){
        Iterable<User> listRenter = userService.findAllRenter();
        return new ResponseEntity<>(listRenter,HttpStatus.OK);
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

        VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), user);
        verificationTokenService.save(token);

        email.sendEmail(user.getEmail(), "Xác thực tài khoản", "Nhấn vào đường dẫn để xác nhận tài khoản" + "\r\n" +
               "http://localhost:3000/api/registrationConfirm/" + token.getToken());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

//    @PostMapping("/register")
//    public ResponseEntity<User> createUser(@RequestBody User user, BindingResult bindingResult) {
//        user.setAvatar("https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg?w=900");
//        if (bindingResult.hasFieldErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        Iterable<User> users = userService.findAll();
//        for (User currentUser : users) {
//            if (currentUser.getUsername().equals(user.getUsername())) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//        if (!userService.isCorrectConfirmPassword(user)) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        if (user.getRoles() != null) {
//            Role role = roleService.findByName("ROLE_OWNER");
//            Set<Role> roles = new HashSet<>();
//            roles.add(role);
//            user.setRoles(roles);
//        } else {
//            Role role1 = roleService.findByName("ROLE_RENTER");
//            Set<Role> roles1 = new HashSet<>();
//            roles1.add(role1);
//            user.setRoles(roles1);
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
//        userService.save(user);
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getAvatar(),currentUser.getFirstname(),currentUser.getLastname(),currentUser.getProvince(),currentUser.getDistrict(),currentUser.getWard(),currentUser.getAddress(),currentUser.getEmail(),currentUser.getPhone(),currentUser.getFrontside(),currentUser.getBackside(), userDetails.getAuthorities()));
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

    @PutMapping("/api/updateFirstNameAndLastName")
    public ResponseEntity<User> updateFirstNameAndLastName( @RequestBody User user){
        Optional<User> user1 = userService.findById(user.getId());
        if(user1.isPresent()){
            user1.get().setFirstname(user.getFirstname());
            user1.get().setLastname(user.getLastname());
            userService.save(user1.get());
            return new ResponseEntity<>(user1.get(),HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/updateEmail")
    public ResponseEntity<User> updateEmail( @RequestBody ChangeEmail user){
        Optional<User> user1 = userService.findById(user.getId());
        if(user1.isPresent()){
            user1.get().setEmail(user.getEmail());
            userService.save(user1.get());
            return new ResponseEntity<>(user1.get(),HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/updatePhone")
    public ResponseEntity<User> updatePhone( @RequestBody ChangePhone user){
        Optional<User> user1 = userService.findById(user.getId());
        if(user1.isPresent()){
            user1.get().setPhone(user.getPhone());
            userService.save(user1.get());
            return new ResponseEntity<>(user1.get(),HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/updateIdentifyCard")
    public ResponseEntity<User> updateIdentifyCard( @RequestBody ChangeIdentifyCard user){
        Optional<User> user1 = userService.findById(user.getId());
        if(user1.isPresent()){
            user1.get().setFrontside(user.getFrontCard());
            user1.get().setBackside(user.getBackCard());
            userService.save(user1.get());
            return new ResponseEntity<>(user1.get(),HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/api/updateAddress")
    public ResponseEntity<User> updateAddress( @RequestBody ChangeAddress user){
        Optional<User> user1 = userService.findById(user.getId());
        if(user1.isPresent()){
            user1.get().setDistrict(user.getDistrict());
            user1.get().setProvince(user.getProvince());
            user1.get().setWard(user.getWard());
            user1.get().setAddress(user.getAddress());
            userService.save(user1.get());
            return new ResponseEntity<>(user1.get(),HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/registrationConfirm")
    public ResponseEntity<User> confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if(verificationToken == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/api/checkEmail/{email}")
    public ResponseEntity<User> checkEmail(@PathVariable("email") String email) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if(optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @GetMapping("/api/checkUsername/{username}")
    public ResponseEntity<User> checkUsername(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        if(user != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @PutMapping("/api/confirmingWaited/{id}")
    public ResponseEntity<User> confirmingWaited(@PathVariable Long id){
        Optional<User> user = this.userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            user.get().setStatus("AdminConfirm");
            userService.save(user.get());
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
    }
    @PutMapping("/admin/allowOwnerUserToBeActive")
    public ResponseEntity<User> allowOwnerUserToBeActive(@RequestBody User user){
       Optional<User> user1 = userService.findById(user.getId());
       if (user1.isPresent()){
        Role role = new Role();
        role.setId(2L);
        Role role1 = new Role();
        role.setId(3L);
        user1.get().setStatus("");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        roles.add(role1);
           user1.get().setRoles(roles);
        userService.save(user1.get());
        return new ResponseEntity<>(user1.get(),HttpStatus.OK);
    } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }}
    @GetMapping("/admin/showListAccountAreWaitingConfirm")
    public ResponseEntity<List<User>> showListAccountAreWaitingConfirm(){
        List <User> userList = userService.findAllRenterIsWaitingConfirm();
        if (userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }
}
