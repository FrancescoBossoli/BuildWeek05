import { Role } from "./role";

export interface JwtResponse {
  token: string,
  type: string,
  id: number,
  username: string,
  email: string,
  roles: Role[]
}
