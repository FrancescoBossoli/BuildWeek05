import { Municipality } from "./municipality";

export interface Address {
  id: number,
	street: string,
	number: number,
	city:string,
	postCode: number,
	municipality: Municipality
}
