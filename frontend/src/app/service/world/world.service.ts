import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {WebSocket} from "./util/webSocket";
import {World} from "../../model/world/world";
import {Subject} from "rxjs";
import {WorldParameters} from "../../model/others/worldParameters";

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

  getWorldParameters() {
    return this.httpClient.get<any>(environment.apiUrl + this.worldApi + "/parameters");
  }

  generate(worldParameters: WorldParameters) {
    let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

    return this.httpClient.post<any>(environment.apiUrl + this.worldApi + "/generate", JSON.stringify(worldParameters), {
      headers: headers,
      observe: 'response'
    })
  }

  pause() {
    return this.httpClient.put<any>(environment.apiUrl + this.worldApi + "/pause", null, {observe: 'response'});
  }

  /* Temporary */

  getPathNodes(posX: number, posY: number) {
    return this.httpClient.get<any>(environment.apiUrl + 'api/move?posX=' + posX + "&posY=" + posY);
  }
}
