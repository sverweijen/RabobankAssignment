import {CreateComponent} from './create.component';
import {PowerOfAttorneyResource} from "../power-of-attorney.resource";
import {PowerOfAttorneyService} from "../power-of-attorney.service";
import {Authorization, PowerOfAttorneyResponse} from "../power-of-attorney.model";
import {AccountType} from "../../account/account.model";
import {of, throwError} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";

describe('CreateComponent', () => {
  let component: CreateComponent;

  let powerOfAttorneyResourceSpy = jasmine.createSpyObj('PowerOfAttorneyResource', ['createPowerOfAttorney']);
  let powerOfAttorneyServiceSpy = jasmine.createSpyObj('PowerOfAttorneyService', ['validateAllFormFields', 'openSnackBar']);

  beforeEach(() => {
    component = new CreateComponent(powerOfAttorneyResourceSpy, powerOfAttorneyServiceSpy);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('createPowerOfAttorney', done => {
    component.powerOfAttorney = new FormGroup({
      accountNumber: new FormControl('fake_accountNumber', Validators.required),
      granteeName: new FormControl('Grantee_1', Validators.required),
      grantorName: new FormControl('Holder_1', Validators.required),
      authorization: new FormControl(Authorization.READ, Validators.required),
    });
    const powerOfAttorneyResponse: PowerOfAttorneyResponse = {
      id: "1",
      authorization: Authorization.READ,
      granteeName: "Grantee_1",
      grantorName: "Holder_1",
      account: {
        id: "1",
        accountNumber: "fake_accountNumber",
        accountHolderName: "Holder_1",
        balance: 12000,
        accountType: AccountType.SAVINGS
      },
    }
    powerOfAttorneyResourceSpy.createPowerOfAttorney.and.returnValue(of(powerOfAttorneyResponse));
    component.createPowerOfAttorney();
    powerOfAttorneyResourceSpy.createPowerOfAttorney(component.powerOfAttorney.getRawValue()).subscribe(() => {
      expect(powerOfAttorneyServiceSpy.openSnackBar).toHaveBeenCalledWith('Power of Attorney successfully created', 'OK');
      done();
    });
  });

  it('createPowerOfAttorney form invalid', () => {
    component.powerOfAttorney = new FormGroup({
      accountNumber: new FormControl('', Validators.required),
      granteeName: new FormControl('', Validators.required),
      grantorName: new FormControl('', Validators.required),
      authorization: new FormControl(null, Validators.required),
    });
    component.createPowerOfAttorney();
    expect(powerOfAttorneyServiceSpy.validateAllFormFields).toHaveBeenCalledWith(component.powerOfAttorney);
  });

  it('createPowerOfAttorney BAD REQUEST', () => {
    component.powerOfAttorney = new FormGroup({
      accountNumber: new FormControl('fake_accountNumber', Validators.required),
      granteeName: new FormControl('Grantee_1', Validators.required),
      grantorName: new FormControl('Holder_1', Validators.required),
      authorization: new FormControl(Authorization.READ, Validators.required),
    });
    powerOfAttorneyResourceSpy.createPowerOfAttorney.and.returnValue(throwError('error'));
    component.createPowerOfAttorney();
    expect(powerOfAttorneyServiceSpy.openSnackBar).toHaveBeenCalledWith('BAD REQUEST: Grantor name does not match account holder', 'OK');
  });
});
