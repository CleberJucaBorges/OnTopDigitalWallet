package com.ontopchallenge.ontopdigitalwallet.Controller;
import com.ontopchallenge.ontopdigitalwallet.Dto.User.UserResponseDto;
import com.ontopchallenge.ontopdigitalwallet.Dto.DestinationAccount.DestinationAccountRequestDto;
import com.ontopchallenge.ontopdigitalwallet.Dto.DestinationAccount.DestinationAccountResponseDto;
import com.ontopchallenge.ontopdigitalwallet.Exception.InvalidAccountException;
import com.ontopchallenge.ontopdigitalwallet.Model.UserModel;
import com.ontopchallenge.ontopdigitalwallet.Model.DestinationAccountModel;
import com.ontopchallenge.ontopdigitalwallet.Service.UserService;
import com.ontopchallenge.ontopdigitalwallet.Service.DestinationAccountService;
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
@RequestMapping("/api/destination-account")
public class DestinationAccountController {
    final DestinationAccountService destinationAccountService;
    final UserService userService;
    public DestinationAccountController(DestinationAccountService destinationAccountService, UserService userService) {
        this.destinationAccountService = destinationAccountService;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<Object> saveDestinationAccount(@RequestBody @Valid DestinationAccountRequestDto destinationAccountRequestDto)  {

        var destinationAccountModel = new DestinationAccountModel();

        Optional<UserModel> accountModelOptional = userService.findById(destinationAccountRequestDto.getAccount_id());
        if (accountModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");

        destinationAccountModel.setUser(accountModelOptional.get());

        BeanUtils.copyProperties(destinationAccountRequestDto, destinationAccountModel);
        destinationAccountModel.setCreatedBy("api_user");
        var response = new DestinationAccountResponseDto();
        try {
            BeanUtils.copyProperties(destinationAccountService.save(destinationAccountModel), response);
            var accountResponseDto = new UserResponseDto();
            BeanUtils.copyProperties(accountModelOptional.get(), accountResponseDto);
            response.setAccount(accountResponseDto);

        } catch (InvalidAccountException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ;
        }
        return ResponseEntity.status(HttpStatus.CREATED).body( response) ;
    }
    @GetMapping
    public ResponseEntity<Object> getAllDestinationAccounts(){

        userService.findAll();
        var destinationAccounts = destinationAccountService.findAll();
        List<DestinationAccountResponseDto> response = new ArrayList<>();

        for (DestinationAccountModel destinationAccountModel : destinationAccounts) {
            DestinationAccountResponseDto responseDto = new DestinationAccountResponseDto();
            BeanUtils.copyProperties(destinationAccountModel, responseDto);

            var accountResponseDto = new UserResponseDto();
            BeanUtils.copyProperties(destinationAccountModel.getUser(), accountResponseDto);
            responseDto.setAccount(accountResponseDto);
            response.add(responseDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body( response) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDestinationAccountById(@PathVariable(value = "id") long id){

        userService.findAll();
        Optional<DestinationAccountModel> destinationAccountResponseDtoOptional = destinationAccountService.findById(id);
        if (destinationAccountResponseDtoOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");

        var response = new DestinationAccountResponseDto();
        BeanUtils.copyProperties(destinationAccountResponseDtoOptional.get(), response);
        var accountResponseDto = new UserResponseDto();
        BeanUtils.copyProperties(destinationAccountResponseDtoOptional.get().getUser(), accountResponseDto);
        response.setAccount(accountResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDestinationAccount(@PathVariable(value = "id") long id,
                                                           @RequestBody @Valid DestinationAccountRequestDto destinationAccountRequestDto){

        Optional<UserModel> accountModelOptional = userService.findById(id);
        if (accountModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");

        Optional<DestinationAccountModel> destinationAccountModelOptional = destinationAccountService.findById(id);
        if (destinationAccountModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destination account not found");
        }

        var destinationAccountModel = new DestinationAccountModel();
        BeanUtils.copyProperties(destinationAccountRequestDto, destinationAccountModel);
        destinationAccountModel.setId(destinationAccountModelOptional.get().getId());
        destinationAccountModel.setCreatedAt(destinationAccountModelOptional.get().getCreatedAt());
        destinationAccountModel.setCreatedBy(destinationAccountModelOptional.get().getCreatedBy());
        destinationAccountModel.setUser(destinationAccountModelOptional.get().getUser());
        destinationAccountModel.setUpdatedBy("api_user");

        var response = new DestinationAccountResponseDto();
        try {
            BeanUtils.copyProperties(destinationAccountService.save(destinationAccountModel), response);
            UserResponseDto accountDto = new UserResponseDto();
            BeanUtils.copyProperties( accountModelOptional.get(),accountDto);
            response.setAccount(accountDto);

        } catch (InvalidAccountException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ;
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response) ;
    }
}