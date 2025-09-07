package com.project.lostfound.entity;

import jakarta.persistence.*;
//import lombok.*;

@Entity
@Table(name = "claims")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lost_item_id", nullable = false)
    private LostItem lostItem;

    @ManyToOne
    @JoinColumn(name = "found_item_id", nullable = false)
    private FoundItem foundItem;

    @ManyToOne
    @JoinColumn(name = "claimed_by", nullable = false)
    private User claimedBy; // Who is claiming the item

    @Enumerated(EnumType.STRING)
    private ClaimStatus status; // PENDING, APPROVED, REJECTED

	public Claim() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Claim(Long id, LostItem lostItem, FoundItem foundItem, User claimedBy, ClaimStatus status) {
		super();
		this.id = id;
		this.lostItem = lostItem;
		this.foundItem = foundItem;
		this.claimedBy = claimedBy;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LostItem getLostItem() {
		return lostItem;
	}

	public void setLostItem(LostItem lostItem) {
		this.lostItem = lostItem;
	}

	public FoundItem getFoundItem() {
		return foundItem;
	}

	public void setFoundItem(FoundItem foundItem) {
		this.foundItem = foundItem;
	}

	public User getClaimedBy() {
		return claimedBy;
	}

	public void setClaimedBy(User claimedBy) {
		this.claimedBy = claimedBy;
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Claim [id=" + id + ", lostItem=" + lostItem + ", foundItem=" + foundItem + ", claimedBy=" + claimedBy
				+ ", status=" + status + "]";
	}
    
    
}