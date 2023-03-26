package com.ontopchallenge.ontopdigitalwallet.Service;
import com.ontopchallenge.ontopdigitalwallet.Model.UserModel;
import com.ontopchallenge.ontopdigitalwallet.Repository.IUserRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository accountRepository;
    public UserService(IUserRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        if (userModel.getId() == 0){
            userModel.setCreatedAt(LocalDateTime.now());
        }
        else{
            userModel.setUpdatedAt(LocalDateTime.now());
        }
        return  accountRepository.save(userModel);
    }

    public List<UserModel> findAll() {
        return accountRepository.findAll();
    }

    public Optional<UserModel> findById(long id) {
        return accountRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    @Transactional
    public void delete(UserModel userModel) {
        accountRepository.delete(userModel);
    }


}

