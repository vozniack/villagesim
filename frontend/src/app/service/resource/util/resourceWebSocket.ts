import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {Resource} from "../../../model/world/resource";

@Injectable({
    providedIn: 'root'
})
export class ResourceWebSocket {

    private stompClient: any;

    private endpoint: string = 'http://localhost:8080/world';
    private topicToReceive: string = '/topic/resource';

    private resourceDataSource = new Subject<Resource>();
    resource$ = this.resourceDataSource.asObservable();

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
        this.resourceDataSource.next(message.body);
    }

    onErrorCallback(error) {
        console.log("## Error callback")
        console.log(error);
    }

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
    }
}
