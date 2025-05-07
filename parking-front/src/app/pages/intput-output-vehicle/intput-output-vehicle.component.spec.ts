import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntputOutputVehicleComponent } from './intput-output-vehicle.component';

describe('IntputOutputVehicleComponent', () => {
  let component: IntputOutputVehicleComponent;
  let fixture: ComponentFixture<IntputOutputVehicleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IntputOutputVehicleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntputOutputVehicleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
