package com.coma.snack.dto;

import java.time.LocalDate;

public class ApplyRequest {
	
	private String studentId; // 학번
    private String name; // 이름
    private String phone; // 전화번호
    private Long optionId; // 선택한 옵션 번호
    private LocalDate pickupDate; // 선택한 수령일

    public ApplyRequest() {
    }

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	public LocalDate getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

}
