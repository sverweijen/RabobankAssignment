import {PowerOfAttorneyResource} from './power-of-attorney.resource';
import {Authorization, PowerOfAttorney, PowerOfAttorneyResponse} from "./power-of-attorney.model";
import {of} from "rxjs";
import {AccountType} from "../account/account.model";

describe('PowerOfAttorneyService', () => {
  let resource: PowerOfAttorneyResource;

  let httpClientSpy = jasmine.createSpyObj('HttpClient', ['post', 'get']);
  // const baseUrl = "http://localhost:8080/power-of-attorney";
  const powerOfAttorney: PowerOfAttorney = {
    accountNumber: "fake_accountNumber",
    authorization: Authorization.WRITE,
    granteeName: "Grantee_1",
    grantorName: "Holder_1"
  }
  beforeEach(() => {
    resource = new PowerOfAttorneyResource(httpClientSpy);
  });

  it('createPowerOfAttorney', (done: DoneFn) => {
    // Arrange
    httpClientSpy.post.and.returnValue(of(powerOfAttorney));
    // Act
    resource.createPowerOfAttorney(powerOfAttorney).subscribe(poa => {
      // Assert
      expect(poa).toEqual(powerOfAttorney);
      done();
    },
      done.fail
    );
    expect(httpClientSpy.post.calls.count()).toBe(1, 'one call');
  });

  it('getPowerOfAttorneysByFilter', (done: DoneFn) => {
    // Arrange
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
    httpClientSpy.get.and.returnValue(of([powerOfAttorney]));
    // Act
    resource.getPowerOfAttorneysByFilter("Grantee_1", Authorization.READ).subscribe(poas => {
      // Assert
      expect(poas).toEqual([powerOfAttorney]);
      done();
    },
      done.fail
    );
    expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
  });
});
