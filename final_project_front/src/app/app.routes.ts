import { Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {CardComponent} from "./pages/card/card.component";

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
    path: 'card',
    component: CardComponent
  }
];
