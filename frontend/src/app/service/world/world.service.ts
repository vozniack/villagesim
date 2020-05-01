import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {WebSocket} from "./util/WebSocket";

@Injectable({
  providedIn: 'root'
})
export class WorldService {

  worldApi: string = 'api/world'

  webSocket: any;

  constructor(private httpClient: HttpClient) {
  }

  generate() {
    return this.httpClient.post<any>(environment.apiUrl + this.worldApi + "/generate", null, {observe: 'response'})
  }

  getWorld() {
    this.webSocket = new WebSocket('//localhost:8080/world', '/topic/world', null);
    this.webSocket.connect();
  }
}
