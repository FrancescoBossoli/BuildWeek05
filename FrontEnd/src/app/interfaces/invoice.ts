import { Customer } from "./customer"

export interface Invoice {
  id:number,
  year: number,
  date: Date,
  total: number,
  customer: Customer,
  state: string
}
