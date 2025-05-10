import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatOption, MatSelect } from '@angular/material/select';
import { CatVehicles } from '../../models/cat-vehicles';
import { VehiclesService } from '../../services/vehicles.service';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { GenerateReportService } from '../../services/report/generate-report.service';

@Component({
  selector: 'app-report-page',
  imports: [
    MatCard,
    MatCardContent,
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    FormsModule,
    CommonModule,
    MatIcon,
    MatButtonModule,
    MatCardHeader,
    MatCardTitle,
    MatInputModule,
  ],
  templateUrl: './report-page.component.html',
  styleUrl: './report-page.component.scss'
})
export class ReportPageComponent implements OnInit {

  validateName: string | undefined
  validateOption: string | undefined

  listCatVehicles: CatVehicles[] = []
  catVehicle: number = 0;
  nameFile: string = ''

  defaultOptionVehicle: CatVehicles = {
    id: 0,
    name: 'Sin Seleccion',
    description: '',
  }

  constructor(
    private vehiclesService: VehiclesService,
    private generateReportService: GenerateReportService
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

  generateReport(): void {

    this.validateName = undefined
    this.validateOption = undefined

    const regex = /^[A-Za-z0-9 ]{5,}$/

    if (regex.test(this.nameFile) === false) {
      this.validateName = 'Corregir formato (exp: Nombre archivo, Nom8 43tipo) Min 5 caracteres'
    }

    if (this.catVehicle == 0) {
      this.validateOption = 'Selecciona alguna opcion'
    }

    if (this.validateOption || this.validateName) return;

    this.generateReportService.generateReportByCatVehicleWithName(this.catVehicle, this.nameFile).subscribe(response => {
      const blob = new Blob([response.body!], { type: 'text/csv' });

      // Extraer nombre del archivo del header 'Content-Disposition'
      const contentDisposition = response.headers.get('Content-Disposition');
      const matches = /filename="?(.*)"?/.exec(contentDisposition || '');
      const filename = this.nameFile + ".csv";

      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = filename;
      link.click();

      window.URL.revokeObjectURL(link.href);

      this.nameFile = ''
      this.catVehicle = 0
    });
  }

}
