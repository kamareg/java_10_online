export interface Product {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  price: number;
  type: Type;
}

export enum Type {
  FAST_FOOD = 'FAST_FOOD',
  DRINK = 'DRINK',
  BURGER = 'BURGER'
}
