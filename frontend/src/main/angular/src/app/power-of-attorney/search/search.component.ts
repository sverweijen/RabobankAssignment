import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Authorization, PowerOfAttorneyResponse} from "../power-of-attorney.model";
import {PowerOfAttorneyResource} from "../power-of-attorney.resource";
import {PowerOfAttorneyService} from "../power-of-attorney.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  displayedColumns: string[] = ['holder', 'balance', 'type', 'authorization'];
  dataSource?: PowerOfAttorneyResponse[];

  public powerOfAttorney = new FormGroup({
    granteeName: new FormControl('', Validators.required),
    authorization: new FormControl(''),
  });

  public options = [null, Authorization.READ, Authorization.WRITE];

  constructor(
    private powerOfAttorneyResource: PowerOfAttorneyResource,
    public powerOfAttorneyService: PowerOfAttorneyService
  ) { }

  public ngOnInit(): void { }

  public searchPowerOfAttorney() {
    if (this.powerOfAttorney.valid) {
      const powerOfAttorney = this.powerOfAttorney.getRawValue();
      this.powerOfAttorneyResource.getPowerOfAttorneysByFilter(powerOfAttorney.granteeName, powerOfAttorney.authorization)
        .subscribe({
          next: poa => this.dataSource = poa,
          error: err => console.error(err)
        });
    }
    this.powerOfAttorneyService.validateAllFormFields(this.powerOfAttorney);
  }

}
