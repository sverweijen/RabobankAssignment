import {PowerOfAttorneyService} from './power-of-attorney.service';
import {FormControl, FormGroup} from "@angular/forms";

describe('PowerOfAttorneyService', () => {
  let service: PowerOfAttorneyService;
  let matSnackBarSpy = jasmine.createSpyObj('MatSnackBar', ['open']);
  beforeEach(() => {
    service = new PowerOfAttorneyService(matSnackBarSpy);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('validateAllFormFields', () => {
    const form = new FormGroup({control: new FormControl('')});
    service.validateAllFormFields(form);
    expect(form.get('control')?.touched).toBeTruthy();
  });

  it('getErrorMessage', () => {
    expect(service.getErrorMessage()).toEqual('You must enter a value');
  });

  it('openSnackBar', () => {
    service.openSnackBar('message', 'action');
    expect(matSnackBarSpy.open).toHaveBeenCalledWith('message', 'action');
  });
});
