package com.coma.snack.dto;

public class ApplyResponse {
	
	private boolean success; // 성공 여부
    private String code; // 결과 코드 (SUCCESS, SOLD_OUT 등)
    private String message; // 안내 메시지
    private Long applicationId; // 생성된 신청 번호 (성공 시)
    private Long optionId; // 신청한 옵션 번호 (성공 시)
    private int remaining; // 신청 후 남은 수량 (성공 시)
    private boolean eventClosed; // 이 신청으로 행사가 마감됐는지 (2순위)

    public ApplyResponse() {
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	public Integer getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}

	public boolean isEventClosed() {
		return eventClosed;
	}

	public void setEventClosed(boolean eventClosed) {
		this.eventClosed = eventClosed;
	}
    
    
}
