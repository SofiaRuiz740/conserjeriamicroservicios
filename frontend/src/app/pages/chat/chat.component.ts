import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../services/chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="chat-container">
      <h1>Chat con IA Generativa</h1>
      <div class="conversations">
        <div class="new-conversation">
          <input [(ngModel)]="newConvTitle" placeholder="Nuevo tema de conversación">
          <button (click)="createConversation()">Crear Conversación</button>
        </div>
        <div *ngFor="let conv of conversations" [class.active]="conv.id === selectedConvId" (click)="selectConversation(conv.id)" class="conv-item">
          {{ conv.title }}
        </div>
      </div>
      <div class="chat-area" *ngIf="selectedConvId">
        <div class="messages">
          <div *ngFor="let msg of messages" [class.user]="msg.type === 'USER'" [class.ai]="msg.type === 'AI'" class="message">
            <p>{{ msg.content }}</p>
          </div>
        </div>
        <div class="message-input">
          <input [(ngModel)]="messageText" placeholder="Escribe tu mensaje..." (keyup.enter)="sendMessage()">
          <button (click)="sendMessage()">Enviar</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .chat-container { display: flex; height: calc(100vh - 80px); }
    .conversations { width: 300px; background: #ecf0f1; padding: 1rem; overflow-y: auto; }
    .conv-item { padding: 0.75rem; cursor: pointer; border-radius: 4px; margin-bottom: 0.5rem; }
    .conv-item:hover, .conv-item.active { background: #3498db; color: white; }
    .new-conversation { margin-bottom: 1rem; display: flex; gap: 0.5rem; }
    .new-conversation input { flex: 1; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px; }
    .chat-area { flex: 1; display: flex; flex-direction: column; }
    .messages { flex: 1; padding: 1rem; overflow-y: auto; display: flex; flex-direction: column; gap: 1rem; }
    .message { padding: 0.75rem 1rem; border-radius: 8px; max-width: 70%; }
    .message.user { background: #3498db; color: white; align-self: flex-end; }
    .message.ai { background: #ecf0f1; align-self: flex-start; }
    .message-input { padding: 1rem; display: flex; gap: 0.5rem; }
    .message-input input { flex: 1; padding: 0.75rem; border: 1px solid #ddd; border-radius: 4px; }
    .message-input button { padding: 0.75rem 1.5rem; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer; }
  `]
})
export class ChatComponent implements OnInit {
  conversations: any[] = [];
  messages: any[] = [];
  messageText = '';
  newConvTitle = '';
  selectedConvId: number | null = null;

  constructor(private chatService: ChatService) {}

  ngOnInit() {
    this.loadConversations();
  }

  loadConversations() {
    this.chatService.getConversations().subscribe(
      data => this.conversations = data
    );
  }

  createConversation() {
    if (this.newConvTitle.trim()) {
      this.chatService.createConversation(this.newConvTitle).subscribe(
        () => {
          this.newConvTitle = '';
          this.loadConversations();
        }
      );
    }
  }

  selectConversation(id: number) {
    this.selectedConvId = id;
    this.loadMessages();
  }

  loadMessages() {
    if (this.selectedConvId) {
      this.chatService.getConversation(this.selectedConvId).subscribe(
        data => this.messages = data.messages || []
      );
    }
  }

  sendMessage() {
    if (this.messageText.trim() && this.selectedConvId) {
      this.chatService.sendMessage(this.selectedConvId, this.messageText).subscribe(
        () => {
          this.messageText = '';
          this.loadMessages();
        }
      );
    }
  }
}
