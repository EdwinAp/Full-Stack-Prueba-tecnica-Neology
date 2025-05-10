import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenerateReportService {

  urlBase: string = 'http://localhost:8080/neo'

  constructor(
    private http: HttpClient
  ) { }

  

  generateReportByCatVehicleWithName(catVehicleId: number, nameFile: string) : Observable<HttpResponse<Blob>> {
     return this.http.get(`${this.urlBase}/payments/by/${catVehicleId}/${nameFile}`, {
      observe: 'response',
      responseType: 'blob'
    });
  }

}
