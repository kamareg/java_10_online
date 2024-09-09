import {Component, OnInit} from '@angular/core';
import {Order} from "../../models/order";
import {MainService} from "../../services/main.service";
import {Router} from "@angular/router";
import {NgForOf} from "@angular/common";
declare var bootstrap: any;

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './card.component.html',
})
export class CardComponent implements OnInit {
  order?: Order
  constructor(private mainService: MainService, private router: Router) {
  }
  ngOnInit(): void {
    this.getCurrentOrder();
  }
  getCurrentOrder(): void {
    this.mainService.getCurrentOrder()
      .subscribe(order => {
        this.order = order;
      });
  }
  goToShopping(): void {
    this.router.navigateByUrl(`tastyBurgers`)
  }

  makeAnOrder(): void{
    console.log(this.order)
    this.mainService.makeAnOrder(this.order)
      .subscribe(res => {
        this.showSuccessModal();
      });
  }

  get hasProducts(): boolean {
    if (!this.order) return false;
    return this.order?.products.length != 0;
  }

  showSuccessModal() {
    const modalElement = document.getElementById('orderSuccessModal');
    const modal = new bootstrap.Modal(modalElement);
    if (modalElement) {
      modalElement.addEventListener('hidden.bs.modal', () => {
        window.location.reload();
      });
      modal.show();
    }
  }
}
