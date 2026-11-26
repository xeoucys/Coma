
// 행사 목록과 옵션별 남은 수량 조회
async getEvents(){
	const response = await fetch("/api/events");
	
	return await response.json();
}
// 신청 화면용 행사 정보 조회
async getEvent(int eventId){
	const response = await fetch(`/api/events/${eventId}`);
	
	return await response.json();
}
// - apply(eventId, request)   → POST /api/events/{eventId}/applications
//
// 2순위 이후
// - getMyApplications(studentId, name) → GET /api/applications
// - 관리자 API (/api/admin/...)
//
// 요청·응답 형식: docs/design-server-api.md 4-2
