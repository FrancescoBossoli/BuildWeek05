import { Customer } from "./customer";

export interface Paging {
  content: Customer[],
  empty: boolean,
  first: boolean,
  last: boolean,
  number: number,
  numberOfElements: number,
  pageable: {},
  size: number,
  sort: {},
  totalElements: number,
  totalPages: number
}
