import {Component, OnInit} from '@angular/core';
import {MainService} from "../../services/main.service";
import {Product, Type} from "../../models/product";
import {CommonModule} from "@angular/common";
import {Order} from "../../models/order";
import {ProductInOrder} from "../../models/product_in_order";
import {ProductItemComponent} from "../product-item/product-item.component";
import {Router, RouterOutlet} from "@angular/router";
import {catchError, combineLatest, map, of} from 'rxjs';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [CommonModule, ProductItemComponent, RouterOutlet],
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit {
  products: ProductInOrder[] = [];
  fastFoodProducts?: ProductInOrder[];
  drinksProducts?: ProductInOrder[];
  burgerProducts?: ProductInOrder[];
  order?: Order;

  constructor(private mainService: MainService, private router: Router) {
  }

  ngOnInit(): void {
    this.loadOrderAndProducts().subscribe({
      next: (data) => {
        if (data) {
          this.order = data.order;
          this.products = data.products;
        }
        this.groupProductsByType();
      }
    });
  }


  loadOrderAndProducts() {
    return combineLatest([
      this.mainService.getCurrentOrder(),
      this.mainService.loadProducts()
    ]).pipe(
      map(([order, products]: [Order, Product[]]) => {
        const processedProducts = products.map(product => {
          const productInOrder = order.products.find(p => p.product.id === product.id);
          return {
            product: product,
            count: productInOrder ? productInOrder.count : 0
          };
        });
        return {order, products: processedProducts};
      }),
      catchError(() => {
        this.mainService.handleInvalidToken();
        return of(null);
      })
    );
  }

  groupProductsByType(): void {
    this.fastFoodProducts = this.products.filter(product => product.product.type === Type.FAST_FOOD);
    this.drinksProducts = this.products.filter(product => product.product.type === Type.DRINK);
    this.burgerProducts = this.products.filter(product => product.product.type === Type.BURGER);
  }

  goToCard(): void {
    this.router.navigateByUrl(`tastyBurgers/card`);
  }
}
