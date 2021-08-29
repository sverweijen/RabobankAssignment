import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Authorization, PowerOfAttorney} from "../power-of-attorney.model";
import {PowerOfAttorneyResource} from "../power-of-attorney.resource";
import {PowerOfAttorneyService} from "../power-of-attorney.service";

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html'
})
export class CreateComponent implements OnInit {

  public powerOfAttorney = new FormGroup({
    accountNumber: new FormControl('', Validators.required),
    granteeName: new FormControl('', Validators.required),
    grantorName: new FormControl('', Validators.required),
    authorization: new FormControl('', Validators.required),
  });

  public options = [Authorization.READ, Authorization.WRITE];

  constructor(
    private powerOfAttorneyResource: PowerOfAttorneyResource,
    public powerOfAttorneyService: PowerOfAttorneyService
  ) { }

  public ngOnInit(): void { }

  public createPowerOfAttorney() {
    if (this.powerOfAttorney.valid) {
      const powerOfAttorney: PowerOfAttorney = this.powerOfAttorney.getRawValue();
      this.powerOfAttorneyResource.createPowerOfAttorney(powerOfAttorney)
        .subscribe({
          next: poa => console.log("poa: ", poa),
          error: err => console.error(err)
        });
    }
    this.powerOfAttorneyService.validateAllFormFields(this.powerOfAttorney);
  }

}
