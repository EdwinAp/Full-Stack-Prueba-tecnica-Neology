import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogCuestionComponent } from './dialog-cuestion.component';

describe('DialogCuestionComponent', () => {
  let component: DialogCuestionComponent;
  let fixture: ComponentFixture<DialogCuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DialogCuestionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogCuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
