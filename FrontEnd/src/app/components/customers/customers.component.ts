import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Customer } from 'src/app/interfaces/customer';
import { JwtResponse } from 'src/app/interfaces/jwt-response';
import { DataService } from 'src/app/services/data.service';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss']
})
export class CustomersComponent implements OnInit {

  param!: string | null;
  allCustomers! : Customer[];

  constructor(private route: ActivatedRoute,private dataSrv: DataService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.param = params.get("id");
      console.log(this.param);

      if(!this.param){
        this.getAllCustomers();
      } else if(this.param.startsWith("gap")) {
        this.getByGap();
      } else if( this.param.startsWith("subDate")){
        this.getBySubDate();
      } else if(this.param.startsWith("lastContact")){
        this.getByLastContact();
      }else if(this.param.startsWith("filter")){
        this.getFiltered(this.param);
      } else{
        this.getByProvince();
      }
  })
}

  getFiltered(param: string){
    const user = localStorage.getItem('user');
    if(!user){
      return;
    } else{
      const userParsed: JwtResponse = JSON.parse(user);
      this.dataSrv.filter(param,userParsed.token).subscribe({
        next:data => data != null? this.allCustomers = data : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  getByProvince(){
    const user = localStorage.getItem('user');
    if(!user){
      return;
    } else{
      const userParsed: JwtResponse = JSON.parse(user);
      this.dataSrv.getByProvince(userParsed.token).subscribe({
        next:data => data != null? this.allCustomers = data : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  getByLastContact(){
    const user = localStorage.getItem('user');
    if(!user){
      return;
    } else{
      const userParsed: JwtResponse = JSON.parse(user);
      this.dataSrv.getByLastContact(userParsed.token).subscribe({
        next:data => data != null? this.allCustomers = data.content : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  getByGap(){
    const user = localStorage.getItem('user');
    if(!user){
      return;
    } else{
      const userParsed: JwtResponse = JSON.parse(user);
      this.dataSrv.getByGap(userParsed.token).subscribe({
        next:data => data != null? this.allCustomers = data.content : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  getBySubDate(){
    const user = localStorage.getItem('user');
    if(!user){
      return;
    } else{
      const userParsed: JwtResponse = JSON.parse(user);
      this.dataSrv.getBySubDate(userParsed.token).subscribe({
        next:data => data != null? this.allCustomers = data.content : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  getAllCustomers(){
    const user = localStorage.getItem('user');
    if(!user){
      return;
    } else{
      const userParsed: JwtResponse = JSON.parse(user);
      this.dataSrv.getAllCustomers(userParsed.token).subscribe({
        next:data => data != null? this.allCustomers = data : console.log(data),
        error: e => console.log(e)
      })
    }
  }

}
