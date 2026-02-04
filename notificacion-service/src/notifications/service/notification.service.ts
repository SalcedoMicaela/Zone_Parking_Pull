import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";
import { CreateNotificationDto } from "../dto/create-notification.dto";
import { Notification } from "../entities/notification.entity";

@Injectable()
export class NotificationService {

  constructor(
    @InjectRepository(Notification)
    private readonly notificationRepository: Repository<Notification>,
  ) {}

  async create(
    createNotificationDto: CreateNotificationDto,
  ): Promise<Notification> {

    const notification = this.notificationRepository.create({
      ...createNotificationDto,
      eventId: String(createNotificationDto.eventId),
    });

    return await this.notificationRepository.save(notification);
  }

  async findAll(): Promise<Notification[]> {
    return await this.notificationRepository.find();
  }

  async createFromEvent(event: any): Promise<Notification> {
  const notification = this.notificationRepository.create({
    eventId: event.eventId ?? event.id ?? null,
    microservice: event.microservice,
    entity: event.entity ?? event.entityType ?? null,
    entityId: event.entityId,
    message: event.message,
    severity: event.severity ?? 'INFO',
    read: false,
    processed: true,
    data: event.data ?? {},
    eventTimestamp: event.eventTimeStamp ?? new Date(), 
    createdAt: new Date(),
  });

  return await this.notificationRepository.save(notification);
}

}
