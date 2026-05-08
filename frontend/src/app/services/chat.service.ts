import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = `${environment.apiUrl}/service-b`;

  constructor(private http: HttpClient) {}

  getConversations(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/conversations`);
  }

  getConversation(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/conversations/${id}`);
  }

  createConversation(title: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/conversations`, { title });
  }

  sendMessage(conversationId: number, content: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/conversations/${conversationId}/messages`, { content });
  }

  getRecommendations(conversationId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/conversations/${conversationId}/recommendations`);
  }
}
