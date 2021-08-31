import {Injectable} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class PowerOfAttorneyService {

  constructor(private matSnackBar: MatSnackBar) { }

  public validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  public getErrorMessage() {
    return 'You must enter a value';
  }

  public openSnackBar(message: string, action: string) {
    this.matSnackBar.open(message, action);
  }
}
