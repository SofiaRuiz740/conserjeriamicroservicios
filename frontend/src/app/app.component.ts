import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent],
  template: `
    <app-navbar *ngIf="isAuthenticated$ | async"></app-navbar>
    <main class="container">
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [`
    .container {
      padding: 20px;
    }
  `]
})
export class AppComponent implements OnInit {
  isAuthenticated$ = this.authService.isAuthenticated$;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.checkAuth();
  }
}
