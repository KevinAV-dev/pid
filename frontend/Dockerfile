# Etapa 1: Construir la aplicación Angular
# Usamos una imagen de Node.js como base para compilar el proyecto.
FROM node:20-alpine AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /usr/src/app

# Copiar package.json y package-lock.json para instalar las dependencias
# Esto aprovecha el caché de Docker si las dependencias no han cambiado.
COPY package.json package-lock.json ./

# Instalar las dependencias del proyecto
RUN npm install

# Copiar el resto del código fuente al contenedor
COPY . .

# Compilar la aplicación Angular en modo de producción
# El comando 'ng build' generará los archivos estáticos en la carpeta 'dist'.
RUN npm run build --prod

# Etapa 2: Servir la aplicación con Nginx
# Usamos una imagen ligera de Nginx para servir los archivos estáticos.
FROM nginx:alpine

# Copiar los archivos de la aplicación compilada desde la etapa "build"
# al directorio por defecto de Nginx para servir páginas web.
COPY --from=build /usr/src/app/dist /usr/share/nginx/html

# Exponer el puerto 80 para que el contenedor pueda recibir tráfico HTTP
EXPOSE 80

# Comando para iniciar Nginx en primer plano
CMD ["nginx", "-g", "daemon off;"]
