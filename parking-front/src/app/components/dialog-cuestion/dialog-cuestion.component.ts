import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-cuestion',
  imports: [
    MatButtonModule, 
    MatDialogActions, 
    MatDialogClose, 
    MatDialogTitle, 
    MatDialogContent
  ],
  templateUrl: './dialog-cuestion.component.html',
  styleUrl: './dialog-cuestion.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DialogCuestionComponent {

  readonly dialogRef = inject(MatDialogRef<DialogCuestionComponent>);
  
  cerrarDialogo(resultado?: any): void {
    this.dialogRef.close(resultado);
  }

}
