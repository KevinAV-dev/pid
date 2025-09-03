import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, NgIf, NgFor, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { NotificationService } from './notification.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-formulario',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule, NgIf, NgFor, NgClass],
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.css']
})
export class FormularioComponent implements OnInit, OnDestroy {

  formularioInscripcion: FormGroup;
  isModalOpen = false;
  institutions: any[] = [];
  filteredInstitutions: any[] = [];
  searchQuery: string = '';

  notification: { message: string, type: 'success' | 'error' | null } | null = null;
  private notificationSubscription: Subscription | undefined;

  // Define la URL base de tu backend con el puerto 8080
  private apiUrl = 'http://154.12.224.158:8080/api/formulario';

  constructor(private fb: FormBuilder, private http: HttpClient, private notificationService: NotificationService) {
    this.formularioInscripcion = this.fb.group({
      personaId: [null],
      institucionEducativaId: [null],
      directorId: [null],
      correoId: [null],
      telefonoId: [null],
      correoDirectorId: [null],
      telefonoDirectorId: [null],

      dni: ['', [Validators.required, Validators.pattern('[0-9]{8}')]],
      nombres: ['', Validators.required],
      apellidos: ['', Validators.required],
      mentor: [''],
      correo: ['', [Validators.required, Validators.email]],
      celular: ['', [Validators.required, Validators.pattern('[0-9]{9}')]],
      codigoModular: ['', Validators.required],
      nombreInstitucion: ['', Validators.required],
      nombresDirector: ['', Validators.required],
      apellidosDirector: ['', Validators.required],
      telefonoDirector: ['', [Validators.required, Validators.pattern('[0-9]{9}')]],
      emailDirector: ['', [Validators.required, Validators.email]],
      observaciones: ['']
    });

    // Deshabilita los campos que no se deben editar después de la inicialización
    this.formularioInscripcion.get('nombres')?.disable();
    this.formularioInscripcion.get('apellidos')?.disable();
    this.formularioInscripcion.get('mentor')?.disable();
    this.formularioInscripcion.get('codigoModular')?.disable();
    this.formularioInscripcion.get('nombreInstitucion')?.disable();
    this.formularioInscripcion.get('nombresDirector')?.disable();
    this.formularioInscripcion.get('apellidosDirector')?.disable();
    this.formularioInscripcion.get('telefonoDirector')?.disable();
    this.formularioInscripcion.get('emailDirector')?.disable();
  }

  ngOnInit(): void {
    this.notificationSubscription = this.notificationService.notification$.subscribe(notif => {
      this.notification = notif.message ? notif : null;
    });
    this.loadInstitutions();
  }

  ngOnDestroy(): void {
    if (this.notificationSubscription) {
      this.notificationSubscription.unsubscribe();
    }
  }

  loadInstitutions(): void {
    const url = `${this.apiUrl}/instituciones`;
    this.http.get<any[]>(url).subscribe({
      next: (response) => {
        // Corregido: Mapea la respuesta para añadir la propiedad del director
        this.institutions = response.map(inst => ({
          ...inst,
          directorDisplay: inst.director
            ? `${inst.director.persona.personaNombres} ${inst.director.persona.personaApellidos}`
            : 'Sin asignar'
        }));
        this.filteredInstitutions = [...this.institutions];
      },
      error: (error) => {
        console.error('Error al cargar las instituciones:', error);
        this.notificationService.showError('No se pudieron cargar las instituciones.');
      }
    });
  }

