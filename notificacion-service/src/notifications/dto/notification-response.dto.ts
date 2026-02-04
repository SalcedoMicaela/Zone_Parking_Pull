import { ApiProperty } from "@nestjs/swagger";

export class NotificationResponseDto {


    @ApiProperty()
    id: string;

    @ApiProperty()
    action: string;

    @ApiProperty()
    microservice: string;

    @ApiProperty()
    entityId: string;

    @ApiProperty()
    message: string;

    @ApiProperty()
    createdAt: Date;

    @ApiProperty()
    eventTimestamp: Date;

    @ApiProperty()
    data?: Record<string, any>;

    @ApiProperty()
    severity: string;

    @ApiProperty()
    read: boolean;

    @ApiProperty()
    processed: boolean;
    


}