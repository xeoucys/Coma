// WebSocket 연결, 수량 갱신 (담당: 서유정)
 function connectWebSocket(onMessage, onDisconnect, onReconnect){
	let socket;
	let reconnectTimer;
	let disconnected = false;
	
	function connect(){
		const protocol = location.protocol === "https:" ? "wss:" : "ws:";
			
		socket = new WebSocket(
			`${protocol}//${location.host}/ws/stock`
		);
		
		//연결 성공
		socket.onopen = () => {
			console.log("WebSocket 연결 성공");
			
			clearTimeout(reconnectTimer);
			
			if(disconnected){
				disconnect = false;
				onReconnect();
			}
		};
		
		//서버 메세지 수신
		socket.onmessage = (event) => {
			try{
				const message = JSON.parse(evetn.data);
				
				if(message.type === "STOCK" || message.type === "CLOSED"){
					onMessage(message);
				}
			}catch(error){
				console.error("메세지 처리 오류: ", error);
			}
		};
		
		//연결 끊김
		socket.onclose = () =>{
			disconnected = true;
			
			onDisconnect("서버와 연결이 끊어졌습니다.");
			
			clearTimeout(reconnectTimer);
			reconnectTimer = setTimeout(connect, 3000);
		};
		
		//연결 오류
		socket.onerror = (error) -> {
			console.error("Websocket 연결 오류: ", error);
		};
		
	}
	
	connect();
 }