import {Component, OnInit} from '@angular/core';
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

  public displayedColumns: string[] = ['holder', 'balance', 'type', 'authorization'];
  public dataSource?: PowerOfAttorneyResponse[];
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
        .subscribe(poa => {
          this.dataSource = poa;
          if (this.dataSource.length === 0) {
            this.powerOfAttorneyService.openSnackBar('There are no Power of Attoneys that match this query', 'OK');
          }
        });
    }
    this.powerOfAttorneyService.validateAllFormFields(this.powerOfAttorney);
  }

}
