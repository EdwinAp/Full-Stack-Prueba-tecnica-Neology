import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatTab, MatTabGroup } from '@angular/material/tabs';
import { ActivatedRoute } from '@angular/router';
import { VehiclesService } from '../../services/vehicles.service';
import { VehicleData } from '../../models/vehicle-data';
import { StaysVehicle } from '../../models/stays-vehicle';
import { MatCell, MatHeaderCell, MatHeaderRow, MatRow, MatTable, MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-detail-vehicle',
  imports: [
    MatTabGroup,
    MatTab,
    CommonModule,
    MatTable,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatTableModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './detail-vehicle.component.html',
  styleUrl: './detail-vehicle.component.scss'
})
export class DetailVehicleComponent {

  isLoading: boolean = true

  displayedColumns: string[] = ['plate', 'inputDate', 'outputDate',];

  plate: string = ''
  vehicleData!: VehicleData;
  staysVehicle: StaysVehicle[] = []

  constructor(
    private route: ActivatedRoute,
    private vehicleService: VehiclesService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.plate = params.get('plate')!;
      console.log('Plate recibido:', this.plate);

      this.isLoading = true
      
      forkJoin({
        vehicleData: this.vehicleService.obtainVehicleInformation(this.plate),
        staysVehicle: this.vehicleService.obtainStayVehicle(this.plate)
      }).subscribe({
        next: result => {
          this.vehicleData = result.vehicleData;
          this.staysVehicle = result.staysVehicle;
          this.isLoading = false;
        },
        error: ex => {
          console.error('Error en alguna solicitud:', ex);
          this.isLoading = false;
        }
      });

    });

    
/*
    this.vehicleService.obtainVehicleInformation(this.plate)
      .subscribe({
        next: data => {
          this.vehicleData = data
          this.isLoading = false
        },
        error: ex => {

        }
      })

    this.vehicleService.obtainStayVehicle(this.plate)
      .subscribe({
        next: data => {
          this.staysVehicle = data
          this.isLoading = false
        },
        error: ex => {

        }
      })*/
  }

}
