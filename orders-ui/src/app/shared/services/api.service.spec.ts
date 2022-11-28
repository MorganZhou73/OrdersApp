import { TestBed } from '@angular/core/testing';
import {HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing'; 
import { ApiService } from './api.service';
import { Order } from 'src/app/model/order';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
		imports: [HttpClientTestingModule],
		providers: [ApiService]
	});
    service = TestBed.inject(ApiService);
	httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  
  it('should retrieve data from the API via httpGet', () => {
	const orders: Order[] = [{
	  orderNumber: "1",
	  customerName: "Joe",
	  dueDate: "2022-11-20",
	  orderType: "BUNDLE"
	},{
	  orderNumber: "2",
	  customerName: "Morgan",
	  dueDate: "2022-11-20",
	  orderType: "TV"
	}];
	service.httpGet(`orders/v1/orders?duedate=2022-11-20`).subscribe(data => {
		expect(data.length).toBe(2);
		expect(data).toEqual(orders);
	});

	const request = httpMock.expectOne(`orders/v1/orders?duedate=2022-11-20`);
	expect(request.request.method).toBe('GET');
	request.flush(orders);
  });

  it('should retrieve data from the API via httpPost', () => {
	const order: Order = {
	  "orderNumber": "",
	  "customerName": "Joe",
	  "dueDate": "2022-11-20",
	  "orderType": "BUNDLE"
	};
	service.httpPost(`orders/v1/orders`, order).subscribe(data => {
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
