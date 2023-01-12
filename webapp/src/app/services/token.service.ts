import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const USER_ROLE='user-role';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  signOut(): void {
    sessionStorage.clear();
  }

  public saveToken(token: string): void {
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(userName: string): void {
    sessionStorage.removeItem(USER_KEY);
    sessionStorage.setItem(USER_KEY, userName);
  }

  public getUser(): string {
    return sessionStorage.getItem(USER_KEY);
  }

  public saveRole(role: string): void {
    sessionStorage.removeItem(USER_ROLE);
    sessionStorage.setItem(USER_ROLE, role);
  }

  public getRole(): string | null {
    return sessionStorage.getItem(USER_ROLE);
  }

}
