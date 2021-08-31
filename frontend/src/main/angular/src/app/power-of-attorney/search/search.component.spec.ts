import {SearchComponent} from './search.component';
import {PowerOfAttorneyResource} from "../power-of-attorney.resource";
import {PowerOfAttorneyService} from "../power-of-attorney.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {of} from "rxjs";
import {Authorization, PowerOfAttorneyResponse} from "../power-of-attorney.model";
import {AccountType} from "../../account/account.model";

describe('SearchComponent', () => {
  let component: SearchComponent;
  let powerOfAttorneyResourceSpy = jasmine.createSpyObj('PowerOfAttorneyResource', ['getPowerOfAttorneysByFilter']);
  let powerOfAttorneyServiceSpy = jasmine.createSpyObj('PowerOfAttorneyService', ['validateAllFormFields', 'openSnackBar']);

  beforeEach(() => {
    component = new SearchComponent(powerOfAttorneyResourceSpy, powerOfAttorneyServiceSpy);

    component.powerOfAttorney = new FormGroup({
      granteeName: new FormControl('holder', Validators.required),
      authorization: new FormControl(''),
    });
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('searchPowerOfAttorney', (done: DoneFn) => {
    const powerOfAttorney: PowerOfAttorneyResponse = {
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
    powerOfAttorneyResourceSpy.getPowerOfAttorneysByFilter.and.returnValue(of([powerOfAttorney]));
    component.searchPowerOfAttorney();
    powerOfAttorneyResourceSpy.getPowerOfAttorneysByFilter('Grantee', Authorization.READ).subscribe((poas: PowerOfAttorneyResponse[]) => {
      expect(poas.length).toEqual(1);
      expect(component.dataSource?.length).toEqual(1);
      expect(component.dataSource).toEqual(poas);
      done();
    });
  });
  it('searchPowerOfAttorney no data', (done: DoneFn) => {
    powerOfAttorneyResourceSpy.getPowerOfAttorneysByFilter.and.returnValue(of([]));
    component.searchPowerOfAttorney();
    powerOfAttorneyResourceSpy.getPowerOfAttorneysByFilter('Grantee', Authorization.READ).subscribe((poas: PowerOfAttorneyResponse[]) => {
      expect(poas.length).toEqual(0);
      expect(component.dataSource?.length).toEqual(0);
      expect(component.dataSource).toEqual(poas);
      expect(powerOfAttorneyServiceSpy.openSnackBar).toHaveBeenCalledWith('There are no Power of Attoneys that match this query', 'OK');
      done();
    });
  });
  it('searchPowerOfAttorney invalid form', () => {
    component.powerOfAttorney.get('granteeName')?.setValue('');
    component.searchPowerOfAttorney();
    expect(powerOfAttorneyServiceSpy.validateAllFormFields).toHaveBeenCalledWith(component.powerOfAttorney);
  });
});
