package com.coma.snack.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventSummaryDto {
	
	private Long eventId;
    private String name;
    private LocalDateTime openAt;
    private LocalDateTime closeAt;
    private EventStatus status;
    private List<OptionDto> options;

    public EventSummaryDto() {
    }

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getOpenAt() {
		return openAt;
	}

	public void setOpenAt(LocalDateTime openAt) {
		this.openAt = openAt;
	}

	public LocalDateTime getCloseAt() {
		return closeAt;
	}

	public void setCloseAt(LocalDateTime closeAt) {
		this.closeAt = closeAt;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public List<OptionDto> getOptions() {
		return options;
	}

	public void setOptions(List<OptionDto> options) {
		this.options = options;
	}
    
    
}
