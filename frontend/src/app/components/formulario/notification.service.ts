import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationSubject = new BehaviorSubject<{ message: string, type: 'success' | 'error' | null }>(
    { message: '', type: null }
  );
  notification$ = this.notificationSubject.asObservable();

  showSuccess(message: string): void {
    this.notificationSubject.next({ message, type: 'success' });
    this.hideAfterTimeout();
  }

  showError(message: string): void {
    this.notificationSubject.next({ message, type: 'error' });
    this.hideAfterTimeout();
  }

  hide(): void {
    this.notificationSubject.next({ message: '', type: null });
  }

  private hideAfterTimeout(): void {
    setTimeout(() => {
      this.hide();
    }, 5000); // Oculta la notificación después de 5 segundos
  }
}
