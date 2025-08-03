#!/bin/bash

echo "🔧 Fixing Habit Tracker Application Issues..."

# Stop all containers
echo "🛑 Stopping all containers..."
docker compose down

# Remove containers and volumes to start fresh
echo "🧹 Cleaning up containers and volumes..."
docker compose down -v
docker system prune -f

# Rebuild images to incorporate fixes
echo "🏗️ Rebuilding images..."
docker compose build --no-cache

# Start services in the correct order
echo "🚀 Starting services..."

# Start MongoDB first
echo "📊 Starting MongoDB..."
docker compose up -d mongodb

# Wait for MongoDB to be ready
echo "⏳ Waiting for MongoDB to be ready..."
sleep 30

# Check MongoDB health
echo "🔍 Checking MongoDB status..."
docker compose logs mongodb | tail -10

# Start Mongo Express
echo "🌐 Starting Mongo Express..."
docker-compose up -d mongo-express

# Wait a bit
sleep 10

# Start Backend
echo "🏃 Starting Backend..."
docker compose up -d backend

# Wait for backend to be ready
echo "⏳ Waiting for backend to be ready..."
sleep 30

# Check backend logs
echo "🔍 Checking backend status..."
docker compose logs backend | tail -10

# Start Frontend
echo "🎨 Starting Frontend..."
docker compose up -d frontend

# Wait for frontend
sleep 10

# Final status check
echo "📋 Final status check..."
docker compose ps

echo ""
echo "✅ Setup complete! Services should be running on:"
echo "🌍 Frontend: http://localhost:3000"
echo "🔧 Backend: http://localhost:8080"
echo "📊 Mongo Express: http://localhost:8081"
echo "🗄️ MongoDB: mongodb://admin:password123@localhost:27017"
echo ""
echo "📋 To check logs:"
echo "docker compose logs -f [service-name]"
echo ""
echo "🔄 To restart everything:"
echo "docker compose restart"
