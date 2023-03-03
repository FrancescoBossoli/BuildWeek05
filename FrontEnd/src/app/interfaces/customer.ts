import { Address } from "./address";
import { Invoice } from "./invoice";

export interface Customer {
  id:number,
	businessName: string,
	vatNumber: number,
	email: string,
	subDate: Date,
  lastDate: Date,
	gap: number,
	pec: string,
	phone: string,
	contactEmail: string,
	contactName: string,
	contactSurname: string,
	contactPhone: string,
	type: string,
	legalAddress: Address,
  productionAddress: Address,
  invoices: Invoice[]
}
