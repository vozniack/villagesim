import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private taskApi: string = 'api/tasks'

  constructor(private httpClient: HttpClient) {

  }

  createTask(taskType: string) {
    return this.httpClient.post<any>(environment.apiUrl + this.taskApi + "?taskType=" + taskType, null, {observe: 'response'});
  }
}
