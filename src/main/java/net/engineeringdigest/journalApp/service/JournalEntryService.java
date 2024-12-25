package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The service holds the main business logic. The controller makes only the end-points
 */
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public boolean saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
        return true;
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> getById(ObjectId entryID){
        return journalEntryRepository.findById(entryID);
    }

    public void deleteById(ObjectId entryID){
        journalEntryRepository.deleteById(entryID);
    }

}
