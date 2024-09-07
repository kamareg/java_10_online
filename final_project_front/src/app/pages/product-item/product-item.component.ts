import {Component, Input} from '@angular/core';
import {ProductInOrder} from "../../models/product_in_order";
import {MainService} from "../../services/main.service";
import {Order} from "../../models/order";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-product-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-item.component.html',
})
export class ProductItemComponent {
  @Input() products?: ProductInOrder[] = [];
  @Input() current_order?: Order;

  constructor(private mainService: MainService) {
  }

  increaseCount(productInOrder: ProductInOrder): void {
    productInOrder.count += 1;
    this.mainService.updateOrder(productInOrder, this.current_order).subscribe();
  }

  decreaseCount(productInOrder: ProductInOrder): void {
    if (productInOrder.count > 0) {
      productInOrder.count -= 1;
      this.mainService.updateOrder(productInOrder, this.current_order).subscribe();
    }
  }
}
