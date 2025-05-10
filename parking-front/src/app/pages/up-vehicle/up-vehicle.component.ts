import { Component, OnInit } from '@angular/core';
import { VehiclesService } from '../../services/vehicles.service';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CatVehicles } from '../../models/cat-vehicles';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatDividerModule } from '@angular/material/divider';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';

@Component({
  selector: 'app-up-vehicle',
  imports: [
    MatCard,
    MatCardContent,
    MatSelectModule,
    MatInputModule,
    MatFormFieldModule,
    CommonModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    MatGridListModule,
    MatDividerModule,
    MatCardHeader,
    MatCardTitle,
  ],
  templateUrl: './up-vehicle.component.html',
  styleUrl: './up-vehicle.component.scss'
})
export class UpVehicleComponent implements OnInit {

  validatePlate: string | undefined
  validateOption: string | undefined

  listCatVehicles: CatVehicles[] = []

  defaultOptionVehicle: CatVehicles = {
    id: 0,
    name: 'Sin Seleccion',
    description: '',
  }

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
        this.listCatVehicles = [this.defaultOptionVehicle, ...data];
        console.log(data)
      }
    })
  }

  saveVehicle() {

    this.validatePlate = undefined
    this.validateOption = undefined

    const regex = /^(?:[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*){4,}$/

    if (regex.test(this.plate) === false) {
      this.validatePlate = 'Corregir formato (exp: B67-IBT7, 765KR, I76-5NT9-8NP...) Min 4 caracteres'
    }

    if (this.catVehicle == 0) {
      this.validateOption = 'Selecciona alguna opcion'
    }

    if (this.validateOption || this.validatePlate) return;


    this.vehiclesService.upVehicle({ 
      plate: this.plate,
      catVehicle:  this.catVehicle
    }).subscribe({
      next: (data) => {
        console.log(data)
        this.plate = ''
        this.catVehicle = 0
      }
    })
  }

}
