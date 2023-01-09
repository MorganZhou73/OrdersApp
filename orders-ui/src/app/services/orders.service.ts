import { Injectable } from '@angular/core';
import { Order } from '../model/order';
import { Observable } from 'rxjs';
import { AppConstants } from '../shared/constants/app.constants';
import { ApiService } from '../shared/services/api.service';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private api: ApiService) { }

  url = 'orders';

  getOrders(orderType: string, dueDate: string): Observable<any>{
    let queryParams = new HttpParams();
    
    if(orderType?.length !== 0) {
      queryParams = queryParams.set('type', orderType);
    }

    if(dueDate?.length !== 0) {
      queryParams = queryParams.set('duedate', dueDate);
    }

    return this.api.httpGet(`${AppConstants.URLS.orders}/${this.url}`, {params:queryParams});
  }

  addOrder(order: Order) {
    return this.api.httpPost(`${AppConstants.URLS.orders}/${this.url}`, order);
  }
}
