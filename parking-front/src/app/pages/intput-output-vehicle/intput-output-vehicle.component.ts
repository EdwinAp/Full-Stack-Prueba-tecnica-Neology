import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule, MatHint } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { VehiclesService } from '../../services/vehicles.service';
import { UpVehicle } from '../../models/up-vehicle';
import { MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle } from '@angular/material/card';

enum OptionsIO {
  DEFAULT,
  INPUT,
  OUTPUT
}

interface OptionDesc {
  key: OptionsIO
  name: string
}

@Component({
  selector: 'app-intput-output-vehicle',
  imports: [
    MatSelectModule,
    MatInputModule,
    MatFormFieldModule,
    CommonModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
  ],
  templateUrl: './intput-output-vehicle.component.html',
  styleUrl: './intput-output-vehicle.component.scss'
})
export class IntputOutputVehicleComponent {

  validatePlate: string | undefined
  validateOption: string | undefined

  plate: string = '';

  optionIO: OptionsIO = OptionsIO.DEFAULT

  inputOutput: OptionDesc[] = [
    {
      key: OptionsIO.DEFAULT,
      name: 'Sin Seleccionar'
    },
    {
      key: OptionsIO.INPUT,
      name: 'Registrar Entrada'
    },
    {
      key: OptionsIO.OUTPUT,
      name: 'Registrar Salida'
    }
  ]

  constructor(
    private vehicleService: VehiclesService
  ) {}


  registerInputOutput() {

    this.validatePlate = undefined
    this.validateOption = undefined

    const regex = /^(?:[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*){4,}$/

    if (regex.test(this.plate) === false) {
      this.validatePlate = 'Corregir formato (exp: B67-IBT7, 765KR, I76-5NT9-8NP...) Min 4 caracteres'
    }

    if (this.optionIO == OptionsIO.DEFAULT) {
      this.validateOption = 'Selecciona alguna opcion'
    }

    if (this.validateOption || this.validatePlate) return;

    let vehicle: UpVehicle = {
      plate: this.plate,
      catVehicle: 0
    }
    if (OptionsIO.INPUT == this.optionIO) {
      this.vehicleService.inputVehicle(vehicle).subscribe({
        next: (vehicle) => {
          console.log(vehicle);
          this.plate = '';
          this.optionIO = OptionsIO.DEFAULT
        }
      })
    }
    if (OptionsIO.OUTPUT == this.optionIO) {
      this.vehicleService.outputVehicle(vehicle).subscribe({
        next: (vehicle) => {
          console.log(vehicle);
          this.plate = '';
          this.optionIO = OptionsIO.DEFAULT
        }
      })
    }
  }

}
