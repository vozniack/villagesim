import { Injectable } from '@angular/core';
import {LogWebSocket} from "./util/logWebSocket";
import {Subject} from "rxjs";
import {Resource} from "../../model/others/resource";

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private logDataSource = new Subject<Resource>();
  log$ = this.logDataSource.asObservable();

  constructor(private logWebSocket: LogWebSocket) {
    this.logWebSocket.connect();

    this.logWebSocket.log$.subscribe((value: any) => {
      this.logDataSource.next(value);
    })
  }
}
