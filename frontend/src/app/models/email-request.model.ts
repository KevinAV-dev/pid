import { Recipient } from './recipient.model';

export interface EmailRequest {
  to: Recipient[];
  cc?: Recipient[];
  bcc?: Recipient[];
  subject: string;
  bodyTemplate: string;
  sendIndividual: boolean;
}
