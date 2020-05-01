import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

export class WebSocket {

  stompClient: any;

  endpoint: string = 'http://localhost:8080/world';

  topicToReceive: string = '/topic/world';
  topicToSend: string = '/app/hello';


  public constructor(endpoint: string, topicToReceive: string, topicToSend: string) {
    this.endpoint = endpoint;
    this.topicToReceive = topicToReceive;
  }

  connect() {
    console.log("Initialize Web Socket connection...")

    let webSocket = new SockJS(this.endpoint);
    this.stompClient = Stomp.over(webSocket);

    const _this = this;

    this.stompClient.connect({}, function (frame) {
      _this.stompClient.subscribe(_this.topicToReceive, (message) => {
        _this.onMessageReceived(message);
      })
    }, _this.onErrorCallback);
  }

  onMessageReceived(message) {
    console.log("## Message received")

    // #todo return message with emit @Output
  }

  onErrorCallback(error) {
    console.log("## Error callback")
    console.log(error);
  }

  _disconnect() { // #todo use it somewhere
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
  }

  _send(message) { // #todo use it somewhere
    this.stompClient.send(this.topicToSend, {}, JSON.stringify(message));
  }
}
