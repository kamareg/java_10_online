import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../models/product';
import {Order} from '../models/order';
import {ProductInOrder} from '../models/product_in_order';
import {Router} from '@angular/router';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class MainService {
  private url: string = 'http://localhost:8080';
  private tokenKey = 'token';

  constructor(private http: HttpClient, private router: Router) {
  }

  private getAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    if (token) {
      return new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
    } else {
      return new HttpHeaders();
    }
  }

  loadProducts(): Observable<Product[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<Product[]>(`${this.url}/tastyBurgers/getProducts`, {headers});
  }

  loadUsers(): Observable<User[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<User[]>(`${this.url}/admin/users`, {headers});
  }

  loadOrders(): Observable<Order[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<Order[]>(`${this.url}/admin/orders`, {headers});
  }

  deleteUser(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.url}/admin/users/${id}`, {headers});
  }

  updateOrderStatus(order: Order): Observable<void> {
    const headers = this.getAuthHeaders();
    const requestBody = {
      status: order.status
    };
    return this.http.put<void>(`${this.url}/admin/orders/${order.id}`, requestBody, {headers});
  }

  deleteProduct(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.url}/admin/products/${id}`, {headers});
  }

  addProduct(product: Product): Observable<Product> {
    const headers = this.getAuthHeaders();
    return this.http.post<Product>(`${this.url}/admin/products`, product, {headers});
  }

  getCurrentOrder(): Observable<Order> {
    const headers = this.getAuthHeaders();
    return this.http.get<Order>(`${this.url}/tastyBurgers/currentOrder`, {headers});
  }

  updateOrder(productInOrder: ProductInOrder, order: Order | undefined): Observable<any> {
    const headers = this.getAuthHeaders();
    const requestBody = {
      order: order?.id,
      product: productInOrder.product.id,
      count: productInOrder.count
    };
    return this.http.post(`${this.url}/tastyBurgers/updateCount`, requestBody, {headers});
  }


  makeAnOrder(order: Order | undefined): Observable<Object> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.url}/tastyBurgers/updateStatus`, order?.id, {headers});
  }

  public getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  public setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  public login(authRequest: { email: string, password: string }): Observable<any> {
    return this.http.post(`${this.url}/auth/login`, authRequest);
  }

  registerUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.url}/auth/register`, user);
  }

  handleInvalidToken() {
    this.router.navigate(['/auth/login']);
  }
}
