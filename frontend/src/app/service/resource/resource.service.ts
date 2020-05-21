import { Injectable } from '@angular/core';
import {ResourceWebSocket} from "./util/resourceWebSocket";
import {Subject} from "rxjs";
import {Resource} from "../../model/world/resource";

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  private resourceDataSource = new Subject<Resource>();
  resource$ = this.resourceDataSource.asObservable();

  constructor(private webSocket: ResourceWebSocket) {
    this.webSocket.connect();

    this.webSocket.resource$.subscribe((value: Resource) => {
      this.resourceDataSource.next(value);
    })
  }
}
