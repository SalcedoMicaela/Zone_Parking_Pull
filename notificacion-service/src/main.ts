import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { Logger, ValidationPipe } from '@nestjs/common';

async function bootstrap() {
  const logger = new Logger('Bootstrap');
  const app = await NestFactory.create(AppModule);
  
  app.useGlobalPipes(new ValidationPipe());
  app.enableCors();
  
  const port = process.env.PORT || 3001;
  await app.listen(port);
  
  logger.log(` Aplicación corriendo en puerto ${port}`);
  logger.log(` http://localhost:${port}`);
}
bootstrap();
