import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtResponse } from 'src/app/interfaces/jwt-response';
import { User } from 'src/app/interfaces/user';
import { DataService } from 'src/app/services/data.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  jwtRes!: JwtResponse;
  user: User|null = null;

  gap: string = "";
  subDate: string = "";
  lastContact: string = "";
  partial: string = "";
  date: string = "";
  year: string = "";
  state: string = "";
  moreThan: string = "";
  lessThan: string = "";

  @ViewChild('form') form!: NgForm;
  @ViewChild('form1') form1!: NgForm;
  @ViewChild('form2') form2!: NgForm;
  @ViewChild('form3') form3!: NgForm;
  @ViewChild('form4') form4!: NgForm;
  @ViewChild('form5') form5!: NgForm;
  @ViewChild('form6') form6!: NgForm;
  @ViewChild('form7') form7!: NgForm;

  constructor(private dataSrv: DataService,  private router: Router) { }

 ngOnInit():void {
    this.getUser();
  }

  getUser(){
    const user = localStorage.getItem('user');
    if(!user) return;
    else{
      this.dataSrv.getUser(JSON.parse(user).id, JSON.parse(user).token).subscribe({
        next:data => data != null? this.user = data : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  async onSubmit(){
    this.router.navigate(['/customer/filter_by_gap='+this.form.value.gap]);
  }
  async onSubmit1(){
    this.router.navigate(['/customer/filter_by_sub_date='+this.form1.value.subDate]);
  }
  async onSubmit2(){
    this.router.navigate(['/customer/filter_by_last_date='+this.form2.value.lastContact]);
  }
  async onSubmit3(){
    this.router.navigate(['/customer/filter_by_partial_name='+this.form3.value.partial]);
  }
  async onSubmit4(){
    this.router.navigate(['/invoice/filter_by_date='+this.form4.value.date]);
  }
  async onSubmit5(){
    this.router.navigate(['/invoice/filter_by_year='+this.form5.value.year]);
  }
  async onSubmit6(){
    this.router.navigate(['/invoice/filter_by_state='+this.form6.value.state]);
  }
  async onSubmit7(){
    this.router.navigate(['/invoice/where_total_more_than=' + this.form7.value.moreThan + '&less_than=' + this.form7.value.lessThan]);
  }

}
