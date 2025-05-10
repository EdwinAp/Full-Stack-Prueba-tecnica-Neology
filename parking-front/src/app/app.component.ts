import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Router } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { DialogCuestionComponent } from './components/dialog-cuestion/dialog-cuestion.component';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { VehiclesService } from './services/vehicles.service';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    MatMenuModule,
    MatButtonModule,
    MatToolbarModule,
    CommonModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'parking-front';

  readonly dialog = inject(MatDialog);

  constructor(
    private router: Router,
    private vehiclesService: VehiclesService

  ) { }

  navegarAListado() {
    this.router.navigate(['/list-vehicle']);
  }

  navegarAEntradaSalida() {
    this.router.navigate(['/input-output-vehicle']);
  }

  navegarAAltaVehicle() {
    this.router.navigate(['/up-vehicle']);
  }

  navegarAReport() {
    this.router.navigate(['/report-page']);
  }


  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogRef = this.dialog.open(DialogCuestionComponent, {
      width: 'auto',
      enterAnimationDuration,
      exitAnimationDuration,
    });

    dialogRef.afterClosed().subscribe(result => {

      if (result === true) {
        this.vehiclesService.deleteStay().subscribe({
          next: (response) => {
            console.log(response)
          }
        })
      }
    });
  }


}
