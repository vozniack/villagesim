import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {Resource} from "../../model/others/resource";
import {StatisticsWebSocket} from "./util/statisticsWebSocket";

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private statisticsDataSource = new Subject<Resource>();
  statistics$ = this.statisticsDataSource.asObservable();

  constructor(private webSocket: StatisticsWebSocket) {
    this.webSocket.connect();

    this.webSocket.statistics$.subscribe((value: Resource) => {
      this.statisticsDataSource.next(value);
    })
  }
}
