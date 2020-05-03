import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {WebSocket} from "./util/webSocket";
import {World} from "../../model/world/world";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WorldService {

  private worldApi: string = 'api/world'

  private worldDataSource = new Subject<World>();
  world$ = this.worldDataSource.asObservable();

  constructor(private httpClient: HttpClient, private webSocket: WebSocket) {
    this.webSocket.connect();

    this.webSocket.world$.subscribe((value: World) => {
      this.worldDataSource.next(value);
    })
  }

  generate() {
    return this.httpClient.post<any>(environment.apiUrl + this.worldApi + "/generate", null, {observe: 'response'})
  }

  pause() {
    return this.httpClient.put<any>(environment.apiUrl + this.worldApi + "/pause", null, {observe: 'response'});
  }
}
