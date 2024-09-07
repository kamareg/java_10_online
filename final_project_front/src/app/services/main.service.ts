import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../models/product";
import {Order} from "../models/order";
import {ProductInOrder} from "../models/product_in_order";

@Injectable({
  providedIn: 'root'
})
export class MainService {
  private url: string = 'http://localhost:8080/tastyBurgers';

  constructor(private http: HttpClient) {
  }
  loadProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.url);
  }
  getCurrentOrder(): Observable<Order> {
    return this.http.get<Order>(`${this.url}/current`);
  }

  updateOrder(productInOrder: ProductInOrder, order: Order | undefined): Observable<any> {
    const requestBody = {
      order: order,
      productInOrder: productInOrder
    };
    return this.http.post(`${this.url}/update`, requestBody);
  }
}
