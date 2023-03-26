package com.ontopchallenge.ontopdigitalwallet.Controller;
import com.ontopchallenge.ontopdigitalwallet.Dto.User.UserRequestDto;
import com.ontopchallenge.ontopdigitalwallet.Dto.User.UserResponseDto;
import com.ontopchallenge.ontopdigitalwallet.Model.UserModel;
import com.ontopchallenge.ontopdigitalwallet.Service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*" , maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {
    final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserRequestDto userRequestDto){
        var accountModel = new UserModel();
        BeanUtils.copyProperties(userRequestDto, accountModel);
        accountModel.setCreatedBy("api_user");
        var response = new UserResponseDto();
        BeanUtils.copyProperties(userService.save(accountModel), response);
        return ResponseEntity.status(HttpStatus.CREATED).body( response) ;
    }
    @GetMapping
    public ResponseEntity<Object> getAllUsers(){
        var accounts = userService.findAll();
        List<UserResponseDto> response = new ArrayList<>();

        for (UserModel account : accounts) {
            UserResponseDto responseDto = new UserResponseDto();
            BeanUtils.copyProperties(account, responseDto);
            response.add(responseDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body( response) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") long id){
        Optional<UserModel> accountModelOptional = userService.findById(id);
        if (accountModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");

        var response = new UserResponseDto();
        BeanUtils.copyProperties(accountModelOptional.get(), response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") long id,
                                                @RequestBody @Valid UserRequestDto userRequestDto){

        Optional<UserModel> accountModelOptional = userService.findById(id);
        if (accountModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        var accountModel = new UserModel();
        BeanUtils.copyProperties(userRequestDto, accountModel);
        accountModel.setId(accountModelOptional.get().getId());
        accountModel.setCreatedAt(accountModelOptional.get().getCreatedAt());
        accountModel.setCreatedBy(accountModelOptional.get().getCreatedBy());
        accountModel.setUpdatedBy("api_user");

        var response = new UserResponseDto();
        BeanUtils.copyProperties(userService.save(accountModel), response);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response) ;
    }
}