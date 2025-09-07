package com.project.lostfound.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.lostfound.entity.Claim;
import com.project.lostfound.entity.ClaimStatus;
import com.project.lostfound.entity.FoundItem;
import com.project.lostfound.entity.LostItem;
import com.project.lostfound.entity.User;
import com.project.lostfound.repository.ClaimRepository;
import com.project.lostfound.repository.FoundItemRepository;
import com.project.lostfound.repository.LostItemRepository;
import com.project.lostfound.repository.UserRepository;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private LostItemRepository lostItemRepository;

    @Autowired
    private FoundItemRepository foundItemRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }
    

    public Claim createClaim(Claim claimRequest) {
    	
    	String currentUsername = getCurrentLoggedInUsername();

        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        if (!claimRequest.getClaimedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You can only create claims for yourself!");
        }
    	
        LostItem lostItem = lostItemRepository.findById((long) claimRequest.getLostItem().getId())
                .orElseThrow(() -> new RuntimeException("Lost item not found"));

        FoundItem foundItem = foundItemRepository.findById(claimRequest.getFoundItem().getId())
                .orElseThrow(() -> new RuntimeException("Found item not found"));

        Claim claim = new Claim();
        claim.setLostItem(lostItem);
        claim.setFoundItem(foundItem);
        claim.setClaimedBy(currentUser);
        claim.setStatus(ClaimStatus.PENDING);

        return claimRepository.save(claim);
    }
    
    
    public Claim updateClaimStatus(Long claimId, ClaimStatus status) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setStatus(status);
        return claimRepository.save(claim);
    }

    public void deleteClaim(Long id) {
    	claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim with ID " + id + " not found"));

        claimRepository.deleteById(id);
    }
    
    
    private String getCurrentLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}

