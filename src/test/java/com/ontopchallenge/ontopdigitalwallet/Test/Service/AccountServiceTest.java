package com.ontopchallenge.ontopdigitalwallet.Test.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ontopchallenge.ontopdigitalwallet.Model.AccountModel;
import com.ontopchallenge.ontopdigitalwallet.Repository.IAccountRepository;
import com.ontopchallenge.ontopdigitalwallet.Service.AccountService;

public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private IAccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAccount() {
        AccountModel account = new AccountModel();
        account.setName("Cleber");
        account.setSurName("Juca");
        account.setIdentificationNumber(123456789L);
        account.setAccountNumber("asdf");
        account.setBankName("Bank");

        LocalDateTime now = LocalDateTime.now();
        when(accountRepository.save(account)).thenReturn(account);

        AccountModel savedAccount = accountService.save(account);

        verify(accountRepository).save(account);

        assertNotNull(savedAccount);
        assertEquals("Cleber", savedAccount.getName());
        assertEquals("Juca", savedAccount.getSurName());
        assertEquals(123456789L, savedAccount.getIdentificationNumber());
        assertEquals(123, savedAccount.getAccountNumber());
        assertEquals("Bank", savedAccount.getBankName());
        assertEquals(now.getYear(), savedAccount.getCreatedAt().getYear());
        assertEquals(now.getMonth(), savedAccount.getCreatedAt().getMonth());
        assertEquals(now.getDayOfMonth(), savedAccount.getCreatedAt().getDayOfMonth());
    }

    @Test
    public void testFindAllAccounts() {
        List<AccountModel> accounts = new ArrayList<>();
        accounts.add(new AccountModel());
        accounts.add(new AccountModel());
        when(accountRepository.findAll()).thenReturn(accounts);

        List<AccountModel> retrievedAccounts = accountService.findAll();

        verify(accountRepository).findAll();

        assertEquals(accounts.size(), retrievedAccounts.size());
    }

    @Test
    public void testFindAccountById() {
        long id = 1L;
        AccountModel account = new AccountModel();
        account.setId(id);
        account.setName("Cleber");
        account.setSurName("Juca");
        account.setIdentificationNumber(123456789L);
        account.setAccountNumber("111");
        account.setBankName("Bank");
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        Optional<AccountModel> retrievedAccount = accountService.findById(id);

        verify(accountRepository).findById(id);

        assertTrue(retrievedAccount.isPresent());
        assertEquals("Cleber", retrievedAccount.get().getName());
        assertEquals("Juca", retrievedAccount.get().getSurName());
        assertEquals(123456789L, retrievedAccount.get().getIdentificationNumber());
        assertEquals(123, retrievedAccount.get().getAccountNumber());
        assertEquals("Bank", retrievedAccount.get().getBankName());
        assertEquals(id, retrievedAccount.get().getId());
    }

    @Test
    public void testDeleteAccount() {
        AccountModel account = new AccountModel();
        account.setId(1L);
        account.setName("Cleber");
        account.setSurName("Juca");
        account.setIdentificationNumber(123456789L);
        account.setAccountNumber("1221");
        account.setBankName("Bank");

        accountService.delete(account);

        verify(accountRepository).delete(account);
    }
}