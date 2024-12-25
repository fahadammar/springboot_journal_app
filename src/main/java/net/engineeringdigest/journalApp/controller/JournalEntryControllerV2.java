package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = journalEntryService.getAll();
        if(all != null && !all.isEmpty()) return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody  JournalEntry entry){
        try {
            entry.setDateTime(LocalDateTime.now());
            journalEntryService.saveEntry(entry);
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

    @DeleteMapping("id/{entryID}")
    public boolean deleteJournalEntryByID(@PathVariable ObjectId entryID){
        journalEntryService.deleteById(entryID);
        return true;
    }

    @PutMapping("id/{entryID}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId entryID, @RequestBody JournalEntry newEntry){
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
