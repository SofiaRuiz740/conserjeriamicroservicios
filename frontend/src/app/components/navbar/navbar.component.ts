import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav class="navbar">
      <div class="nav-container">
        <h1 class="brand">Conserjería Virtual</h1>
        <ul class="nav-menu">
          <li><a routerLink="/dashboard" routerLinkActive="active">Dashboard</a></li>
          <li><a routerLink="/requests" routerLinkActive="active">Solicitudes</a></li>
          <li><a routerLink="/chat" routerLinkActive="active">Chat IA</a></li>
          <li><button (click)="logout()" class="logout-btn">Salir</button></li>
        </ul>
      </div>
    </nav>
  `,
  styles: [`
    .navbar { background: #333; color: white; padding: 1rem 0; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
    .nav-container { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 0 20px; }
    .brand { margin: 0; font-size: 1.5rem; }
    .nav-menu { list-style: none; display: flex; gap: 2rem; margin: 0; align-items: center; }
    a { color: white; text-decoration: none; padding: 0.5rem 1rem; border-radius: 4px; transition: background 0.3s; }
    a:hover, a.active { background: #555; }
    .logout-btn { background: #e74c3c; border: none; color: white; padding: 0.5rem 1rem; border-radius: 4px; cursor: pointer; }
    .logout-btn:hover { background: #c0392b; }
  `]
})
export class NavbarComponent {
  constructor(private authService: AuthService) {}
  logout() { this.authService.logout(); }
}
