import { Routes } from '@angular/router';
import { ListVehicleComponent } from './pages/list-vehicle/list-vehicle.component';
import { DetailVehicleComponent } from './pages/detail-vehicle/detail-vehicle.component';
import { IntputOutputVehicleComponent } from './pages/intput-output-vehicle/intput-output-vehicle.component';
import { ReportPageComponent } from './pages/report-page/report-page.component';
import { UpVehicleComponent } from './pages/up-vehicle/up-vehicle.component';

export const routes: Routes = [
    { path: 'list-vehicle', component: ListVehicleComponent},
    { path: 'detail-vehicle', component: DetailVehicleComponent},
    { path: 'input-output-vehicle', component: IntputOutputVehicleComponent},
    { path: 'report-page', component: ReportPageComponent},
    { path: 'up-vehicle', component: UpVehicleComponent},
];
