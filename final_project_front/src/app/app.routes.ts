import { Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {CardComponent} from "./pages/card/card.component";
import {AuthComponent} from "./pages/login/auth.component";
import {RegComponent} from "./pages/reg/reg.component";
import {AdminComponent} from "./pages/admin/admin.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'tastyBurgers',
    pathMatch: 'full'
  },
  {
    path: 'tastyBurgers',
    component: MainComponent
  },
  {
    path: 'tastyBurgers/card',
    component: CardComponent
  },
  {
    path: 'auth/login',
    component: AuthComponent
  },
  {
    path: 'auth/reg',
    component: RegComponent
  },
  {
    path: 'auth',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },
  {
    path: 'admin',
    component: AdminComponent
  }
];
