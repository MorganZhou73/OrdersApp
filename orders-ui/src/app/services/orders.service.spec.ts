import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Order } from '../model/order';

import { OrdersService } from './orders.service';

describe('OrdersService', () => {
  let service: OrdersService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OrdersService]
    });

    httpMock = TestBed.inject(HttpTestingController);
    service = TestBed.inject(OrdersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  
  it('should GET data via getOrders filter by both type and duedate', () => {
    const orders: Order[] = [{
      orderNumber: "1",
      customerName: "Joe",
      dueDate: "2022-11-20",
      orderType: "TV"
    },{
      orderNumber: "2",
      customerName: "Morgan",
      dueDate: "2022-11-20",
      orderType: "TV"
    }];
 
    service.getOrders("TV", "2022-11-20").subscribe(result => {
      expect(result.length).toBe(2);
      expect(result).toEqual(orders);
    });

    let request = httpMock.expectOne(`orders/v1/orders?type=TV&duedate=2022-11-20`);
    expect(request.request.method).toBe('GET');
    request.flush(orders);

  });

  it('should GET data via getOrders filter by only type or duedate ', () => {
    const orders: Order[] = [{
      orderNumber: "1",
      customerName: "Joe",
      dueDate: "2022-11-20",
      orderType: "TV"
    },{
      orderNumber: "2",
      customerName: "Morgan",
      dueDate: "2022-11-20",
      orderType: "TV"
    }];
 
    // test with only type parameter
    service.getOrders("TV", "").subscribe(result => {
      expect(result.length).toBe(2);
      expect(result).toEqual(orders);
    });

    let request = httpMock.expectOne(`orders/v1/orders?type=TV`);
    expect(request.request.method).toBe('GET');
    request.flush(orders);

    // test with only duedate parameter
    service.getOrders("", "2022-11-20").subscribe(result => {
      expect(result.length).toBe(2);
      expect(result).toEqual(orders);
    });
    request = httpMock.expectOne(`orders/v1/orders?duedate=2022-11-20`);
    expect(request.request.method).toBe('GET');
    request.flush(orders);

    // test with no parameter
    service.getOrders("", "").subscribe(result => {
      expect(result.length).toBe(2);
      expect(result).toEqual(orders);
    });
    request = httpMock.expectOne(`orders/v1/orders`);
    expect(request.request.method).toBe('GET');
    request.flush(orders);

  });

  it('should POST success in addOrder ', () => {
    const order: Order = {
      "orderNumber": "",
      "customerName": "Joe",
      "dueDate": "2022-11-20",
      "orderType": "BUNDLE"
    };
    service.addOrder(order).subscribe(data => {
      expect(data.customerName).toEqual(order.customerName);
      expect(data.dueDate).toEqual(order.dueDate);
      expect(data.orderType).toEqual(order.orderType);
      expect(data.orderNumber).toEqual('5');
    });
    const request = httpMock.expectOne(`orders/v1/orders`);
    expect(request.request.method).toBe('POST');

    let order2 = Object.assign({}, order);
    order2.orderNumber = "5";
    request.flush(order2);
  });

});
