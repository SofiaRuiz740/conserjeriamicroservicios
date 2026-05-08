import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <nav class="navbar">
      <div class="navbar-brand">Concierge Virtual</div>
      <div class="navbar-links">
        <a *ngIf="isAuthenticated$ | async" routerLink="/dashboard">Dashboard</a>
        <a *ngIf="isAuthenticated$ | async" routerLink="/dashboard/requests">Requests</a>
        <a *ngIf="isAuthenticated$ | async" routerLink="/dashboard/chat">Chat</a>
        <a *ngIf="!(isAuthenticated$ | async)" routerLink="/auth/login">Login</a>
        <a *ngIf="!(isAuthenticated$ | async)" routerLink="/auth/register">Register</a>
        <button *ngIf="isAuthenticated$ | async" (click)="logout()" class="btn-logout">Logout</button>
      </div>
    </nav>
  `,
  styles: [`
    .navbar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px 30px;
      background-color: #333;
      color: white;
      position: fixed;
      width: 100%;
      top: 0;
      z-index: 1000;
    }
    .navbar-brand { font-size: 24px; font-weight: bold; }
    .navbar-links { display: flex; gap: 20px; }
    .navbar-links a { color: white; text-decoration: none; }
    .btn-logout { background: red; color: white; border: none; padding: 8px 15px; cursor: pointer; border-radius: 4px; }
  `]
})
export class NavbarComponent {
  isAuthenticated$ = this.authService.currentUser$.pipe();

  constructor(private authService: AuthService) {}

  logout() {
    this.authService.logout();
    window.location.href = '/auth/login';
  }
}
