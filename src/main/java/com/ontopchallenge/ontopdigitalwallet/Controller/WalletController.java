package com.ontopchallenge.ontopdigitalwallet.Controller;
import com.ontopchallenge.ontopdigitalwallet.Dto.Wallet.WalletTransactionRequestDto;
import com.ontopchallenge.ontopdigitalwallet.Dto.Wallet.WalletTransactionResponseDto;
import com.ontopchallenge.ontopdigitalwallet.Dto.Wallet.WalletTransactionStatusRequestDto;
import com.ontopchallenge.ontopdigitalwallet.Enum.TransactionType;
import com.ontopchallenge.ontopdigitalwallet.Enum.WalletTransactionStatus;
import com.ontopchallenge.ontopdigitalwallet.Exception.*;
import com.ontopchallenge.ontopdigitalwallet.Model.AccountModel;
import com.ontopchallenge.ontopdigitalwallet.Model.DestinationAccountModel;
import com.ontopchallenge.ontopdigitalwallet.Model.WalletTransactionModel;
import com.ontopchallenge.ontopdigitalwallet.Service.AccountService;
import com.ontopchallenge.ontopdigitalwallet.Service.DestinationAccountService;
import com.ontopchallenge.ontopdigitalwallet.Service.WalletTransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*" , maxAge = 3600)
@RequestMapping("/api/wallet/transactions")
public class WalletController {
    final AccountService accountService;
    final WalletTransactionService walletTransactionService;
    final DestinationAccountService destinationAccountService;
    public WalletController(AccountService accountService , WalletTransactionService walletTransactionService, DestinationAccountService destinationAccountService) {
        this.accountService = accountService;
        this.walletTransactionService = walletTransactionService;
        this.destinationAccountService = destinationAccountService;
    }
    @PostMapping
    public ResponseEntity<Object> saveWalletTransactions(@RequestBody @Valid WalletTransactionRequestDto walletRequestDto){

        Optional<AccountModel> accountModelOptional = accountService.findById(walletRequestDto.getAccount_id());
        if (accountModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        /*
        if (walletRequestDto.getTransactionType() == TransactionType.TRANSFER)
            destinationAccountService.findAll();
*/
        var walletTransactionModel = new WalletTransactionModel();
        BeanUtils.copyProperties(walletRequestDto, walletTransactionModel);
        walletTransactionModel.setAccount(accountModelOptional.get());
        walletTransactionModel.setWalletTransactionStatus(WalletTransactionStatus.Procesing);
        walletTransactionModel.setCreatedBy("api_user");

        if (walletRequestDto.getTransactionType() == TransactionType.TRANSFER)
        {
            Optional<DestinationAccountModel> destinationAccountModelOptional = accountModelOptional.get().getDestinationAccounts()
                    .stream()
                    .filter(destinationAccount -> destinationAccount.getId() == walletRequestDto.getDestination_account_id())
                    .findFirst();

            walletTransactionModel.setDestinationAccount(destinationAccountModelOptional.get());
        }

        var response = new WalletTransactionResponseDto();
        try {
            BeanUtils.copyProperties(walletTransactionService.save(walletTransactionModel), response);
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ;
        }
        response.setAccount_id(accountModelOptional.get().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body( response) ;
    }
    @GetMapping
    public ResponseEntity<Object> getAllWalletTransaction(){
        var walletTransactions = walletTransactionService.findAll();
        List<WalletTransactionResponseDto> response = new ArrayList<>();

        for (WalletTransactionModel walletTransaction : walletTransactions) {
            WalletTransactionResponseDto responseDto = new WalletTransactionResponseDto();
            BeanUtils.copyProperties(walletTransaction, responseDto);
            responseDto.setAccount_id(walletTransaction.getId());
            response.add(responseDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body( response) ;
    }

    @GetMapping("/account/{account_id}")
    public Page<WalletTransactionModel> getWalletTransactionsByAccountId(@PathVariable(value = "account_id") long account_id,
                                                                         @RequestParam(value = "createdAtStart") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createdAtStart,
                                                                         @RequestParam(value = "createdAtEnd") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime  createdAtEnd,
                                                                         @RequestParam(value = "amountStart") Double amountStart,
                                                                         @RequestParam(value = "amountEnd") Double amountEnd,
                                                                         @RequestParam(value = "sort_by", defaultValue = "createdAt") String sortBy,
                                                                         @RequestParam(value = "sort_dir", defaultValue = "desc") String sortDirection,
                                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(value = "page_size", defaultValue = "2") int size

    ){
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<WalletTransactionModel> walletTransactionServicePageable;
        walletTransactionServicePageable = walletTransactionService.findByAccountId(
                    account_id
                ,   createdAtStart
                ,   createdAtEnd
                ,   amountStart
                ,   amountEnd
                ,   pageRequest
        );
        return walletTransactionServicePageable;
   }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getWalletTransactionById(@PathVariable(value = "id") long id){
        Optional<WalletTransactionModel> walletTransactionModelOptional = walletTransactionService.findById(id);
        if (walletTransactionModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet Transaction not found");

        var response = new WalletTransactionResponseDto();
        BeanUtils.copyProperties(walletTransactionModelOptional.get(), response);
        response.setAccount_id(walletTransactionModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/callback")
    public ResponseEntity<Object> updateWalletTransactionStatusCallBack(@PathVariable(value = "id") long id,
                                                                        @RequestBody @Valid WalletTransactionStatusRequestDto walletTransactionStatusRequestDto){

        Optional<WalletTransactionModel> walletTransactionModelOptional = walletTransactionService.findById(id);
        if (walletTransactionModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet Transaction not found");

        walletTransactionModelOptional.get().setUpdatedBy("api_user");
        var response = new WalletTransactionResponseDto();

        try {
            BeanUtils.copyProperties(
                    walletTransactionService.updateStatus(  walletTransactionModelOptional.get() ,
                                                            walletTransactionStatusRequestDto.getWalletTransactionStatus()),
                    response);
        } catch (Exception e) {
            if (    e instanceof WalletTransactionAlreadyCanceledException ||
                    e instanceof InvalidTransactionTypeException ||
                    e instanceof WalletTransactionAlreadyFinishedException ||
                    e instanceof BalanceNotExistException ||
                    e instanceof InvalidWalletTransactionStatusException ||
                    e instanceof NotEnoughBalanceException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something went wrong, please contact support");
            }

        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response) ;
    }
}