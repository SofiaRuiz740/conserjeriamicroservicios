import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  private apiUrl = `${environment.apiUrl}/service-a`;

  constructor(private http: HttpClient) {}

  getCategories(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/categories`);
  }

  createCategory(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/categories`, data);
  }

  getRequests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/requests`);
  }

  getRequest(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/requests/${id}`);
  }

  createRequest(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/requests`, data);
  }

  updateRequest(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/requests/${id}`, data);
  }

  deleteRequest(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/requests/${id}`);
  }

  addComment(requestId: number, content: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/requests/${requestId}/comments`, { content });
  }
}
