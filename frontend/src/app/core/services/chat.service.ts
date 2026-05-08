import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private apiUrl = `${environment.apiBaseUrl}/api/chat-service`;

  constructor(private http: HttpClient) {}

  createConversation(title: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/conversations`, { title });
  }

  getConversations(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/conversations`);
  }

  getConversationById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/conversations/${id}`);
  }

  sendMessage(conversationId: number, content: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/conversations/${conversationId}/messages`, { content });
  }

  getMessages(conversationId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/conversations/${conversationId}/messages`);
  }

  addRecommendation(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/recommendations`, data);
  }

  getRecommendations(conversationId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/recommendations/${conversationId}`);
  }
}
