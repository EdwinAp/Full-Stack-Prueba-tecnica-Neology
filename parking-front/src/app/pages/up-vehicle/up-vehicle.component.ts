import { Component, OnInit } from '@angular/core';
import { VehiclesService } from '../../services/vehicles.service';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CatVehicles } from '../../models/cat-vehicles';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CONTEXT_MENU } from '@angular/cdk/keycodes';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-up-vehicle',
  imports: [
    MatSelectModule,
    MatInputModule,
    MatFormFieldModule,
    CommonModule,
    MatButtonModule,
    MatIconModule,
    FormsModule
  ],
  templateUrl: './up-vehicle.component.html',
  styleUrl: './up-vehicle.component.scss'
})
export class UpVehicleComponent implements OnInit {

  listCatVehicles: CatVehicles[] = []

  plate: string = '';
  catVehicle: number = 0;

  constructor(
    private vehiclesService: VehiclesService
  ) {

    
  }

  ngOnInit(): void {
    
    this.vehiclesService.getCatVehicles()
    .subscribe({
      next: (data) => {
        this.listCatVehicles = data;
        console.log(data)
      }
    })
  }

  saveVehicle() {
    this.vehiclesService.upVehicle({ 
      plate: this.plate,
      catVehicle:  this.catVehicle
    }).subscribe({
      next: (data) => {
        console.log(data)
      }
    })
  }

}
