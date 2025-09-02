import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component } from '@angular/core';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips'; // Importar MatChipsModule aquí
import { EmailService } from '../../services/email.service';
import { Recipient } from '../../models/recipient.model';
import { FormControl, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms'; // Importar módulos de formularios
import { MatCardModule } from '@angular/material/card'; // Importar MatCardModule
import { MatFormFieldModule } from '@angular/material/form-field'; // Importar MatFormFieldModule
import { MatInputModule } from '@angular/material/input'; // Importar MatInputModule
import { MatButtonModule } from '@angular/material/button'; // Importar MatButtonModule
import { MatIconModule } from '@angular/material/icon'; // Importar MatIconModule
import { MatCheckboxModule } from '@angular/material/checkbox'; // Importar MatCheckboxModule
import { CommonModule } from '@angular/common'; // Necesario para directivas como *ngIf, *ngFor

@Component({
  selector: 'app-email-sender',
  templateUrl: './email-sender.component.html',
  styleUrls: ['./email-sender.component.css'],
  // *** CAMBIO CLAVE PARA STANDALONE COMPONENT ***
  standalone: true,
  imports: [
    CommonModule, // Contiene *ngIf, *ngFor
    ReactiveFormsModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatCheckboxModule
    // Asegúrate de importar cualquier otro módulo de Angular Material que uses en el HTML
  ]
})
export class EmailSenderComponent {
  emailForm: FormGroup;
  separatorKeysCodes: number[] = [ENTER, COMMA];

  // Para 'Para'
  toRecipients: Recipient[] = [];
  toCtrl = new FormControl('');

  // Para 'Cc'
  ccRecipients: Recipient[] = [];
  ccCtrl = new FormControl('');

  // Para 'Bcc'
  bccRecipients: Recipient[] = [];
  bccCtrl = new FormControl('');

  // Simulación de carga de destinatarios (en un caso real, vendrían de una API)
  mockRecipientsData: Recipient[] = [
    { email: 'user1@example.com', data: { DNI: '12345678A', NOMBRE: 'Juan' } },
    { email: 'user2@example.com', data: { DNI: '87654321B', NOMBRE: 'Maria' } },
    { email: 'user3@example.com', data: { DNI: '11223344C', NOMBRE: 'Pedro' } }
  ];

  constructor(private emailService: EmailService) {
    this.emailForm = new FormGroup({
      subject: new FormControl('', Validators.required),
      bodyTemplate: new FormControl('', Validators.required),
      sendIndividual: new FormControl(false),
    });
  }

  addRecipient(event: MatChipInputEvent, type: 'to' | 'cc' | 'bcc'): void {
    const value = (event.value || '').trim();

    if (value) {
      const newRecipient: Recipient = { email: value, data: {} }; // Puedes pedir los datos en un modal o simularlos
      if (type === 'to') {
        this.toRecipients.push(newRecipient);
      } else if (type === 'cc') {
        this.ccRecipients.push(newRecipient);
      } else if (type === 'bcc') {
        this.bccRecipients.push(newRecipient);
      }
    }

    // Clear the input value
    event.chipInput!.clear();

    if (type === 'to') {
      this.toCtrl.setValue(null);
    } else if (type === 'cc') {
      this.ccCtrl.setValue(null);
    } else if (type === 'bcc') {
      this.bccCtrl.setValue(null);
    }
  }

  removeRecipient(recipient: Recipient, type: 'to' | 'cc' | 'bcc'): void {
    let index: number;
    if (type === 'to') {
      index = this.toRecipients.indexOf(recipient);
      if (index >= 0) {
        this.toRecipients.splice(index, 1);
      }
    } else if (type === 'cc') {
      index = this.ccRecipients.indexOf(recipient);
      if (index >= 0) {
        this.ccRecipients.splice(index, 1);
      }
    } else if (type === 'bcc') {
      index = this.bccRecipients.indexOf(recipient);
      if (index >= 0) {
        this.bccRecipients.splice(index, 1);
      }
    }
  }

  // Cargar destinatarios de un botón (simulado)
  loadToRecipients(): void {
    this.toRecipients = [...this.mockRecipientsData];
  }

  loadCcRecipients(): void {
    this.ccRecipients = [...this.mockRecipientsData];
  }

  loadBccRecipients(): void {
    this.bccRecipients = [...this.mockRecipientsData];
  }

  sendEmail(): void {
    if (this.emailForm.invalid || this.toRecipients.length === 0) {
      alert('Por favor, completa todos los campos requeridos y añade al menos un destinatario.');
      return;
    }

    const { subject, bodyTemplate, sendIndividual } = this.emailForm.value;

    const emailRequest = {
      to: this.toRecipients,
      cc: this.ccRecipients.length > 0 ? this.ccRecipients : undefined,
      bcc: this.bccRecipients.length > 0 ? this.bccRecipients : undefined,
      subject,
      bodyTemplate,
      sendIndividual,
    };

    this.emailService.sendEmails(emailRequest).subscribe({
      next: (response) => {
        alert(response);
        this.resetForm();
      },
      error: (error) => {
        console.error('Error al enviar el correo:', error);
        alert('Error al enviar el correo. Revisa la consola para más detalles.');
      }
    });
  }

  resetForm(): void {
    this.emailForm.reset({ sendIndividual: false });
    this.toRecipients = [];
    this.ccRecipients = [];
    this.bccRecipients = [];
    this.toCtrl.setValue(null);
    this.ccCtrl.setValue(null);
    this.bccCtrl.setValue(null);
  }
}
