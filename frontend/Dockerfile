# Build stage
FROM node:18-alpine as build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies (remove --only=production for dev dependencies needed by Vite)
RUN npm ci

# Copy source code
COPY . .

# Build the app (Vite builds to 'dist' folder, not 'build')
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy built app to nginx (Vite uses 'dist' folder)
COPY --from=build /app/dist /usr/share/nginx/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]