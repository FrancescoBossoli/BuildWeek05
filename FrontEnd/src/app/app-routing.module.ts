import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { CustomersComponent } from './components/customers/customers.component';
import { InvoicesComponent } from './components/invoices/invoices.component';

const routes: Routes = [
  {path:"", component: HomeComponent},
  {path:"login", component: LoginComponent},
  {path:"customers", component: CustomersComponent},
  {path:"customers/:id", component: CustomersComponent},
  {path:"customer/:id", component: CustomersComponent},
  {path:"invoices", component: InvoicesComponent},
  {path:"invoices/:id", component: InvoicesComponent},
  {path:"invoice/:id", component: InvoicesComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
