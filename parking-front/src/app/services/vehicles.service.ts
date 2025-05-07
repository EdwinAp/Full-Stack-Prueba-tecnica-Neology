import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UpVehicle } from '../models/up-vehicle';
import { Observable } from 'rxjs';
import { CatVehicles } from '../models/cat-vehicles';


@Injectable({
  providedIn: 'root',
})
export class VehiclesService  {

  urlBase: string = 'http://localhost:8080/neo'

  constructor(
    private http: HttpClient
  ) { }

  upVehicle(upVehicle: UpVehicle) : Observable<UpVehicle> {
    return this.http.post<UpVehicle>(`${this.urlBase}/vehicle/up`, upVehicle);
  }

  getCatVehicles() : Observable<CatVehicles[]> {
    return this.http.get<CatVehicles[]>(`${this.urlBase}/vehicle/catalog`);
  }

  inputVehicle(upVehicle: UpVehicle) : Observable<UpVehicle> {
    return this.http.post<UpVehicle>(`${this.urlBase}/stays/input`, upVehicle);
  }

  outputVehicle(upVehicle: UpVehicle) : Observable<UpVehicle> {
    return this.http.post<UpVehicle>(`${this.urlBase}/stays/output`, upVehicle);
  }

}
