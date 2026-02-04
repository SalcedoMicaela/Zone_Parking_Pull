import { ApiOperation, ApiResponse, ApiTags } from "@nestjs/swagger";
import { Controller, Get } from "@nestjs/common";
import { NotificationService } from "../service/notification.service";
import { NotificationResponseDto } from "../dto/notification-response.dto";

@ApiTags('notifications')
@Controller('notifications')

export class NotificationController {

    constructor(private readonly notificationService: NotificationService) {}

    @Get()
    @ApiOperation({
        summary: 'Obtener todas las notificaciones',
        description: 'Recupera una lista de todas las notificaciones almacenadas en el sistema.'
    })

    @ApiResponse({
        status: 200,

        description: 'Lista todas las notificaciones exitosamente.',
        type: [NotificationResponseDto],
    })


    async findAll() {
        return await this.notificationService.findAll();
    }

}