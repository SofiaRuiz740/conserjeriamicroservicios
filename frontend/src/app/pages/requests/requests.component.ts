import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RequestService } from '../../services/request.service';

@Component({
  selector: 'app-requests',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="requests-container">
      <h1>Solicitudes de Conserjería</h1>
      <div class="new-request">
        <h3>Nueva Solicitud</h3>
        <form (ngSubmit)="createRequest()">
          <select [(ngModel)]="newRequest.categoryId" name="category" required>
            <option value="">Selecciona una categoría</option>
            <option *ngFor="let cat of categories" [value]="cat.id">{{ cat.name }}</option>
          </select>
          <textarea [(ngModel)]="newRequest.description" name="description" placeholder="Describe tu solicitud" required></textarea>
          <select [(ngModel)]="newRequest.priority" name="priority" required>
            <option value="LOW">Baja</option>
            <option value="MEDIUM">Media</option>
            <option value="HIGH">Alta</option>
            <option value="URGENT">Urgente</option>
          </select>
          <button type="submit">Crear Solicitud</button>
        </form>
      </div>
      <div class="requests-list">
        <h3>Tus Solicitudes</h3>
        <div *ngFor="let req of requests" class="request-card">
          <h4>{{ req.description }}</h4>
          <p>Estado: <span [class]="'status-' + req.status">{{ req.status }}</span></p>
          <p>Prioridad: {{ req.priority }}</p>
          <button (click)="deleteRequest(req.id)">Eliminar</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .requests-container { max-width: 1200px; margin: 0 auto; }
    .new-request { background: #ecf0f1; padding: 2rem; border-radius: 8px; margin-bottom: 2rem; }
    form { display: flex; flex-direction: column; gap: 1rem; }
    select, textarea { padding: 0.75rem; border: 1px solid #ddd; border-radius: 4px; font-size: 1rem; }
    button { padding: 0.75rem; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer; }
    button:hover { background: #2980b9; }
    .requests-list { margin-top: 2rem; }
    .request-card { background: white; padding: 1.5rem; border-radius: 8px; margin-bottom: 1rem; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
    .status-PENDING { color: #f39c12; }
    .status-IN_PROGRESS { color: #3498db; }
    .status-COMPLETED { color: #27ae60; }
    .status-CANCELLED { color: #e74c3c; }
  `]
})
export class RequestsComponent implements OnInit {
  requests: any[] = [];
  categories: any[] = [];
  newRequest = { categoryId: '', description: '', priority: 'MEDIUM' };

  constructor(private requestService: RequestService) {}

  ngOnInit() {
    this.loadCategories();
    this.loadRequests();
  }

  loadCategories() {
    this.requestService.getCategories().subscribe(
      data => this.categories = data
    );
  }

  loadRequests() {
    this.requestService.getRequests().subscribe(
      data => this.requests = data
    );
  }

  createRequest() {
    this.requestService.createRequest(this.newRequest).subscribe(
      () => {
        this.newRequest = { categoryId: '', description: '', priority: 'MEDIUM' };
        this.loadRequests();
      }
    );
  }

  deleteRequest(id: number) {
    this.requestService.deleteRequest(id).subscribe(
      () => this.loadRequests()
    );
  }
}
