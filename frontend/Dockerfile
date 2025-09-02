# --- Etapa 1: Construcción de la aplicación de Angular ---
# Utiliza una imagen de Node.js para compilar tu aplicación.
FROM node:20-alpine AS build

# Establece el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copia los archivos de configuración de dependencias para instalarlas.
COPY package*.json ./

# Instala las dependencias del proyecto.
RUN npm install

# Copia el resto del código fuente de Angular.
COPY . .

# Compila la aplicación para producción.
RUN npm run build -- --output-path=./dist/app --base-href=/

# --- Etapa 2: Servir la aplicación con Nginx ---
# Utiliza una imagen ligera de Nginx para servir los archivos estáticos.
FROM nginx:stable-alpine

# Copia tu archivo de configuración de Nginx personalizado.
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# Copia los archivos de build de la etapa anterior al directorio de Nginx.
COPY --from=build /app/dist/app /usr/share/nginx/html

# Expone el puerto 80, el puerto estándar para tráfico HTTP.
EXPOSE 80

# Comando para iniciar Nginx.
CMD ["nginx", "-g", "daemon off;"]
