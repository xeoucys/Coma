
// 행사 목록 화면 (담당: 서유정)

// 1. API 명세를 기반으로 한 임시 데이터
const mockEvents = [
    {
        eventId: 1,
        name: "기말고사 간식 행사",
        openAt: "2026-12-09T12:00:00",
        closeAt: "2026-12-10T18:00:00",
        status: "OPEN",
        options: [
            { optionId: 1, name: "논비건 버거", remaining: 12 },
            { optionId: 2, name: "비건 버거", remaining: 0 }
        ]
    }
];

// 2. 행사 상태별 안내 문구
const statusMessages = {
    OPEN: "신청 가능",
    UPCOMING: "아직 신청기간이 아닙니다.",
    CLOSED: "신청이 마감된 행사입니다."
};

// 3. 행사 카드 생성
function createEventCard(event) {
    const card = document.createElement("article");
    card.className = "event-card";
    card.dataset.eventId = event.eventId;

    // 행사 이름
    const title = document.createElement("h2");
    title.textContent = event.name;
    card.appendChild(title);

    // 신청 기간
    const period = document.createElement("p");
    period.textContent =
        `신청 기간: ${event.openAt.replace("T", " ")} ~ ${event.closeAt.replace("T", " ")}`;
    card.appendChild(period);

    // 행사 상태
    const status = document.createElement("p");
    status.className = `event-status ${event.status}`;
    status.textContent = statusMessages[event.status] || "상태 확인 필요";
    card.appendChild(status);

    // 옵션별 남은 수량
    event.options.forEach(option => {
        const optionRow = document.createElement("p");

        const optionName = document.createElement("span");
        optionName.textContent = `${option.name}: `;

        const remaining = document.createElement("span");
        remaining.dataset.eventId = event.eventId;
        remaining.dataset.optionId = option.optionId;
        remaining.textContent =
            option.remaining === 0
                ? "품절"
                : `${option.remaining}개 남음`;

        optionRow.append(optionName, remaining);
        card.appendChild(optionRow);
    });

    // 행사 상세 페이지 이동
    card.addEventListener("click", () => {
        window.location.href =
            `event.html?eventId=${encodeURIComponent(event.eventId)}`;
    });

    return card;
}

// 4. 행사 목록 렌더링
function renderEvents(events) {
    const eventList = document.getElementById("event-list");
    eventList.replaceChildren();

    if (events.length === 0) {
        eventList.textContent =
            "현재 진행 중이거나 예정된 행사가 없습니다.";
        return;
    }

    events.forEach(event => {
        eventList.appendChild(createEventCard(event));
    });
}

// 5. STOCK 메시지 처리
function updateStock(message) {
    const options = document.querySelectorAll(
        "[data-event-id][data-option-id]"
    );

    options.forEach(option => {
        if (
            option.dataset.eventId === String(message.eventId) &&
            option.dataset.optionId === String(message.optionId)
        ) {
            option.textContent =
                message.remaining === 0
                    ? "품절"
                    : `${message.remaining}개 남음`;
        }
    });
}

// 6. CLOSED 메시지 처리
function closeEvent(message) {
    const cards = document.querySelectorAll(
        ".event-card[data-event-id]"
    );

    cards.forEach(card => {
        if (card.dataset.eventId === String(message.eventId)) {
            const status = card.querySelector(".event-status");

            if (status) {
                status.className = "event-status CLOSED";
                status.textContent = statusMessages.CLOSED;
            }
        }
    });
}

// 7. WebSocket 메시지 이벤트 처리
window.addEventListener("socket-message", event => {
    const message = event.detail;

    if (message.type === "STOCK") {
        updateStock(message);
    } else if (message.type === "CLOSED") {
        closeEvent(message);
    }
});

// 8. 재연결 후 최신 행사 목록 조회
window.addEventListener("socket-reconnected", async () => {
    try {
        const events = await getEvents();
        renderEvents(events);
    } catch (error) {
        console.error("행사 목록 갱신 실패:", error);
    }
});

// 9. 초기 화면 출력 (임시 데이터)
renderEvents(mockEvents);
