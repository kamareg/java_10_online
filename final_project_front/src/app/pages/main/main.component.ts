import {Component, OnInit} from '@angular/core';
import {MainService} from "../../services/main.service";
import {Product} from "../../models/product";
import {CommonModule} from "@angular/common";
import {Order} from "../../models/order";
import {ProductInOrder} from "../../models/product_in_order";
import {ProductItemComponent} from "../product-item/product-item.component";
import {Router} from "@angular/router";
import {combineLatest} from 'rxjs';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [CommonModule, ProductItemComponent],
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
    this.loadOrderAndProducts();
  }

  loadOrderAndProducts(): void {
    combineLatest([
      this.mainService.getCurrentOrder(),
      this.mainService.loadProducts()
    ]).subscribe(([order, products]: [Order, Product[]]) => {
      this.order = order;
      this.products = products.map(product => {
        const productInOrder = order.products.find(p => p.product.id === product.id);
        return {
          product: product,
          count: productInOrder ? productInOrder.count : 0
        };
      });
      this.groupProductsByType();
    });
  }

  groupProductsByType(): void {
    this.fastFoodProducts = this.products.filter(product => product.product.type === 'FAST_FOOD');
    this.drinksProducts = this.products.filter(product => product.product.type === 'DRINK');
    this.burgerProducts = this.products.filter(product => product.product.type === 'BURGER');
  }

  goToCard(): void {
    this.router.navigateByUrl(`card`);
  }
}
