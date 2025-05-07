import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { VehiclesService } from '../../services/vehicles.service';
import { UpVehicle } from '../../models/up-vehicle';

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
    FormsModule
  ],
  templateUrl: './intput-output-vehicle.component.html',
  styleUrl: './intput-output-vehicle.component.scss'
})
export class IntputOutputVehicleComponent {

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
    let vehicle: UpVehicle = {
      plate: this.plate,
      catVehicle: 0
    }
    if (OptionsIO.INPUT == this.optionIO) {
      this.vehicleService.inputVehicle(vehicle).subscribe({
        next: (vehicle) => {
          console.log(vehicle);
        }
      })
    }
    if (OptionsIO.OUTPUT == this.optionIO) {
      this.vehicleService.outputVehicle(vehicle).subscribe({
        next: (vehicle) => {
          console.log(vehicle);
        }
      })
    }
  }

}
