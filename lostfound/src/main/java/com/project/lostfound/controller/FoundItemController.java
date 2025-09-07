package com.project.lostfound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lostfound.entity.FoundItem;
import com.project.lostfound.service.FoundItemService;

import java.util.List;

@RestController
@RequestMapping("/api/found")
public class FoundItemController {
    @Autowired
    private FoundItemService foundItemService;

    @GetMapping("/items")
    public List<FoundItem> getAllItems() {
        return foundItemService.getAllFoundItems();
    }
    
    @GetMapping("/items/{item_id}")
    public ResponseEntity<?> getFoundItemById(@PathVariable long item_id) {
    	try {
            FoundItem item = foundItemService.findFoundItemById(item_id);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found with id: " + item_id);
        }
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportLostItem(@RequestPart("item") String itemJson,
                                                   @RequestPart MultipartFile imageFile) {
        try {
        	
            ObjectMapper objectMapper = new ObjectMapper();
            FoundItem item = objectMapper.readValue(itemJson, FoundItem.class);
            
            	
            FoundItem savedItem = foundItemService.reportFoundItem(item, imageFile);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/report/{id}")
    public ResponseEntity<?> updateFoundItem(
            @PathVariable Long id,
            @RequestPart("item") String itemJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FoundItem updatedItem = objectMapper.readValue(itemJson, FoundItem.class);
            
            FoundItem savedItem = foundItemService.updateFoundItem(id, updatedItem, imageFile);
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/report/{item_id}")
    public ResponseEntity<?> deleteReport(@PathVariable long item_id) {
    	FoundItem item = foundItemService.findFoundItemById(item_id);
    	
    	if (item == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Item id is not found");
		}
		
		foundItemService.deleteById(item_id);
		return ResponseEntity.status(HttpStatus.OK).body("Item was deleted");
    }
}