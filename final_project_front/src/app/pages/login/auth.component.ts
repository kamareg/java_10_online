import {Component} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {MainService} from "../../services/main.service";
import {NgForOf} from "@angular/common";
import {FormsModule} from "@angular/forms";

declare var bootstrap: any;

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    FormsModule
  ],
  templateUrl: './auth.component.html',
})
export class AuthComponent {
  email: string = '';
  password: string = '';

  constructor(private mainService: MainService, private router: Router) {
  }

  onLogin() {
    const authRequest = {
      email: this.email,
      password: this.password
    };
    this.mainService.login(authRequest).subscribe({
      next: (response) => {
        if (response.data && response.data.token) {
          this.mainService.setToken(response.data.token);
          this.router.navigate(['tastyBurgers/']);
        }
      },
      error: (err) => {
        this.showFailModal();
      }
    });
  }

  showFailModal() {
    const modalElement = document.getElementById('noSuccessModal');
    const modal = new bootstrap.Modal(modalElement);
    if (modalElement) {
      modalElement.addEventListener('hidden.bs.modal', () => {
        window.location.reload();
      });
      modal.show();
    }
  }
}
