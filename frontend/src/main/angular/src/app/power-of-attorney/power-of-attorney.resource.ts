import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, of} from "rxjs";
import {Authorization, PowerOfAttorney, PowerOfAttorneyResponse} from "./power-of-attorney.model";
import {catchError} from "rxjs/operators";

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
    return this.http.post<PowerOfAttorney>(this.baseUrl, powerOfAttorney, httpOptions).pipe(
      catchError(this.handleError<PowerOfAttorney>('createPowerOfAttorney'))
    );
  }

  public getPowerOfAttorneysByFilter(granteeName: string, authorization?: Authorization): Observable<PowerOfAttorneyResponse[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.set("granteeName", granteeName);
    if (authorization) {
      httpParams = httpParams.set("authorization", authorization);
    }
    return this.http.get<PowerOfAttorneyResponse[]>(this.baseUrl, {params: httpParams}).pipe(
      catchError(this.handleError<PowerOfAttorneyResponse[]>('getPowerOfAttorneysByFilter'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    }
  }
}
