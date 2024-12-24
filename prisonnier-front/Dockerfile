# Stage 1: Build the Angular application
FROM node:20 as build-stage
WORKDIR /app

# Copy the Angular project files
COPY . .

# Install dependencies and build the Angular application
RUN npm install && npm run build

# Stage 2: Serve the Angular app with Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy the build output from the previous stage
COPY --from=build-stage /app/dist/persionner-front/browser .

# Copy the custom Nginx configuration
COPY nginx/nginx.conf /etc/nginx/nginx.conf

# Expose the default HTTP port
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