  buscarDni(): void {
    const dniControl = this.formularioInscripcion.get('dni');
    if (dniControl?.valid) {
      const dni = dniControl.value;
      const url = `${this.apiUrl}/info/${dni}`;

      this.http.get<any>(url).subscribe({
        next: (response) => {
          if (response && response.persona) {
            this.notificationService.showSuccess('¡Datos de DNI cargados con éxito! 🎉');

            this.formularioInscripcion.patchValue({
              personaId: response.persona?.personaId,
              institucionEducativaId: response.institucionEducativa?.institucionEducativaId,
              directorId: response.institucionEducativa?.director?.persona?.personaId,

              correoId: response.persona?.correos?.[0]?.correoId,
              telefonoId: response.persona?.telefonos?.[0]?.telefonoId,
              correoDirectorId: response.institucionEducativa?.director?.persona?.correos?.[0]?.correoId,
              telefonoDirectorId: response.institucionEducativa?.director?.persona?.telefonos?.[0]?.telefonoId,

              nombres: response.persona?.personaNombres,
              apellidos: response.persona?.personaApellidos,
              mentor: response.persona?.mentor || 'No aplica',
              correo: response.persona?.correos?.[0]?.correoDescripcion,
              celular: response.persona?.telefonos?.[0]?.telefonoDescripcion,
              codigoModular: response.institucionEducativa?.institucionEducativaCodigoModular,
              nombreInstitucion: response.institucionEducativa?.institucionEducativaNombre,
              nombresDirector: response.institucionEducativa?.director?.persona?.personaNombres,
              apellidosDirector: response.institucionEducativa?.director?.persona?.personaApellidos,
              emailDirector: response.institucionEducativa?.director?.persona?.correos?.[0]?.correoDescripcion,
              telefonoDirector: response.institucionEducativa?.director?.persona?.telefonos?.[0]?.telefonoDescripcion,
              observaciones: response.verificacion?.verificacionObservaciones
            });

            // Habilita solo los campos que el usuario puede editar
            this.formularioInscripcion.get('correo')?.enable();
            this.formularioInscripcion.get('celular')?.enable();
            this.formularioInscripcion.get('nombresDirector')?.enable();
            this.formularioInscripcion.get('apellidosDirector')?.enable();
            this.formularioInscripcion.get('telefonoDirector')?.enable();
            this.formularioInscripcion.get('emailDirector')?.enable();
            this.formularioInscripcion.get('observaciones')?.enable();

          } else {
            this.notificationService.showError('DNI no encontrado.');
            this.formularioInscripcion.reset({ dni: dni });
            dniControl?.enable();
          }
        },
        error: (error) => {
          this.notificationService.showError('Error de conexión. Intente de nuevo.');
          this.formularioInscripcion.reset({ dni: dni });
          dniControl?.enable();
        }
      });
    } else {
      this.notificationService.showError('Por favor, ingrese un DNI válido de 8 dígitos.');
      dniControl?.markAsTouched();
    }
  }

  onSubmit(): void {
    if (this.formularioInscripcion.valid) {
      const formValue = this.formularioInscripcion.value;

      const datosAEnviar = {
        novelId: formValue.personaId,
        correoId: formValue.correoId,
        correo: formValue.correo,
        telefonoId: formValue.telefonoId,
        telefono: formValue.celular,
        institucionId: formValue.institucionEducativaId,
        directorId: formValue.directorId,
        nombresDirector: formValue.nombresDirector,
        apellidosDirector: formValue.apellidosDirector,
        correoDirectorId: formValue.correoDirectorId,
        correoDirector: formValue.emailDirector,
        telefonoDirectorId: formValue.telefonoDirectorId,
        telefonoDirector: formValue.telefonoDirector,
        observaciones: formValue.observaciones
      };

      const url = `${this.apiUrl}/update`;

      this.http.put(url, datosAEnviar).subscribe({
        next: (response) => {
          console.log('Datos actualizados con éxito:', response);
          this.notificationService.showSuccess('¡Formulario actualizado con éxito! 🎉');
        },
        error: (error) => {
          console.error('Error al actualizar los datos:', error);
          this.notificationService.showError(error.error.mensaje);
        }
      });
    } else {
      this.notificationService.showError('Por favor, complete todos los campos obligatorios.');
      this.formularioInscripcion.markAllAsTouched();
    }
  }

  openModal(): void {
    this.isModalOpen = true;
    this.searchQuery = '';
    this.filteredInstitutions = [...this.institutions];
  }

  closeModal(): void {
    this.isModalOpen = false;
  }

  searchInstitutions(): void {
    const query = this.searchQuery.toLowerCase();
    this.filteredInstitutions = this.institutions.filter(inst =>
      inst.institucionEducativaNombre.toLowerCase().includes(query) || inst.institucionEducativaCodigoModular.includes(query)
    );
  }

  selectInstitution(inst: any): void {
    this.formularioInscripcion.patchValue({
      institucionEducativaId: inst.institucionEducativaId,
      directorId: inst.director?.directorId,
      codigoModular: inst.institucionEducativaCodigoModular,
      nombreInstitucion: inst.institucionEducativaNombre,
    });

    if (inst.director) {
      this.formularioInscripcion.patchValue({
        nombresDirector: inst.director.persona?.personaNombres,
        apellidosDirector: inst.director.persona?.personaApellidos,
        telefonoDirector: inst.director.persona?.telefonos?.[0]?.telefonoDescripcion,
        emailDirector: inst.director.persona?.correos?.[0]?.correoDescripcion,

        correoDirectorId: inst.director.persona?.correos?.[0]?.correoId,
        telefonoDirectorId: inst.director.persona?.telefonos?.[0]?.telefonoId,
      });
      this.notificationService.showSuccess('Datos de la institución y director cargados.');
    } else {
      this.formularioInscripcion.patchValue({
        nombresDirector: '',
        apellidosDirector: '',
        telefonoDirector: '',
        emailDirector: '',
        correoDirectorId: null,
        telefonoDirectorId: null,
      });
      this.notificationService.showError('La institución seleccionada no tiene un director asignado.');
    }

    this.closeModal();
  }
}
