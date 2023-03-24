package com.ontopchallenge.ontopdigitalwallet.Test.Service;
import com.ontopchallenge.ontopdigitalwallet.Enum.TransactionType;
import com.ontopchallenge.ontopdigitalwallet.Enum.WalletTransactionStatus;
import com.ontopchallenge.ontopdigitalwallet.Exception.*;
import com.ontopchallenge.ontopdigitalwallet.Model.*;
import com.ontopchallenge.ontopdigitalwallet.Repository.IWalletTransactionRepository;
import com.ontopchallenge.ontopdigitalwallet.Service.BalanceService;
import com.ontopchallenge.ontopdigitalwallet.Service.WalletTransactionService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class WalletTransactionServiceTest {
    @Mock
    private IWalletTransactionRepository walletTransactionRepository;
    @Mock
    private BalanceService balanceService;
    @Mock
    private WalletTransactionService walletTransactionService;
    private AccountModel testAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testAccount = new AccountModel();
        testAccount.setId(1);
        testAccount.setName("Cleber");
        testAccount.setSurName("Juca");
        testAccount.setIdentificationNumber(123456789L);
        testAccount.setAccountNumber(123);
        testAccount.setBankName("Bank");
        testAccount.setCreatedAt(LocalDateTime.now());
        testAccount.setCreatedBy("test_user");
    }

    private @NotNull WalletTransactionModel getDefaultTransaction(){
        WalletTransactionModel testTransaction = new WalletTransactionModel();
        testTransaction.setId(1);
        testTransaction.setAccount(testAccount);
        testTransaction.setAmount(100.0);
        testTransaction.setCreatedAt(LocalDateTime.now());
        testTransaction.setCreatedBy("test_user");
        return testTransaction;
    }
    @Test
    void saveTopUp_shouldSaveTopUpFirstTransaction(){
        WalletTransactionModel testTransaction = getDefaultTransaction();
        testTransaction.setWalletTransactionStatus(WalletTransactionStatus.Completed);
        testTransaction.setTransactionType(TransactionType.TOPUP);
        testTransaction.setFeeAmount(null);

        BalanceModel balanceExpected = new BalanceModel();
        balanceExpected.setId(1);
        balanceExpected.setAccount(testAccount);
        balanceExpected.setAmount(100.00);
        balanceExpected.setCreatedAt(LocalDateTime.now());
        balanceExpected.setCreatedBy("test_user");
        balanceExpected.setAccount(testAccount);

        when(walletTransactionRepository.save(testTransaction)).thenReturn(testTransaction);
        when(balanceService.save(balanceExpected)).thenReturn(balanceExpected);

        WalletTransactionModel savedTransaction = walletTransactionRepository.save(testTransaction);
        BalanceModel savedBalance = balanceService.save(balanceExpected);

        verify(balanceService, times(1)).save(any(BalanceModel.class));
        verify(walletTransactionRepository, times(1)).save(any(WalletTransactionModel.class));

        assertEquals(TransactionType.TOPUP, savedTransaction.getTransactionType());
        assertEquals(WalletTransactionStatus.Completed, savedTransaction.getWalletTransactionStatus());
        Assertions.assertNull(savedTransaction.getFeeAmount());
        assertEquals(savedTransaction.getAmount() , savedBalance.getAmount());
    }
    @Test
    void saveTopUp_shouldSaveTopUpTransactionAndUpdateBalance() {
        WalletTransactionModel testTransaction = new WalletTransactionModel();
        testTransaction.setId(1);
        testTransaction.setAccount(testAccount);
        testTransaction.setAmount(100.0);
        testTransaction.setWalletTransactionStatus(WalletTransactionStatus.Completed);
        testTransaction.setTransactionType(TransactionType.TOPUP);
        testTransaction.setFeeAmount(null);
        testTransaction.setCreatedAt(LocalDateTime.now());
        testTransaction.setCreatedBy("test_user");

        BalanceModel testOldBalance = new BalanceModel();
        testOldBalance.setAmount(100.00);

        BalanceModel testNewBalance = new BalanceModel();
        testNewBalance.setAmount(200.00);

        when(walletTransactionRepository.save(testTransaction)).thenReturn(testTransaction);
        when(balanceService.save(testNewBalance)).thenReturn(testNewBalance);

        WalletTransactionModel savedTransaction = walletTransactionRepository.save(testTransaction);
        BalanceModel savedBalance = balanceService.save(testNewBalance);

        verify(balanceService, times(1)).save(any(BalanceModel.class));
        verify(walletTransactionRepository, times(1)).save(any(WalletTransactionModel.class));

        assertEquals(TransactionType.TOPUP, savedTransaction.getTransactionType());
        assertEquals(WalletTransactionStatus.Completed, savedTransaction.getWalletTransactionStatus());
        Assertions.assertNotNull(savedTransaction.getCreatedAt());
        Assertions.assertNull(savedTransaction.getFeeAmount());

        var newBalanceShouldBe = savedTransaction.getAmount() + testOldBalance.getAmount();
        assertEquals(newBalanceShouldBe , savedBalance.getAmount());
    }
    @Test
    void saveWithdraw_shouldSaveWithdrawTransactionWithWalletTransactionStatusEqualsPROCESSING()  {
        WalletTransactionModel testTransaction = getDefaultTransaction();
        testTransaction.setWalletTransactionStatus(WalletTransactionStatus.Procesing);
        testTransaction.setTransactionType(TransactionType.WITHDRAW);
        testTransaction.setFeeAmount(null);

        when(walletTransactionRepository.save(testTransaction)).thenReturn(testTransaction);
        WalletTransactionModel savedTransaction = walletTransactionRepository.save(testTransaction);
        verify(walletTransactionRepository, times(1)).save(any(WalletTransactionModel.class));
        Assertions.assertEquals(WalletTransactionStatus.Procesing, savedTransaction.getWalletTransactionStatus());
    }

    @Test
    public void saveWithdraw_shouldThrowInvalidWalletTransactionStatusExceptionAndNotSaveBalanceOrWalletTransaction() throws NotEnoughBalanceException, BalanceNotExistException, InvalidWalletTransactionStatusException {
        WalletTransactionModel walletTransactionModel = new WalletTransactionModel();

        when(walletTransactionService.saveWithDraw(walletTransactionModel)).thenThrow(new InvalidWalletTransactionStatusException("invalid status for this type of transaction"));

        assertThrows(InvalidWalletTransactionStatusException.class, () -> {
            walletTransactionService.saveWithDraw(walletTransactionModel);
        });

        verifyNoInteractions(balanceService);
        verifyNoInteractions(walletTransactionRepository);
    }

    @Test
    public void saveWithdraw_shouldUpdateBalance() throws NotEnoughBalanceException {
        WalletTransactionModel walletTransactionModel = new WalletTransactionModel();
        walletTransactionModel.setId(1L);
        walletTransactionModel.setAmount(100.0);
        walletTransactionModel.setTransactionType(TransactionType.WITHDRAW);
        walletTransactionModel.setWalletTransactionStatus(WalletTransactionStatus.Procesing);

        BalanceModel balanceModel = new BalanceModel();
        balanceModel.setAmount(200.0);

        when(balanceService.save(balanceModel)).thenReturn(balanceModel);
        when(walletTransactionRepository.save(walletTransactionModel)).thenReturn(walletTransactionModel);

        WalletTransactionModel resultWalletTransaction = walletTransactionRepository.save(walletTransactionModel);

        BalanceModel resultBalance = balanceService.save(balanceModel);

        verify(balanceService).save(balanceModel);
        verify(walletTransactionRepository).save(walletTransactionModel);

        assertEquals(WalletTransactionStatus.Procesing, resultWalletTransaction.getWalletTransactionStatus());
        assertTrue(resultBalance.getAmount() > 0);
    }

    @Test
    public void saveWithdraw_shouldThrowNotEnoughBalanceExceptionAndNotSaveBalanceOrWalletTransaction() throws NotEnoughBalanceException, BalanceNotExistException {
        WalletTransactionModel walletTransactionModel = new WalletTransactionModel();

        when(walletTransactionService.verifyBalance(walletTransactionModel)).thenThrow(new NotEnoughBalanceException("not enough balance"));
        assertThrows(NotEnoughBalanceException.class, () -> {
            walletTransactionService.verifyBalance(walletTransactionModel);
        });

        verifyNoInteractions(balanceService);
        verifyNoInteractions(walletTransactionRepository);
    }
    @Test
    public void saveWithdraw_shouldThrowBalanceNotExistExceptionAndNotSaveBalanceOrWalletTransaction() throws NotEnoughBalanceException, BalanceNotExistException {
        WalletTransactionModel walletTransactionModel = new WalletTransactionModel();

        when(walletTransactionService.verifyBalance(walletTransactionModel)).thenThrow(new BalanceNotExistException("you have to deposit into your account first for to do an withdraw"));

        assertThrows(BalanceNotExistException.class, () -> {
            walletTransactionService.verifyBalance(walletTransactionModel);
        });

        verifyNoInteractions(balanceService);
        verifyNoInteractions(walletTransactionRepository);
    }
    @Test
    public void updateStatus_shouldThrowWalletTransactionAlreadyCanceledExceptionAndNotSaveBalanceOrWalletTransaction() throws InvalidTransactionTypeException, WalletTransactionAlreadyFinishedException, WalletTransactionAlreadyCanceledException, BalanceNotExistException, InvalidWalletTransactionStatusException, NotEnoughBalanceException {
        WalletTransactionModel walletTransactionModel = new WalletTransactionModel();
        walletTransactionModel.setWalletTransactionStatus(WalletTransactionStatus.Failed);

        when(walletTransactionService.updateStatus(walletTransactionModel, WalletTransactionStatus.Failed )).thenThrow(new WalletTransactionAlreadyCanceledException("this transaction has already been canceled"));
        assertThrows(WalletTransactionAlreadyCanceledException.class, () -> {
            walletTransactionService.updateStatus(walletTransactionModel, WalletTransactionStatus.Failed);
        });

        verifyNoInteractions(balanceService);
        verifyNoInteractions(walletTransactionRepository);
    }
    @Test
    public void updateStatus_shouldThrowWalletTransactionAlreadyFinishedExceptionAndNotSaveBalanceOrWalletTransaction() throws InvalidTransactionTypeException, WalletTransactionAlreadyFinishedException, WalletTransactionAlreadyCanceledException, BalanceNotExistException, InvalidWalletTransactionStatusException, NotEnoughBalanceException {
        WalletTransactionModel walletTransactionModel = new WalletTransactionModel();
        walletTransactionModel.setWalletTransactionStatus(WalletTransactionStatus.Completed);

        when(walletTransactionService.updateStatus(walletTransactionModel, WalletTransactionStatus.Completed)).thenThrow(new WalletTransactionAlreadyFinishedException("this transaction is already finished"));
        assertThrows(WalletTransactionAlreadyFinishedException.class, () -> {
            walletTransactionService.updateStatus(walletTransactionModel, WalletTransactionStatus.Completed);
        });

        verifyNoInteractions(balanceService);
        verifyNoInteractions(walletTransactionRepository);
    }
    @Test
    public void updateStatus_shouldThrowInvalidTransactionTypeExceptionExceptionAndNotSaveBalanceOrWalletTransaction() throws InvalidTransactionTypeException, WalletTransactionAlreadyFinishedException, WalletTransactionAlreadyCanceledException, BalanceNotExistException, InvalidWalletTransactionStatusException, NotEnoughBalanceException {
        WalletTransactionModel walletTransactionModel = new WalletTransactionModel();
        walletTransactionModel.setTransactionType(TransactionType.TOPUP);
        walletTransactionModel.setWalletTransactionStatus(WalletTransactionStatus.Failed);

        when(walletTransactionService.updateStatus(walletTransactionModel, WalletTransactionStatus.Failed )).thenThrow(new InvalidTransactionTypeException("this transaction is already finished"));
        assertThrows(InvalidTransactionTypeException.class, () -> {
            walletTransactionService.updateStatus(walletTransactionModel, WalletTransactionStatus.Failed);
        });
        verifyNoInteractions(balanceService);
        verifyNoInteractions(walletTransactionRepository);
    }
}
