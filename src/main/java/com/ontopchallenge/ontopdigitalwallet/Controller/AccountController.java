package com.ontopchallenge.ontopdigitalwallet.Controller;
import com.ontopchallenge.ontopdigitalwallet.Dto.Account.AccountRequestDto;
import com.ontopchallenge.ontopdigitalwallet.Dto.Account.AccountResponseDto;
import com.ontopchallenge.ontopdigitalwallet.Model.AccountModel;
import com.ontopchallenge.ontopdigitalwallet.Service.AccountService;
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
@RequestMapping("/api/account")
public class AccountController {
    final
    AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping
    public ResponseEntity<Object> saveAccount(@RequestBody @Valid AccountRequestDto accountRequestDto){
        var accountModel = new AccountModel();
        BeanUtils.copyProperties(accountRequestDto, accountModel);
        accountModel.setCreatedBy("api_user");
        var response = new AccountResponseDto();
        BeanUtils.copyProperties(accountService.save(accountModel), response);
        return ResponseEntity.status(HttpStatus.CREATED).body( response) ;
    }
    @GetMapping
    public ResponseEntity<Object> getAllAccounts(){
        var accounts = accountService.findAll();
        List<AccountResponseDto> response = new ArrayList<>();

        for (AccountModel account : accounts) {
            AccountResponseDto responseDto = new AccountResponseDto();
            BeanUtils.copyProperties(account, responseDto);
            response.add(responseDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body( response) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable(value = "id") long id){
        Optional<AccountModel> accountModelOptional = accountService.findById(id);
        if (accountModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");

        var response = new AccountResponseDto();
        BeanUtils.copyProperties(accountModelOptional.get(), response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAccount(@PathVariable(value = "id") long id,
                                                @RequestBody @Valid AccountRequestDto accountRequestDto){

        Optional<AccountModel> accountModelOptional = accountService.findById(id);
        if (accountModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        var accountModel = new AccountModel();
        BeanUtils.copyProperties(accountRequestDto, accountModel);
        accountModel.setId(accountModelOptional.get().getId());
        accountModel.setCreatedAt(accountModelOptional.get().getCreatedAt());
        accountModel.setCreatedBy(accountModelOptional.get().getCreatedBy());
        accountModel.setUpdatedBy("api_user");

        var response = new AccountResponseDto();
        BeanUtils.copyProperties(accountService.save(accountModel), response);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable(value = "id") long id){
        Optional<AccountModel> accountModelOptional = accountService.findById(id);
        if (accountModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        accountService.delete(accountModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Account deleted") ;
    }
}