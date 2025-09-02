import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Necesario si usas directivas básicas aquí
import { EmailSenderComponent } from './components/email-sender/email-sender.component'; // Importar el componente hijo
import {FormularioComponent} from './components/formulario/formulario.component'; // Importar el componente hijo

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
  // *** CAMBIO CLAVE PARA STANDALONE COMPONENT ***
  standalone: true,
  imports: [
    CommonModule,
    FormularioComponent,
    // Importar el componente que vamos a usar directamente
  ]
})
export class AppComponent {
  title = 'email-app-frontend';
}
