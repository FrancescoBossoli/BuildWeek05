import { Province } from "./province"

export interface Municipality {
  id: number,
  districtCode: number
  name: string
  province: Province[]
}
