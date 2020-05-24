import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {World} from "../../../model/world/world";
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WorldWebSocket {

  private stompClient: any;

  private endpoint: string = 'http://localhost:8080/world';
  private topicToReceive: string = '/topic/world';
  private topicToSend: string = '/app/hello';

  private worldDataSource = new Subject<World>();
  world$ = this.worldDataSource.asObservable();

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
    this.worldDataSource.next(message.body);
  }

  onErrorCallback(error) {
    console.log("## Error callback")
    console.log(error);
  }
}
