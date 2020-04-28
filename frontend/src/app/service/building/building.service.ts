import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BuildingService {

  buildingApi: string = 'api/buildings';

  constructor(private httpClient: HttpClient) {
  }

  createBuilding(buildingType: string) {
    return this.httpClient.post<any>(environment.apiUrl + this.buildingApi + "?buildingType=" + buildingType, null, {observe: 'response'});
  }
}
