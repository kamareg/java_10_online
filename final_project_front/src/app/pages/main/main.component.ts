import {Component, OnInit} from '@angular/core';
import {MainService} from "../../services/main.service";
import {Product} from "../../models/product";
import {CommonModule} from "@angular/common";
import {Order} from "../../models/order";
import {ProductInOrder} from "../../models/product_in_order";
import {ProductItemComponent} from "../product-item/product-item.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [CommonModule, ProductItemComponent],
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit {
  products?: ProductInOrder[] = []
  fastFoodProducts?: ProductInOrder[];
  drinksProducts?: ProductInOrder[];
  burgerProducts?: ProductInOrder[];
  order?: Order

  constructor(private mainService: MainService, private router: Router) {
  }

  ngOnInit(): void {
    this.loadProducts();
    this.getCurrentOrder();
  }

  loadProducts(): void {
    this.mainService.loadProducts()
      .subscribe((prod: Product[]) => {
        prod.forEach((product) => {
          const productInOrder = this.products?.find(p => p.product.id === product.id);
          if (!productInOrder) {
            this.products?.push({product: product, count: 0});
          }
        });
      });
  }

  getCurrentOrder(): void {
    this.mainService.getCurrentOrder()
      .subscribe(order => {
        console.log(order);
        this.order = order;
        order.products.forEach((productInOrder) => {
          const matchingProduct = this.products?.find(p => p.product.id === productInOrder.product.id);
          if (matchingProduct) {
            matchingProduct.count = productInOrder.count;
          }
        });
        this.fastFoodProducts = this.products?.filter(product => product.product.type === 'FAST_FOOD');
        this.drinksProducts = this.products?.filter(product => product.product.type === 'DRINK');
        this.burgerProducts = this.products?.filter(product => product.product.type === 'BURGER');
      });
  }

  goToCard(): void {
    this.router.navigateByUrl(`card`)
  }
}
