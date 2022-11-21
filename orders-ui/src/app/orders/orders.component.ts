import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Order } from '../model/order';
import { OrdersService } from '../services/orders.service';
import { AppConstants } from '../shared/constants/app.constants';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders: Order[] = [];

  allOrderTypes = AppConstants.OrderTypes;
  message: string = '';
  messageType: string = '';

  filterForm = new FormGroup({
    orderType: new FormControl(''),
    dueDate: new FormControl('')
  });
  
  destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private router: Router, private ordersService: OrdersService) { 

  }

  ngOnInit(): void {
    this.getOrders("", "");
  }
  
  onFilterSearch() {
    let orderType = this.controlValueToString(this.filterForm.controls['orderType'].value);
    let dueDate = this.controlValueToString(this.filterForm.controls['dueDate'].value);
    if(orderType.length > 0){
      orderType = orderType.toUpperCase();
    }

    dueDate = this.dateUiToRepo(dueDate);

    this.getOrders(orderType, dueDate);
  }
  
  controlValueToString(value: any) : string{
    if(value !== undefined){
      if(typeof value === 'string') {
        return value;
      }
    }

    return "";
  }

  getOrders(orderType: string, dueDate: string) {
    this.message = '';

    this.ordersService.getOrders(orderType, dueDate).subscribe(
      (response: any) => {
      if(!response){
        return;
      }

      this.orders = response;
      },
      (error: any) => {
        this.orders = [];
        
        this.message = 'There is some error, please check and try again. ';
        this.messageType = 'Error';
        console.error(error);
      }
    );
  }

  dateRepoToUi(date:string){
    return date.replace(/-/g, '/');
  }

  dateUiToRepo(date:string){
    return date.length > 0 ? date.replace(/\//g, '-') : '';
  }
}
