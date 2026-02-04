import { Injectable, Logger, OnModuleDestroy, OnModuleInit } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import * as amqp from 'amqplib';
import { NotificationService } from '../notifications/service/notification.service';

@Injectable()
export class RabbitMQService implements OnModuleInit, OnModuleDestroy {
  private readonly logger = new Logger(RabbitMQService.name);
  private connection: any;
  private channel: any;

  private readonly exchangeName = 'Notificaciones.Exchange';
  private readonly queueName = 'Notificaciones.Queue';
  private readonly routingKey = 'notificaciones';

  constructor(
    private readonly configService: ConfigService,
    private readonly notificationService: NotificationService,
  ) {}

  async onModuleInit() {
    this.logger.log(' RabbitMQService inicializado');
    await this.connect();
  }

  async onModuleDestroy() {
    await this.closeConnection();
  }

  private async connect(): Promise<void> {
    try {
      const host = this.configService.get<string>('RABBITMQ_HOST', 'localhost');
      const port = this.configService.get<number>('RABBITMQ_PORT',  5672);
      const username = this.configService.get<string>('RABBITMQ_USERNAME', 'guest');
      const password = this.configService.get<string>('RABBITMQ_PASSWORD', 'guest');

      const connectionString = `amqp://${username}:${password}@${host}:${port}`;
      this.logger.log(` Conectando a RabbitMQ: ${connectionString}`);

      this.connection = await amqp.connect(connectionString);
      this.channel = await this.connection.createChannel();

      await this.channel.assertExchange(this.exchangeName, 'direct', { durable: true });
      await this.channel.assertQueue(this.queueName, { durable: true });
      await this.channel.bindQueue(this.queueName, this.exchangeName, this.routingKey);

      this.logger.log(` Exchange activo: ${this.exchangeName}`);
      this.logger.log(` Queue activa: ${this.queueName}`);

      await this.channel.consume(
        this.queueName,
        async (msg: amqp.ConsumeMessage | null) => {
          if (!msg) return;

          try {
            const payload = JSON.parse(msg.content.toString());

            this.logger.log(` Mensaje recibido: ${JSON.stringify(payload)}`);

            /**
             * Normalización mínima para asegurar compatibilidad
             */
            const eventTimestamp = this.parseTimestamp(
  payload.timestamp ??
  payload.timeStamp ??
  payload.eventTimeStamp
);

await this.notificationService.createFromEvent({
  eventId: payload.eventId || payload.id || payload.uuid || crypto.randomUUID(),
  microservice: payload.microservice,
  entity: payload.entity || payload.entityType,
  entityId: payload.entityId,
  message: payload.message,
  eventTimeStamp: eventTimestamp, 
  data: payload.data,
  severity: payload.severity || 'INFO',
});


            this.channel.ack(msg);
            this.logger.log(' Mensaje procesado correctamente');
          } catch (error) {
            this.logger.error(' Error procesando mensaje', error);
            this.channel.nack(msg, false, false);
          }
        },
      );

      this.logger.log(' Escuchando mensajes en RabbitMQ...');
    } catch (error) {
      this.logger.error(` Error conectando a RabbitMQ: ${error.message}`);
      this.logger.warn(' Reintentando conexión en 5 segundos...');
      setTimeout(() => this.connect(), 5000);
    }
  }

  /**
   * Publicador compatible con el mismo exchange
   */
  async publishNotification(notification: any): Promise<void> {
    try {
      const message = JSON.stringify(notification);
      this.channel.publish(
        this.exchangeName,
        this.routingKey,
        Buffer.from(message),
        { persistent: true },
      );
      this.logger.log(` Notificación publicada`);
    } catch (error) {
      this.logger.error(' Error publicando notificación', error);
    }
  }

  private async closeConnection(): Promise<void> {
    try {
      if (this.channel) await this.channel.close();
      if (this.connection) await this.connection.close();
      this.logger.log(' Conexión a RabbitMQ cerrada');
    } catch (error) {
      this.logger.error(' Error cerrando conexión RabbitMQ', error);
    }
  }
  private parseTimestamp(value?: string): Date {
  if (!value) {
    return new Date();
  }

  const parsed = new Date(value);

  if (isNaN(parsed.getTime())) {
    this.logger.warn(
      `Timestamp inválido recibido (${value}), usando fecha actual`,
    );
    return new Date();
  }

  return parsed;
}

}
