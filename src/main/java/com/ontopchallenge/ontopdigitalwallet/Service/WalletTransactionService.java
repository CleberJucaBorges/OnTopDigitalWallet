package com.ontopchallenge.ontopdigitalwallet.Service;
import com.ontopchallenge.ontopdigitalwallet.Exception.InvalidTransactionTypeException;
import com.ontopchallenge.ontopdigitalwallet.Exception.NotEnoughBalanceException;
import com.ontopchallenge.ontopdigitalwallet.Model.BalanceModel;
import com.ontopchallenge.ontopdigitalwallet.Model.WalletTransactionModel;
import com.ontopchallenge.ontopdigitalwallet.Repository.IWalletTransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class WalletTransactionService {
    private final IWalletTransactionRepository walletTransactionRepository;
    private final BalanceService balanceService;
    public WalletTransactionService(
                IWalletTransactionRepository walletTransactionRepository
            ,   BalanceService balanceService
    ) {
        this.walletTransactionRepository = walletTransactionRepository;
        this.balanceService = balanceService;
    }

    @Transactional
    public WalletTransactionModel save(WalletTransactionModel walletTransactionModel)
            throws NotEnoughBalanceException
            , InvalidTransactionTypeException {


        switch (walletTransactionModel.getTransactionType()) {
            case TOPUP -> walletTransactionModel = saveTopUp(walletTransactionModel);
            case WITHDRAW -> walletTransactionModel = saveWithDraw(walletTransactionModel);
            case CANCELED -> walletTransactionModel = cancellWithDraw(walletTransactionModel);
            default -> throw new InvalidTransactionTypeException("Invalid transaction type");
        }
        return walletTransactionModel;
    }

    private WalletTransactionModel saveTopUp(WalletTransactionModel walletTransactionModel)
    {
        saveBalanceOnSaveTopUp(walletTransactionModel);
        walletTransactionModel.setCreatedAt(LocalDateTime.now());
        return  walletTransactionRepository.save(walletTransactionModel);
    }

    private void saveBalanceOnSaveTopUp(WalletTransactionModel walletTransactionModel)
    {
        BalanceModel balance = balanceService.findByAccountId(walletTransactionModel.getAccount().getId());
        if (balance == null)
        {
            balance = new BalanceModel();
            balance.setAmount(walletTransactionModel.getAmount());
            balance.setAccount(walletTransactionModel.getAccount());
            balance.setCreatedAt(LocalDateTime.now());
        }
        else
        {
            balance.setAmount(balance.getAmount() + walletTransactionModel.getAmount());
            balance.setAccount(walletTransactionModel.getAccount());
            balance.setUpdatedAt(LocalDateTime.now());
        }
        balance.setCreatedBy("api_user");
        balanceService.save(balance);
    }

    private WalletTransactionModel cancellWithDraw(WalletTransactionModel walletTransactionModel) throws NotEnoughBalanceException {
        var balance =  verifyBalance(walletTransactionModel);
        balanceService.save(balance);
        applyFee(walletTransactionModel);
        walletTransactionModel.setCreatedAt(LocalDateTime.now());
        return  walletTransactionRepository.save(walletTransactionModel);
    }
    private WalletTransactionModel saveWithDraw(WalletTransactionModel walletTransactionModel)
            throws NotEnoughBalanceException {
        var balance =  verifyBalance(walletTransactionModel);
        balanceService.save(balance);
        applyFee(walletTransactionModel);
        walletTransactionModel.setCreatedAt(LocalDateTime.now());
        return  walletTransactionRepository.save(walletTransactionModel);
    }

    private void applyFee(WalletTransactionModel walletTransactionModel)
    {
        Double feeAmount = walletTransactionModel.getAmount() * 0.1 ;
        walletTransactionModel.setFeeAmount(feeAmount );
    }

    private BalanceModel verifyBalance(WalletTransactionModel walletTransactionModel) throws NotEnoughBalanceException {
        BalanceModel balance =  balanceService.findByAccountId(walletTransactionModel.getAccount().getId());
        double newBalance = balance.getAmount() - walletTransactionModel.getAmount();
        if (newBalance < 0)
            throw new NotEnoughBalanceException("not enough balance");
        balance.setAmount(newBalance);
        return balance;
    }
    @Transactional
    public WalletTransactionModel updateStatus(WalletTransactionModel walletTransactionModel) {
        walletTransactionModel.setUpdatedAt(LocalDateTime.now());
        //if status is cancelled you have to add the value to the Balance
        return  walletTransactionRepository.save(walletTransactionModel);
    }

    public List<WalletTransactionModel> findAll() {
        return walletTransactionRepository.findAll();
    }

    public Optional<WalletTransactionModel> findById(long id) {
        return walletTransactionRepository.findById(id);
    }



 public Page<WalletTransactionModel> findPageable(
         Long account_id
         ,   LocalDateTime  createdAtStart
         ,   LocalDateTime  createdAtEnd
         ,   Double amountStart
         ,   Double amountEnd
         ,   Pageable pageable)
 {
     return walletTransactionRepository.findByAccount_IdAndCreatedAtBetweenAndAmountBetween
     (
        account_id
     ,  createdAtStart
     ,  createdAtEnd
     ,  amountStart
     ,  amountEnd
     ,  pageable
     );
 }




}

