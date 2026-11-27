function connectWebSocket() {

    let socket;
    let reconnectTimer;
    let disconnected = false;

    function connect() {

        const protocol =
            location.protocol === "https:" ? "wss:" : "ws:";

        socket = new WebSocket(
            `${protocol}//${location.host}/ws/stock`
        );

        // 연결 성공
        socket.onopen = () => {
            console.log("WebSocket 연결 성공");

            clearTimeout(reconnectTimer);

            if (disconnected) {
                disconnected = false;

                window.dispatchEvent(
                    new CustomEvent("socket-reconnected")
                );
            }
        };

        // 서버 메시지 수신
        socket.onmessage = (event) => {
            try {
                const message = JSON.parse(event.data);

                if (message.type === "STOCK" ||
                    message.type === "CLOSED") {

                    window.dispatchEvent(
                        new CustomEvent("socket-message", {
                            detail: message
                        })
                    );
                }

            } catch (error) {
                console.error("메시지 처리 오류:", error);
            }
        };

        // 연결 끊김
        socket.onclose = () => {
            disconnected = true;

            window.dispatchEvent(
                new CustomEvent("socket-disconnected", {
                    detail: "서버와 연결이 끊어졌습니다."
                })
            );

            clearTimeout(reconnectTimer);

            reconnectTimer = setTimeout(connect, 3000);
        };

        // 연결 오류
        socket.onerror = (error) => {
            console.error("WebSocket 연결 오류:", error);
        };
    }

    connect();
}