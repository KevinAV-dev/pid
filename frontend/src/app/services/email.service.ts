import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmailRequest } from '../models/email-request.model';

@Injectable({
  providedIn: 'root'
})
export class EmailService {
  private apiUrl = 'http://localhost:8080/api/emails';

  constructor(private http: HttpClient) { }

  sendEmails(emailRequest: EmailRequest): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/send`, emailRequest);
  }
}
