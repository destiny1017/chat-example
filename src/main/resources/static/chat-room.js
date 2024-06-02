
const chats = document.querySelector('.chats');
const messageContent = document.querySelector('#message');
const btnSend = document.querySelector('.btn-send');

const sockJS = new SockJS("/stomp/chat");
const stomp = Stomp.over(sockJS);

stomp.heartbeat.outgoing = 0; //Rabbit에선 heartbeat 안먹힌다고 함
stomp.heartbeat.incoming = 0; //Rabbit에선 heartbeat 안먹힌다고 함

function onError(e) {
    console.log("STOMP ERROR", e);
}

function onDebug(m) {
    console.log("STOMP DEBUG", m);
}

headers = {
    nickname : nickname
}

stomp.debug = onDebug;

stomp.connect("guest", "guest", function (frame) {

    console.log('STOMP Connected');

    /* subscribe 설정에 따라 rabbit의 Exchange, Queue가 상당히 많이 바뀜 */
    stomp.subscribe("/exchange/chat.exchange/*.room." + chatRoomId, function (content) {

        const payload = JSON.parse(content.body);

        let className = payload.nickname == nickname? 'yours' : 'mine';

        const html = `<div class="${className}">
                        <div class="nickname">${payload.nickname}</div>
                        <div class="message">${payload.message}</div>
                    </div>`

        chats.insertAdjacentHTML('beforeend', html);

        //밑의 인자는 Queue 생성 시 주는 옵션
        //auto-delete : Consumer가 없으면 스스로 삭제되는 Queue
        //durable : 서버와 연결이 끊겨도 메세지를 저장하고 있음
        //exclusive : 동일한 이름의 Queue 생길 수 있음
    },{'auto-delete':true, 'durable':false, 'exclusive':false});

    //입장 메세지 전송
    stomp.send(`/pub/chat.enter.${chatRoomId}`, {}, JSON.stringify({
        chatRoomId: chatRoomId,
        nickname: nickname
    }));

}, onError, '/');

//메세지 전송 버튼 click
btnSend.addEventListener('click', (e) => {
    e.preventDefault();

    const message = messageContent.value;
    messageContent.value = '';

    stomp.send(`/pub/chat.message.${chatRoomId}`, {}, JSON.stringify({
        message: message,
        nickname: nickname
    }));
});
