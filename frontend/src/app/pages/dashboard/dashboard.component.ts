import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService, User } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard">
      <h1>Dashboard</h1>
      <div *ngIf="user$ | async as user" class="welcome">
        <h2>Bienvenido, {{ user.name }}</h2>
        <p>Email: {{ user.email }}</p>
        <p>Rol: {{ user.role }}</p>
      </div>
      <div class="stats">
        <div class="stat-card">
          <h3>Solicitudes</h3>
          <p>Gestiona tus solicitudes de conserjería</p>
        </div>
        <div class="stat-card">
          <h3>Chat IA</h3>
          <p>Asistente virtual con IA generativa</p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dashboard { max-width: 1200px; margin: 0 auto; }
    h1 { color: #333; margin-bottom: 2rem; }
    .welcome { background: #ecf0f1; padding: 2rem; border-radius: 8px; margin-bottom: 2rem; }
    .stats { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 2rem; }
    .stat-card { background: white; padding: 1.5rem; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
    .stat-card h3 { color: #3498db; }
  `]
})
export class DashboardComponent {
  user$ = this.authService.user$;
  constructor(private authService: AuthService) {}
}
