import { AuthService } from 'src/app/services/auth.service';
import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, AfterViewChecked {

  search: string = "";
  @ViewChild('form') form!: NgForm;
  @ViewChild('login') login!: ElementRef<HTMLDataListElement>;
  @ViewChild('logout') logout!: ElementRef<HTMLDataListElement>;
  token : string|null = null;
  tok:string|null = "";

  constructor(private router: Router, private authSrv: AuthService) { }

  ngOnInit(): void {
    this.authSrv.auth$.subscribe({
      next:data => data != null? this.token = data.token: console.log(data),
      error: e => console.log(e)
    })
    this.tok = localStorage.getItem("user");
  }

  ngAfterViewChecked(): void {
    if (this.tok != "" && this.tok != null) {
      this.login.nativeElement.classList.add("d-none");
      this.logout.nativeElement.classList.remove("d-none");
    }
  }

  async onSubmit(){
    this.router.navigate(['/invoices/' + this.form.value.search]);
  }

  onLogout() {
    this.authSrv.logout();
  }
}
