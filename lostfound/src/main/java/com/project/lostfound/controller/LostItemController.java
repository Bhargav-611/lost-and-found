package com.project.lostfound.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lostfound.entity.LostItem;
import com.project.lostfound.service.LostItemService;

@RestController
@RequestMapping("/api/lost")
public class LostItemController {
    @Autowired
    private LostItemService lostItemService;

    @GetMapping("/items")
    public List<LostItem> findAllItems() {
        return lostItemService.findAllLostItems();
    }
    
    @GetMapping("/items/{item_id}")
    public ResponseEntity<?> getLostItemById(@PathVariable long item_id) {
    	try {
            LostItem item = lostItemService.findLostItemById(item_id);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found with id: " + item_id);
        }
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportLostItem(@RequestPart("item") String itemJson,
                                                   @RequestPart MultipartFile imageFile) {
        try {
        	
        	// Convert JSON string to LostItem object
            ObjectMapper objectMapper = new ObjectMapper();
            LostItem item = objectMapper.readValue(itemJson, LostItem.class);
            
            	
            LostItem savedItem = lostItemService.reportLostItem(item, imageFile);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/report/{id}")
    public ResponseEntity<?> updateLostItem(
            @PathVariable int id,
            @RequestPart("item") String itemJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LostItem updatedItem = objectMapper.readValue(itemJson, LostItem.class);

            LostItem savedItem = lostItemService.updateLostItem(id, updatedItem, imageFile);
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/report/{item_id}")
    public ResponseEntity<?> deleteReport(@PathVariable long item_id) {
    	LostItem item = lostItemService.findLostItemById(item_id);
    	
    	if (item == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Item id is not found");
		}
		
		lostItemService.deleteById(item_id);
		return ResponseEntity.status(HttpStatus.OK).body("Item was deleted");
    }
}