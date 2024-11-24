package com.example.backendtask.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity representing QuotaResourceTwo in the system.
 * Tracks the blocking threshold for this quota resource.
 */
@Entity
@Table(name = "quota_resource_two")
public class QuotaResourceTwo {

    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(blockingThreshold, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuotaResourceTwo other = (QuotaResourceTwo) obj;
		return blockingThreshold == other.blockingThreshold && Objects.equals(id, other.id);
	}

	/**
	 * @param id
	 * @param blockingThreshold
	 */
	public QuotaResourceTwo(Long id, int blockingThreshold) {
		super();
		this.id = id;
		this.blockingThreshold = blockingThreshold;
		this.requestsMade = 0;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the blockingThreshold
	 */
	public int getBlockingThreshold() {
		return blockingThreshold;
	}

	/**
	 * @param blockingThreshold the blockingThreshold to set
	 */
	public void setBlockingThreshold(int blockingThreshold) {
		this.blockingThreshold = blockingThreshold;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    /**
     * Maximum number of allowed requests for a user before blocking.
     */
    private int blockingThreshold;
    private Integer requestsMade = 0;  // Track the number of requests made by the user
	/**
	 * @return the requestsMade
	 */
	public Integer getRequestsMade() {
		return requestsMade;
	}

	/**
	 * @param requestsMade the requestsMade to set
	 */
	public void setRequestsMade(Integer requestsMade) {
		this.requestsMade = requestsMade;
	}
}
