package com.concierge.chat.service;

import org.springframework.stereotype.Service;

@Service
public class AIResponseService {

    /**
     * Genera una respuesta basada en la entrada del usuario.
     * En una implementación real, esto llamaría a una API de IA como OpenAI, Google Vertex AI, etc.
     * Por ahora, retorna respuestas genéricas basadas en palabras clave.
     */
    public String generateResponse(String userMessage) {
        String lowerMessage = userMessage.toLowerCase();

        if (lowerMessage.contains("restaurante") || lowerMessage.contains("comer")) {
            return "Tenemos excelentes opciones de restaurantes en el hotel. ¿Qué tipo de cocina prefieres?";
        } else if (lowerMessage.contains("actividad") || lowerMessage.contains("hacer")) {
            return "Hay muchas actividades disponibles en la zona. ¿Te interesa algo cultural, deportivo o de entretenimiento?";
        } else if (lowerMessage.contains("transporte") || lowerMessage.contains("taxi")) {
            return "Podemos ayudarte a organizar transporte. ¿A dónde necesitas ir?";
        } else if (lowerMessage.contains("reserva") || lowerMessage.contains("reservar")) {
            return "Puedo ayudarte con reservaciones. ¿Qué deseas reservar?";
        } else if (lowerMessage.contains("hola") || lowerMessage.contains("hi")) {
            return "¡Hola! Bienvenido al servicio de concierge virtual. ¿Cómo puedo ayudarte hoy?";
        } else if (lowerMessage.contains("ayuda") || lowerMessage.contains("help")) {
            return "Estoy aquí para ayudarte con: Restaurantes, Actividades, Transporte, Reservaciones y más. ¿Qué necesitas?";
        } else {
            return "Gracias por tu mensaje. Puedo ayudarte con restaurantes, actividades, transporte y reservaciones. ¿En qué puedo asistirte?";
        }
    }
}
