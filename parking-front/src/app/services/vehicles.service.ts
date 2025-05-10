import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UpVehicle } from '../models/up-vehicle';
import { Observable } from 'rxjs';
import { CatVehicles } from '../models/cat-vehicles';
import { PageResponse } from '../models/page-response';
import { VehicleData } from '../models/vehicle-data';
import { StaysVehicle } from '../models/stays-vehicle';


@Injectable({
  providedIn: 'root',
})
export class VehiclesService  {

  urlBase: string = 'http://localhost:8080/neo'

  constructor(
    private http: HttpClient
  ) { }

  deleteStay(): Observable<boolean> {
    return this.http.delete<boolean>(`${this.urlBase}/stays/current`);
  }

  obtainVehicleInformation(plate: string): Observable<VehicleData> {
    return this.http.get<VehicleData>(`${this.urlBase}/vehicle/${plate}`);
  }

  obtainStayVehicle(plate: string) : Observable<StaysVehicle[]> {
    return this.http.get<StaysVehicle[]>(`${this.urlBase}/stays/${plate}`);
  }

  pageableVehicles(pageRquest: PageResponse<any>) : Observable<PageResponse<VehicleData>> {
    return this.http.post<PageResponse<VehicleData>>(`${this.urlBase}/vehicle/pageable`, pageRquest);
  }

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
