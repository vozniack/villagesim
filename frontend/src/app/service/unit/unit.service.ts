import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  unitApi: string = 'api/units';

  constructor(private httpClient: HttpClient) {
  }

  createUnit(unitType: string) {
    return this.httpClient.post<any>(environment.apiUrl + this.unitApi + "?unitType=" + unitType, null, {observe: 'response'});
  }
}
