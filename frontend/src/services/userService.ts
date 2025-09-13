import { User } from '../features/user';

export async function getUsers(): Promise<User[]> {
  return fetch('/api/users').then(res => res.json());
}
