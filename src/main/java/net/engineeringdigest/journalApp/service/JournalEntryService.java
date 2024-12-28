package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The service holds the main business logic. The controller makes only the end-points
 */
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDateTime(LocalDateTime.now());
        JournalEntry save = journalEntryRepository.save(journalEntry); // saves the journalEntry into the DB a
        user.getJournalEntries().add(save); // than we add that journalEntry into the user
        userService.saveEntry(user); // we save the user into the DB
    }

    public void saveEntry(JournalEntry journalEntry){
      journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId entryID){
        return journalEntryRepository.findById(entryID);
    }

    public void deleteById(ObjectId entryID, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(entryID));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(entryID);
    }

}
