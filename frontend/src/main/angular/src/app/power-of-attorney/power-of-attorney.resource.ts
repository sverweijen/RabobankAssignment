import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";
import {Authorization, PowerOfAttorney, PowerOfAttorneyResponse} from "./power-of-attorney.model";

@Injectable({
  providedIn: 'root'
})
export class PowerOfAttorneyResource {

  private readonly baseUrl = "http://localhost:8080/power-of-attorney";

  constructor(private readonly http: HttpClient) {
  }

  public createPowerOfAttorney(powerOfAttorney: PowerOfAttorney): Observable<PowerOfAttorney> {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    return this.http.post<PowerOfAttorney>(this.baseUrl, powerOfAttorney, httpOptions);
  }

  public getPowerOfAttorneysByFilter(granteeName: string, authorization?: Authorization): Observable<PowerOfAttorneyResponse[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.set("granteeName", granteeName);
    if (authorization) {
      httpParams = httpParams.set("authorization", authorization);
    }
    return this.http.get<PowerOfAttorneyResponse[]>(this.baseUrl, {params: httpParams});
  }
}
