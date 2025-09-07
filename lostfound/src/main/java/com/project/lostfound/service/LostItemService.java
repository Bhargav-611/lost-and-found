package com.project.lostfound.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.lostfound.entity.LostItem;
import com.project.lostfound.entity.User;
import com.project.lostfound.repository.LostItemRepository;
import com.project.lostfound.repository.UserRepository;

import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

@Service
public class LostItemService {
	
	private static final Logger logger = LoggerFactory.getLogger(LostItemService.class);
	
    @Autowired
    private LostItemRepository lostItemRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<LostItem> findAllLostItems() {
        List<LostItem> items = lostItemRepository.findAll();
        logger.info("Fetched items: {}", items);
        return items;
    }

	public LostItem reportLostItem(LostItem item, MultipartFile imageFile) throws IOException {
		
		if (item.getUser() == null || item.getUser().getId() == null) {
            throw new RuntimeException("User information is missing in the request.");
        }

        // Fetch the user from DB using the provided ID
        User user = userRepository.findById(item.getUser().getId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!user.getRoles().getFirst().getName().equals("ROLE_LOST_USER")) {
        	throw new RuntimeException("Your are not valid user.");
        }

		item.setUser(user); 
		
		item.setImageName(imageFile.getOriginalFilename());
		item.setImageType(imageFile.getContentType());
		item.setImageData(imageFile.getBytes());
		return lostItemRepository.save(item);
	}

	public LostItem findLostItemById(long item_id) {
		return lostItemRepository.findById(item_id).orElseThrow(() -> new RuntimeException("Lost item not found with id: " + item_id));
	}

	public LostItem save(LostItem item, MultipartFile imageFile) throws IOException {
		User user = userRepository.findById(item.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
		
		item.setUser(user); 
		
		item.setImageName(imageFile.getOriginalFilename());
		item.setImageType(imageFile.getContentType());
		item.setImageData(imageFile.getBytes());
		return lostItemRepository.save(item);
	}

	public void deleteById(long item_id) {
		lostItemRepository.deleteById(item_id);
	}

	public LostItem updateLostItem(long id, LostItem updatedItem, MultipartFile imageFile) throws IOException {
        LostItem existingItem = lostItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existingItem.setItemName(updatedItem.getItemName());
        existingItem.setDateLost(updatedItem.getDateLost());
        existingItem.setLocation(updatedItem.getLocation());
        existingItem.setDescription(updatedItem.getDescription());

        if (imageFile != null && !imageFile.isEmpty()) {
            existingItem.setImageName(imageFile.getOriginalFilename());
            existingItem.setImageType(imageFile.getContentType());
            existingItem.setImageData(imageFile.getBytes());
        }

        return lostItemRepository.save(existingItem);
    }
}