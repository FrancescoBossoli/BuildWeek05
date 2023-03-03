import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string = "";
  password: string = "";
  @ViewChild('form') form!: NgForm;

  constructor(private authSrv: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authSrv.checkLog();
  }

  async onSubmit(){
    let login = this.authSrv.login(this.form.value);
    let response = await lastValueFrom(login);
    this.form.reset();
    this.router.navigate(['/']);

  }
}
