import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { OrdersService } from 'src/app/services/orders.service';
import { AppConstants } from 'src/app/shared/constants/app.constants';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit, OnDestroy {

  message: string = '';
  messageType: string = '';
  allOrderTypes = AppConstants.OrderTypes;
  
  orderForm = new FormGroup({
    orderType: new FormControl('', Validators.required),
    dueDate: new FormControl('', Validators.required),
    customerName: new FormControl('', Validators.required)
  });
   
  destroy$: Subject<boolean> = new Subject<boolean>();
 
  constructor(private ordersService: OrdersService) { }

  ngOnInit(): void {
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  onSubmit() {
    this.message = '';
    this.messageType = 'Warning';

    let order = this.orderForm.value;

    if(order.orderType?.length > 0)
      order.orderType = order.orderType.toUpperCase();
      
    if(order.dueDate?.length > 0)
      order.dueDate = order.dueDate.replace(/\//g, '-');

    this.ordersService.addOrder(order).pipe(takeUntil(this.destroy$)).subscribe({
      next: ((response: any) => {
        console.log('resonse::::', response);
        this.message = 'The new order is created successfully';
        this.orderForm.reset();
      }),
      error:((error: any) => {
        this.message = 'There is some error, please check and try again. '; //  + error.message
        this.messageType = 'Error';
        console.error(error);
      })
    });
  }
}
