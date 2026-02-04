import {
  Column,
  Entity,
  Index,
  PrimaryGeneratedColumn,
  CreateDateColumn,
} from 'typeorm';

@Entity('notifications')
export class Notification {

  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column({ name: 'event_id', type: 'uuid' })
  @Index()
  eventId: string;

  @Column({ name: 'microservice', type: 'varchar', length: 100 })
  microservice: string;

  @Column({ name: 'entity', type: 'varchar', length: 100 })
  entity: string;

  @Column({ name: 'entity_id', type: 'varchar', length: 100 })
  @Index()
  entityId: string;

  @Column({ type: 'text' })
  message: string;

  @Column({ type: 'varchar', length: 10, default: 'INFO' })
  severity: string;

  @Column({ type: 'boolean', default: false })
  read: boolean;

  @Column({ type: 'boolean', default: false })
  processed: boolean;

  @Column({ type: 'jsonb', nullable: true })
  data?: Record<string, any>;

  @Column({ name: 'event_timestamp', type: 'timestamp' })
  eventTimestamp: Date;

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date;
}
