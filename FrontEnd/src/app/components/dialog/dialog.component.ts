import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';


@Component({
  selector: 'dialog',
  templateUrl: 'dialog.component.html',
})
export class DialogComponent{

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onYesClick() {
    console.log('usun w pizdu');
    this.dialogRef.close();
  }
}
