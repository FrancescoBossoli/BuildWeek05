import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Invoice } from 'src/app/interfaces/invoice';
import { DataService } from 'src/app/services/data.service';

@Component({
  selector: 'app-invoices',
  templateUrl: './invoices.component.html',
  styleUrls: ['./invoices.component.scss']
})
export class InvoicesComponent implements OnInit {

  name!: string | null;
  allInvoices!: Invoice[];

  constructor(private route: ActivatedRoute, private dataSrv: DataService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.name = params.get("id");
      if (!this.name) this.getAllInvoices();
      else if (this.name.startsWith("filter")) this.filter();
      else if (this.name.startsWith("where")) this.rangedSearch();
      else this.getSingleInvoice(this.name);
    });
  }

  getSingleInvoice(name: string) {
    const user = localStorage.getItem('user');
    if (!user) return;
    else {
      this.dataSrv.getSingleInvoice(name, JSON.parse(user).token).subscribe({
        next: data => data != null ? this.allInvoices = data : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  getAllInvoices() {
    const user = localStorage.getItem('user');
    if (!user) return;
    else {
      this.dataSrv.getAllInvoices(JSON.parse(user).token).subscribe({
        next: data => data != null ? this.allInvoices = data : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  filter() {
    const user = localStorage.getItem('user');
    if (!user) return;
    else {
      this.dataSrv.getFilteredInvoices(this.name!, JSON.parse(user).token).subscribe({
        next: data => data != null ? this.allInvoices = data : console.log(data),
        error: e => console.log(e)
      })
    }
  }

  rangedSearch() {
    const user = localStorage.getItem('user');
    if (!user) return;
    else {
      let amount1 = this.name!.substring(22, this.name!.indexOf("&"));
      let amount2 = this.name!.substring(this.name!.indexOf("less_than") + 10);
      this.dataSrv.getInvoicesWithAmountRange(amount1, amount2, JSON.parse(user).token).subscribe({
        next: data => data != null ? this.allInvoices = data : console.log(data),
        error: e => console.log(e)
      })
    }
  }

}
