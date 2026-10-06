/**
 * Estructura de la respuesta de éxito que devuelve el backend en los
 * endpoints POST /guardar, POST /actualizar y POST /eliminar.
 */
export interface MiRespuestaRs {
    status: number;
    message: string;
}

/**
 * Estructura de la respuesta de error que devuelve el backend
 * a través del GlobalExceptionHandler.
 */
export interface ErrorResponse {
    status: number;
    error: string;
    message: string;
}