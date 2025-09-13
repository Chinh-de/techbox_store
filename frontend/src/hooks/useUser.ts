import { useEffect, useState } from 'react';
import { getUsers } from '../services/userService';
import { User } from '../features/user';

export function useUser() {
  const [users, setUsers] = useState<User[]>([]);
  useEffect(() => {
    getUsers().then(setUsers);
  }, []);
  return users;
}
