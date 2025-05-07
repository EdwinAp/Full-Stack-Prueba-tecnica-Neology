import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpVehicleComponent } from './up-vehicle.component';

describe('UpVehicleComponent', () => {
  let component: UpVehicleComponent;
  let fixture: ComponentFixture<UpVehicleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpVehicleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpVehicleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
