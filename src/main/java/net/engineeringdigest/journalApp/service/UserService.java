package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The service holds the main business logic. The controller makes only the end-points
 */
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean saveEntry(User user){
        userRepository.save(user);
        return true;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }


    public Optional<User> getById(ObjectId entryID){
        return userRepository.findById(entryID);
    }

    public void deleteById(ObjectId entryID){
        userRepository.deleteById(entryID);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
