package com.ontopchallenge.ontopdigitalwallet.Service;
import com.ontopchallenge.ontopdigitalwallet.Enum.TransactionType;
import com.ontopchallenge.ontopdigitalwallet.Enum.WalletTransactionStatus;
import com.ontopchallenge.ontopdigitalwallet.Exception.*;
import com.ontopchallenge.ontopdigitalwallet.Model.BalanceModel;
import com.ontopchallenge.ontopdigitalwallet.Model.WalletTransactionModel;
import com.ontopchallenge.ontopdigitalwallet.Repository.IWalletTransactionRepository;
import org.jetbrains.annotations.NotNull;
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
    public WalletTransactionModel save(@NotNull WalletTransactionModel walletTransactionModel)
            throws NotEnoughBalanceException,
            InvalidTransactionTypeException, BalanceNotExistException, InvalidWalletTransactionStatusException {

        switch (walletTransactionModel.getTransactionType()) {
            case TOPUP -> walletTransactionModel = saveTopUp(walletTransactionModel);
            case WITHDRAW ->walletTransactionModel = saveWithDraw(walletTransactionModel);
            //case CANCELED -> walletTransactionModel = cancelWithdraw(walletTransactionModel);
            default -> throw new InvalidTransactionTypeException("Invalid transaction type");
        }
        return walletTransactionModel;
    }

    private WalletTransactionModel saveTopUp(WalletTransactionModel walletTransactionModel)
    {
        saveBalanceOnSaveTopUp(walletTransactionModel);
        walletTransactionModel.setWalletTransactionStatus(WalletTransactionStatus.Completed);
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
        balance.setCreatedBy("sys_user");
        balanceService.save(balance);
    }
/*
    private WalletTransactionModel cancelWithdraw(WalletTransactionModel walletTransactionModel) throws NotEnoughBalanceException {
        BalanceModel balance;
        try {
            balance = verifyBalance(walletTransactionModel);
        } catch (BalanceNotExistException e) {
            throw new RuntimeException(e);
        }
        balanceService.save(balance);
        applyFee(walletTransactionModel);
        walletTransactionModel.setCreatedAt(LocalDateTime.now());
        return  walletTransactionRepository.save(walletTransactionModel);
    }*/
    public WalletTransactionModel saveWithDraw(WalletTransactionModel walletTransactionModel)
            throws NotEnoughBalanceException, BalanceNotExistException, InvalidWalletTransactionStatusException {

        if (walletTransactionModel.getWalletTransactionStatus() != WalletTransactionStatus.Procesing) {
            throw new InvalidWalletTransactionStatusException("invalid status for this type of transaction");
        }

        BalanceModel balance;
        try {
            balance = verifyBalance(walletTransactionModel);
        } catch (BalanceNotExistException e) {
            throw e;
        }
        catch (NotEnoughBalanceException e) {
            throw e;
        }

        balanceService.save(balance);
        applyFee(walletTransactionModel);
        walletTransactionModel.setCreatedAt(LocalDateTime.now());
        walletTransactionModel.setWalletTransactionStatus(WalletTransactionStatus.Procesing);
        return  walletTransactionRepository.save(walletTransactionModel);
    }

    private void applyFee(WalletTransactionModel walletTransactionModel)
    {
        Double feeAmount = walletTransactionModel.getAmount() * 0.1 ;
        walletTransactionModel.setFeeAmount(feeAmount );
    }

    public BalanceModel verifyBalance(WalletTransactionModel walletTransactionModel) throws BalanceNotExistException, NotEnoughBalanceException {
        BalanceModel balance =  balanceService.findByAccountId(walletTransactionModel.getAccount().getId());

        if (balance == null)
            throw new BalanceNotExistException("you have to deposit into your account first for to do an withdraw");

        double newBalance = balance.getAmount() - walletTransactionModel.getAmount();
        if (newBalance < 0)
            throw new NotEnoughBalanceException("not enough balance");
        balance.setAmount(newBalance);
        return balance;
    }
    @Transactional
    public WalletTransactionModel updateStatus(WalletTransactionModel walletTransactionModel , WalletTransactionStatus newStatus ) throws WalletTransactionAlreadyCanceledException, InvalidTransactionTypeException, WalletTransactionAlreadyFinishedException, BalanceNotExistException, InvalidWalletTransactionStatusException, NotEnoughBalanceException {

        var actualStatus = walletTransactionModel.getWalletTransactionStatus();

        if (actualStatus == WalletTransactionStatus.Failed)
            throw new WalletTransactionAlreadyCanceledException("this transaction has already been canceled");

        if (actualStatus == WalletTransactionStatus.Completed)
            throw new WalletTransactionAlreadyFinishedException("this transaction is already finished");

        if (newStatus == WalletTransactionStatus.Failed && walletTransactionModel.getTransactionType() == TransactionType.WITHDRAW )
            balanceService.recomposeBalance(walletTransactionModel);

        if (newStatus == WalletTransactionStatus.Failed && walletTransactionModel.getTransactionType() == TransactionType.TOPUP )
            throw new InvalidTransactionTypeException("cancel a TopUp transaction is not possible,  you may have to make an withdraw");

        walletTransactionModel.setWalletTransactionStatus(newStatus);
        walletTransactionModel.setUpdatedAt(LocalDateTime.now());
        return  walletTransactionRepository.save(walletTransactionModel);
    }

    public List<WalletTransactionModel> findAll() {
        return walletTransactionRepository.findAll();
    }

    public Optional<WalletTransactionModel> findById(long id) {
        return walletTransactionRepository.findById(id);
    }
    public Page<WalletTransactionModel> findByAccountId(
                Long account_id
            ,  LocalDateTime  createdAtStart
            ,  LocalDateTime  createdAtEnd
            ,  Double amountStart
            ,  Double amountEnd
            ,  Pageable pageable){
         return walletTransactionRepository.findByAccount_IdAndCreatedAtBetweenAndAmountBetween(
                    account_id
                 ,  createdAtStart
                 ,  createdAtEnd
                 ,  amountStart
                 ,  amountEnd
                 ,  pageable
         );
     }
}