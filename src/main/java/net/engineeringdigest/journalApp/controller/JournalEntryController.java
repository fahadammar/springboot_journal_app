package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()) return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody  JournalEntry entry, @PathVariable String userName){
        try {
            entry.setDateTime(LocalDateTime.now());
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<JournalEntry>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{entryID}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId entryID){
        Optional<JournalEntry> journalEntry = journalEntryService.getById(entryID);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{userName}/{entryID}")
    public boolean deleteJournalEntryByID(@PathVariable ObjectId entryID, @PathVariable String userName){
        journalEntryService.deleteById(entryID, userName);
        return true;
    }

    @PutMapping("id/{userName}/{entryID}")
    public JournalEntry updateJournalEntry(
            @PathVariable ObjectId entryID,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName
    ){
        JournalEntry oldEntry = journalEntryService.getById(entryID).orElse(null);
        if(oldEntry != null){
            String newTitle = newEntry.getTitle() == null && newEntry.getTitle().isEmpty() ? oldEntry.getTitle() : newEntry.getTitle();
            String newContent = newEntry.getContent() == null && newEntry.getContent().isEmpty() ? oldEntry.getContent() : newEntry.getContent();

            oldEntry.setTitle(newTitle);
            oldEntry.setContent(newContent);

            journalEntryService.saveEntry(oldEntry);
            return oldEntry;
        }

        return null;
    }
}
