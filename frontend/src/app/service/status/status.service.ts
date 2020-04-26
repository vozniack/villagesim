import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class StatusService {

  serverStatusApi: string = 'api/server/status'

  constructor(private httpClient: HttpClient) {
  }

  getServerStatus() {
    return this.httpClient.get<any>(environment.apiUrl + this.serverStatusApi);
  }
}
