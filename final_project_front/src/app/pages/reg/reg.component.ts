import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {MainService} from "../../services/main.service";
import {User} from "../../models/user";
import {NgIf} from "@angular/common";

declare var bootstrap: any;

@Component({
  selector: 'app-reg',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    NgIf
  ],
  templateUrl: './reg.component.html',
})
export class RegComponent {
  email: string = '';
  password: string = '';
  first_name?: string;
  last_name?: string;
  age?: number;

  constructor(private mainService: MainService, private router: Router) {
  }

  onCreate() {
    if (this.email && this.password) {
      const user: User = {
        id: 0,
        email: this.email,
        password: this.password,
        firstName: this.first_name || '',
        lastName: this.last_name || '',
        role: 'ROLE_USER',
        age: this.age || 0
      };

      this.mainService.registerUser(user).subscribe({
        next: (response) => {
          this.showCreateModal()
        },
        error: (err) => {
          if (err.status === 409) {
            this.showFailModal("This email has already exist")
          } else {
            this.showFailModal("Please check correctness of your credentials! Email should be like name&#64;example.com and the password can't be blank!")
          }
        }
      });
    }
  }

  showCreateModal() {
    const modalElement = document.getElementById('regSuccessModal');
    const modal = new bootstrap.Modal(modalElement);
    if (modalElement) {
      modalElement.addEventListener('hidden.bs.modal', () => {
        this.router.navigate(['/auth/login']);
      });
      modal.show();
    }
  }

  showFailModal(message: string) {
    const modalElement = document.getElementById('noSuccessModal');
    if (modalElement) {
      const modalBody = modalElement.querySelector('.modal-body');
      if (modalBody) {
        modalBody.innerHTML = `<p>${message}</p>`;

        const modal = new bootstrap.Modal(modalElement);
        modal.show();
      } else {
        console.error('Modal body element not found.');
      }
    } else {
      console.error('Modal element not found.');
    }
  }

}
