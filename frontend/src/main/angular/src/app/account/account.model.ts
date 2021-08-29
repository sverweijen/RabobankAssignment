export interface Account {
  id: string;
  accountNumber: string
  accountHolderName: string;
  balance: number;
  accountType: AccountType
}

export enum AccountType {
  PAYMENT = "PAYMENT",
  SAVINGS = "SAVINGS"
}
