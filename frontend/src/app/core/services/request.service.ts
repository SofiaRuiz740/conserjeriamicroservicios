import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class RequestService {
  private apiUrl = `${environment.apiBaseUrl}/api/request-service`;

  constructor(private http: HttpClient) {}

  getCategories(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/categories`);
  }

  getRequests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/requests`);
  }

  getRequestById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/requests/${id}`);
  }

  createRequest(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/requests`, data);
  }

  updateRequest(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/requests/${id}`, data);
  }

  updateRequestStatus(id: number, status: string): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/requests/${id}/status?status=${status}`, {});
  }

  deleteRequest(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/requests/${id}`);
  }

  addComment(id: number, message: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/requests/${id}/comments?message=${message}`, {});
  }

  getComments(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/requests/${id}/comments`);
  }
}
