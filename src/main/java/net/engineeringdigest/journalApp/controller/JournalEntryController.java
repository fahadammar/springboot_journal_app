package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody  JournalEntry entry){
        return true;
    }

    @GetMapping("id/{myID}")
    public JournalEntry getJournalById(@PathVariable long myID){
        return journalEntries.get(myID);
    }

    @DeleteMapping("id/{myID}")
    public boolean deleteJournalEntryByID(@PathVariable long myID){
        journalEntries.remove(myID);
        return true;
    }

    @PutMapping("id/{myID}")
    public JournalEntry updateJournalEntry(@PathVariable long myID, @RequestBody JournalEntry journalEntryUpdate){
        journalEntries.put(myID, journalEntryUpdate);
        return journalEntries.get(myID);
    }
}
