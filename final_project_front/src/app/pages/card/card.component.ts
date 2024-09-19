import {Component, OnInit} from '@angular/core';
import {Order} from "../../models/order";
import {MainService} from "../../services/main.service";
import {Router} from "@angular/router";
import {NgForOf} from "@angular/common";
import {catchError, of} from "rxjs";

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
    this.getCurrentOrder().subscribe({
      next: (data) => {
        if (data) {
          this.order = data
        }
      }
    });
  }

  getCurrentOrder() {
    return (this.mainService.getCurrentOrder()).pipe(
      catchError(() => {
        this.mainService.handleInvalidToken();
        return of(null);
      }))
  }

  goToShopping(): void {
    this.router.navigateByUrl(`tastyBurgers`)
  }

  makeAnOrder(): void {
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
