const params = new URLSearchParams(window.location.search);
const eventId = params.get("eventId");
const applyForm = document.getElementById("apply-form");
const applyButton = document.getElementById("apply-button");
const message = document.getElementById("message");


//행사 및 신청 상태 관리
let currentEvent = null;
let isSubmitting = false;
let isClosed = false;


//행사 상세 정보 불러오기
async function loadEvent() {
    if (!eventId) {
        message.textContent = "잘못된 행사 접근입니다.";
        return;
    }
    try {
        const event = await getEvent(eventId);
        currentEvent = event;
        isClosed = event.status === "CLOSED";
        renderEvent(event);
        updateApplyButton();
    } catch (error) {
        console.error(error);
        message.textContent =
            "행사 정보를 불러오지 못했습니다.";
        applyButton.disabled = true;
    }
}

//행사 정보 출력
function renderEvent(event) {
    document.getElementById("event-name").textContent =
        event.name;
    document.getElementById("event-period").textContent =
        `신청 기간: ${formatDateTime(event.openAt)} ~ ${formatDateTime(event.closeAt)}`;
    document.getElementById("event-description").textContent =
        event.description || "";
    renderOptions(event.options);
    renderPickupDates(event.pickupDates);
}

//신청 날짜 형식 지정
function formatDateTime(dateTime) {
    if (!dateTime) {
        return "";
    }
    return dateTime
        .replace("T", " ")
        .substring(0, 16);
}

//간식 옵션 출력
function renderOptions(options) {
    const optionList = document.getElementById("option-list");
    optionList.replaceChildren();
    options.forEach(option => {
        const label = document.createElement("label");
        label.className = "radio-item";
		
        const radio = document.createElement("input");

        radio.type = "radio";
        radio.name = "optionId";
        radio.value = option.optionId;
        radio.required = true;

        const name = document.createElement("span");
        name.textContent = option.name;

        const remaining = document.createElement("span");
        remaining.className = "remaining";

        if (option.remaining <= 0) {
            radio.disabled = true;
            label.classList.add("disabled");
            remaining.classList.add("sold-out");
            remaining.textContent = "품절";
        } else {
            remaining.textContent =
                `잔여 ${option.remaining}개`;
        }
        label.append(radio, name, remaining);
        optionList.appendChild(label);
    });
}


//수령일 선택지 출력
function renderPickupDates(pickupDates) {
    const pickupDateList =
        document.getElementById("pickup-date-list");

    pickupDateList.replaceChildren();
    pickupDates.forEach(date => {
        const label = document.createElement("label");
        label.className = "radio-item";

        const radio = document.createElement("input");

        radio.type = "radio";
        radio.name = "pickupDate";
        radio.value = date;
        radio.required = true;

        const dateText = document.createElement("span");
        dateText.textContent = date;

        label.append(radio, dateText);
        pickupDateList.appendChild(label);
    });
}


//신청 버튼 활성화 및 비활성화
function updateApplyButton() {
    if (isSubmitting) {
        applyButton.disabled = true;
        applyButton.textContent = "신청 중...";
        return;
    }

    // 행사 정보가 없는 경우
    if (!currentEvent) {
        applyButton.disabled = true;
        return;
    }

    // 신청 마감
    if (isClosed) {
        applyButton.disabled = true;
        applyButton.textContent = "신청 마감";
        return;
    }

    // 신청 시작 전
    if (currentEvent.status === "UPCOMING") {
        applyButton.disabled = true;
        applyButton.textContent = "신청 기간이 아닙니다";
        return;
    }

    // 모든 옵션이 품절인 경우
    const allSoldOut = currentEvent.options.every(
        option => option.remaining <= 0
    );
    if (allSoldOut) {
        applyButton.disabled = true;
        applyButton.textContent = "전체 품절";
        return;
    }

    // 수령일이 없는 경우
    if (currentEvent.pickupDates.length === 0) {
        applyButton.disabled = true;
        applyButton.textContent = "수령일 선택 불가";
        return;
    }

    // 정상적으로 신청 가능한 경우
    applyButton.disabled = false;
    applyButton.textContent = "간식 신청하기";
}


//신청 결과 팝업
function showPopup(text) {
    alert(text);
}


//옵션 잔여 수량 갱신
function updateOptionStock(optionId, remaining) {
    const option = currentEvent.options.find(
        option => Number(option.optionId) === Number(optionId)
    );
    if (!option) {
        return;
    }
    option.remaining = remaining;
	
    const radios = document.querySelectorAll(
        'input[name="optionId"]'
    );

    const radio = Array.from(radios).find(
        radio => Number(radio.value) === Number(optionId)
    );
    if (!radio) {
        return;
    }

    const label = radio.closest("label");

    const remainingText =
        label.querySelector(".remaining");

    if (remaining <= 0) {
        radio.disabled = true;
        radio.checked = false;
        label.classList.add("disabled");
        remainingText.classList.add("sold-out");
        remainingText.textContent = "품절";
    } else {
        radio.disabled = false;
        label.classList.remove("disabled");
        remainingText.classList.remove("sold-out");
        remainingText.textContent =
            `잔여 ${remaining}개`;
    }

    updateApplyButton();
}


//신청 처리
async function handleSubmit(event) {
    // 페이지 새로고침 방지
    event.preventDefault();
    // 중복 요청 방지
    if (isSubmitting || applyButton.disabled) {
        return;
    }

    const studentId =
        document.getElementById("student-id").value.trim();

    const name =
        document.getElementById("student-name").value.trim();

    const phone =
        document.getElementById("phone").value.trim();

    const selectedOption = document.querySelector(
        'input[name="optionId"]:checked'
    );

    const selectedDate = document.querySelector(
        'input[name="pickupDate"]:checked'
    );
    if (!/^[0-9]{7}$/.test(studentId)) {
        showPopup("학번을 7자리 숫자로 입력해주세요.");
        return;
    }

    if (!name) {
        showPopup("이름을 입력해주세요.");
        return;
    }

    if (!/^010-?[0-9]{4}-?[0-9]{4}$/.test(phone)) {
        showPopup("전화번호를 올바르게 입력해주세요.");
        return;
    }

    if (!selectedOption) {
        showPopup("간식 옵션을 선택해주세요.");
        return;
    }

    if (selectedOption.disabled) {
        showPopup("품절된 옵션은 신청할 수 없습니다.");
        return;
    }

    if (!selectedDate) {
        showPopup("수령일을 선택해주세요.");
        return;
    }

    const request = {
        studentId: studentId,
        name: name,
        phone: phone,
        optionId: Number(selectedOption.value),
        pickupDate: selectedDate.value

    };
	
    isSubmitting = true;
    updateApplyButton();
	
    try {

        const result = await apply(eventId, request);

        if (result.success) {
            if (result.optionId != null &&
                typeof result.remaining === "number") {

                updateOptionStock(
                    result.optionId,
                    result.remaining
                );
            }
            showPopup(
                result.message || "간식 신청이 완료되었습니다."
            );

        } else {
            showPopup(
                result.message || "간식 신청에 실패했습니다."
            );
        }
        if (result.eventClosed === true) {
            isClosed = true;
        }
    } catch (error) {
        console.error(error);
        showPopup(
            "서버와 통신하는 중 오류가 발생했습니다. " +
            "신청 내역을 확인한 후 다시 시도해주세요."
        );

    } finally {
        isSubmitting = false;
        updateApplyButton();
    }
}

//신청 버튼 이벤트 연결
applyForm.addEventListener("submit", handleSubmit);

//페이지 실행 시 행사 정보 불러오기
loadEvent();