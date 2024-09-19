import {User} from "./user";
import {ProductInOrder} from "./product_in_order";

export interface Order {
  id: number;
  user: User;
  status: Status;
  cost: number;
  products: ProductInOrder[];
}

export enum Status {
  NEW = 'NEW',
  CREATED = 'CREATED',
  APPROVED = 'APPROVED',
  FINISHED = 'FINISHED'
}
