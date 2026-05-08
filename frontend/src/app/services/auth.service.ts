import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface User {
  id: number;
  email: string;
  name: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private userSubject = new BehaviorSubject<User | null>(null);
  public user$ = this.userSubject.asObservable();

  public isAuthenticated$ = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    this.checkAuth();
  }

  checkAuth() {
    const token = this.getToken();
    if (token) {
      this.isAuthenticated$.next(true);
      this.getCurrentUser().subscribe({
        error: () => this.logout()
      });
    }
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, password }).pipe(
      tap((response: any) => {
        localStorage.setItem('token', response.token);
        this.isAuthenticated$.next(true);
        // Backend returns { token, userId, email, fullName, role }
        this.userSubject.next({
          id: response.userId,
          email: response.email,
          name: response.fullName,
          role: response.role
        });
      })
    );
  }

  register(email: string, password: string, name: string): Observable<any> {
    // Backend expects { email, fullName, password }
    return this.http.post(`${this.apiUrl}/register`, { email, fullName: name, password }).pipe(
      tap((response: any) => {
        localStorage.setItem('token', response.token);
        this.isAuthenticated$.next(true);
        this.userSubject.next({
          id: response.userId,
          email: response.email,
          name: response.fullName,
          role: response.role
        });
      })
    );
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<any>(`${this.apiUrl}/me`).pipe(
      tap((user: any) => this.userSubject.next({
        id: user.id,
        email: user.email,
        name: user.fullName,
        role: user.role
      }))
    );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.isAuthenticated$.next(false);
    this.userSubject.next(null);
  }
}
