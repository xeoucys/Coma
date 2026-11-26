// 서버 요청 공통 함수 (담당: 서유정)
//
// TODO 1순위
// - getEvents()               → GET  /api/events             (index.html)
async getEvents(){
	const response = await fetch("/api/events");
	
	return await response.json();
}
// - getEvent(eventId)         → GET  /api/events/{eventId}   (event.html)
// - apply(eventId, request)   → POST /api/events/{eventId}/applications
//
// 2순위 이후
// - getMyApplications(studentId, name) → GET /api/applications
// - 관리자 API (/api/admin/...)
//
// 요청·응답 형식: docs/design-server-api.md 4-2
