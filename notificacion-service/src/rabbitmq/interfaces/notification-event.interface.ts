export interface NotificationEvent {
   id: string;
   microservice: string;
   action: string;
   entityId: string;
   message: string;
   timestamp: string;
   data?: Record<string, any>;
   severity?: string;
}
