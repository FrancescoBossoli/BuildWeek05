import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, tap, throwError } from 'rxjs';
import { JwtResponse } from '../interfaces/jwt-response';
import { LoginRequest } from '../interfaces/login-request';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url: string = "http://localhost:9090/";
  private authSubject = new BehaviorSubject<JwtResponse | null>(null);
  auth$ = this.authSubject.asObservable();
  jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) { }

  login(logReq: LoginRequest) {
    return this.http.post<JwtResponse>(this.url + "auth/login", logReq).pipe(tap((data) => {
      this.storeToken(data);
    }), catchError(this.errors));
  }

  storeToken(data: JwtResponse) {
    this.authSubject.next(data);
    localStorage.setItem("user", JSON.stringify(data));
  }

  checkLog() {
    const token = localStorage.getItem("user");
    if (token) {
      const res: JwtResponse = JSON.parse(token);
      const expiration = this.jwtHelper.getTokenExpirationDate(res.token) as Date;
      this.authSubject.next(res);
      const remainingTime = expiration.getTime() - new Date().getTime();
      if (remainingTime <= 0) this.logout();
    } else return;
  }

  logout() {
    this.authSubject.next(null);
    localStorage.removeItem('user')
    window.location.href = 'http://localhost:4200/';
  }

  private errors(e: any) {
    switch (e.error) {
      case "Not Found":
        return throwError(() => new Error("Username o password errati!"));
      case "Email and password are required":
        return throwError(() => new Error("Email e password sono necessarie"));
      case "Email already exists":
        return throwError(() => new Error("Utente gia registrato"));
      case "Email format is invalid":
        return throwError(() => new Error('Il campo "email" presenta errori'));
      case "Cannot find user":
        return throwError(() => new Error("Non è stato trovato alcun utente corrispondente ai dati inseriti"));
      default:
        return throwError(() => new Error("Errore!"));
    }
  }

}
