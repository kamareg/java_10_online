import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/user";
import {MainService} from "../../services/main.service";
import {catchError, of, tap} from "rxjs";
import {FormsModule} from "@angular/forms";
import {Order, Status} from "../../models/order";
import {Product, Type} from "../../models/product";

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    NgIf
  ],
  templateUrl: './admin.component.html',
})
export class AdminComponent implements OnInit {
  products: any[] = [];
  orders: Order[] = [];
  users: User[] = [];
  allStatuses = Object.values(Status);
  selectableStatuses = ['APPROVED', 'FINISHED'];
  productTypes = Object.values(Type);
  newProduct: Product = {
    id: 0,
    name: '',
    description: '',
    imageUrl: '',
    price: 0,
    type: Type.BURGER
  };

  constructor(private mainService: MainService, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.loadUsers().subscribe({
      next: (data) => {
        if (data) {
          this.users = data
        }
      }
    });
    this.loadOrders().subscribe({
      next: (data) => {
        if (data) {
          this.orders = data;
        }
      }
    });
    this.loadProducts().subscribe({
      next: (data) => {
        if (data) {
          this.products = data;
        }
      }
    });
  }

  loadUsers() {
    return (this.mainService.loadUsers()).pipe(
      catchError(() => {
        this.mainService.handleInvalidToken();
        return of(null);
      }))
  }

  loadOrders() {
    return (this.mainService.loadOrders()).pipe(
      catchError(() => {
        this.mainService.handleInvalidToken();
        return of(null);
      }),
      tap(data => console.log('Loaded orders:', data))
    )
  }

  loadProducts() {
    return (this.mainService.loadProducts()).pipe(
      catchError(() => {
        this.mainService.handleInvalidToken();
        return of(null);
      }))
  }

  deleteUser(id: number) {
    if (confirm("Are you sure you want to delete this user?")) {
      this.mainService.deleteUser(id).subscribe({
        next: () => {
          this.users = this.users.filter(u => u.id !== id);
        },
        error: (err) => {
          if (err.status === 400 && err.error === 'User has active orders and cannot be deleted') {
            alert('This user has active orders and cannot be deleted.');
          } else {
            alert('An error occurred while deleting the user.');
          }
        }
      });
    }
  }

  updateOrderStatus(order: Order) {
    this.mainService.updateOrderStatus(order).subscribe({
      next: () => alert('Order status updated successfully!'),
      error: () => alert('Failed to update order status.')
    });
  }

  isSelectable(status: string): boolean {
    return this.selectableStatuses.includes(status);
  }

  deleteProduct(id: number) {
    if (confirm('Are you sure you want to delete this product?')) {
      this.mainService.deleteProduct(id).subscribe({
        next: () => {
          this.products = this.products.filter(p => p.id !== id);
        },
        error: (err) => {
          if (err.status === 409) {
            alert('This product is in active orders and cannot be deleted.');
          } else {
            alert('Failed to delete the product.');
          }
        }
      });
    }
  }

  addProduct() {
    this.mainService.addProduct(this.newProduct).subscribe({
      next: (product) => {
        this.products.push(product);
        this.newProduct = {
          id: 0,
          name: '',
          description: '',
          imageUrl: '',
          price: 0,
          type: Type.BURGER
        };
      },
      error: () => alert('Failed to add the product.')
    });
  }
}
