import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Router } from '@angular/router';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    MatMenuModule,
    MatButtonModule,
    MatToolbarModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'parking-front';

  constructor(private router: Router) { }

  navegarAListado() {
    this.router.navigate(['/list-vehicle']);
  }

  navegarAEntradaSalida() {
    this.router.navigate(['/input-output-vehicle']);
  }

  navegarAAltaVehicle() {
    this.router.navigate(['/up-vehicle']);
  }

  navegarADetailVehicle() {
    this.router.navigate(['/detail-vehicle']);
  }
  
  
  navegarAReport() {
    this.router.navigate(['/report-page']);
  }

}
