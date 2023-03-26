package com.ontopchallenge.ontopdigitalwallet.Service;

import com.ontopchallenge.ontopdigitalwallet.Exception.InvalidAccountException;
import com.ontopchallenge.ontopdigitalwallet.Model.DestinationAccountModel;
import com.ontopchallenge.ontopdigitalwallet.Repository.IDestinationAccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationAccountService {
    private final IDestinationAccountRepository  destinationAccountRepository;
    public DestinationAccountService(IDestinationAccountRepository destinationAccountRepository) {
        this.destinationAccountRepository = destinationAccountRepository;
    }

    @Transactional
    public DestinationAccountModel save(DestinationAccountModel destinationAccountModel) throws InvalidAccountException {

        if (destinationAccountModel.getUser() == null)
            throw new InvalidAccountException("invalid account");

        if (destinationAccountModel.getId() == 0){
            destinationAccountModel.setCreatedAt(LocalDateTime.now());
        }
        else{
            destinationAccountModel.setUpdatedAt(LocalDateTime.now());
        }
        return  destinationAccountRepository.save(destinationAccountModel);
    }
    @Transactional

    public List<DestinationAccountModel> findAll() {
        return destinationAccountRepository.findAll();
    }

    public Optional<DestinationAccountModel> findById(long id) {
        return destinationAccountRepository.findById(id);
    }


    public boolean existsById(Long id) {
        return destinationAccountRepository.existsById(id);
    }
}

