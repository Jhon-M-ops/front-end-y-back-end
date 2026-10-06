import { HttpErrorResponse } from '@angular/common/http';
import { ErrorResponse } from 'src/app/models/respuesta';

/**
 * Extrae el mensaje de error enviado por el backend.
 *
 * @param error Error producido por la llamada HTTP.
 * @returns El mensaje de negocio del backend o un texto genérico.
 */
export function extraerMensajeError(error: unknown): string {
    if (error instanceof HttpErrorResponse) {
        const cuerpo: unknown = error.error;

        if (cuerpo && typeof cuerpo === 'object') {
            const mensaje = (cuerpo as Partial<ErrorResponse>).message;
            if (mensaje) {
                return mensaje;
            }
        }

        if (typeof cuerpo === 'string' && cuerpo.trim().length > 0) {
            return cuerpo;
        }

        if (error.status === 0) {
            return 'No hay conexión con el servidor de la clínica.';
        }

        if (error.message) {
            return error.message;
        }
    }

    if (error instanceof Error && error.message) {
        return error.message;
    }

    return 'Ocurrió un error inesperado.';
}