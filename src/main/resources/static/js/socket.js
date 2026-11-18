// WebSocket 연결, 수량 갱신 (담당: 서유정)
//
// TODO
// - ws://서버주소/ws/stock 연결
// - {"type":"STOCK", ...} 받으면 해당 옵션 수량 갱신, 0이면 품절 표시
// - 연결이 끊기면 "서버와 연결이 끊어졌습니다." 안내, 3초마다 재연결
// - 재연결되면 getEvent()로 최신 수량 다시 조회
// 메시지 형식: docs/design-server-api.md 4-6
