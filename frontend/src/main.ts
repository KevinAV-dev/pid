import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http'; // Para HttpClient
import { provideAnimations } from '@angular/platform-browser/animations'; // Para Angular Material

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptorsFromDi()), // Configura HttpClient
    provideAnimations() // Configura las animaciones de Angular Material
    // Aquí puedes añadir otros servicios o configuraciones globales si los tuvieras
  ]
}).catch(err => console.error(err));
