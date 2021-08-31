import {Account} from "../account/account.model";

export interface PowerOfAttorney {
  accountNumber: string;
  granteeName: string
  grantorName: string;
  authorization: Authorization
}

export interface PowerOfAttorneyResponse {
  id: string;
  granteeName: string;
  grantorName: string;
  authorization: Authorization;
  account: Account;
}

export enum Authorization {
  READ = "READ",
  WRITE = "WRITE"
}
