import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="login-container">
      <div class="login-form">
        <h2>Iniciar Sesión</h2>
        <form (ngSubmit)="login()">
          <input [(ngModel)]="email" name="email" type="email" placeholder="Email" required>
          <input [(ngModel)]="password" name="password" type="password" placeholder="Contraseña" required>
          <button type="submit">Ingresar</button>
          <p>¿No tienes cuenta? <a routerLink="/register">Registrarse</a></p>
        </form>
        <div *ngIf="error" class="error">{{ error }}</div>
      </div>
    </div>
  `,
  styles: [`
    .login-container { display: flex; justify-content: center; align-items: center; height: 100vh; background: #f5f5f5; }
    .login-form { background: white; padding: 2rem; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
    h2 { text-align: center; margin-bottom: 1.5rem; }
    input { width: 100%; padding: 0.75rem; margin-bottom: 1rem; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
    button { width: 100%; padding: 0.75rem; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer; }
    button:hover { background: #2980b9; }
    p { text-align: center; margin-top: 1rem; }
    a { color: #3498db; text-decoration: none; }
    .error { color: #e74c3c; margin-top: 1rem; }
  `]
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';
  constructor(private authService: AuthService, private router: Router) {}
  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => this.error = 'Email o contraseña inválidos'
    });
  }
}
