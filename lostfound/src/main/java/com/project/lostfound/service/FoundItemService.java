package com.project.lostfound.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.lostfound.entity.FoundItem;
import com.project.lostfound.entity.User;
import com.project.lostfound.repository.FoundItemRepository;
import com.project.lostfound.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@Service
public class FoundItemService {
    @Autowired
    private FoundItemRepository foundItemRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<FoundItem> getAllFoundItems() {
        return foundItemRepository.findAll();
    }

	public FoundItem reportFoundItem(FoundItem item, MultipartFile imageFile) throws IOException {
			
			if (item.getUser() == null || item.getUser().getId() == null) {
	            throw new RuntimeException("User information is missing in the request.");
	        }

	        User user = userRepository.findById(item.getUser().getId())
	                                  .orElseThrow(() -> new RuntimeException("User not found"));
	        
	    
	        
	        if (!user.getRoles()
                    .stream()
                    .anyMatch(role -> role.getName().equals("ROLE_FOUND_USER"))) {
	        	System.out.println(user.getRoles().getFirst().getName());
	        	throw new RuntimeException("Your are not valid user.");
	        }
	        
	        

			item.setUser(user); 
			
			item.setImageName(imageFile.getOriginalFilename());
			item.setImageType(imageFile.getContentType());
			item.setImageData(imageFile.getBytes());
			return foundItemRepository.save(item);
		
	}

	public FoundItem findFoundItemById(long item_id) {
		return foundItemRepository.findById(item_id).orElseThrow(() -> new RuntimeException("Lost item not found with id: " + item_id));
	}
	

	public void deleteById(long item_id) {
		foundItemRepository.deleteById(item_id);
	}

	public FoundItem updateFoundItem(Long id, FoundItem updatedItem, MultipartFile imageFile) throws IOException {
        FoundItem existingItem = foundItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existingItem.setItemName(updatedItem.getItemName());
        existingItem.setDateFound(updatedItem.getDateFound());
        existingItem.setLocation(updatedItem.getLocation());
        existingItem.setDescription(updatedItem.getDescription());

        if (imageFile != null && !imageFile.isEmpty()) {
            existingItem.setImageName(imageFile.getOriginalFilename());
            existingItem.setImageType(imageFile.getContentType());
            existingItem.setImageData(imageFile.getBytes());
        }

        return foundItemRepository.save(existingItem);
    }
}
