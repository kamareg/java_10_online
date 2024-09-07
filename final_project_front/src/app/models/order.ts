import {User} from "./user";
import {Product} from "./product";
import {ProductInOrder} from "./product_in_order";

export interface Order {
  id: number;
  user: User;
  status: string;
  cost: number;
  products: ProductInOrder[];
}
