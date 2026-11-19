package com.coma.snack.dto;

import java.time.LocalDateTime;

public class EventDto {
	
	private Long eventId;            // event_id
    private String name;             // name
    private LocalDateTime openAt;    // open_at
    private LocalDateTime closeAt;   // close_at
    private boolean closed;          // closed

    public EventDto() {
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

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
    
    
}
